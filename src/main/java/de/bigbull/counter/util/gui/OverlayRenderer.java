package de.bigbull.counter.util.gui;

import net.minecraft.client.Minecraft;
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
                              OverlayAlignment align,
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

        float scaledWidthF = width * scale;
        float scaledHeightF = height * scale;
        int scaledWidth = Math.round(scaledWidthF);
        int scaledHeight = Math.round(scaledHeightF);
        OverlayUtils.Position pos = OverlayUtils.computePosition(configX, configY, scaledWidth, scaledHeight, align);

        g.pose().pushPose();
        g.pose().translate(pos.x(), pos.y(), 0);
        g.pose().scale(scale, scale, 1.0F);
        drawer.draw(g, new OverlayUtils.Position(pos.x(), pos.y(), 0, 0));
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

            String symbol = switch (align) {
                case LEFT -> "◀";
                case CENTER -> "●";
                case RIGHT -> "▶";
            };

            float symbolScale = Math.min(0.5f * scale, 0.6f);
            int symbolWidth = Minecraft.getInstance().font.width(symbol);
            int lineHeight = Minecraft.getInstance().font.lineHeight;

            float symbolOffsetX = switch (align) {
                case LEFT -> 0f;
                case CENTER -> (scaledWidthF - symbolWidth * symbolScale) / 2f;
                case RIGHT -> scaledWidthF - symbolWidth * symbolScale;
            };

            float symbolOffsetY = scaledHeightF - lineHeight * symbolScale + (4 * scale);

            g.pose().pushPose();
            g.pose().translate(pos.x() + symbolOffsetX, pos.y() + symbolOffsetY, 0);
            g.pose().scale(symbolScale, symbolScale, 1.0f);
            g.drawString(Minecraft.getInstance().font, symbol, 0, 0, 0xFFFFFF);
            g.pose().popPose();
        }
    }
}