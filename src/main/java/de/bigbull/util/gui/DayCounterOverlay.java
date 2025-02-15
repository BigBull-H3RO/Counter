package de.bigbull.util.gui;

import de.bigbull.config.ConfigValues;
import de.bigbull.network.client.ClientDayCounterState;
import de.bigbull.network.client.ClientDayOverlayState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class DayCounterOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (minecraft.level == null || player == null) {
            return;
        }

        if (!ConfigValues.SHOW_DAY_OVERLAY || !ConfigValues.ENABLE_DAY_COUNTER) {
            return;
        }

        if (!ClientDayOverlayState.isOverlayEnabled(player)) {
            return;
        }

        long days = ClientDayCounterState.getDayCounter();
        int x = ConfigValues.DAY_OVERLAY_X;
        int y = ConfigValues.DAY_OVERLAY_Y;
        float scale = (float) ConfigValues.DAY_OVERLAY_SIZE;
        int color = 0xFFFFFF;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.day", days), (int) (x / scale), (int) (y / scale), color);
        guiGraphics.pose().popPose();
    }
}