package de.bigbull.counter.util.gui.overlay;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.ClientCounterState;
import de.bigbull.counter.util.CounterManager;
import de.bigbull.counter.util.gui.GuiEditScreen;
import de.bigbull.counter.util.gui.OverlayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class SurvivalTimeOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof GuiEditScreen;
        GuiEditScreen guiEditScreen = isEditMode ? (GuiEditScreen) minecraft.screen : null;

        if (minecraft.level == null || player == null) {
            return;
        }
        if (!ServerConfig.SHOW_SURVIVAL_OVERLAY.get() || !ServerConfig.ENABLE_SURVIVAL_COUNTER.get()) {
            return;
        }

        boolean show = OverlayUtils.shouldShowOverlay(
                ClientConfig.SHOW_SURVIVAL_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_SURVIVAL_OVERLAY.get(),
                isEditMode
        );

        if (!show) return;

        float scale = ClientConfig.SURVIVAL_OVERLAY_SIZE.get().floatValue();
        int textColor = ClientConfig.SURVIVAL_OVERLAY_TEXT_COLOR.get();
        int width = calcSurvivalWidth();
        int height = calcSurvivalHeight();
        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);
        OverlayUtils.Position pos = OverlayUtils.computePosition(
                ClientConfig.SURVIVAL_OVERLAY_X.get(),
                ClientConfig.SURVIVAL_OVERLAY_Y.get(),
                scale, scaledWidth, scaledHeight);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);

        guiGraphics.drawString(minecraft.font, Component.literal(getSurvivalString()), pos.drawX(), pos.drawY(), textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            int iconColor = ClientConfig.SHOW_SURVIVAL_OVERLAY.get() ? 0xFF00FF00 : 0xFFFF0000;

            OverlayUtils.drawCornerIcons(guiGraphics, pos.x(), pos.y(), scaledWidth, scaledHeight, iconColor);

            if (guiEditScreen.getSelectedOverlay() == GuiEditScreen.DragTarget.SURVIVAL) {
                OverlayUtils.drawBorder(guiGraphics, pos.x(), pos.y(), scaledWidth, scaledHeight, 0xFFFFFF00, 3);
            } else {
                OverlayUtils.drawBorder(guiGraphics, pos.x(), pos.y(), scaledWidth, scaledHeight, 0xFFFF0000, 3);
            }
        }
    }

    private static String getSurvivalString() {
        long currentTick = Minecraft.getInstance().level.getGameTime();
        long lastTick = ClientCounterState.getLastDeathTick();
        long survived = currentTick - lastTick;
        String base = CounterManager.formatSurvivalTime(survived);
        if (ServerConfig.SHOW_BEST_SURVIVAL_TIME.get()) {
            String best = CounterManager.formatSurvivalTime(ClientCounterState.getBestSurvivalTime());
            base += " (" + best + ")";
        }
        String key = ClientConfig.SHOW_EMOJIS.get() ? "overlay.counter.survival_with_emoji" : "overlay.counter.survival_no_emoji";
        return Component.translatable(key, base).getString();
    }

    public static int calcSurvivalWidth() {
        Minecraft mc = Minecraft.getInstance();
        String text = getSurvivalString();
        return mc.font.width(text);
    }

    public static int calcSurvivalHeight() {
        Minecraft mc = Minecraft.getInstance();
        return mc.font.lineHeight;
    }
}
