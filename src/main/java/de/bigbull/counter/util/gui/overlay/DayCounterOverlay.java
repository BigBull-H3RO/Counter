package de.bigbull.counter.util.gui.overlay;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import de.bigbull.counter.util.gui.GuiEditScreen;
import de.bigbull.counter.util.gui.OverlayRenderer;
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

        float scale = ClientConfig.DAY_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.ensureAlphaChannel(ClientConfig.DAY_OVERLAY_TEXT_COLOR.get());
        int width = calcDayWidth();
        int height = calcDayHeight();

        OverlayRenderer.render(
                guiGraphics,
                ClientConfig.SHOW_DAY_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_DAY_OVERLAY.get(),
                isEditMode,
                guiEditScreen,
                scale,
                ClientConfig.DAY_OVERLAY_X.get(),
                ClientConfig.DAY_OVERLAY_Y.get(),
                ClientConfig.DAY_OVERLAY_ALIGN.get(),
                width,
                height,
                ClientConfig.SHOW_DAY_OVERLAY.get(),
                GuiEditScreen.DragTarget.DAY,
                0,
                0,
                0,
                0,
                (g, pos) -> {
                    String dayString;
                    if (ServerConfig.SHOW_COMBINED_DAY_TIME.get() && ServerConfig.ENABLE_TIME_COUNTER.get()) {
                        dayString = Component.literal(CounterManager.getCombinedDayTime()).getString();
                    } else {
                        dayString = Component.literal(CounterManager.getDay()).getString();
                    }
                    g.drawString(minecraft.font, dayString, pos.x(), pos.y(), textColor);
                }
        );
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