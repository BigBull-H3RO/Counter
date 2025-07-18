package de.bigbull.counter.network.packet;

import de.bigbull.counter.Counter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DayCounterPacket(long dayCounter) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DayCounterPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MOD_ID, "day_counter"));

    public static final StreamCodec<FriendlyByteBuf, DayCounterPacket> CODEC = StreamCodec.of(
            (buf, packet) -> buf.writeLong(packet.dayCounter),
            buf -> new DayCounterPacket(buf.readLong()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}