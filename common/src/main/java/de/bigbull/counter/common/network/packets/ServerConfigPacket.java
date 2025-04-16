package de.bigbull.counter.common.network.packets;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.config.IServerConfig;
import de.bigbull.counter.common.network.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public record ServerConfigPacket(
        boolean enabledDayCounter,
        boolean showDayOverlay,
        boolean enableDayMessage,
        boolean showDayInChat,
        int dayChatTextColor,
        boolean enableDeathCounter,
        boolean showDeathOverlay,
        int maxPlayersShown,
        IServerConfig.DeathOverlayMode deathOverlayMode,
        boolean enableDeathInChat,
        IServerConfig.DeathInChatMode deathInChatMode,
        IServerConfig.DeathChatMode deathChatMode,
        boolean showDeathListOnDeathGlobal,
        int deathListChatTextColor,
        int deathSelfChatTextColor,
        boolean enableTimeCounter,
        boolean showTimeOverlay,
        boolean showCombinedDayTime,
        boolean timeFormat24h,
        boolean enableCoordsCounter,
        boolean showCoordsOverlay
) implements PacketBase {
    public static final Type<ServerConfigPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MOD_ID, "server_config"));

    public static final StreamCodec<FriendlyByteBuf, ServerConfigPacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeBoolean(packet.enabledDayCounter);
                buf.writeBoolean(packet.showDayOverlay);
                buf.writeBoolean(packet.enableDayMessage);
                buf.writeBoolean(packet.showDayInChat);
                buf.writeInt(packet.dayChatTextColor);
                buf.writeBoolean(packet.enableDeathCounter);
                buf.writeBoolean(packet.showDeathOverlay);
                buf.writeInt(packet.maxPlayersShown);
                buf.writeEnum(packet.deathOverlayMode);
                buf.writeBoolean(packet.enableDeathInChat);
                buf.writeEnum(packet.deathInChatMode);
                buf.writeEnum(packet.deathChatMode);
                buf.writeBoolean(packet.showDeathListOnDeathGlobal);
                buf.writeInt(packet.deathListChatTextColor);
                buf.writeInt(packet.deathSelfChatTextColor);
                buf.writeBoolean(packet.enableTimeCounter);
                buf.writeBoolean(packet.showTimeOverlay);
                buf.writeBoolean(packet.showCombinedDayTime);
                buf.writeBoolean(packet.timeFormat24h);
                buf.writeBoolean(packet.enableCoordsCounter);
                buf.writeBoolean(packet.showCoordsOverlay);
            },
            buf -> new ServerConfigPacket(
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readInt(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readInt(),
                    buf.readEnum(IServerConfig.DeathOverlayMode.class),
                    buf.readBoolean(),
                    buf.readEnum(IServerConfig.DeathInChatMode.class),
                    buf.readEnum(IServerConfig.DeathChatMode.class),
                    buf.readBoolean(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean()
            ));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handle(Player player) {
        // Prüfe, ob wir auf der Client-Seite sind
        if (Minecraft.getInstance().level != null) {
            Minecraft.getInstance().execute(() -> {
                // Erstelle ein Backup, falls noch keins existiert
                if (ConfigHelper.get().getServer().getClass().getSimpleName().equals("ServerConfig")) {
                    try {
                        // Da ClientConfigBackup in fabric ist, müssen wir es per Reflection aufrufen
                        Class<?> backupClass = Class.forName("de.bigbull.counter.fabric.config.ClientConfigBackup");
                        if (!((Boolean)backupClass.getMethod("hasBackup").invoke(null))) {
                            Object serverConfig = ConfigHelper.get().getServer();
                            backupClass.getMethod("backup", serverConfig.getClass()).invoke(null, serverConfig);
                        }
                    } catch (Exception e) {
                        Counter.logger.error("Failed to create client config backup", e);
                    }
                }

                // Aktualisiere die Konfiguration
                IServerConfig serverConfig = ConfigHelper.get().getServer();
                FabricServerConfigSetter setter = new FabricServerConfigSetter(serverConfig);
                setter.update(this);

                Counter.logger.info("Server config synchronized: Day counter enabled = {}",
                        serverConfig.enabledDayCounter());
            });
        }
    }

    // Hilfsmethode zum Erstellen eines Pakets aus einer Serverkonfiguration
    public static ServerConfigPacket fromServerConfig(IServerConfig config) {
        return new ServerConfigPacket(
                config.enabledDayCounter(),
                config.showDayOverlay(),
                config.enableDayMessage(),
                config.showDayInChat(),
                config.dayChatTextColor(),
                config.enableDeathCounter(),
                config.showDeathOverlay(),
                config.maxPlayersShown(),
                config.deathOverlayMode(),
                config.enableDeathInChat(),
                config.deathInChatMode(),
                config.deathChatMode(),
                config.showDeathListOnDeathGlobal(),
                config.deathListChatTextColor(),
                config.deathSelfChatTextColor(),
                config.enableTimeCounter(),
                config.showTimeOverlay(),
                config.showCombinedDayTime(),
                config.timeFormat24h(),
                config.enableCoordsCounter(),
                config.showCoordsOverlay()
        );
    }

    /**
     * Hilfsklasse zum Setzen von Werten in einer IServerConfig-Implementierung
     */
    public static class FabricServerConfigSetter {
        private final IServerConfig config;

        public FabricServerConfigSetter(IServerConfig config) {
            this.config = config;
        }

        public void update(ServerConfigPacket packet) {
            // Wir greifen hier direkt auf die spezifische Implementierung zu
            if (config.getClass().getSimpleName().equals("ServerConfig")) {
                try {
                    // Verwende Reflexion, um die Felder direkt zu aktualisieren
                    java.lang.reflect.Field field;

                    field = config.getClass().getDeclaredField("enabledDayCounter");
                    field.setAccessible(true);
                    field.set(config, packet.enabledDayCounter);

                    field = config.getClass().getDeclaredField("showDayOverlay");
                    field.setAccessible(true);
                    field.set(config, packet.showDayOverlay);

                    field = config.getClass().getDeclaredField("enableDayMessage");
                    field.setAccessible(true);
                    field.set(config, packet.enableDayMessage);

                    field = config.getClass().getDeclaredField("showDayInChat");
                    field.setAccessible(true);
                    field.set(config, packet.showDayInChat);

                    field = config.getClass().getDeclaredField("dayChatTextColor");
                    field.setAccessible(true);
                    field.set(config, packet.dayChatTextColor);

                    field = config.getClass().getDeclaredField("enableDeathCounter");
                    field.setAccessible(true);
                    field.set(config, packet.enableDeathCounter);

                    field = config.getClass().getDeclaredField("showDeathOverlay");
                    field.setAccessible(true);
                    field.set(config, packet.showDeathOverlay);

                    field = config.getClass().getDeclaredField("maxPlayersShown");
                    field.setAccessible(true);
                    field.set(config, packet.maxPlayersShown);

                    field = config.getClass().getDeclaredField("deathOverlayMode");
                    field.setAccessible(true);
                    field.set(config, packet.deathOverlayMode);

                    field = config.getClass().getDeclaredField("enableDeathInChat");
                    field.setAccessible(true);
                    field.set(config, packet.enableDeathInChat);

                    field = config.getClass().getDeclaredField("deathInChatMode");
                    field.setAccessible(true);
                    field.set(config, packet.deathInChatMode);

                    field = config.getClass().getDeclaredField("deathChatMode");
                    field.setAccessible(true);
                    field.set(config, packet.deathChatMode);

                    field = config.getClass().getDeclaredField("showDeathListOnDeathGlobal");
                    field.setAccessible(true);
                    field.set(config, packet.showDeathListOnDeathGlobal);

                    field = config.getClass().getDeclaredField("deathListChatTextColor");
                    field.setAccessible(true);
                    field.set(config, packet.deathListChatTextColor);

                    field = config.getClass().getDeclaredField("deathSelfChatTextColor");
                    field.setAccessible(true);
                    field.set(config, packet.deathSelfChatTextColor);

                    field = config.getClass().getDeclaredField("enableTimeCounter");
                    field.setAccessible(true);
                    field.set(config, packet.enableTimeCounter);

                    field = config.getClass().getDeclaredField("showTimeOverlay");
                    field.setAccessible(true);
                    field.set(config, packet.showTimeOverlay);

                    field = config.getClass().getDeclaredField("showCombinedDayTime");
                    field.setAccessible(true);
                    field.set(config, packet.showCombinedDayTime);

                    field = config.getClass().getDeclaredField("timeFormat24h");
                    field.setAccessible(true);
                    field.set(config, packet.timeFormat24h);

                    field = config.getClass().getDeclaredField("enableCoordsCounter");
                    field.setAccessible(true);
                    field.set(config, packet.enableCoordsCounter);

                    field = config.getClass().getDeclaredField("showCoordsOverlay");
                    field.setAccessible(true);
                    field.set(config, packet.showCoordsOverlay);

                } catch (Exception e) {
                    Counter.logger.error("Failed to update server config via reflection", e);
                }
            } else {
                Counter.logger.warn("Unexpected server config class: {}", config.getClass().getName());
            }
        }
    }
}