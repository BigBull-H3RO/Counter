package de.bigbull.counter.common.Events;

import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.config.IServerConfig;
import de.bigbull.counter.common.network.packets.DayCounterPacket;
import de.bigbull.counter.common.network.packets.DeathCounterPacket;
import de.bigbull.counter.common.platform.Services;
import de.bigbull.counter.common.util.saveddata.DayCounterData;
import de.bigbull.counter.common.util.saveddata.DeathCounterData;
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

public class ModGameEvents {
    public static void onServerTick(MinecraftServer server) {
        if (!ConfigHelper.get().getServer().enabledDayCounter()) {
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

            if (ConfigHelper.get().getServer().enableDayMessage()) {
                server.getPlayerList().broadcastSystemMessage(
                        Component.translatable("chat.daycounter.new_day", newCounterValue), false);
            }

            Services.PLATFORM.sendPacketToAllClient(new DayCounterPacket(newCounterValue), server);
        } else if (realDay < lastRealDay) {
            data.setLastRealDay(realDay);
        }
    }

    public static void onPlayerLoggedIn(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        DeathCounterData data = DeathCounterData.get(level);
        data.updatePlayerName(player.getUUID(), player.getScoreboardName());

        Services.PLATFORM.sendPacketToClient(new DeathCounterPacket(data.getDeathCountMap(), data.getPlayerNames()), player);
        Services.PLATFORM.sendPacketToClient(new DayCounterPacket(DayCounterData.getCurrentDay(level)), player);

        if (ConfigHelper.get().getServer().deathInChatMode() == IServerConfig.DeathInChatMode.ON_JOIN ||
                ConfigHelper.get().getServer().deathInChatMode() == IServerConfig.DeathInChatMode.BOTH) {
            sendDeathCounterMessage(player, level, data, false);
        }

        if (ConfigHelper.get().getServer().showDayInChat()) {
            sendDayCounterToChat(level, player);
        }
    }

    public static void onPlayerDeath(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        DeathCounterData data = DeathCounterData.get(level);
        data.addDeath(player.getUUID());

        Services.PLATFORM.sendPacketToAllClient(new DeathCounterPacket(data.getDeathCountMap(), data.getPlayerNames()), level.getServer());

        if (ConfigHelper.get().getServer().deathInChatMode() == IServerConfig.DeathInChatMode.ON_DEATH ||
                ConfigHelper.get().getServer().deathInChatMode() == IServerConfig.DeathInChatMode.BOTH) {
            sendDeathCounterMessage(player, level, data, true);
        }
    }

    private static void sendDeathCounterMessage(ServerPlayer affectedPlayer, ServerLevel level, DeathCounterData data, boolean isDeathEvent) {
        if (!ConfigHelper.get().getServer().enableDeathCounter() || !ConfigHelper.get().getServer().enableDeathInChat()) {
            return;
        }

        boolean sendToAll = isDeathEvent && ConfigHelper.get().getServer().showDeathListOnDeathGlobal();

        if (ConfigHelper.get().getServer().deathChatMode() == IServerConfig.DeathChatMode.LIST) {
            sendDeathListToPlayer(affectedPlayer, level, data, sendToAll);
        } else {
            sendDeathSelfToPlayer(affectedPlayer, level, data, sendToAll);
        }
    }

    private static void sendDeathSelfToPlayer(ServerPlayer affectedPlayer, ServerLevel level, DeathCounterData data, boolean sendToAll) {
        MinecraftServer server = level.getServer();
        int textColor = ConfigHelper.get().getServer().deathSelfChatTextColor();
        int playerDeaths = data.getDeathCountMap().getOrDefault(affectedPlayer.getUUID(), 0);

        Component personalMessage = Component.translatable("overlay.counter.deaths_with_emoji", playerDeaths)
                .withStyle(style -> style.withColor(textColor));

        if (sendToAll) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if (!player.getUUID().equals(affectedPlayer.getUUID())) {
                    Component broadcastMessage = Component.translatable(
                            playerDeaths == 1 ? "chat.deathcounter.player_death.singular" : "chat.deathcounter.player_death.plural",
                            affectedPlayer.getScoreboardName(), playerDeaths
                    );
                    player.sendSystemMessage(broadcastMessage);
                }
            }
        }

        affectedPlayer.sendSystemMessage(personalMessage);
    }

    private static void sendDeathListToPlayer(ServerPlayer player, ServerLevel level, DeathCounterData data, boolean sendToAll) {
        int textColor = ConfigHelper.get().getServer().deathListChatTextColor();
        List<Map.Entry<UUID, Integer>> sortedDeaths = data.getDeathCountMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ConfigHelper.get().getServer().maxPlayersShown())
                .toList();

        if (sortedDeaths.isEmpty()) return;

        Component header = Component.translatable("overlay.counter.deathlist").withStyle(style -> style.withColor(textColor));

        List<MutableComponent> deathEntries = sortedDeaths.stream().map(entry -> {
            String playerName = data.getPlayerNames().getOrDefault(entry.getKey(), "Unknown");
            int deaths = entry.getValue();

            Component positionComponent = Component.literal((sortedDeaths.indexOf(entry) + 1) + ".")
                    .setStyle(Style.EMPTY.withColor(0xFFFFFF));
            Component playerAndDeaths = (deaths == 1)
                    ? Component.translatable("overlay.counter.deathlist.entry.singular", Component.literal(playerName), deaths)
                    : Component.translatable("overlay.counter.deathlist.entry.plural", Component.literal(playerName), deaths);

            return Component.translatable("overlay.counter.deathlist.entry.full", positionComponent, playerAndDeaths);
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

    private static void sendDayCounterToChat(ServerLevel level, ServerPlayer player) {
        long currentDay = DayCounterData.getCurrentDay(level);
        int textColor = ConfigHelper.get().getServer().dayChatTextColor();

        if (!ConfigHelper.get().getServer().enabledDayCounter()) {
            return;
        }

        player.sendSystemMessage(Component.translatable("overlay.counter.day_with_emoji", currentDay)
                .withStyle(style -> style.withColor(textColor)));
    }
}
