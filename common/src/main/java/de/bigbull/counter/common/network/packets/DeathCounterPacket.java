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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record DeathCounterPacket(Map<UUID, Integer> deathCounts, Map<UUID, String> nameMap) implements PacketBase {
    public static final Type<DeathCounterPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Counter.MOD_ID, "death_counter"));

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

                return new DeathCounterPacket(deathCounts, nameMap);
            });

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handle(Player player) {
        Minecraft.getInstance().execute(() -> {
            ClientCounterState.updateDeathCounts(this.deathCounts());
            ClientCounterState.updateNameMap(this.nameMap());
        });
    }
}