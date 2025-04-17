package de.bigbull.counter.fabric.network;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.IServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Netzwerkpaket für die Konfigurationssynchronisation
 */
public class ConfigSyncPacket implements CustomPacketPayload {
    public static final Type<ConfigSyncPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MOD_ID, "config_sync"));

    public static final StreamCodec<FriendlyByteBuf, ConfigSyncPacket> CODEC = StreamCodec.of(
            ConfigSyncPacket::encode,
            ConfigSyncPacket::decode
    );

    // Die tatsächliche Serverkonfiguration
    private final IServerConfig serverConfig;

    public ConfigSyncPacket(IServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * Kodiert die Serverkonfiguration in den Buffer
     */
    private static void encode(FriendlyByteBuf buf, ConfigSyncPacket packet) {
        // Jede Einstellung einzeln in den Buffer schreiben
        // Tag Counter Einstellungen
        buf.writeBoolean(packet.serverConfig.enabledDayCounter());
        buf.writeBoolean(packet.serverConfig.showDayOverlay());
        buf.writeBoolean(packet.serverConfig.enableDayMessage());
        buf.writeBoolean(packet.serverConfig.showDayInChat());
        buf.writeInt(packet.serverConfig.dayChatTextColor());

        // Death Counter Einstellungen
        buf.writeBoolean(packet.serverConfig.enableDeathCounter());
        buf.writeBoolean(packet.serverConfig.showDeathOverlay());
        buf.writeInt(packet.serverConfig.maxPlayersShown());
        buf.writeEnum(packet.serverConfig.deathOverlayMode());

        // Death Counter Chat Einstellungen
        buf.writeBoolean(packet.serverConfig.enableDeathInChat());
        buf.writeEnum(packet.serverConfig.deathInChatMode());
        buf.writeEnum(packet.serverConfig.deathChatMode());
        buf.writeBoolean(packet.serverConfig.showDeathListOnDeathGlobal());
        buf.writeInt(packet.serverConfig.deathListChatTextColor());
        buf.writeInt(packet.serverConfig.deathSelfChatTextColor());

        // Time Counter Einstellungen
        buf.writeBoolean(packet.serverConfig.enableTimeCounter());
        buf.writeBoolean(packet.serverConfig.showTimeOverlay());
        buf.writeBoolean(packet.serverConfig.showCombinedDayTime());
        buf.writeBoolean(packet.serverConfig.timeFormat24h());

        // Coords Counter Einstellungen
        buf.writeBoolean(packet.serverConfig.enableCoordsCounter());
        buf.writeBoolean(packet.serverConfig.showCoordsOverlay());
    }

    /**
     * Dekodiert die Serverkonfiguration aus dem Buffer
     */
    private static ConfigSyncPacket decode(FriendlyByteBuf buf) {
        // Temporäres Objekt erstellen, das die empfangene Konfiguration enthält
        return new ConfigSyncPacket(new ReceivedServerConfig(
                // Tag Counter Einstellungen
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readInt(),

                // Death Counter Einstellungen
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readInt(),
                buf.readEnum(IServerConfig.DeathOverlayMode.class),

                // Death Counter Chat Einstellungen
                buf.readBoolean(),
                buf.readEnum(IServerConfig.DeathInChatMode.class),
                buf.readEnum(IServerConfig.DeathChatMode.class),
                buf.readBoolean(),
                buf.readInt(),
                buf.readInt(),

                // Time Counter Einstellungen
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean(),

                // Coords Counter Einstellungen
                buf.readBoolean(),
                buf.readBoolean()
        ));
    }

    /**
     * Gibt die empfangene Serverkonfiguration zurück
     */
    public IServerConfig getServerConfig() {
        return serverConfig;
    }

    /**
     * Klasse zur Repräsentation einer empfangenen Serverkonfiguration
     */
    private static class ReceivedServerConfig implements IServerConfig {
        // Tag Counter Einstellungen
        private final boolean enabledDayCounter;
        private final boolean showDayOverlay;
        private final boolean enableDayMessage;
        private final boolean showDayInChat;
        private final int dayChatTextColor;

        // Death Counter Einstellungen
        private final boolean enableDeathCounter;
        private final boolean showDeathOverlay;
        private final int maxPlayersShown;
        private final DeathOverlayMode deathOverlayMode;

        // Death Counter Chat Einstellungen
        private final boolean enableDeathInChat;
        private final DeathInChatMode deathInChatMode;
        private final DeathChatMode deathChatMode;
        private final boolean showDeathListOnDeathGlobal;
        private final int deathListChatTextColor;
        private final int deathSelfChatTextColor;

        // Time Counter Einstellungen
        private final boolean enableTimeCounter;
        private final boolean showTimeOverlay;
        private final boolean showCombinedDayTime;
        private final boolean timeFormat24h;

        // Coords Counter Einstellungen
        private final boolean enableCoordsCounter;
        private final boolean showCoordsOverlay;

        public ReceivedServerConfig(
                boolean enabledDayCounter, boolean showDayOverlay, boolean enableDayMessage,
                boolean showDayInChat, int dayChatTextColor, boolean enableDeathCounter,
                boolean showDeathOverlay, int maxPlayersShown, DeathOverlayMode deathOverlayMode,
                boolean enableDeathInChat, DeathInChatMode deathInChatMode, DeathChatMode deathChatMode,
                boolean showDeathListOnDeathGlobal, int deathListChatTextColor, int deathSelfChatTextColor,
                boolean enableTimeCounter, boolean showTimeOverlay, boolean showCombinedDayTime,
                boolean timeFormat24h, boolean enableCoordsCounter, boolean showCoordsOverlay) {
            this.enabledDayCounter = enabledDayCounter;
            this.showDayOverlay = showDayOverlay;
            this.enableDayMessage = enableDayMessage;
            this.showDayInChat = showDayInChat;
            this.dayChatTextColor = dayChatTextColor;
            this.enableDeathCounter = enableDeathCounter;
            this.showDeathOverlay = showDeathOverlay;
            this.maxPlayersShown = maxPlayersShown;
            this.deathOverlayMode = deathOverlayMode;
            this.enableDeathInChat = enableDeathInChat;
            this.deathInChatMode = deathInChatMode;
            this.deathChatMode = deathChatMode;
            this.showDeathListOnDeathGlobal = showDeathListOnDeathGlobal;
            this.deathListChatTextColor = deathListChatTextColor;
            this.deathSelfChatTextColor = deathSelfChatTextColor;
            this.enableTimeCounter = enableTimeCounter;
            this.showTimeOverlay = showTimeOverlay;
            this.showCombinedDayTime = showCombinedDayTime;
            this.timeFormat24h = timeFormat24h;
            this.enableCoordsCounter = enableCoordsCounter;
            this.showCoordsOverlay = showCoordsOverlay;
        }

        @Override
        public boolean enabledDayCounter() {
            return enabledDayCounter;
        }

        @Override
        public boolean showDayOverlay() {
            return showDayOverlay;
        }

        @Override
        public boolean enableDayMessage() {
            return enableDayMessage;
        }

        @Override
        public boolean showDayInChat() {
            return showDayInChat;
        }

        @Override
        public int dayChatTextColor() {
            return dayChatTextColor;
        }

        @Override
        public boolean enableDeathCounter() {
            return enableDeathCounter;
        }

        @Override
        public boolean showDeathOverlay() {
            return showDeathOverlay;
        }

        @Override
        public int maxPlayersShown() {
            return maxPlayersShown;
        }

        @Override
        public DeathOverlayMode deathOverlayMode() {
            return deathOverlayMode;
        }

        @Override
        public boolean enableDeathInChat() {
            return enableDeathInChat;
        }

        @Override
        public DeathInChatMode deathInChatMode() {
            return deathInChatMode;
        }

        @Override
        public DeathChatMode deathChatMode() {
            return deathChatMode;
        }

        @Override
        public boolean showDeathListOnDeathGlobal() {
            return showDeathListOnDeathGlobal;
        }

        @Override
        public int deathListChatTextColor() {
            return deathListChatTextColor;
        }

        @Override
        public int deathSelfChatTextColor() {
            return deathSelfChatTextColor;
        }

        @Override
        public boolean enableTimeCounter() {
            return enableTimeCounter;
        }

        @Override
        public boolean showTimeOverlay() {
            return showTimeOverlay;
        }

        @Override
        public void setShowTimeOverlay(boolean value) {
            // Da dies eine empfangene Konfiguration ist, kann dieser Wert nicht geändert werden
        }

        @Override
        public boolean showCombinedDayTime() {
            return showCombinedDayTime;
        }

        @Override
        public boolean timeFormat24h() {
            return timeFormat24h;
        }

        @Override
        public boolean enableCoordsCounter() {
            return enableCoordsCounter;
        }

        @Override
        public boolean showCoordsOverlay() {
            return showCoordsOverlay;
        }
    }
}