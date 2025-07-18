package de.bigbull.counter.network;

import de.bigbull.counter.network.packet.DayCounterPacket;
import de.bigbull.counter.network.packet.DeathCounterPacket;
import de.bigbull.counter.network.packet.SurvivalTimePacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkHandler {
    public static void register() {
        PayloadTypeRegistry.playS2C().register(DayCounterPacket.TYPE, DayCounterPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(DeathCounterPacket.TYPE, DeathCounterPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SurvivalTimePacket.TYPE, SurvivalTimePacket.CODEC);
    }
}