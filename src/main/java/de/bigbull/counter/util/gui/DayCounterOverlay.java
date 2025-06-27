package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class DayCounterOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof OverlayEditScreen;
        OverlayEditScreen editScreen = isEditMode ? (OverlayEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }
        if (!ServerConfig.SHOW_DAY_OVERLAY.get() || !ServerConfig.ENABLE_DAY_COUNTER.get()) {
            return;
        }

        boolean showDay = OverlayUtils.shouldShowOverlay(
                ClientConfig.SHOW_DAY_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_DAY_OVERLAY.get(),
                isEditMode
        );

        if (!showDay) return;

        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.DAY_OVERLAY_TEXT_COLOR.get();
        int width = calcDayWidth();
        int height = calcDayHeight();
        OverlayUtils.Position pos = OverlayUtils.computePosition(
                ClientConfig.DAY_OVERLAY_X.get(),
                ClientConfig.DAY_OVERLAY_Y.get(),
                scale, width, height);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);

        String dayString;

        if (ServerConfig.SHOW_COMBINED_DAY_TIME.get() && ServerConfig.ENABLE_TIME_COUNTER.get()) {
            dayString = Component.literal(CounterManager.getCombinedDayTime()).getString();
        } else {
            dayString = Component.literal(CounterManager.getDay()).getString();
        }

        guiGraphics.drawString(minecraft.font, dayString, pos.drawX(), pos.drawY(), textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            int iconColor = ClientConfig.SHOW_DAY_OVERLAY.get() ? 0xFF00FF00 : 0xFFFF0000;
            int iconSize = 6;
            int iconX = pos.x() + calcDayWidth() + 5;
            int iconY = pos.y() + (calcDayHeight() / 2) - (iconSize / 2);

            guiGraphics.fill(iconX, iconY, iconX + iconSize, iconY + iconSize, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.DAY) {
                CounterManager.drawBorder(guiGraphics, pos.x(), pos.y(), calcDayWidth(), calcDayHeight(), 0xFFFFFF00, 3);
            } else {
                CounterManager.drawBorder(guiGraphics, pos.x(), pos.y(), calcDayWidth(), calcDayHeight(), 0xFFFF0000, 3);
            }
        }
    }

    public static int calcDayWidth() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        String text;
        if (ServerConfig.SHOW_COMBINED_DAY_TIME.get() && ServerConfig.ENABLE_TIME_COUNTER.get()) {
            text = Component.literal(CounterManager.getCombinedDayTime()).getString();
        } else {
            text = Component.literal(CounterManager.getDay()).getString();
        }
        return (int) (mc.font.width(text) * scale);
    }

    public static int calcDayHeight() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        int line = mc.font.lineHeight;
        return (int)(line * scale);
    }
}