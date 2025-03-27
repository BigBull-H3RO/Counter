package de.bigbull.counter.neoforge.events;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.Events.ModGameEvents;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Counter.MOD_ID)
public class NeoForgeGameEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        Counter.registerCommands(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        ModGameEvents.onServerTick(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ModGameEvents.onPlayerLoggedIn(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ModGameEvents.onPlayerDeath(player);
        }
    }
}
