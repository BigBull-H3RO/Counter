//package de.bigbull.counter.fabric.config;
//
//import de.bigbull.counter.common.network.packets.ServerConfigPacket;
//import net.minecraft.client.Minecraft;
//
//public class FabricPacketHandler {
//    /**
//     * Verarbeitet ein ServerConfigPacket auf der Client-Seite.
//     */
//    public static void handleServerConfigPacket(ServerConfigPacket packet, net.minecraft.world.entity.player.Player player) {
//        Minecraft.getInstance().execute(() -> {
//            if (TempConfig.SERVER instanceof ServerConfig serverConfig) {
//                updateServerConfig(serverConfig, packet);
//            }
//        });
//    }
//
//    /**
//     * Aktualisiert die ServerConfig mit Daten aus dem Paket.
//     */
//    private static void updateServerConfig(ServerConfig config, ServerConfigPacket packet) {
//        config.enabledDayCounter = packet.enabledDayCounter();
//        config.showDayOverlay = packet.showDayOverlay();
//        config.enableDayMessage = packet.enableDayMessage();
//        config.showDayInChat = packet.showDayInChat();
//        config.dayChatTextColor = packet.dayChatTextColor();
//        config.enableDeathCounter = packet.enableDeathCounter();
//        config.showDeathOverlay = packet.showDeathOverlay();
//        config.maxPlayersShown = packet.maxPlayersShown();
//        config.deathOverlayMode = packet.deathOverlayMode();
//        config.enableDeathInChat = packet.enableDeathInChat();
//        config.deathInChatMode = packet.deathInChatMode();
//        config.deathChatMode = packet.deathChatMode();
//        config.showDeathListOnDeathGlobal = packet.showDeathListOnDeathGlobal();
//        config.deathListChatTextColor = packet.deathListChatTextColor();
//        config.deathSelfChatTextColor = packet.deathSelfChatTextColor();
//        config.enableTimeCounter = packet.enableTimeCounter();
//        config.showTimeOverlay = packet.showTimeOverlay();
//        config.showCombinedDayTime = packet.showCombinedDayTime();
//        config.timeFormat24h = packet.timeFormat24h();
//        config.enableCoordsCounter = packet.enableCoordsCounter();
//        config.showCoordsOverlay = packet.showCoordsOverlay();
//    }
//}