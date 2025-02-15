package de.bigbull.event;

import de.bigbull.Counter;
import de.bigbull.config.ConfigValues;
import de.bigbull.config.ServerConfig;
import de.bigbull.data.saveddata.daycounter.PlayerDayOverlayData;
import de.bigbull.data.saveddata.deathcounter.DeathCounterData;
import de.bigbull.data.saveddata.deathcounter.PlayerDeathOverlayData;
import de.bigbull.network.DayCounterPacket;
import de.bigbull.network.DeathCounterPacket;
import de.bigbull.network.client.ClientDayOverlayState;
import de.bigbull.network.client.ClientDeathOverlayState;
import de.bigbull.network.overlay.SyncDayOverlayConfigPacket;
import de.bigbull.network.overlay.SyncDayOverlayStatePacket;
import de.bigbull.network.overlay.SyncDeathOverlayConfigPacket;
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
        if (!ConfigValues.ENABLE_DAY_COUNTER) {
            return;
        }

        MinecraftServer server = event.getServer();
        ServerLevel level = server.overworld();
        long realDay = level.getDayTime() / 24000;
        long newCounterValue = realDay + CounterManager.getOffset(level);

        if (CounterManager.getCurrentDay(level) != newCounterValue) {
            CounterManager.setDayCounter(level, newCounterValue);

            if (ConfigValues.ENABLE_DAY_MESSAGE) {
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

            // Senden der aktuellen DeathCounter-Daten
            PacketDistributor.sendToPlayer(player, new DeathCounterPacket(deathData.getDeathCountMap()));
            PacketDistributor.sendToPlayer(player, new DayCounterPacket(CounterManager.getCurrentDay(level)));

            // DayCounter Overlay Sync
            PacketDistributor.sendToPlayer(player, new SyncDayOverlayConfigPacket(ServerConfig.SHOW_OVERLAY.get()));
            PacketDistributor.sendToPlayer(player, new SyncDayOverlayStatePacket(player.getUUID(), PlayerDayOverlayData.get(level).isOverlayEnabled(player)));

            // DeathCounter Overlay Sync
            PacketDistributor.sendToPlayer(player, new SyncDeathOverlayConfigPacket(
                    ServerConfig.SHOW_DEATH_OVERLAY.get(),
                    ServerConfig.SHOW_DEATH_OVERLAY_ALWAYS.get(),
                    ServerConfig.MAX_PLAYERS_SHOWN.get(),
                    ServerConfig.DEATH_OVERLAY_MODE.get()));
            PacketDistributor.sendToPlayer(player, new SyncDeathOverlayStatePacket(player.getUUID(), PlayerDeathOverlayData.get(level).isOverlayEnabled(player)));

            if (ConfigValues.SHOW_DEATH_LIST_IN_CHAT) {
                sendDeathCounterToChat(level);
            }

            if (ServerConfig.SHOW_DAY_IN_CHAT.get()) {
                sendDayCounterToChat(level, player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            ConfigValues.SHOW_DAY_OVERLAY = true;
            ConfigValues.SHOW_DEATH_OVERLAY = true;
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

            if (ConfigValues.SHOW_DEATH_LIST_IN_CHAT) {
                sendDeathCounterToChat(level);
            }
        }
    }

    private static void sendDeathCounterToChat(ServerLevel level) {
        DeathCounterData data = DeathCounterData.get(level);
        List<Map.Entry<UUID, Integer>> sortedDeaths = data.getDeathCountMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ServerConfig.MAX_PLAYERS_SHOWN.get())
                .toList();

        if (!sortedDeaths.isEmpty()) {
            level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("overlay.counter.deathlist"), false);
            int position = 1;
            for (Map.Entry<UUID, Integer> entry : sortedDeaths) {
                ServerPlayer player = level.getServer().getPlayerList().getPlayer(entry.getKey());
                String playerName = (player != null) ? player.getScoreboardName() : "Unknown";

                level.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("overlay.counter.deathlist.entry", position + ". " + playerName, entry.getValue()), false);
                position++;
            }
        }
    }

    private static void sendDayCounterToChat(ServerLevel level, ServerPlayer player) {
        long currentDay = CounterManager.getCurrentDay(level);
        player.sendSystemMessage(Component.translatable("chat.daycounter.current_day", currentDay));
    }
}
