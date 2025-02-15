package de.bigbull.network;

import de.bigbull.Counter;
import de.bigbull.network.client.ClientDeathCounterState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;
import java.util.UUID;

public record DeathCounterPacket(Map<UUID, Integer> deathCounts) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DeathCounterPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MODID, "death_counter"));

    public static final StreamCodec<FriendlyByteBuf, DeathCounterPacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeVarInt(packet.deathCounts.size());
                for (Map.Entry<UUID, Integer> entry : packet.deathCounts.entrySet()) {
                    buf.writeUUID(entry.getKey());
                    buf.writeVarInt(entry.getValue());
                }
            },
            buf -> {
                int size = buf.readVarInt();
                Map<UUID, Integer> deathCounts = new java.util.HashMap<>();
                for (int i = 0; i < size; i++) {
                    deathCounts.put(buf.readUUID(), buf.readVarInt());
                }
                return new DeathCounterPacket(deathCounts);
            });

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DeathCounterPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientDeathCounterState.updateDeathCounts(packet.deathCounts());
        });
    }
}