package de.bigbull.counter.network.packet;

import de.bigbull.counter.Counter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SurvivalTimePacket(long lastDeathTick, long bestTime) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SurvivalTimePacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MOD_ID, "survival_time"));

    public static final StreamCodec<FriendlyByteBuf, SurvivalTimePacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeLong(packet.lastDeathTick);
                buf.writeLong(packet.bestTime);
            },
            buf -> new SurvivalTimePacket(buf.readLong(), buf.readLong()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}