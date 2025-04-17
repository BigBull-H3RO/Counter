package de.bigbull.counter.fabric.events;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.Events.ModGameEvents;
import de.bigbull.counter.fabric.network.ConfigSyncManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;

public class FabricGameEvents {
    public static void registerEvents() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            Counter.registerCommands(dispatcher);
        }));

        ServerTickEvents.END_SERVER_TICK.register(ModGameEvents::onServerTick);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ModGameEvents.onPlayerLoggedIn(handler.getPlayer());

            ConfigSyncManager.syncConfigToPlayer(handler.getPlayer());
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, world) -> {
            if (entity instanceof ServerPlayer player) {
                ModGameEvents.onPlayerDeath(player);
            }
        });
    }
}
