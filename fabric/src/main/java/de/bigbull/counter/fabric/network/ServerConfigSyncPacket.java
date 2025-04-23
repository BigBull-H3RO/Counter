package de.bigbull.counter.fabric.network;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.IServerConfig;
import de.bigbull.counter.fabric.config.FabricServerConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record ServerConfigSyncPacket(
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
        boolean showCoordsOverlay) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Counter.MOD_ID, "server_config_sync");
    public static final Type<ServerConfigSyncPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, ServerConfigSyncPacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                // Day Counter
                buf.writeBoolean(packet.enabledDayCounter);
                buf.writeBoolean(packet.showDayOverlay);
                buf.writeBoolean(packet.enableDayMessage);
                buf.writeBoolean(packet.showDayInChat);
                buf.writeInt(packet.dayChatTextColor);

                // Death Counter
                buf.writeBoolean(packet.enableDeathCounter);
                buf.writeBoolean(packet.showDeathOverlay);
                buf.writeVarInt(packet.maxPlayersShown);
                buf.writeEnum(packet.deathOverlayMode);

                // Death Chat
                buf.writeBoolean(packet.enableDeathInChat);
                buf.writeEnum(packet.deathInChatMode);
                buf.writeEnum(packet.deathChatMode);
                buf.writeBoolean(packet.showDeathListOnDeathGlobal);
                buf.writeInt(packet.deathListChatTextColor);
                buf.writeInt(packet.deathSelfChatTextColor);

                // Time Counter
                buf.writeBoolean(packet.enableTimeCounter);
                buf.writeBoolean(packet.showTimeOverlay);
                buf.writeBoolean(packet.showCombinedDayTime);
                buf.writeBoolean(packet.timeFormat24h);

                // Coords Counter
                buf.writeBoolean(packet.enableCoordsCounter);
                buf.writeBoolean(packet.showCoordsOverlay);
            },
            buf -> new ServerConfigSyncPacket(
                    // Day Counter
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readInt(),

                    // Death Counter
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readVarInt(),
                    buf.readEnum(IServerConfig.DeathOverlayMode.class),

                    // Death Chat
                    buf.readBoolean(),
                    buf.readEnum(IServerConfig.DeathInChatMode.class),
                    buf.readEnum(IServerConfig.DeathChatMode.class),
                    buf.readBoolean(),
                    buf.readInt(),
                    buf.readInt(),

                    // Time Counter
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),

                    // Coords Counter
                    buf.readBoolean(),
                    buf.readBoolean()
            )
    );

    public static ServerConfigSyncPacket fromConfig(IServerConfig config) {
        return new ServerConfigSyncPacket(
                // Day Counter
                config.enabledDayCounter(),
                config.showDayOverlay(),
                config.enableDayMessage(),
                config.showDayInChat(),
                config.dayChatTextColor(),

                // Death Counter
                config.enableDeathCounter(),
                config.showDeathOverlay(),
                config.maxPlayersShown(),
                config.deathOverlayMode(),

                // Death Chat
                config.enableDeathInChat(),
                config.deathInChatMode(),
                config.deathChatMode(),
                config.showDeathListOnDeathGlobal(),
                config.deathListChatTextColor(),
                config.deathSelfChatTextColor(),

                // Time Counter
                config.enableTimeCounter(),
                config.showTimeOverlay(),
                config.showCombinedDayTime(),
                config.timeFormat24h(),

                // Coords Counter
                config.enableCoordsCounter(),
                config.showCoordsOverlay()
        );
    }

    public static void send(ServerPlayer player, IServerConfig config) {
        ServerConfigSyncPacket packet = fromConfig(config);
        ServerPlayNetworking.send(player, packet);
    }

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Environment(EnvType.CLIENT)
    public void applyToClientConfig(FabricServerConfig config) {
        // Day Counter
        config.setEnabledDayCounter(enabledDayCounter);
        config.setShowDayOverlay(showDayOverlay);
        config.setEnableDayMessage(enableDayMessage);
        config.setShowDayInChat(showDayInChat);
        config.setDayChatTextColor(dayChatTextColor);

        // Death Counter
        config.setEnableDeathCounter(enableDeathCounter);
        config.setShowDeathOverlay(showDeathOverlay);
        config.setMaxPlayersShown(maxPlayersShown);
        config.setDeathOverlayMode(deathOverlayMode);

        // Death Chat
        config.setEnableDeathInChat(enableDeathInChat);
        config.setDeathInChatMode(deathInChatMode);
        config.setDeathChatMode(deathChatMode);
        config.setShowDeathListOnDeathGlobal(showDeathListOnDeathGlobal);
        config.setDeathListChatTextColor(deathListChatTextColor);
        config.setDeathSelfChatTextColor(deathSelfChatTextColor);

        // Time Counter
        config.setEnableTimeCounter(enableTimeCounter);
        config.setShowTimeOverlay(showTimeOverlay);
        config.setShowCombinedDayTime(showCombinedDayTime);
        config.setTimeFormat24h(timeFormat24h);

        // Coords Counter
        config.setEnableCoordsCounter(enableCoordsCounter);
        config.setShowCoordsOverlay(showCoordsOverlay);
    }
}
