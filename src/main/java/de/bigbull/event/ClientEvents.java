package de.bigbull.event;

import de.bigbull.Counter;
import de.bigbull.util.gui.DayCounterOverlay;
import de.bigbull.util.gui.DeathCounterOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = Counter.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        GuiGraphics guiGraphics = event.getGuiGraphics();

        if (minecraft.level == null || player == null) {
            return;
        }

        DayCounterOverlay.render(guiGraphics);
        DeathCounterOverlay.render(guiGraphics);
    }
}
