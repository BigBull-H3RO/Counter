package de.bigbull.network.overlay;

import de.bigbull.Counter;
import de.bigbull.network.client.ClientDayOverlayState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public record SyncDayOverlayStatePacket(UUID playerUUID, boolean enabled) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncDayOverlayStatePacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MODID, "sync_overlay_state"));

    public static final StreamCodec<FriendlyByteBuf, SyncDayOverlayStatePacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeUUID(packet.playerUUID);
                buf.writeBoolean(packet.enabled);
            },
            buf -> new SyncDayOverlayStatePacket(buf.readUUID(), buf.readBoolean()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncDayOverlayStatePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientDayOverlayState.setOverlayState(packet.playerUUID, packet.enabled);
        });
    }
}