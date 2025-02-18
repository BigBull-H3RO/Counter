package de.bigbull.util.gui;

import de.bigbull.config.ClientConfig;
import de.bigbull.config.ServerConfig;
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

        if (!ServerConfig.SHOW_DAY_OVERLAY.get() || !ServerConfig.ENABLE_DAY_COUNTER.get()) {
            return;
        }

        if (!ClientDayOverlayState.isOverlayEnabled(player)) {
            return;
        }

        long days = ClientDayCounterState.getDayCounter();
        int x = ClientConfig.DAY_OVERLAY_X.get();
        int y = ClientConfig.DAY_OVERLAY_Y.get();
        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        int color = 0xFFFFFF;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.day", days), (int) (x / scale), (int) (y / scale), color);
        guiGraphics.pose().popPose();
    }
}