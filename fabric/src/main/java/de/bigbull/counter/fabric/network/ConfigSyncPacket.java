package de.bigbull.counter.fabric.network;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.fabric.config.FabricClientConfig;
import de.bigbull.counter.fabric.config.FabricServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Netzwerkpaket für die Konfigurationssynchronisation
 */
public class ConfigSyncPacket implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Counter.MOD_ID, "config_sync");
    public static final Type<ConfigSyncPacket> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, ConfigSyncPacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeVarInt(packet.configValues.size());

                for (Map.Entry<String, byte[]> entry : packet.configValues.entrySet()) {
                    buf.writeUtf(entry.getKey());
                    buf.writeVarInt(entry.getValue().length);
                    buf.writeBytes(entry.getValue());
                }
            },
            buf -> {
                int count = buf.readVarInt();
                Map<String, byte[]> values = new HashMap<>();

                for (int i = 0; i < count; i++) {
                    String key = buf.readUtf();
                    int length = buf.readVarInt();
                    byte[] value = new byte[length];
                    buf.readBytes(value);
                    values.put(key, value);
                }

                return new ConfigSyncPacket(values);
            }
    );

    // Schlüssel und serialisierte Werte
    private final Map<String, byte[]> configValues;

    public ConfigSyncPacket() {
        this(new HashMap<>());
    }

    public ConfigSyncPacket(Map<String, byte[]> configValues) {
        this.configValues = configValues;
    }

    /**
     * Erstellt ein Synchronisationspaket aus der Server-Konfiguration
     */
    public static ConfigSyncPacket fromServerConfig(FabricServerConfig config) {
        Map<String, byte[]> values = new HashMap<>();

        // Tag Counter Einstellungen
        addBooleanValue(values, "day.enabled", config.enabledDayCounter());
        addBooleanValue(values, "day.showOverlay", config.showDayOverlay());
        addBooleanValue(values, "day.enableMessage", config.enableDayMessage());
        addBooleanValue(values, "day.showInChat", config.showDayInChat());
        addIntValue(values, "day.chatTextColor", config.dayChatTextColor());

        // Death Counter Einstellungen
        addBooleanValue(values, "death.enabled", config.enableDeathCounter());
        addBooleanValue(values, "death.showOverlay", config.showDeathOverlay());
        addIntValue(values, "death.maxPlayersShown", config.maxPlayersShown());
        addStringValue(values, "death.overlayMode", config.deathOverlayMode().name());

        // Death Counter Chat Einstellungen
        addBooleanValue(values, "deathChat.enabled", config.enableDeathInChat());
        addStringValue(values, "deathChat.chatMode", config.deathInChatMode().name());
        addStringValue(values, "deathChat.mode", config.deathChatMode().name());
        addBooleanValue(values, "deathChat.showListOnDeathGlobal", config.showDeathListOnDeathGlobal());
        addIntValue(values, "deathChat.listChatTextColor", config.deathListChatTextColor());
        addIntValue(values, "deathChat.selfChatTextColor", config.deathSelfChatTextColor());

        // Time Counter Einstellungen
        addBooleanValue(values, "time.enabled", config.enableTimeCounter());
        addBooleanValue(values, "time.showOverlay", config.showTimeOverlay());
        addBooleanValue(values, "time.showCombinedDayTime", config.showCombinedDayTime());
        addBooleanValue(values, "time.format24h", config.timeFormat24h());

        // Coords Counter Einstellungen
        addBooleanValue(values, "coords.enabled", config.enableCoordsCounter());
        addBooleanValue(values, "coords.showOverlay", config.showCoordsOverlay());

        return new ConfigSyncPacket(values);
    }

    /**
     * Wendet die Konfigurationswerte auf die Client-Konfiguration an
     */
    public void applyToClientConfig(FabricClientConfig config) {
        // Wir aktualisieren nur Einstellungen, die der Server kontrollieren soll
        // und die für das Client-Verhalten relevant sind

        // Tag Counter Overlay
        if (getBooleanValue("day.showOverlay", true)) {
            // Der Server erlaubt das Overlay
            // Client-Einstellung beibehalten
        } else {
            // Der Server verbietet das Overlay
            config.setShowDayOverlay(false);
        }

        // Death Counter Overlay
        if (getBooleanValue("death.showOverlay", true)) {
            // Der Server erlaubt das Overlay
            // Client-Einstellung beibehalten
        } else {
            // Der Server verbietet das Overlay
            config.setShowDeathListOverlay(false);
            config.setShowDeathSelfOverlay(false);
        }

        // Zeit Overlay
        if (getBooleanValue("time.showOverlay", true)) {
            // Der Server erlaubt das Overlay
            // Client-Einstellung beibehalten
        } else {
            // Der Server verbietet das Overlay
            config.setShowTimeOverlay(false);
        }

        // Koordinaten Overlay
        if (getBooleanValue("coords.showOverlay", true)) {
            // Der Server erlaubt das Overlay
            // Client-Einstellung beibehalten
        } else {
            // Der Server verbietet das Overlay
            config.setShowCoordsOverlay(false);
        }

        // Konfiguration speichern
        config.save();
    }

    private static void addBooleanValue(Map<String, byte[]> values, String key, boolean value) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) (value ? 1 : 0));
        values.put(key, buffer.array());
    }

    private static void addIntValue(Map<String, byte[]> values, String key, int value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(value);
        values.put(key, buffer.array());
    }

    private static void addStringValue(Map<String, byte[]> values, String key, String value) {
        byte[] bytes = value.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(4 + bytes.length);
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        values.put(key, buffer.array());
    }

    private boolean getBooleanValue(String key, boolean defaultValue) {
        byte[] value = configValues.get(key);
        if (value == null || value.length != 1) {
            return defaultValue;
        }
        return value[0] == 1;
    }

    private int getIntValue(String key, int defaultValue) {
        byte[] value = configValues.get(key);
        if (value == null || value.length != 4) {
            return defaultValue;
        }
        return ByteBuffer.wrap(value).getInt();
    }

    private String getStringValue(String key, String defaultValue) {
        byte[] value = configValues.get(key);
        if (value == null || value.length < 4) {
            return defaultValue;
        }

        ByteBuffer buffer = ByteBuffer.wrap(value);
        int length = buffer.getInt();

        if (buffer.remaining() < length) {
            return defaultValue;
        }

        byte[] stringBytes = new byte[length];
        buffer.get(stringBytes);
        return new String(stringBytes);
    }

    /**
     * Schreibt das Paket in den Buffer
     */
    private void write(FriendlyByteBuf buffer) {
        buffer.writeVarInt(configValues.size());

        for (Map.Entry<String, byte[]> entry : configValues.entrySet()) {
            buffer.writeUtf(entry.getKey());
            buffer.writeVarInt(entry.getValue().length);
            buffer.writeBytes(entry.getValue());
        }
    }

    /**
     * Liest das Paket aus dem Buffer
     */
    private static ConfigSyncPacket read(FriendlyByteBuf buffer) {
        int count = buffer.readVarInt();
        Map<String, byte[]> values = new HashMap<>();

        for (int i = 0; i < count; i++) {
            String key = buffer.readUtf();
            int length = buffer.readVarInt();
            byte[] value = new byte[length];
            buffer.readBytes(value);
            values.put(key, value);
        }

        return new ConfigSyncPacket(values);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}