package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.ClientCounterState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

public class DayCounterOverlay {
    static String dayKey = ClientConfig.SHOW_EMOJIS.get()
            ? "overlay.counter.day_with_emoji"
            : "overlay.counter.day_no_emoji";

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

        boolean allowDayOverlay = ClientConfig.SHOW_DAY_OVERLAY_ALWAYS.get() || isTabPressed() || isEditMode;

        if (!allowDayOverlay) return;

        boolean showDay = (ClientConfig.SHOW_DAY_OVERLAY_ALWAYS.get() && ClientConfig.SHOW_DAY_OVERLAY.get())
                || isEditMode
                || (isTabPressed() && ClientConfig.SHOW_DAY_OVERLAY.get());

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

        guiGraphics.drawString(minecraft.font, Component.translatable(
                dayKey, ClientCounterState.getDayCounter()), drawX, drawY, textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            int iconColor = ClientConfig.SHOW_DAY_OVERLAY.get() ? 0xFF00FF00 : 0xFFFF0000;
            int iconSize = 6;
            int iconX = x + calcDayWidth() + 5;
            int iconY = y + (calcDayHeight() / 2) - (iconSize / 2);

            guiGraphics.fill(iconX, iconY, iconX + iconSize, iconY + iconSize, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.DAY) {
                drawBorder(guiGraphics, x, y, calcDayWidth(), calcDayHeight(), 0xFFFFFF00);
            } else {
                drawBorder(guiGraphics, x, y, calcDayWidth(), calcDayHeight(), 0xFFFF0000);
            }
        }
    }

    private static void drawBorder(GuiGraphics g, int x, int y, int w, int h, int color) {
        int borderPadding = 3;

        g.fill(x - borderPadding, y - borderPadding, x + w + borderPadding, y - borderPadding + 1, color);
        g.fill(x - borderPadding, y + h + borderPadding - 1, x + w + borderPadding, y + h + borderPadding, color);
        g.fill(x - borderPadding, y - borderPadding, x - borderPadding + 1, y + h + borderPadding, color);
        g.fill(x + w + borderPadding - 1, y - borderPadding, x + w + borderPadding, y + h + borderPadding, color);
    }

    private static boolean isTabPressed() {
        Minecraft minecraft = Minecraft.getInstance();
        return GLFW.glfwGetKey(minecraft.getWindow().getWindow(), GLFW.GLFW_KEY_TAB) == GLFW.GLFW_PRESS
                && minecraft.screen == null;
    }

    public static int calcDayWidth() {
        Minecraft mc = Minecraft.getInstance();
        long days = ClientCounterState.getDayCounter();
        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        String text = Component.translatable(dayKey, days).getString();
        return (int) (mc.font.width(text) * scale);
    }

    public static int calcDayHeight() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        int line = mc.font.lineHeight;
        return (int)(line * scale);
    }
}