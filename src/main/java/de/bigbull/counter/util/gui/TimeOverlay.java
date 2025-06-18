package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import de.bigbull.counter.util.gui.OverlayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class TimeOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof OverlayEditScreen;
        OverlayEditScreen editScreen = isEditMode ? (OverlayEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }
        if (!ServerConfig.ENABLE_TIME_COUNTER.get() || !ServerConfig.SHOW_TIME_OVERLAY.get()) {
            return;
        }

        boolean allowTimeOverlay = ClientConfig.SHOW_TIME_OVERLAY_ALWAYS.get() || CounterManager.isTabPressed() || isEditMode;

        if (!allowTimeOverlay) return;

        boolean showTime = (ClientConfig.SHOW_TIME_OVERLAY_ALWAYS.get() && ClientConfig.SHOW_TIME_OVERLAY.get())
                || isEditMode
                || (CounterManager.isTabPressed() && ClientConfig.SHOW_TIME_OVERLAY.get());

        if (!showTime) return;

        float scale = ClientConfig.TIME_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.ensureAlphaChannel(ClientConfig.TIME_OVERLAY_TEXT_COLOR.get());
        int width = calcTimeWidth();
        int height = calcTimeHeight();
        OverlayUtils.Position pos = OverlayUtils.computePosition(
                ClientConfig.TIME_OVERLAY_X.get(),
                ClientConfig.TIME_OVERLAY_Y.get(),
                scale, width, height);

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().scale(scale, scale);

        int x = pos.x();
        int y = pos.y();
        int drawX = pos.drawX();
        int drawY = pos.drawY();

        guiGraphics.drawString(minecraft.font, Component.literal(CounterManager.getTime()), drawX, drawY, textColor);
        guiGraphics.pose().popMatrix();

        if (isEditMode) {
            int iconColor = ClientConfig.SHOW_TIME_OVERLAY.get() ? 0xFF00FF00 : 0xFFFF0000;
            int iconSize = 6;
            int iconX = x + calcTimeWidth() + 5;
            int iconY = y + (calcTimeHeight() / 2) - (iconSize / 2);

            guiGraphics.fill(iconX, iconY, iconX + iconSize, iconY + iconSize, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.TIME) {
                CounterManager.drawBorder(guiGraphics, x, y, calcTimeWidth(), calcTimeHeight(), 0xFFFFFF00, 3);
            } else {
                CounterManager.drawBorder(guiGraphics, x, y, calcTimeWidth(), calcTimeHeight(), 0xFFFF0000, 3);
            }
        }
    }

    public static int calcTimeWidth() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.TIME_OVERLAY_SIZE.get().floatValue();
        String text = CounterManager.getTime();
        return (int) (mc.font.width(text) * scale);
    }

    public static int calcTimeHeight() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.TIME_OVERLAY_SIZE.get().floatValue();
        return (int) (mc.font.lineHeight * scale);
    }
}
