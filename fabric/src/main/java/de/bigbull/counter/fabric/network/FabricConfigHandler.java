package de.bigbull.counter.fabric.network;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.fabric.config.FabricTomlConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

/**
 * Verwaltet die Netzwerkkommunikation für Konfigurationssynchronisation
 */
public class FabricConfigHandler {

    /**
     * Initialisiert die Netzwerkkommunikation
     */
    public static void init() {
        // Registriere den Pakettyp
        PayloadTypeRegistry.configurationS2C().register(ConfigSyncPacket.TYPE, ConfigSyncPacket.CODEC);

        // Server-Ereignishandler
        ServerConfigurationConnectionEvents.CONFIGURE.register(FabricConfigHandler::onPlayerConfiguration);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            // Konfiguration auch beim Spielbeitritt synchronisieren
            syncConfigToPlayer(handler.getPlayer());
        });

        // Client-Ereignishandler
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            initClient();
        }
    }

    /**
     * Initialisiert die Client-seitigen Handler
     */
    @Environment(EnvType.CLIENT)
    private static void initClient() {
        ClientConfigurationNetworking.registerGlobalReceiver(ConfigSyncPacket.TYPE, (packet, context) -> {
            Counter.logger.info("Synchronisiere Server-Konfiguration mit dem Client");
            // Wende die Serverkonfiguration auf den Client an
            packet.applyToClientConfig(FabricTomlConfig.getClientConfig());
        });
    }

    /**
     * Wird aufgerufen, wenn ein Spieler Konfigurationen mit dem Server austauscht
     */
    private static void onPlayerConfiguration(ServerConfigurationPacketListenerImpl handler, MinecraftServer server) {
        // In der Konfigurationsphase senden wir das Paket direkt über den Handler
        Counter.logger.info("Sende Serverkonfiguration während der Konfigurationsphase");
        ConfigSyncPacket packet = ConfigSyncPacket.fromServerConfig(FabricTomlConfig.getServerConfig());
        ServerConfigurationNetworking.send(handler, packet);
    }

    /**
     * Sendet die Serverkonfiguration an einen Spieler
     */
    public static void syncConfigToPlayer(ServerPlayer player) {
        ConfigSyncPacket packet = ConfigSyncPacket.fromServerConfig(FabricTomlConfig.getServerConfig());

        Counter.logger.info("Sende Serverkonfiguration an Spieler: " + player.getScoreboardName());

        // Verwende die ServerPlayNetworking API, um das Paket zu senden
        ServerPlayNetworking.send(player, packet);
    }

    /**
     * Sendet die Serverkonfiguration an alle Spieler
     */
    public static void syncConfigToAllPlayers(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            syncConfigToPlayer(player);
        }
    }
}