package de.bigbull.counter.forge.events;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.Events.ClientEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Counter.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onRenderGui(CustomizeGuiOverlayEvent event) {
        ClientEvents.onRenderGui(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {
        ClientEvents.onClientTick();
    }
}
