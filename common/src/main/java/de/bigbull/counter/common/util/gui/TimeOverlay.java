package de.bigbull.counter.common.util.gui;

import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.config.IClientConfig;
import de.bigbull.counter.common.util.CounterManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class TimeOverlay {
    static IClientConfig client = ConfigHelper.get().getClient();
    
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof OverlayEditScreen;
        OverlayEditScreen editScreen = isEditMode ? (OverlayEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }
        if (!ConfigHelper.get().getServer().enableTimeCounter() || !ConfigHelper.get().getServer().showTimeOverlay()) {
            return;
        }

        boolean allowTimeOverlay = client.showTimeOverlayAlways() || CounterManager.isTabPressed() || isEditMode;

        if (!allowTimeOverlay) return;

        boolean showTime = (client.showTimeOverlayAlways() && client.showTimeOverlay())
                || isEditMode
                || (CounterManager.isTabPressed() && client.showTimeOverlay());

        if (!showTime) return;

        float scale = (float) client.timeOverlaySize();
        int textColor = client.timeOverlayTextColor();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int x = (int) Math.round(client.timeOverlayX() * screenWidth);
        int y = (int) Math.round(client.timeOverlayY() * screenHeight);
        int maxX = screenWidth - (int) (calcTimeWidth() * scale);
        int maxY = screenHeight - (int) (calcTimeHeight() * scale);

        x = Mth.clamp(x, 0, Math.max(0, maxX));
        y = Mth.clamp(y, 0, Math.max(0, maxY));

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);

        int drawX = (int) (x / scale);
        int drawY = (int) (y / scale);

        guiGraphics.drawString(minecraft.font, Component.literal(CounterManager.getTime()), drawX, drawY, textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            int iconColor = client.showTimeOverlay() ? 0xFF00FF00 : 0xFFFF0000;
            int iconSize = 6;
            int iconX = x + calcTimeWidth() + 5;
            int iconY = y + (calcTimeHeight() / 2) - (iconSize / 2);

            guiGraphics.fill(iconX, iconY, iconX + iconSize, iconY + iconSize, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.TIME) {
                CounterManager.getdrawBorder(guiGraphics, x, y, calcTimeWidth(), calcTimeHeight(), 0xFFFFFF00, 3);
            } else {
                CounterManager.getdrawBorder(guiGraphics, x, y, calcTimeWidth(), calcTimeHeight(), 0xFFFF0000, 3);
            }
        }
    }

    public static int calcTimeWidth() {
        Minecraft mc = Minecraft.getInstance();
        float scale = (float) client.timeOverlaySize();
        String text = CounterManager.getTime();
        return (int) (mc.font.width(text) * scale);
    }

    public static int calcTimeHeight() {
        Minecraft mc = Minecraft.getInstance();
        float scale = (float) client.timeOverlaySize();
        return (int) (mc.font.lineHeight * scale);
    }
}
