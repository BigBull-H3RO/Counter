package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.client.ClientCounterState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeathCounterOverlay {
    public static void render(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (minecraft.level == null || player == null || minecraft.getConnection() == null) return;

        if (!ServerConfig.SHOW_DEATH_OVERLAY.get() || !ServerConfig.ENABLE_DEATH_COUNTER.get()) return;

        boolean isEditMode = (minecraft.screen instanceof OverlayEditScreen);
        if (!ServerConfig.SHOW_DEATH_OVERLAY_ALWAYS.get() && !isTabPressed() && !isEditMode) {
            return;
        }

        boolean serverWantsList = false;
        boolean serverWantsSelf = false;
        if (isMultiplayerGame(minecraft)) {
            switch (ServerConfig.DEATH_OVERLAY_MODE.get()) {
                case LIST ->         serverWantsList = true;
                case ONLY_SELF ->    serverWantsSelf = true;
                case BOTH -> {
                    serverWantsList = true;
                    serverWantsSelf = true;
                }
            }
        } else {
            serverWantsSelf = true;
        }

        boolean showList = (ClientConfig.SHOW_DEATH_LIST_OVERLAY.get() || isEditMode);
        boolean showSelf = (ClientConfig.SHOW_DEATH_SELF_OVERLAY.get() || isEditMode);

        boolean finalDrawList = (serverWantsList && showList);
        boolean finalDrawSelf = (serverWantsSelf && showSelf);

        if (!finalDrawList && !finalDrawSelf) {
            return;
        }

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        int listX = (int) (ClientConfig.DEATH_LIST_X.get() * screenWidth);
        int listY = (int) (ClientConfig.DEATH_LIST_Y.get() * screenHeight);
        float listScale = ClientConfig.DEATH_LIST_SIZE.get().floatValue();

        int selfX = (int) (ClientConfig.DEATH_SELF_X.get() * screenWidth);
        int selfY = (int) (ClientConfig.DEATH_SELF_Y.get() * screenHeight);
        float selfScale = ClientConfig.DEATH_SELF_SIZE.get().floatValue();

        Map<UUID, Integer> deathCounts = ClientCounterState.getDeathCounts();

        guiGraphics.pose().pushPose();

        if (finalDrawList) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(listScale, listScale, 1.0F);
            drawListOverlay(guiGraphics, listX, listY, deathCounts, isEditMode);
            guiGraphics.pose().popPose();
        }

        if (finalDrawSelf) {
            drawSelfOverlay(guiGraphics, selfX, selfY, selfScale, deathCounts, player, isEditMode);
        }

        guiGraphics.pose().popPose();
    }

    private static void drawSelfOverlay(GuiGraphics guiGraphics, int x, int y, float scale, Map<UUID, Integer> deathCounts, LocalPlayer player, boolean isEditMode) {
        int color = 0xFFFFFF;
        int playerDeaths = deathCounts.getOrDefault(player.getUUID(), 0);
        int drawX = (int) (x / scale);
        int drawY = (int) (y / scale);
        int drawWidth = (int) (calcDeathSelfWidth() * scale);
        int drawHeight = (int) (calcDeathSelfHeight() * scale);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("overlay.counter.deaths", playerDeaths), drawX, drawY, color);
        guiGraphics.pose().popPose();

        drawStatusAndBorder(guiGraphics, x, y, drawWidth, drawHeight, isEditMode, OverlayEditScreen.DragTarget.DEATH_SELF);
    }

    private static void drawListOverlay(GuiGraphics guiGraphics, int x, int y, Map<UUID, Integer> deathCounts, boolean isEditMode) {
        Minecraft minecraft = Minecraft.getInstance();
        List<Map.Entry<UUID, Integer>> sortedDeaths = getSortedDeaths(deathCounts);
        int textColor = 0xFFFFFF;
        int backgroundColor = 0x80000000;
        int boxWidth = calcDeathListWidth();
        int boxHeight = calcDeathListHeight();

        if (ClientConfig.DEATH_OVERLAY_STYLE.get() == ClientConfig.DeathOverlayStyle.TABLE) {
            drawTable(guiGraphics, x, y, sortedDeaths, isEditMode);
        } else {
            if (ClientConfig.DEATH_OVERLAY_STYLE.get() == ClientConfig.DeathOverlayStyle.BOXED) {
                guiGraphics.fill(x - 5, y - 5, x + boxWidth + 5, y + boxHeight + 5, backgroundColor);
            }
            guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.deathlist"), x, y, textColor);
            for (int i = 0; i < sortedDeaths.size(); i++) {
                UUID uuid = sortedDeaths.get(i).getKey();
                int deaths = sortedDeaths.get(i).getValue();
                String playerName = getPlayerName(uuid);
                Component deathEntry = (deaths == 1)
                        ? Component.translatable("overlay.counter.deathlist.entry.singular", i + 1, playerName, deaths)
                        : Component.translatable("overlay.counter.deathlist.entry.plural", i + 1, playerName, deaths);
                guiGraphics.drawString(minecraft.font, deathEntry, x, y + ((i + 1) * 12), textColor);
            }
        }

        drawStatusAndBorder(guiGraphics, x, y, boxWidth, boxHeight, isEditMode, OverlayEditScreen.DragTarget.DEATH_LIST);
    }

    private static void drawTable(GuiGraphics guiGraphics, int x, int y, List<Map.Entry<UUID, Integer>> sortedDeaths, boolean isEditMode) {
        int borderColor = 0xFFFFFFFF;
        int headerColor = 0xFFD700;
        int textColor = 0xFFFFFF;
        int lineColor = 0x80FFFFFF;
        int backgroundColor = 0x80000000;
        Minecraft minecraft = Minecraft.getInstance();
        int tableWidth = calcDeathListWidth();
        int tableHeight = calcDeathListHeight();

        guiGraphics.fill(x - 5, y - 5, x + tableWidth + 5, y + tableHeight + 5, backgroundColor);
        guiGraphics.fill(x - 5, y - 5, x + tableWidth + 5, y - 4, borderColor);
        guiGraphics.fill(x - 5, y + tableHeight + 4, x + tableWidth + 5, y + tableHeight + 5, borderColor);
        guiGraphics.fill(x - 5, y - 5, x - 4, y + tableHeight + 5, borderColor);
        guiGraphics.fill(x + tableWidth + 4, y - 5, x + tableWidth + 5, y + tableHeight + 5, borderColor);

        guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.deathlist"), x + 5, y, headerColor);
        guiGraphics.fill(x - 5, y + 12 - 2, x + tableWidth + 5, y + 12 - 1, lineColor);

        for (int i = 0; i < sortedDeaths.size(); i++) {
            UUID uuid = sortedDeaths.get(i).getKey();
            int deaths = sortedDeaths.get(i).getValue();
            String playerName = getPlayerName(uuid);
            int rowY = y + ((i + 1) * 12 + 1);
            Component deathEntry = (deaths == 1)
                    ? Component.translatable("overlay.counter.deathlist.entry.singular", i + 1, playerName, deaths)
                    : Component.translatable("overlay.counter.deathlist.entry.plural", i + 1, playerName, deaths);
            guiGraphics.drawString(minecraft.font, deathEntry, x + 5, rowY, textColor);
            guiGraphics.fill(x - 5, rowY + 10, x + tableWidth + 5, rowY + 11, lineColor);
        }

        drawStatusAndBorder(guiGraphics, x, y, tableWidth, tableHeight, isEditMode, OverlayEditScreen.DragTarget.DEATH_LIST);
    }

    private static void drawStatusAndBorder(GuiGraphics guiGraphics, int x, int y, int width, int height, boolean isEditMode, OverlayEditScreen.DragTarget target) {
        if (!isEditMode) return;

        Minecraft mc = Minecraft.getInstance();
        boolean isEnabled = switch (target) {
            case DEATH_LIST -> ClientConfig.SHOW_DEATH_LIST_OVERLAY.get();
            case DEATH_SELF -> ClientConfig.SHOW_DEATH_SELF_OVERLAY.get();
            default -> false;
        };

        int iconColor = isEnabled ? 0xFF00FF00 : 0xFFFF0000;
        int iconSize = 6;
        int iconX = x + width + 5;
        int iconY = y + (height / 2) - (iconSize / 2);

        drawStatusIcon(guiGraphics, iconX, iconY, iconSize, iconColor);

        OverlayEditScreen editScreen = (mc.screen instanceof OverlayEditScreen) ? (OverlayEditScreen) mc.screen : null;

        if (editScreen != null && editScreen.getSelectedOverlay() == target) {
            drawBorder(guiGraphics, x, y, width, height, 0xFFFFFF00);
        } else {
            drawBorder(guiGraphics, x, y, width, height, 0xFFFF0000);
        }
    }

    private static void drawBorder(GuiGraphics g, int x, int y, int w, int h, int color) {
        int borderPadding = 3;
        g.fill(x - borderPadding, y - borderPadding, x + w + borderPadding, y - borderPadding + 1, color);
        g.fill(x - borderPadding, y + h + borderPadding - 1, x + w + borderPadding, y + h + borderPadding, color);
        g.fill(x - borderPadding, y - borderPadding, x - borderPadding + 1, y + h + borderPadding, color);
        g.fill(x + w + borderPadding - 1, y - borderPadding, x + w + borderPadding, y + h + borderPadding, color);
    }

    private static void drawStatusIcon(GuiGraphics guiGraphics, int x, int y, int size, int color) {
        guiGraphics.fill(x, y, x + size, y + size, color);
    }

    private static boolean isMultiplayerGame(Minecraft minecraft) {
        return (minecraft.hasSingleplayerServer() && minecraft.getSingleplayerServer().isPublished())
                || minecraft.getCurrentServer() != null;
    }

    private static boolean isTabPressed() {
        Minecraft minecraft = Minecraft.getInstance();
        return GLFW.glfwGetKey(minecraft.getWindow().getWindow(), GLFW.GLFW_KEY_TAB) == GLFW.GLFW_PRESS
                && minecraft.screen == null;
    }

    public static int calcDeathListWidth() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.DEATH_LIST_SIZE.get().floatValue();
        int maxTextWidth = ClientConfig.DEATH_OVERLAY_WIDTH.get();
        List<Map.Entry<UUID, Integer>> sortedDeaths = getSortedDeaths(ClientCounterState.getDeathCounts());

        for (Map.Entry<UUID, Integer> entry : sortedDeaths) {
            String playerName = getPlayerName(entry.getKey());
            int textWidth = mc.font.width(
                    Component.translatable("overlay.counter.deathlist.entry.plural", 1, playerName, entry.getValue()).getString()
            );
            maxTextWidth = Math.max(maxTextWidth, textWidth);
        }
        return Math.max((int) (maxTextWidth * scale) + 10, 50);
    }

    public static int calcDeathListHeight() {
        float scale = ClientConfig.DEATH_LIST_SIZE.get().floatValue();
        int rowHeight = 12;
        int totalRows = getSortedDeaths(ClientCounterState.getDeathCounts()).size();
        return (int) ((totalRows * rowHeight + 20) * scale);
    }

    public static int calcDeathSelfWidth() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        int playerDeaths = ClientCounterState.getDeathCounts().getOrDefault(player.getUUID(), 0);
        float scale = ClientConfig.DEATH_SELF_SIZE.get().floatValue();
        int txtWidth = mc.font.width(Component.translatable("overlay.counter.deaths", playerDeaths).getString());
        return (int) (txtWidth * scale);
    }

    public static int calcDeathSelfHeight() {
        Minecraft mc = Minecraft.getInstance();
        float scale = ClientConfig.DEATH_SELF_SIZE.get().floatValue();
        return (int) (mc.font.lineHeight * scale);
    }

    private static List<Map.Entry<UUID, Integer>> getSortedDeaths(Map<UUID, Integer> deathCounts) {
        return deathCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ServerConfig.MAX_PLAYERS_SHOWN.get())
                .toList();
    }

    private static String getPlayerName(UUID uuid) {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.getConnection().getOnlinePlayers().stream()
                .filter(p -> p.getProfile().getId().equals(uuid))
                .findFirst()
                .map(p -> p.getProfile().getName())
                .orElse("Unknown");
    }
}