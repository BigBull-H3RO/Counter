package de.bigbull.counter.util.gui.overlay;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import de.bigbull.counter.util.gui.GuiEditScreen;
import de.bigbull.counter.util.gui.OverlayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class DayCounterOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof GuiEditScreen;
        GuiEditScreen guiEditScreen = isEditMode ? (GuiEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }
        if (!ServerConfig.SHOW_DAY_OVERLAY.get() || !ServerConfig.ENABLE_DAY_COUNTER.get()) {
            return;
        }

        boolean showDay = OverlayUtils.shouldShowOverlay(
                ClientConfig.SHOW_DAY_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_DAY_OVERLAY.get(),
                isEditMode
        );

        if (!showDay) return;

        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.DAY_OVERLAY_TEXT_COLOR.get();
        int width = calcDayWidth();
        int height = calcDayHeight();
        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);
        OverlayUtils.Position pos = OverlayUtils.computePosition(
                ClientConfig.DAY_OVERLAY_X.get(),
                ClientConfig.DAY_OVERLAY_Y.get(),
                scale, scaledWidth, scaledHeight);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);

        String dayString;

        if (ServerConfig.SHOW_COMBINED_DAY_TIME.get() && ServerConfig.ENABLE_TIME_COUNTER.get()) {
            dayString = Component.literal(CounterManager.getCombinedDayTime()).getString();
        } else {
            dayString = Component.literal(CounterManager.getDay()).getString();
        }

        guiGraphics.drawString(minecraft.font, dayString, pos.drawX(), pos.drawY(), textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            boolean enabled = ClientConfig.SHOW_DAY_OVERLAY.get();
            int borderColor = enabled ? 0xFF00FF00 : 0xFFFF0000;

            if (guiEditScreen.getSelectedOverlay() == GuiEditScreen.DragTarget.DAY) {
                borderColor = 0xFFFFFF00;
            }
            OverlayUtils.drawBorder(guiGraphics, pos.x(), pos.y(), scaledWidth, scaledHeight, borderColor, 3);
        }
    }

    public static int calcDayWidth() {
        Minecraft mc = Minecraft.getInstance();
        String text;
        if (ServerConfig.SHOW_COMBINED_DAY_TIME.get() && ServerConfig.ENABLE_TIME_COUNTER.get()) {
            text = Component.literal(CounterManager.getCombinedDayTime()).getString();
        } else {
            text = Component.literal(CounterManager.getDay()).getString();
        }
        return mc.font.width(text);
    }

    public static int calcDayHeight() {
        Minecraft mc = Minecraft.getInstance();
        return mc.font.lineHeight;
    }
}