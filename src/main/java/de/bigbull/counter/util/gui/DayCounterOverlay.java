package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

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

        boolean allowDayOverlay = ClientConfig.SHOW_DAY_OVERLAY_ALWAYS.get() || CounterManager.isTabPressed() || isEditMode;

        if (!allowDayOverlay) return;

        boolean showDay = (ClientConfig.SHOW_DAY_OVERLAY_ALWAYS.get() && ClientConfig.SHOW_DAY_OVERLAY.get())
                || isEditMode
                || (CounterManager.isTabPressed() && ClientConfig.SHOW_DAY_OVERLAY.get());

        if (!showDay) return;

        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.DAY_OVERLAY_TEXT_COLOR.get();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int x = (int) Math.round(ClientConfig.DAY_OVERLAY_X.get() * screenWidth);
        int y = (int) Math.round(ClientConfig.DAY_OVERLAY_Y.get() * screenHeight);
        int maxX = screenWidth - (int) (calcDayWidth() * scale);
        int maxY = screenHeight - (int) (calcDayHeight() * scale);

        x = Mth.clamp(x, 0, Math.max(0, maxX));
        y = Mth.clamp(y, 0, Math.max(0, maxY));

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);

        int drawX = (int) (x / scale);
        int drawY = (int) (y / scale);

        String dayString;

        if (ServerConfig.SHOW_COMBINED_DAY_TIME.get() && ServerConfig.ENABLE_TIME_Counter.get()) {
            dayString = Component.literal(CounterManager.getCombinedDayTime()).getString();

            if (ServerConfig.SHOW_COMBINED_DAY_TIME.get()) {
                ServerConfig.SHOW_TIME_OVERLAY.set(false);
            }
        } else {
            dayString = Component.literal(CounterManager.getDay()).getString();
        }

        guiGraphics.drawString(minecraft.font, dayString, drawX, drawY, textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            int iconColor = ClientConfig.SHOW_DAY_OVERLAY.get() ? 0xFF00FF00 : 0xFFFF0000;
            int iconSize = 6;
            int iconX = x + calcDayWidth() + 5;
            int iconY = y + (calcDayHeight() / 2) - (iconSize / 2);

            guiGraphics.fill(iconX, iconY, iconX + iconSize, iconY + iconSize, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.DAY) {
                CounterManager.getdrawBorder(guiGraphics, x, y, calcDayWidth(), calcDayHeight(), 0xFFFFFF00, 3);
            } else {
                CounterManager.getdrawBorder(guiGraphics, x, y, calcDayWidth(), calcDayHeight(), 0xFFFF0000, 3);
            }
        }
    }

    public static int calcDayWidth() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        String text;
        if (ServerConfig.SHOW_COMBINED_DAY_TIME.get() && ServerConfig.ENABLE_TIME_Counter.get()) {
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