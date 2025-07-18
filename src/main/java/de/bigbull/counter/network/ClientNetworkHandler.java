package de.bigbull.counter.network;

import de.bigbull.counter.network.packet.DayCounterPacket;
import de.bigbull.counter.network.packet.DeathCounterPacket;
import de.bigbull.counter.network.packet.SurvivalTimePacket;
import de.bigbull.counter.util.ClientCounterState;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientNetworkHandler {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(DayCounterPacket.TYPE,
                (payload, context) -> {
                    context.client().execute(() -> {
                        ClientCounterState.setDayCounter(payload.dayCounter());
                    });
                });

        ClientPlayNetworking.registerGlobalReceiver(DeathCounterPacket.TYPE,
                (payload, context) -> {
                    context.client().execute(() -> {
                        ClientCounterState.updateDeathCounts(payload.deathCounts());
                        ClientCounterState.updateNameMap(payload.nameMap());
                        ClientCounterState.updateBestTimes(payload.bestTimes());
                    });
                });

        ClientPlayNetworking.registerGlobalReceiver(SurvivalTimePacket.TYPE,
                (payload, context) -> {
                    context.client().execute(() -> {
                        ClientCounterState.setLastDeathTick(payload.lastDeathTick());
                        ClientCounterState.setBestSurvivalTime(payload.bestTime());
                    });
                });
    }
}