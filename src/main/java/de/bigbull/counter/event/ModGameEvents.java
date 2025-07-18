package de.bigbull.counter.event;

import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.network.packet.DayCounterPacket;
import de.bigbull.counter.network.packet.DeathCounterPacket;
import de.bigbull.counter.network.PacketDistributor;
import de.bigbull.counter.network.packet.SurvivalTimePacket;
import de.bigbull.counter.util.CounterCommands;
import de.bigbull.counter.util.CounterManager;
import de.bigbull.counter.util.saveddata.DayCounterData;
import de.bigbull.counter.util.saveddata.DeathCounterData;
import de.bigbull.counter.util.saveddata.SurvivalTimeData;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ModGameEvents {
    public static void registerEvents() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            CounterCommands.register(dispatcher);
        }));

        ServerTickEvents.END_SERVER_TICK.register((MinecraftServer server) -> {
            if (!ServerConfig.ENABLE_DAY_COUNTER.get()) {
                return;
            }

            ServerLevel level = server.overworld();
            long realDay = level.getDayTime() / 24000;

            DayCounterData data = DayCounterData.get(level);
            long lastRealDay = data.getLastRealDay();

            if (realDay > lastRealDay) {
                long delta = realDay - lastRealDay;
                long newCounterValue = data.getDayCounter() + delta;
                data.setDayCounter(newCounterValue);
                data.setLastRealDay(realDay);

                if (ServerConfig.ENABLE_DAY_MESSAGE.get()) {
                    server.getPlayerList().broadcastSystemMessage(
                            Component.translatable("chat.daycounter.new_day", newCounterValue), false);
                }

                PacketDistributor.sendToAllPlayers(new DayCounterPacket(newCounterValue), server);
            } else if (realDay < lastRealDay) {
                data.setLastRealDay(realDay);
            }
        });

        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            if (handler.player instanceof ServerPlayer player) {
                ServerLevel level = player.serverLevel();
                DeathCounterData data = DeathCounterData.get(level);
                data.updatePlayerName(player.getUUID(), player.getScoreboardName());
                SurvivalTimeData surv = SurvivalTimeData.get(level);

                if (surv.getLastDeathTick(player.getUUID()) == 0L) {
                    surv.setLastDeathTick(player.getUUID(), level.getGameTime());
                }

                PacketDistributor.sendToPlayer(player, new DeathCounterPacket(
                        data.getDeathCountMap(),
                        data.getPlayerNames(),
                        surv.getBestTimesMap()));
                PacketDistributor.sendToPlayer(player, new DayCounterPacket(DayCounterData.getCurrentDay(level)));
                PacketDistributor.sendToPlayer(player, new SurvivalTimePacket(surv.getLastDeathTick(player.getUUID()), surv.getBestTime(player.getUUID())));

                if (ServerConfig.SHOW_SURVIVAL_IN_CHAT_MODE.get() == ServerConfig.SurvivalInChatMode.ON_JOIN ||
                        ServerConfig.SHOW_SURVIVAL_IN_CHAT_MODE.get() == ServerConfig.SurvivalInChatMode.BOTH) {
                    long duration = level.getGameTime() - surv.getLastDeathTick(player.getUUID());
                    sendSurvivalCounterMessage(player, level, surv, duration);
                }

                if ((ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.ON_JOIN ||
                        ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.BOTH)) {
                    sendDeathCounterMessage(player, level, data, false);
                }

                if (ServerConfig.SHOW_DAY_IN_CHAT.get()) {
                    sendDayCounterToChat(level, player);
                }
            }
        }));

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, world) -> {
            if (entity instanceof ServerPlayer player) {
                ServerLevel level = player.serverLevel();
                DeathCounterData data = DeathCounterData.get(level);
                data.addDeath(player.getUUID());

                SurvivalTimeData surv = SurvivalTimeData.get(level);

                if (ServerConfig.ENABLE_SURVIVAL_COUNTER.get()) {
                    long now = level.getGameTime();
                    long last = surv.getLastDeathTick(player.getUUID());
                    long duration = now - last;
                    surv.recordSurvival(player.getUUID(), duration, player.getScoreboardName());
                    surv.setLastDeathTick(player.getUUID(), now);

                    if (ServerConfig.SHOW_SURVIVAL_IN_CHAT_MODE.get() == ServerConfig.SurvivalInChatMode.ON_DEATH ||
                            ServerConfig.SHOW_SURVIVAL_IN_CHAT_MODE.get() == ServerConfig.SurvivalInChatMode.BOTH) {
                        sendSurvivalCounterMessage(player, level, surv, duration);
                    }

                    PacketDistributor.sendToPlayer(player, new SurvivalTimePacket(now, surv.getBestTime(player.getUUID())));
                }

                PacketDistributor.sendToAllPlayers(new DeathCounterPacket(
                        data.getDeathCountMap(),
                        data.getPlayerNames(),
                        surv.getBestTimesMap()), level.getServer());

                if ((ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.ON_DEATH ||
                        ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.BOTH)) {
                    sendDeathCounterMessage(player, level, data, true);
                }
            }
        });
    }

    private static void sendDeathCounterMessage(ServerPlayer affectedPlayer, ServerLevel level, DeathCounterData data, boolean isDeathEvent) {
        if (!ServerConfig.ENABLE_DEATH_COUNTER.get() || !ServerConfig.ENABLE_DEATH_IN_CHAT.get()) {
            return;
        }

        boolean sendToAll = isDeathEvent && ServerConfig.SHOW_DEATH_LIST_ON_DEATH_GLOBAL.get();

        if (ServerConfig.DEATH_CHAT_MODE_TYPE.get() == ServerConfig.DeathChatMode.LIST) {
            sendDeathListToPlayer(affectedPlayer, level, data, sendToAll);
        } else {
            sendDeathSelfToPlayer(affectedPlayer, level, data, sendToAll);
        }
    }

    private static void sendDeathSelfToPlayer(ServerPlayer affectedPlayer, ServerLevel level, DeathCounterData data, boolean sendToAll) {
        MinecraftServer server = level.getServer();
        int textColor = ServerConfig.DEATH_SELF_CHATTEXT_COLOR.get();
        int playerDeaths = data.getDeathCountMap().getOrDefault(affectedPlayer.getUUID(), 0);

        Component personalMessage = Component.translatable("overlay.counter.deaths_with_emoji", playerDeaths)
                .withStyle(style -> style.withColor(textColor));

        if (sendToAll) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if (!player.getUUID().equals(affectedPlayer.getUUID())) {
                    Component broadcastMessage = playerDeaths == 1
                            ? Component.translatable(
                            "chat.deathcounter.player_death.singular",
                            affectedPlayer.getScoreboardName())
                            : Component.translatable(
                            "chat.deathcounter.player_death.plural",
                            affectedPlayer.getScoreboardName(),
                            playerDeaths);
                    player.sendSystemMessage(broadcastMessage);
                }
            }
        }

        affectedPlayer.sendSystemMessage(personalMessage);
    }

    private static void sendDeathListToPlayer(ServerPlayer player, ServerLevel level, DeathCounterData data, boolean sendToAll) {
        int textColor = ServerConfig.DEATH_LIST_CHATTEXT_COLOR.get();
        List<Map.Entry<UUID, Integer>> sortedDeaths = data.getDeathCountMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ServerConfig.MAX_PLAYERS_SHOWN.get())
                .toList();

        if (sortedDeaths.isEmpty()) return;

        Component header = Component.translatable("overlay.counter.deathlist").withStyle(style -> style.withColor(textColor));

        AtomicInteger counter = new AtomicInteger(0);

        List<MutableComponent> deathEntries = sortedDeaths.stream().map(entry -> {
            String playerName = data.getPlayerNames().getOrDefault(entry.getKey(), "Unknown");
            int deaths = entry.getValue();

            Component positionComponent = Component.literal(counter.incrementAndGet() + ".")
                    .setStyle(Style.EMPTY.withColor(0xFFFFFF));
            return CounterManager.createDeathEntry(positionComponent, playerName, deaths);
        }).toList();

        player.sendSystemMessage(header);
        deathEntries.forEach(player::sendSystemMessage);

        if (sendToAll) {
            for (ServerPlayer onlinePlayer : level.getServer().getPlayerList().getPlayers()) {
                if (onlinePlayer != player) {
                    onlinePlayer.sendSystemMessage(header);
                    deathEntries.forEach(onlinePlayer::sendSystemMessage);
                }
            }
        }
    }

    private static void sendSurvivalCounterMessage(ServerPlayer player, ServerLevel level, SurvivalTimeData surv, long duration) {
        if (!ServerConfig.ENABLE_SURVIVAL_COUNTER.get() || !ServerConfig.ENABLE_SURVIVAL_IN_CHAT.get()) {
            return;
        }

        boolean withBest = ServerConfig.SHOW_BEST_SURVIVAL_IN_CHAT.get();
        String personalKey = withBest ? "chat.survivalcounter.personal.best" : "chat.survivalcounter.personal";
        String broadcastKey = withBest ? "chat.survivalcounter.broadcast.best" : "chat.survivalcounter.broadcast";

        String time = CounterManager.formatSurvivalTime(duration);
        long best = surv.getBestTime(player.getUUID());
        String bestStr = CounterManager.formatSurvivalTime(best);

        Component msg = ServerConfig.SHOW_SURVIVAL_IN_CHAT_GLOBAL.get()
                ? withBest
                ? Component.translatable(broadcastKey, player.getScoreboardName(), time, bestStr)
                : Component.translatable(broadcastKey, player.getScoreboardName(), time)
                : withBest
                ? Component.translatable(personalKey, time, bestStr)
                : Component.translatable(personalKey, time);

        if (ServerConfig.SHOW_SURVIVAL_IN_CHAT_GLOBAL.get()) {
            level.getServer().getPlayerList().broadcastSystemMessage(msg, false);
        } else {
            player.sendSystemMessage(msg);
        }
    }

    private static void sendDayCounterToChat(ServerLevel level, ServerPlayer player) {
        long currentDay = DayCounterData.getCurrentDay(level);
        int textColor = ServerConfig.DAY_CHATTEXT_COLOR.get();

        if (!ServerConfig.ENABLE_DAY_COUNTER.get()) {
            return;
        }

        player.sendSystemMessage(Component.translatable("overlay.counter.day_with_emoji", currentDay)
                .withStyle(style -> style.withColor(textColor)));
    }
}