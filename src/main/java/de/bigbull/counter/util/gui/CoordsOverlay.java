package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import de.bigbull.counter.util.gui.OverlayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;

public class CoordsOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof OverlayEditScreen;
        OverlayEditScreen editScreen = isEditMode ? (OverlayEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }

        if (!ServerConfig.ENABLE_COORDS_COUNTER.get() || !ServerConfig.SHOW_COORDS_OVERLAY.get()) {
            return;
        }

        boolean allowCoordsOverlay = ClientConfig.SHOW_COORDS_OVERLAY_ALWAYS.get() || CounterManager.isTabPressed() || isEditMode;
        if (!allowCoordsOverlay) return;

        boolean showCoords = (ClientConfig.SHOW_COORDS_OVERLAY_ALWAYS.get() && ClientConfig.SHOW_COORDS_OVERLAY.get())
                || isEditMode
                || (CounterManager.isTabPressed() && ClientConfig.SHOW_COORDS_OVERLAY.get());

        if (!showCoords) return;

        float scale = ClientConfig.COORDS_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.ensureAlphaChannel(ClientConfig.COORDS_OVERLAY_TEXT_COLOR.get());
        int width = calcCoordsWidth();
        int height = calcCoordsHeight();
        OverlayUtils.Position pos = OverlayUtils.computePosition(
                ClientConfig.COORDS_OVERLAY_X.get(),
                ClientConfig.COORDS_OVERLAY_Y.get(),
                scale, width, height);

        int x = pos.x();
        int y = pos.y();

        String coordsText = getCoordsText(player);

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().scale(scale, scale);
        guiGraphics.drawString(minecraft.font, coordsText, pos.drawX(), pos.drawY(), textColor);
        guiGraphics.pose().popMatrix();

        if (isEditMode) {
            int iconColor = ClientConfig.SHOW_COORDS_OVERLAY.get() ? 0xFF00FF00 : 0xFFFF0000;
            int iconSize = 6;
            int iconX = x + calcCoordsWidth() + 5;
            int iconY = y + (calcCoordsHeight() / 2) - (iconSize / 2);

            guiGraphics.fill(iconX, iconY, iconX + iconSize, iconY + iconSize, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.COORDS) {
                CounterManager.drawBorder(guiGraphics, x, y, calcCoordsWidth(), calcCoordsHeight(), 0xFFFFFF00, 3);
            } else {
                CounterManager.drawBorder(guiGraphics, x, y, calcCoordsWidth(), calcCoordsHeight(), 0xFFFF0000, 3);
            }
        }
    }

    private static String getCoordsText(LocalPlayer player) {
        int x = Mth.floor(player.getX());
        int y = Mth.floor(player.getY());
        int z = Mth.floor(player.getZ());

        return String.format("%d, %d, %d", x, y, z);
    }

    public static int calcCoordsWidth() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.COORDS_OVERLAY_SIZE.get().floatValue();
        String text = getCoordsText(mc.player);
        return (int) (mc.font.width(text) * scale);
    }

    public static int calcCoordsHeight() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.COORDS_OVERLAY_SIZE.get().floatValue();
        return (int) (mc.font.lineHeight * scale);
    }
}
