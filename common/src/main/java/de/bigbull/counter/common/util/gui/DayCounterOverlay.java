package de.bigbull.counter.common.util.gui;

import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.config.IClientConfig;
import de.bigbull.counter.common.config.IServerConfig;
import de.bigbull.counter.common.util.CounterManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class DayCounterOverlay {
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
        if (!serverConfig.showDayOverlay() || !serverConfig.enabledDayCounter()) {
            return;
        }

        boolean allowDayOverlay = clientConfig.showDayOverlayAlways() || CounterManager.isTabPressed() || isEditMode;

        if (!allowDayOverlay) return;

        boolean showDay = (clientConfig.showDayOverlayAlways() && clientConfig.showDayOverlay())
                || isEditMode
                || (CounterManager.isTabPressed() && clientConfig.showDayOverlay());

        if (!showDay) return;

        float scale = (float) clientConfig.dayOverlaySize();
        int textColor = clientConfig.dayOverlayTextColor();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int x = (int) Math.round(clientConfig.dayOverlayX() * screenWidth);
        int y = (int) Math.round(clientConfig.dayOverlayY() * screenHeight);
        int maxX = screenWidth - (int) (calcDayWidth() * scale);
        int maxY = screenHeight - (int) (calcDayHeight() * scale);

        x = Mth.clamp(x, 0, Math.max(0, maxX));
        y = Mth.clamp(y, 0, Math.max(0, maxY));

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);

        int drawX = (int) (x / scale);
        int drawY = (int) (y / scale);

        String dayString;

        if (serverConfig.showCombinedDayTime() && serverConfig.enableTimeCounter()) {
            dayString = Component.literal(CounterManager.getCombinedDayTime()).getString();

            if (serverConfig.showCombinedDayTime()) {
                serverConfig.setShowTimeOverlay(false);
            }
        } else {
            dayString = Component.literal(CounterManager.getDay()).getString();
        }

        guiGraphics.drawString(minecraft.font, dayString, drawX, drawY, textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            int iconColor = clientConfig.showDayOverlay() ? 0xFF00FF00 : 0xFFFF0000;
            int iconSize = 6;
            int iconX = x + calcDayWidth() + 5;
            int iconY = y + (calcDayHeight() / 2) - (iconSize / 2);

            guiGraphics.fill(iconX, iconY, iconX + iconSize, iconY + iconSize, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.DAY) {
                CounterManager.getdrawBorder(guiGraphics, x, y, calcDayWidth(), calcDayHeight(), 0xFFFFFF00, 3);
            } else {
                CounterManager.getdrawBorder(guiGraphics, x, y, calcDayWidth(), calcDayHeight(), 0xFFFF0000, 3);
            }
        }
    }

    public static int calcDayWidth() {
        Minecraft mc = Minecraft.getInstance();
        float scale = (float) clientConfig.dayOverlaySize();
        String text;
        if (serverConfig.showCombinedDayTime() && serverConfig.enableTimeCounter()) {
            text = Component.literal(CounterManager.getCombinedDayTime()).getString();
        } else {
            text = Component.literal(CounterManager.getDay()).getString();
        }
        return (int) (mc.font.width(text) * scale);
    }

    public static int calcDayHeight() {
        Minecraft mc = Minecraft.getInstance();
        float scale = (float) clientConfig.dayOverlaySize();
        int line = mc.font.lineHeight;
        return (int)(line * scale);
    }
}