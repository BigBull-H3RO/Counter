package de.bigbull.network.overlay;

import de.bigbull.Counter;
import de.bigbull.config.ConfigValues;
import de.bigbull.config.ServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncDeathOverlayConfigPacket(
        boolean showDeathOverlay,
        boolean showDeathOverlayAlways,
        int maxPlayersShown,
        ServerConfig.DeathOverlayMode deathOverlayMode
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncDeathOverlayConfigPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MODID, "sync_death_overlay_config"));

    public static final StreamCodec<FriendlyByteBuf, SyncDeathOverlayConfigPacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeBoolean(packet.showDeathOverlay);
                buf.writeBoolean(packet.showDeathOverlayAlways);
                buf.writeInt(packet.maxPlayersShown);
                buf.writeEnum(packet.deathOverlayMode);
            },
            buf -> new SyncDeathOverlayConfigPacket(
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readInt(),
                    buf.readEnum(ServerConfig.DeathOverlayMode.class)
            )
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncDeathOverlayConfigPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ConfigValues.SHOW_DEATH_OVERLAY = packet.showDeathOverlay();
            ConfigValues.SHOW_DEATH_OVERLAY_ALWAYS = packet.showDeathOverlayAlways();
            ConfigValues.MAX_PLAYERS_SHOWN = packet.maxPlayersShown();
            ConfigValues.DEATH_OVERLAY_MODE = packet.deathOverlayMode();
        });
    }
}