package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
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

        boolean showCoords = OverlayUtils.shouldShowOverlay(
                ClientConfig.SHOW_COORDS_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_COORDS_OVERLAY.get(),
                isEditMode
        );

        if (!showCoords) return;

        float scale = ClientConfig.COORDS_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.COORDS_OVERLAY_TEXT_COLOR.get();
        int width = calcCoordsWidth();
        int height = calcCoordsHeight();
        OverlayUtils.Position pos = OverlayUtils.computePosition(
                ClientConfig.COORDS_OVERLAY_X.get(),
                ClientConfig.COORDS_OVERLAY_Y.get(),
                scale, width, height);

        String coordsText = getCoordsText(player);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.drawString(minecraft.font, coordsText, pos.drawX(), pos.drawY(), textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            int iconColor = ClientConfig.SHOW_COORDS_OVERLAY.get() ? 0xFF00FF00 : 0xFFFF0000;

            OverlayUtils.drawCornerIcons(guiGraphics, pos.x(), pos.y(), width, height, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.COORDS) {
                OverlayUtils.drawBorder(guiGraphics, pos.x(), pos.y(), calcCoordsWidth(), calcCoordsHeight(), 0xFFFFFF00, 3);
            } else {
                OverlayUtils.drawBorder(guiGraphics, pos.x(), pos.y(), calcCoordsWidth(), calcCoordsHeight(), 0xFFFF0000, 3);
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
