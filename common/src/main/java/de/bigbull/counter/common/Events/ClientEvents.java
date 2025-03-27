package de.bigbull.counter.common.Events;

import de.bigbull.counter.common.util.ModKeybinds;
import de.bigbull.counter.common.util.gui.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;

public class ClientEvents {
    public static void onRenderGui(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (minecraft.level == null || player == null) {
            return;
        }

        if (minecraft.screen instanceof OverlayEditScreen) {
            return;
        }

        DayCounterOverlay.render(guiGraphics);
        DeathCounterOverlay.render(guiGraphics);
        TimeOverlay.render(guiGraphics);
        CoordsOverlay.render(guiGraphics);
    }

    public static void onClientTick() {
        ModKeybinds.handleKeyInputs();
    }
}
