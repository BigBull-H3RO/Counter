package de.bigbull.counter.util.gui;

import net.minecraft.client.gui.GuiGraphics;

public class OverlayRenderer {
    @FunctionalInterface
    public interface Drawer {
        void draw(GuiGraphics g, OverlayUtils.Position pos);
    }

    public static void render(GuiGraphics g,
                              boolean showAlways,
                              boolean showEnabled,
                              boolean isEditMode,
                              GuiEditScreen guiEditScreen,
                              float scale,
                              double configX,
                              double configY,
                              int width,
                              int height,
                              boolean overlayEnabled,
                              GuiEditScreen.DragTarget target,
                              int offsetX,
                              int offsetY,
                              int extraWidth,
                              int extraHeight,
                              Drawer drawer) {
        if (!OverlayUtils.shouldShowOverlay(showAlways, showEnabled, isEditMode)) {
            return;
        }

        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);
        OverlayUtils.Position pos = OverlayUtils.computePosition(configX, configY, scale, scaledWidth, scaledHeight);

        g.pose().pushPose();
        g.pose().scale(scale, scale, 1.0F);
        drawer.draw(g, pos);
        g.pose().popPose();

        if (isEditMode) {
            int borderColor = overlayEnabled ? 0xFF00FF00 : 0xFFFF0000;
            if (guiEditScreen != null && guiEditScreen.getSelectedOverlay() == target) {
                borderColor = 0xFFFFFF00;
            }
            OverlayUtils.drawBorder(g,
                    pos.x() + offsetX,
                    pos.y() + offsetY,
                    scaledWidth + extraWidth,
                    scaledHeight + extraHeight,
                    borderColor,
                    3);
        }
    }
}