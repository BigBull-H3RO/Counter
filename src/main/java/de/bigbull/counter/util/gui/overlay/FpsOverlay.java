package de.bigbull.counter.util.gui.overlay;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import de.bigbull.counter.util.gui.GuiEditScreen;
import de.bigbull.counter.util.gui.OverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;

public class FpsOverlay {
    public static void render(GuiGraphicsExtractor guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.gui.screen() instanceof GuiEditScreen;
        GuiEditScreen guiEditScreen = isEditMode ? (GuiEditScreen) minecraft.gui.screen() : null;

        if (minecraft.level == null || player == null) {
            return;
        }
        if (!ServerConfig.SHOW_FPS_OVERLAY.get() || !ServerConfig.ENABLE_FPS_COUNTER.get()) {
            return;
        }

        float scale = ClientConfig.FPS_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.ensureAlphaChannel(ClientConfig.FPS_OVERLAY_TEXT_COLOR.get());
        int width = calcFpsWidth();
        int height = calcFpsHeight();

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
                (g, pos) -> {
                    String fpsString = CounterManager.getFps();
                    g.text(minecraft.font, fpsString, pos.x(), pos.y(), textColor);
                }
        );
    }

    public static int calcFpsWidth() {
        Minecraft mc = Minecraft.getInstance();
        return mc.font.width(CounterManager.getFps());
    }

    public static int calcFpsHeight() {
        Minecraft mc = Minecraft.getInstance();
        return mc.font.lineHeight;
    }
}
