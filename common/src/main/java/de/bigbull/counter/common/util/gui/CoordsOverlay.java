package de.bigbull.counter.common.util.gui;

import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.config.IClientConfig;
import de.bigbull.counter.common.config.IServerConfig;
import de.bigbull.counter.common.util.CounterManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;

public class CoordsOverlay {
    static IClientConfig clientConfig = ConfigHelper.get().getClient();
    static IServerConfig serverConfig = ConfigHelper.get().getServer();

    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof OverlayEditScreen;
        OverlayEditScreen editScreen = isEditMode ? (OverlayEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }

        if (!serverConfig.enableCoordsCounter() || !serverConfig.showCoordsOverlay()) {
            return;
        }

        boolean allowCoordsOverlay = clientConfig.showCoordsOverlayAlways() || CounterManager.isTabPressed() || isEditMode;
        if (!allowCoordsOverlay) return;

        boolean showCoords = (clientConfig.showCoordsOverlayAlways() && clientConfig.showCoordsOverlay())
                || isEditMode
                || (CounterManager.isTabPressed() && clientConfig.showCoordsOverlay());

        if (!showCoords) return;

        float scale = (float) clientConfig.coordsOverlaySize();
        int textColor = clientConfig.coordsOverlayTextColor();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int x = (int) Math.round(clientConfig.coordsOverlayX() * screenWidth);
        int y = (int) Math.round(clientConfig.coordsOverlayY() * screenHeight);
        int maxX = screenWidth - (int) (calcCoordsWidth() * scale);
        int maxY = screenHeight - (int) (calcCoordsHeight() * scale);

        x = Mth.clamp(x, 0, Math.max(0, maxX));
        y = Mth.clamp(y, 0, Math.max(0, maxY));

        String coordsText = getCoordsText(player);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.drawString(minecraft.font, coordsText, (int) (x / scale), (int) (y / scale), textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            int iconColor = clientConfig.showCoordsOverlay() ? 0xFF00FF00 : 0xFFFF0000;
            int iconSize = 6;
            int iconX = x + calcCoordsWidth() + 5;
            int iconY = y + (calcCoordsHeight() / 2) - (iconSize / 2);

            guiGraphics.fill(iconX, iconY, iconX + iconSize, iconY + iconSize, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.COORDS) {
                CounterManager.getdrawBorder(guiGraphics, x, y, calcCoordsWidth(), calcCoordsHeight(), 0xFFFFFF00, 3);
            } else {
                CounterManager.getdrawBorder(guiGraphics, x, y, calcCoordsWidth(), calcCoordsHeight(), 0xFFFF0000, 3);
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
        float scale = (float) clientConfig.coordsOverlaySize();
        String text = getCoordsText(mc.player);
        return (int) (mc.font.width(text) * scale);
    }

    public static int calcCoordsHeight() {
        Minecraft mc = Minecraft.getInstance();
        float scale = (float) clientConfig.coordsOverlaySize();
        return (int) (mc.font.lineHeight * scale);
    }
}
