package de.bigbull.network.overlay;

import de.bigbull.Counter;
import de.bigbull.config.ConfigValues;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncDayOverlayConfigPacket(boolean showOverlay) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncDayOverlayConfigPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MODID, "sync_overlay_config"));

    public static final StreamCodec<FriendlyByteBuf, SyncDayOverlayConfigPacket> CODEC = StreamCodec.of(
            (buf, packet) -> buf.writeBoolean(packet.showOverlay),
            buf -> new SyncDayOverlayConfigPacket(buf.readBoolean())
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncDayOverlayConfigPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ConfigValues.SHOW_DAY_OVERLAY = packet.showOverlay();
        });
    }
}