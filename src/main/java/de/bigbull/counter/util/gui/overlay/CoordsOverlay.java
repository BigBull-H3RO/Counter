package de.bigbull.counter.util.gui.overlay;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.gui.GuiEditScreen;
import de.bigbull.counter.util.gui.OverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;

public class CoordsOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof GuiEditScreen;
        GuiEditScreen guiEditScreen = isEditMode ? (GuiEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }

        if (!ServerConfig.ENABLE_COORDS_COUNTER.get() || !ServerConfig.SHOW_COORDS_OVERLAY.get()) {
            return;
        }

        float scale = ClientConfig.COORDS_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.COORDS_OVERLAY_TEXT_COLOR.get();
        int width = calcCoordsWidth();
        int height = calcCoordsHeight();

        String coordsText = getCoordsText(player);

        OverlayRenderer.render(
                guiGraphics,
                ClientConfig.SHOW_COORDS_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_COORDS_OVERLAY.get(),
                isEditMode,
                guiEditScreen,
                scale,
                ClientConfig.COORDS_OVERLAY_X.get(),
                ClientConfig.COORDS_OVERLAY_Y.get(),
                width,
                height,
                ClientConfig.SHOW_COORDS_OVERLAY.get(),
                GuiEditScreen.DragTarget.COORDS,
                0,
                0,
                0,
                0,
                (g, pos) -> g.drawString(minecraft.font, coordsText, pos.drawX(), pos.drawY(), textColor)
        );
    }

    private static String getCoordsText(LocalPlayer player) {
        int x = Mth.floor(player.getX());
        int y = Mth.floor(player.getY());
        int z = Mth.floor(player.getZ());

        return String.format("%d, %d, %d", x, y, z);
    }

    public static int calcCoordsWidth() {
        Minecraft mc = Minecraft.getInstance();
        String text = getCoordsText(mc.player);
        return mc.font.width(text);
    }

    public static int calcCoordsHeight() {
        Minecraft mc = Minecraft.getInstance();
        return mc.font.lineHeight;
    }
}
