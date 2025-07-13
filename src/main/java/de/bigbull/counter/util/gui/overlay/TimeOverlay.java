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

        float scale = ClientConfig.TIME_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.ensureAlphaChannel(ClientConfig.TIME_OVERLAY_TEXT_COLOR.get());
        int width = calcTimeWidth();
        int height = calcTimeHeight();

        OverlayRenderer.render(
                guiGraphics,
                ClientConfig.SHOW_TIME_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_TIME_OVERLAY.get(),
                isEditMode,
                guiEditScreen,
                scale,
                ClientConfig.TIME_OVERLAY_X.get(),
                ClientConfig.TIME_OVERLAY_Y.get(),
                ClientConfig.TIME_OVERLAY_ALIGN.get(),
                width,
                height,
                ClientConfig.SHOW_TIME_OVERLAY.get(),
                GuiEditScreen.DragTarget.TIME,
                0,
                0,
                0,
                0,
                (g, pos) -> g.drawString(minecraft.font, Component.literal(CounterManager.getTime()), pos.x(), pos.y(), textColor)
        );
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
