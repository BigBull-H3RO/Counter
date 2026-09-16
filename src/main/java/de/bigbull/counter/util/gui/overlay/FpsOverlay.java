package de.bigbull.counter.util.gui.overlay;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import de.bigbull.counter.util.gui.GuiEditScreen;
import de.bigbull.counter.util.gui.OverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;

public class FpsOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof GuiEditScreen;
        GuiEditScreen guiEditScreen = isEditMode ? (GuiEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }

        if (!ServerConfig.ENABLE_FPS_COUNTER.get() || !ServerConfig.SHOW_FPS_OVERLAY.get()) {
            return;
        }

        float scale = ClientConfig.FPS_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.FPS_OVERLAY_TEXT_COLOR.get();
        int width = calcFpsWidth();
        int height = calcFpsHeight();

        String fpsText = CounterManager.getFps();

        OverlayRenderer.render(
                guiGraphics,
                ClientConfig.SHOW_FPS_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_FPS_OVERLAY.get(),
                isEditMode,
                guiEditScreen,
                scale,
                ClientConfig.FPS_OVERLAY_X.get(),
                ClientConfig.FPS_OVERLAY_Y.get(),
                ClientConfig.FPS_OVERLAY_ALIGN.get(),
                width,
                height,
                ClientConfig.SHOW_FPS_OVERLAY.get(),
                GuiEditScreen.DragTarget.FPS,
                0,
                0,
                0,
                0,
                (g, pos) -> g.drawString(minecraft.font, fpsText, pos.x(), pos.y(), textColor)
        );
    }

    public static int calcFpsWidth() {
        Minecraft mc = Minecraft.getInstance();
        String text = CounterManager.getFps();
        return mc.font.width(text);
    }

    public static int calcFpsHeight() {
        Minecraft mc = Minecraft.getInstance();
        return mc.font.lineHeight;
    }
}
