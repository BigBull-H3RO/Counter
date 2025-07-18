package de.bigbull.counter.event;

import de.bigbull.counter.util.ModKeybinds;
import de.bigbull.counter.util.gui.GuiEditScreen;
import de.bigbull.counter.util.gui.overlay.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class ClientEvents {
    public static void registerEvents() {
        HudRenderCallback.EVENT.register(((guiGraphics, deltaTracker) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;

            if (minecraft.level == null || player == null) {
                return;
            }

            if (minecraft.screen instanceof GuiEditScreen) {
                return;
            }

            DayCounterOverlay.render(guiGraphics);
            DeathCounterOverlay.render(guiGraphics);
            SurvivalTimeOverlay.render(guiGraphics);
            TimeOverlay.render(guiGraphics);
            CoordsOverlay.render(guiGraphics);
        }));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ModKeybinds.OPEN_EDIT_GUI.consumeClick()) {
                client.setScreen(new GuiEditScreen());
            }
        });
    }
}