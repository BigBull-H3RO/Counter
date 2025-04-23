package de.bigbull.counter.fabric.network;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.fabric.config.FabricServerConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;

public class NetworkManager {
    private static FabricServerConfig originalServerConfig = null;

    public static void init() {
        PayloadTypeRegistry.playS2C().register(ServerConfigSyncPacket.TYPE, ServerConfigSyncPacket.CODEC);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.getPlayer();
            Counter.logger.info("Spieler ist beigetreten, sende Konfiguration: {}", player.getScoreboardName());

            server.execute(() -> {
                if (player.isAlive() && player.connection.isAcceptingMessages()) {
                    sendConfigToClient(player);
                }
            });
        });

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            initClient();
        }
    }

    @Environment(EnvType.CLIENT)
    private static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(
                ServerConfigSyncPacket.TYPE,
                (packet, context) -> {
                    Counter.logger.info("Synchronisiere Server-Konfiguration mit dem Client");
                    if (originalServerConfig == null) {
                        saveOriginalConfig();
                    }
                    context.client().execute(() ->
                            packet.applyToClientConfig((FabricServerConfig) ConfigHelper.get().getServer())
                    );
                }
        );

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            restoreOriginalConfig();
        });
    }

    private static void sendConfigToClient(ServerPlayer player) {
        ServerConfigSyncPacket.send(player, ConfigHelper.get().getServer());
    }

    @Environment(EnvType.CLIENT)
    private static void saveOriginalConfig() {
        try {
            if (ConfigHelper.get().getServer() instanceof FabricServerConfig currentConfig) {
                Counter.logger.info("Sichere lokale Serverkonfiguration");
                originalServerConfig = new FabricServerConfig();
                copyConfigValues(currentConfig, originalServerConfig);
            }
        } catch (Exception e) {
            Counter.logger.error("Fehler beim Sichern der Originalkonfiguration", e);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void restoreOriginalConfig() {
        if (originalServerConfig != null) {
            try {
                if (ConfigHelper.get().getServer() instanceof FabricServerConfig currentConfig) {
                    Counter.logger.info("Stelle lokale Serverkonfiguration wieder her");
                    copyConfigValues(originalServerConfig, currentConfig);
                }
            } catch (Exception e) {
                Counter.logger.error("Fehler beim Wiederherstellen der Originalkonfiguration", e);
            } finally {
                originalServerConfig = null;
            }
        }
    }

    private static void copyConfigValues(FabricServerConfig source, FabricServerConfig target) {
        // Day Counter
        target.setEnabledDayCounter(source.enabledDayCounter());
        target.setShowDayOverlay(source.showDayOverlay());
        target.setEnableDayMessage(source.enableDayMessage());
        target.setShowDayInChat(source.showDayInChat());
        target.setDayChatTextColor(source.dayChatTextColor());

        // Death Counter
        target.setEnableDeathCounter(source.enableDeathCounter());
        target.setShowDeathOverlay(source.showDeathOverlay());
        target.setMaxPlayersShown(source.maxPlayersShown());
        target.setDeathOverlayMode(source.deathOverlayMode());

        // Death Chat
        target.setEnableDeathInChat(source.enableDeathInChat());
        target.setDeathInChatMode(source.deathInChatMode());
        target.setDeathChatMode(source.deathChatMode());
        target.setShowDeathListOnDeathGlobal(source.showDeathListOnDeathGlobal());
        target.setDeathListChatTextColor(source.deathListChatTextColor());
        target.setDeathSelfChatTextColor(source.deathSelfChatTextColor());

        // Time Counter
        target.setEnableTimeCounter(source.enableTimeCounter());
        target.setShowTimeOverlay(source.showTimeOverlay());
        target.setShowCombinedDayTime(source.showCombinedDayTime());
        target.setTimeFormat24h(source.timeFormat24h());

        // Coords Counter
        target.setEnableCoordsCounter(source.enableCoordsCounter());
        target.setShowCoordsOverlay(source.showCoordsOverlay());
    }
}