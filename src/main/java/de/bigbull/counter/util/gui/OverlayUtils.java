package de.bigbull.counter.util.gui;

import de.bigbull.counter.util.CounterManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class OverlayUtils {
    public record Position(int x, int y, int drawX, int drawY) {
    }

    public static Position computePosition(double configX, double configY, float scale, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int x = (int) Math.round(configX * screenWidth);
        int y = (int) Math.round(configY * screenHeight);
        int maxX = screenWidth - width;
        int maxY = screenHeight - height;

        x = Mth.clamp(x, 0, Math.max(0, maxX));
        y = Mth.clamp(y, 0, Math.max(0, maxY));

        int drawX = (int) (x / scale);
        int drawY = (int) (y / scale);

        return new Position(x, y, drawX, drawY);
    }

    public static boolean shouldShowOverlay(boolean showAlways, boolean showEnabled, boolean isEditMode) {
        boolean allow = showAlways || CounterManager.isOverlayKeyPressed() || isEditMode;
        if (!allow) return false;

        return (showAlways && showEnabled)
                || isEditMode
                || (CounterManager.isOverlayKeyPressed() && showEnabled);
    }

    public static void drawBorder(GuiGraphics g, int x, int y, int w, int h, int color, int borderPadding) {
        g.fill(x - borderPadding, y - borderPadding, x + w + borderPadding, y - borderPadding + 1, color);
        g.fill(x - borderPadding, y + h + borderPadding - 1, x + w + borderPadding, y + h + borderPadding, color);
        g.fill(x - borderPadding, y - borderPadding, x - borderPadding + 1, y + h + borderPadding, color);
        g.fill(x + w + borderPadding - 1, y - borderPadding, x + w + borderPadding, y + h + borderPadding, color);
    }
}