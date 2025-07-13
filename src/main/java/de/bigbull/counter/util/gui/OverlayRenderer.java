package de.bigbull.counter.util.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class OverlayRenderer {
    private static final int TEXT_BOTTOM_PADDING = 1;
    private static final int BORDER_PADDING = 2;

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

        final float SYMBOL_SCALE = Math.min(scale, 1.0f);

        float scaledWidthF = width * scale;
        float scaledHeightF = height * scale;
        int scaledWidth = Math.round(scaledWidthF);
        int scaledHeight = Math.round(scaledHeightF);
        OverlayUtils.Position pos = OverlayUtils.computePosition(configX, configY, scaledWidth, scaledHeight, align);

        renderOverlayContent(g, pos, scale, drawer);

        if (isEditMode) {
            int borderColor = getBorderColor(overlayEnabled, guiEditScreen, target);
            drawOverlayBorder(g, pos, offsetX, offsetY, scaledWidth, scaledHeight, extraWidth, extraHeight, borderColor);

            renderAlignmentIndicators(g, pos, align, offsetX, offsetY, scaledWidthF, scaledHeightF, extraWidth, extraHeight, SYMBOL_SCALE);
        }
    }

    private static void renderOverlayContent(GuiGraphics g, OverlayUtils.Position pos, float scale, Drawer drawer) {
        g.pose().pushPose();
        g.pose().translate(pos.x(), pos.y(), 0);
        g.pose().scale(scale, scale, 1.0F);
        drawer.draw(g, new OverlayUtils.Position(0, 0));
        g.pose().popPose();
    }

    private static int getBorderColor(boolean overlayEnabled, GuiEditScreen guiEditScreen, GuiEditScreen.DragTarget target) {
        if (guiEditScreen != null && guiEditScreen.getSelectedOverlay() == target) {
            return 0xFFFFFF00;
        }
        return overlayEnabled ? 0xFF00FF00 : 0xFFFF0000;
    }

    private static void drawOverlayBorder(GuiGraphics g, OverlayUtils.Position pos, int offsetX, int offsetY,
                                          int scaledWidth, int scaledHeight, int extraWidth, int extraHeight, int borderColor) {
        OverlayUtils.drawBorder(g,
                pos.x() + offsetX,
                pos.y() + offsetY,
                scaledWidth + extraWidth,
                scaledHeight + extraHeight,
                borderColor,
                BORDER_PADDING);
    }

    private static void renderAlignmentIndicators(GuiGraphics g, OverlayUtils.Position pos, OverlayAlignment align,
                                                  int offsetX, int offsetY, float scaledWidthF, float scaledHeightF,
                                                  int extraWidth, int extraHeight, float symbolScale) {
        switch (align) {
            case RIGHT -> renderAlignmentSymbol(g, pos, "<", calculateLeftSymbolX(offsetX, symbolScale),
                    calculateSymbolY(offsetY, scaledHeightF, extraHeight, symbolScale), symbolScale);
            case LEFT ->
                    renderAlignmentSymbol(g, pos, ">", calculateRightSymbolX(offsetX, scaledWidthF, extraWidth),
                            calculateSymbolY(offsetY, scaledHeightF, extraHeight, symbolScale), symbolScale);
            case CENTER -> {
                renderAlignmentSymbol(g, pos, "<", calculateLeftSymbolX(offsetX, symbolScale),
                        calculateSymbolY(offsetY, scaledHeightF, extraHeight, symbolScale), symbolScale);
                renderAlignmentSymbol(g, pos, ">", calculateRightSymbolX(offsetX, scaledWidthF, extraWidth),
                        calculateSymbolY(offsetY, scaledHeightF, extraHeight, symbolScale), symbolScale);
            }
        }
    }

    private static void renderAlignmentSymbol(GuiGraphics g, OverlayUtils.Position pos, String symbol, float symbolX, float symbolY, float symbolScale) {
        g.pose().pushPose();
        g.pose().translate(pos.x() + symbolX, pos.y() + symbolY, 0);
        g.pose().scale(symbolScale, symbolScale, 1.0f);
        g.drawString(Minecraft.getInstance().font, symbol, 0, 0, 0xFFFFFF);
        g.pose().popPose();
    }

    private static float calculateLeftSymbolX(int offsetX, float symbolScale) {
        int symbolWidth = Minecraft.getInstance().font.width("<");
        return offsetX - BORDER_PADDING - symbolWidth * symbolScale - 1;
    }

    private static float calculateRightSymbolX(int offsetX, float scaledWidthF, int extraWidth) {
        return offsetX + scaledWidthF + extraWidth + BORDER_PADDING + 2;
    }

    private static float calculateSymbolY(int offsetY, float scaledHeightF, int extraHeight, float symbolScale) {
        int lineHeight = Minecraft.getInstance().font.lineHeight;
        return offsetY + (scaledHeightF + extraHeight - lineHeight * symbolScale) / 2f
                + TEXT_BOTTOM_PADDING / 2f;
    }
}