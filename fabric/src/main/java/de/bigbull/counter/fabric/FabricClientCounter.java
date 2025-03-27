package de.bigbull.counter.fabric;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.network.PacketBase;
import de.bigbull.counter.fabric.events.FabricClientEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;

public class FabricClientCounter implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricClientEvents.registerEvents();
        Counter.registerClientPackets(true);
    }

    public static <T extends PacketBase> void registerClientboundPacket(CustomPacketPayload.Type<T> id, BiConsumer<T, Player> handler) {
        ClientPlayNetworking.registerGlobalReceiver(id, (T packet, ClientPlayNetworking.Context context) -> {
            context.client().execute(() -> {
                handler.accept(packet, context.player());
            });
        });
    }
}
