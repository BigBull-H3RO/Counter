package de.bigbull.counter.network;

import de.bigbull.counter.Counter;
import de.bigbull.counter.util.ClientCounterState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record DeathCounterPacket(Map<UUID, Integer> deathCounts, Map<UUID, String> nameMap,
                                 Map<UUID, Long> bestTimes) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DeathCounterPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MODID, "death_counter"));

    public static final StreamCodec<FriendlyByteBuf, DeathCounterPacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeVarInt(packet.deathCounts.size());
                for (Map.Entry<UUID, Integer> entry : packet.deathCounts.entrySet()) {
                    buf.writeUUID(entry.getKey());
                    buf.writeVarInt(entry.getValue());
                }

                buf.writeVarInt(packet.nameMap.size());
                for (var entry : packet.nameMap.entrySet()) {
                    buf.writeUUID(entry.getKey());
                    buf.writeUtf(entry.getValue());
                }

                buf.writeVarInt(packet.bestTimes.size());
                for (var entry : packet.bestTimes.entrySet()) {
                    buf.writeUUID(entry.getKey());
                    buf.writeLong(entry.getValue());
                }
            },
            buf -> {
                int size = buf.readVarInt();
                Map<UUID, Integer> deathCounts = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    deathCounts.put(buf.readUUID(), buf.readVarInt());
                }

                int size2 = buf.readVarInt();
                Map<UUID, String> nameMap = new HashMap<>();
                for (int i = 0; i < size2; i++) {
                    nameMap.put(buf.readUUID(), buf.readUtf());
                }

                int size3 = buf.readVarInt();
                Map<UUID, Long> bestTimes = new HashMap<>();
                for (int i = 0; i < size3; i++) {
                    bestTimes.put(buf.readUUID(), buf.readLong());
                }

                return new DeathCounterPacket(deathCounts, nameMap, bestTimes);
            });

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DeathCounterPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientCounterState.updateDeathCounts(packet.deathCounts());
            ClientCounterState.updateNameMap(packet.nameMap());
            ClientCounterState.updateBestTimes(packet.bestTimes());
        });
    }
}