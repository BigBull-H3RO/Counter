package de.bigbull.event;

import de.bigbull.Counter;
import de.bigbull.config.ServerConfig;
import de.bigbull.data.saveddata.daycounter.PlayerDayOverlayData;
import de.bigbull.data.saveddata.deathcounter.DeathCounterData;
import de.bigbull.data.saveddata.deathcounter.PlayerDeathOverlayData;
import de.bigbull.network.DayCounterPacket;
import de.bigbull.network.DeathCounterPacket;
import de.bigbull.network.client.ClientDayOverlayState;
import de.bigbull.network.client.ClientDeathOverlayState;
import de.bigbull.network.overlay.SyncDayOverlayStatePacket;
import de.bigbull.network.overlay.SyncDeathOverlayStatePacket;
import de.bigbull.util.CounterManager;
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
        long newCounterValue = realDay + CounterManager.getOffset(level);

        if (CounterManager.getCurrentDay(level) != newCounterValue) {
            CounterManager.setDayCounter(level, newCounterValue);

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
            DeathCounterData deathData = DeathCounterData.get(level);

            PacketDistributor.sendToPlayer(player, new DeathCounterPacket(deathData.getDeathCountMap()));
            PacketDistributor.sendToPlayer(player, new DayCounterPacket(CounterManager.getCurrentDay(level)));
            PacketDistributor.sendToPlayer(player, new SyncDayOverlayStatePacket(player.getUUID(), PlayerDayOverlayData.get(level).isOverlayEnabled(player)));
            PacketDistributor.sendToPlayer(player, new SyncDeathOverlayStatePacket(player.getUUID(), PlayerDeathOverlayData.get(level).isOverlayEnabled(player)));

            if ((ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.ON_JOIN ||
                    ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() == ServerConfig.DeathInChatMode.BOTH)) {

                sendDeathCounterMessage(player, level, deathData);
            }

            if (ServerConfig.SHOW_DAY_IN_CHAT.get()) {
                sendDayCounterToChat(level, player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            ClientDayOverlayState.reset();
            ClientDeathOverlayState.reset();
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();
            DeathCounterData data = DeathCounterData.get(level);
            data.addDeath(player.getUUID());

            PacketDistributor.sendToAllPlayers(new DeathCounterPacket(data.getDeathCountMap()));

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

        int playerDeaths = data.getDeathCountMap().getOrDefault(affectedPlayer.getUUID(), 0);

        if (ServerConfig.SHOW_DEATH_LIST_ON_DEATH_GLOBAL.get() && isMultiplayer) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if (!player.getUUID().equals(affectedPlayer.getUUID())) {
                    player.sendSystemMessage(Component.translatable(playerDeaths == 1 ? "chat.deathcounter.player_death.singular" : "chat.deathcounter.player_death.plural", affectedPlayer.getScoreboardName(), playerDeaths));
                }
            }
        } else {
            affectedPlayer.sendSystemMessage(Component.translatable("overlay.counter.deaths", playerDeaths));
        }
    }

    private static void sendDeathListToPlayer(ServerPlayer player, ServerLevel level, DeathCounterData data) {
        List<Map.Entry<UUID, Integer>> sortedDeaths = data.getDeathCountMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ServerConfig.MAX_PLAYERS_SHOWN.get())
                .toList();

        if (!sortedDeaths.isEmpty()) {
            player.sendSystemMessage(Component.translatable("overlay.counter.deathlist"));
            int position = 1;
            for (Map.Entry<UUID, Integer> entry : sortedDeaths) {
                ServerPlayer listedPlayer = level.getServer().getPlayerList().getPlayer(entry.getKey());
                String playerName = (listedPlayer != null) ? listedPlayer.getScoreboardName() : "Unknown";

                if (ServerConfig.SHOW_DEATH_LIST_ON_DEATH_GLOBAL.get()) {
                    player.server.getPlayerList().broadcastSystemMessage(Component.translatable(
                                    entry.getValue() == 1 ? "overlay.counter.deathlist.entry.singular" : "overlay.counter.deathlist.entry.plural",
                                    position, playerName, entry.getValue()), false);
                } else {
                    player.sendSystemMessage(Component.translatable(
                            entry.getValue() == 1 ? "overlay.counter.deathlist.entry.singular" : "overlay.counter.deathlist.entry.plural",
                            position, playerName, entry.getValue()));
                }
                position++;
            }
        }
    }

    private static void sendDayCounterToChat(ServerLevel level, ServerPlayer player) {
        long currentDay = CounterManager.getCurrentDay(level);

        if (!ServerConfig.ENABLE_DAY_COUNTER.get()) {
            return;
        }

        player.sendSystemMessage(Component.translatable("overlay.counter.day", currentDay));
    }
}
