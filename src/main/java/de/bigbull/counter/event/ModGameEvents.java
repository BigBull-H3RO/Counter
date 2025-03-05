package de.bigbull.counter.event;

import de.bigbull.counter.Counter;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.data.saveddata.DayCounterData;
import de.bigbull.counter.data.saveddata.DeathCounterData;
import de.bigbull.counter.network.DayCounterPacket;
import de.bigbull.counter.network.DeathCounterPacket;
import de.bigbull.counter.util.gui.DeathCounterOverlay;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = Counter.MODID)
public class ModGameEvents {
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (!ServerConfig.ENABLE_DAY_COUNTER.get()) {
            return;
        }

        MinecraftServer server = event.getServer();
        ServerLevel level = server.overworld();
        long realDay = level.getDayTime() / 24000;
        long newCounterValue = realDay + DayCounterData.getOffset(level);

        if (DayCounterData.getCurrentDay(level) != newCounterValue) {
            DayCounterData.setDayCounter(level, newCounterValue);

            if (ServerConfig.ENABLE_DAY_MESSAGE.get()) {
                server.getPlayerList().broadcastSystemMessage(
                        Component.translatable("chat.daycounter.new_day", newCounterValue), false);
            }

            for (ServerPlayer player : level.players()) {
                PacketDistributor.sendToPlayer(player, new DayCounterPacket(newCounterValue));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();
            DeathCounterData data = DeathCounterData.get(level);
            data.updatePlayerName(player.getUUID(), player.getScoreboardName());

            PacketDistributor.sendToPlayer(player, new DeathCounterPacket(data.getDeathCountMap(), data.getPlayerNames()));
            PacketDistributor.sendToPlayer(player, new DayCounterPacket(DayCounterData.getCurrentDay(level)));

            if ((ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.ON_JOIN ||
                    ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.BOTH)) {
                sendDeathCounterMessage(player, level, data);
            }

            if (ServerConfig.SHOW_DAY_IN_CHAT.get()) {
                sendDayCounterToChat(level, player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();
            DeathCounterData data = DeathCounterData.get(level);
            data.addDeath(player.getUUID());

            PacketDistributor.sendToAllPlayers(new DeathCounterPacket(data.getDeathCountMap(), data.getPlayerNames()));

            if ((ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.ON_DEATH ||
                    ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.BOTH)) {

                sendDeathCounterMessage(player, level, data);
            }
        }
    }

    private static void sendDeathCounterMessage(ServerPlayer affectedPlayer, ServerLevel level, DeathCounterData data) {
        MinecraftServer server = level.getServer();
        boolean isMultiplayer = server.isDedicatedServer() || server.isPublished();

        if (!ServerConfig.ENABLE_DEATH_COUNTER.get() || !ServerConfig.ENABLE_DEATH_IN_CHAT.get()) {
            return;
        }

        if (isMultiplayer) {
            if (ServerConfig.DEATH_CHAT_MODE_TYPE.get() == ServerConfig.DeathChatMode.LIST) {
                sendDeathListToPlayer(affectedPlayer, level, data);
            } else if (ServerConfig.DEATH_CHAT_MODE_TYPE.get() == ServerConfig.DeathChatMode.ONLY_SELF) {
                sendDeathSelfToPlayer(affectedPlayer, level, data, true);
            }
        } else {
            sendDeathSelfToPlayer(affectedPlayer, level, data, false);
        }
    }

    private static void sendDeathSelfToPlayer(ServerPlayer affectedPlayer, ServerLevel level, DeathCounterData data, boolean isMultiplayer) {
        MinecraftServer server = level.getServer();
        int textColor = ServerConfig.DEATH_SELF_CHATTEXT_COLOR.get();

        int playerDeaths = data.getDeathCountMap().getOrDefault(affectedPlayer.getUUID(), 0);

        if (ServerConfig.SHOW_DEATH_LIST_ON_DEATH_GLOBAL.get() && isMultiplayer) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if (!player.getUUID().equals(affectedPlayer.getUUID())) {
                    player.sendSystemMessage(Component.translatable(playerDeaths == 1 ? "chat.deathcounter.player_death.singular" :
                            "chat.deathcounter.player_death.plural", affectedPlayer.getScoreboardName(), playerDeaths));
                }
            }
        } else {
            affectedPlayer.sendSystemMessage(Component.translatable("overlay.counter.deaths_with_emoji", playerDeaths).withStyle(style -> style.withColor(textColor)));
        }
    }

    private static void sendDeathListToPlayer(ServerPlayer player, ServerLevel level, DeathCounterData data) {
        int textColor = ServerConfig.DEATH_LIST_CHATTEXT_COLOR.get();
        List<Map.Entry<UUID, Integer>> sortedDeaths = data.getDeathCountMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ServerConfig.MAX_PLAYERS_SHOWN.get())
                .toList();

        if (!sortedDeaths.isEmpty()) {
            player.sendSystemMessage(Component.translatable("overlay.counter.deathlist").withStyle(style -> style.withColor(textColor)));

            int position = 0;
            for (Map.Entry<UUID, Integer> entry : sortedDeaths) {
                ServerPlayer listedPlayer = level.getServer().getPlayerList().getPlayer(entry.getKey());
                String playerName = (listedPlayer != null) ? listedPlayer.getScoreboardName() : "Unknown";

                Component deathEntry = DeathCounterOverlay.getDeathEntry(position, playerName, entry.getValue());

                if (ServerConfig.SHOW_DEATH_LIST_ON_DEATH_GLOBAL.get()) {
                    player.server.getPlayerList().broadcastSystemMessage(deathEntry, false);
                } else {
                    player.sendSystemMessage(deathEntry);
                }
                position++;
            }
        }
    }

    private static void sendDayCounterToChat(ServerLevel level, ServerPlayer player) {
        long currentDay = DayCounterData.getCurrentDay(level);
        int textColor = ServerConfig.DAY_OVERLAY_CHATTEXT_COLOR.get();

        if (!ServerConfig.ENABLE_DAY_COUNTER.get()) {
            return;
        }

        player.sendSystemMessage(Component.translatable("overlay.counter.day_with_emoji", currentDay)
                .withStyle(style -> style.withColor(textColor)));
    }
}
