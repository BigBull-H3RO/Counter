package de.bigbull.counter.event;

import de.bigbull.counter.Counter;
import de.bigbull.counter.util.ModKeybinds;
import de.bigbull.counter.util.gui.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = Counter.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (event == null || event.getGuiGraphics() == null) {
            return;
        }
        
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null) {
            return;
        }
        
        LocalPlayer player = minecraft.player;
        GuiGraphics guiGraphics = event.getGuiGraphics();

        if (minecraft.level == null || player == null) {
            return;
        }

        if (minecraft.screen instanceof OverlayEditScreen) {
            return;
        }

        try {
            DayCounterOverlay.render(guiGraphics);
            DeathCounterOverlay.render(guiGraphics);
            TimeOverlay.render(guiGraphics);
            CoordsOverlay.render(guiGraphics);
        } catch (Exception e) {
            Counter.logger.error("Error rendering overlays", e);
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        try {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft == null) {
                return;
            }
            
            if (ModKeybinds.OPEN_EDIT_GUI.consumeClick()) {
                minecraft.setScreen(new OverlayEditScreen());
            }
        } catch (Exception e) {
            Counter.logger.error("Error in client tick", e);
        }
    }
}