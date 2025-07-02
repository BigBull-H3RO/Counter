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

public class TimeOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof GuiEditScreen;
        GuiEditScreen guiEditScreen = isEditMode ? (GuiEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }
        if (ServerConfig.SHOW_COMBINED_DAY_TIME.get()) {
            return;
        }
        if (!ServerConfig.ENABLE_TIME_COUNTER.get() || !ServerConfig.SHOW_TIME_OVERLAY.get()) {
            return;
        }

        boolean showTime = OverlayUtils.shouldShowOverlay(
                ClientConfig.SHOW_TIME_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_TIME_OVERLAY.get(),
                isEditMode
        );

        if (!showTime) return;

        float scale = ClientConfig.TIME_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.TIME_OVERLAY_TEXT_COLOR.get();
        int width = calcTimeWidth();
        int height = calcTimeHeight();
        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);
        OverlayUtils.Position pos = OverlayUtils.computePosition(
                ClientConfig.TIME_OVERLAY_X.get(),
                ClientConfig.TIME_OVERLAY_Y.get(),
                scale, scaledWidth, scaledHeight);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);

        guiGraphics.drawString(minecraft.font, Component.literal(CounterManager.getTime()), pos.drawX(), pos.drawY(), textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            boolean enabled = ClientConfig.SHOW_TIME_OVERLAY.get();
            int borderColor = enabled ? 0xFF00FF00 : 0xFFFF0000;

            if (guiEditScreen.getSelectedOverlay() == GuiEditScreen.DragTarget.TIME) {
                borderColor = 0xFFFFFF00;
            }

            OverlayUtils.drawBorder(guiGraphics, pos.x(), pos.y(), scaledWidth, scaledHeight, borderColor, 3);
        }
    }

    public static int calcTimeWidth() {
        Minecraft mc = Minecraft.getInstance();
        String text = CounterManager.getTime();
        return mc.font.width(text);
    }

    public static int calcTimeHeight() {
        Minecraft mc = Minecraft.getInstance();
        return mc.font.lineHeight;
    }
}
