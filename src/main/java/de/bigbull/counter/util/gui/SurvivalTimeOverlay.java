package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.ClientCounterState;
import de.bigbull.counter.util.CounterManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class SurvivalTimeOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boolean isEditMode = minecraft.screen instanceof OverlayEditScreen;
        OverlayEditScreen editScreen = isEditMode ? (OverlayEditScreen) minecraft.screen : null;

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
        int width = calcWidth();
        int height = calcHeight();
        OverlayUtils.Position pos = OverlayUtils.computePosition(
                ClientConfig.SURVIVAL_OVERLAY_X.get(),
                ClientConfig.SURVIVAL_OVERLAY_Y.get(),
                scale, width, height);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);

        guiGraphics.drawString(minecraft.font, Component.literal(getSurvivalString()), pos.drawX(), pos.drawY(), textColor);
        guiGraphics.pose().popPose();

        if (isEditMode) {
            int iconColor = ClientConfig.SHOW_SURVIVAL_OVERLAY.get() ? 0xFF00FF00 : 0xFFFF0000;

            OverlayUtils.drawCornerIcons(guiGraphics, pos.x(), pos.y(), width, height, iconColor);

            if (editScreen.getSelectedOverlay() == OverlayEditScreen.DragTarget.SURVIVAL) {
                OverlayUtils.drawBorder(guiGraphics, pos.x(), pos.y(), calcWidth(), calcHeight(), 0xFFFFFF00, 3);
            } else {
                OverlayUtils.drawBorder(guiGraphics, pos.x(), pos.y(), calcWidth(), calcHeight(), 0xFFFF0000, 3);
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

    public static int calcWidth() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.SURVIVAL_OVERLAY_SIZE.get().floatValue();
        String text = getSurvivalString();
        return (int) (mc.font.width(text) * scale);
    }

    public static int calcHeight() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.SURVIVAL_OVERLAY_SIZE.get().floatValue();
        return (int) (mc.font.lineHeight * scale);
    }
}
