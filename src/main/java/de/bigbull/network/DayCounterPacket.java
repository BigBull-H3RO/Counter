package de.bigbull.network;

import de.bigbull.Counter;
import de.bigbull.util.client.ClientCounterState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DayCounterPacket(long dayCounter) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DayCounterPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MODID, "day_counter"));

    public static final StreamCodec<FriendlyByteBuf, DayCounterPacket> CODEC = StreamCodec.of(
            (buf, packet) -> buf.writeLong(packet.dayCounter),
            buf -> new DayCounterPacket(buf.readLong()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DayCounterPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientCounterState.setDayCounter(packet.dayCounter);
        });
    }
}