package de.bigbull.counter.fabric.network;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.fabric.config.ClientConfigBacking;
import de.bigbull.counter.fabric.config.FabricServerConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;

/**
 * Manager für die Synchronisierung der Konfiguration zwischen Server und Client
 */
public class ConfigSyncManager {

    /**
     * Initialisiert die Netzwerkkommunikation
     */
    public static void init() {
        // Registriere den Pakettyp für die Konfigurationssynchronisierung
        PayloadTypeRegistry.playS2C().register(ConfigSyncPacket.TYPE, ConfigSyncPacket.CODEC);

        // Registriere den Client-Handler, wenn wir auf dem Client sind
        if (FabricLoader.getInstance().getEnvironmentType() == net.fabricmc.api.EnvType.CLIENT) {
            ClientPlayNetworking.registerGlobalReceiver(ConfigSyncPacket.TYPE,
                    (payload, context) -> handleConfigSync(payload));
        }
    }

    /**
     * Verarbeitet ein empfangenes Konfigurationspaket auf dem Client
     */
    private static void handleConfigSync(ConfigSyncPacket packet) {
        Minecraft.getInstance().execute(() -> {
            Counter.logger.info("Konfiguration vom Server empfangen");

            // Erstelle ein Backup der ursprünglichen Client-Konfiguration
            if (!ClientConfigBacking.hasBackup()) {
                ClientConfigBacking.backup(ConfigHelper.get().getServer());
            }

            // Aktualisiere die serverseitigen Einstellungen im Client
            applyServerConfig(packet.getServerConfig());
        });
    }

    /**
     * Wendet die Serverkonfiguration auf die Client-Konfiguration an
     */
    private static void applyServerConfig(de.bigbull.counter.common.config.IServerConfig receivedConfig) {
        // Wir erstellen hier einen Proxy, der die empfangene Konfiguration verwendet
        // Alle Anfragen an ConfigHelper.get().getServer() werden nun die empfangenen Werte zurückgeben
        ClientConfigBacking.applyServerConfig(receivedConfig);

        Counter.logger.info("Serverkonfiguration angewendet: Day counter enabled = {}",
                receivedConfig.enabledDayCounter());
    }

    /**
     * Sendet die Serverkonfiguration an einen Spieler
     */
    public static void syncConfigToPlayer(ServerPlayer player) {
        FabricServerConfig serverConfig = (FabricServerConfig) ConfigHelper.get().getServer();
        ConfigSyncPacket packet = new ConfigSyncPacket(serverConfig);

        Counter.logger.info("Sende Serverkonfiguration an Spieler: {}", player.getScoreboardName());
        ServerPlayNetworking.send(player, packet);
    }
}