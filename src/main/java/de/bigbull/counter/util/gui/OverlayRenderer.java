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

        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);
        OverlayUtils.Position pos = OverlayUtils.computePosition(configX, configY, scale, scaledWidth, scaledHeight, align);

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

            int symbolWidth = Minecraft.getInstance().font.width(symbol);
            int symbolOffsetX = switch (align) {
                case LEFT -> 0;
                case CENTER -> (scaledWidth - symbolWidth) / 2 + 2; // +4 für 2 Pixel nach rechts bei 0.5f Skalierung
                case RIGHT -> scaledWidth - symbolWidth + 4; // +4 für 2 Pixel nach rechts bei 0.5f Skalierung
            };

            g.pose().pushPose();
            g.pose().scale(0.5f, 0.5f, 1.0f);
            g.drawString(Minecraft.getInstance().font, symbol,
                    (int)((pos.x() + symbolOffsetX) / 0.5f),
                    (int)((pos.y() + 7.5) / 0.5f),
                    0xFFFFFF);
            g.pose().popPose();
        }
    }
}