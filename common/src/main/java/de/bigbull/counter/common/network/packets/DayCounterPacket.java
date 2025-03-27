package de.bigbull.counter.common.network.packets;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.network.PacketBase;
import de.bigbull.counter.common.util.ClientCounterState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public record DayCounterPacket(long dayCounter) implements PacketBase {
    public static final Type<DayCounterPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MOD_ID, "day_counter"));

    public static final StreamCodec<FriendlyByteBuf, DayCounterPacket> CODEC = StreamCodec.of(
            (buf, packet) -> buf.writeLong(packet.dayCounter),
            buf -> new DayCounterPacket(buf.readLong()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handle(Player player) {
        Minecraft.getInstance().execute(() -> {
            ClientCounterState.setDayCounter(this.dayCounter);
        });
    }
}