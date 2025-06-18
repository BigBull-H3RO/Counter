package de.bigbull.counter.util.gui;

import de.bigbull.counter.Counter;
import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.ClientCounterState;
import de.bigbull.counter.util.CounterManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

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
        boolean allowListOverlay = ClientConfig.SHOW_DEATH_LIST_OVERLAY_ALWAYS.get() || CounterManager.isTabPressed() || isEditMode;
        boolean allowSelfOverlay = ClientConfig.SHOW_DEATH_SELF_OVERLAY_ALWAYS.get() || CounterManager.isTabPressed() || isEditMode;

        if (!allowListOverlay && !allowSelfOverlay) {
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

        boolean showList = (ClientConfig.SHOW_DEATH_LIST_OVERLAY_ALWAYS.get() && ClientConfig.SHOW_DEATH_LIST_OVERLAY.get())
                || isEditMode
                || (CounterManager.isTabPressed() && serverWantsList && ClientConfig.SHOW_DEATH_LIST_OVERLAY.get());

        boolean showSelf = (ClientConfig.SHOW_DEATH_SELF_OVERLAY_ALWAYS.get() && ClientConfig.SHOW_DEATH_SELF_OVERLAY.get())
                || isEditMode
                || (CounterManager.isTabPressed() && serverWantsSelf && ClientConfig.SHOW_DEATH_SELF_OVERLAY.get());

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

        guiGraphics.pose().pushMatrix();

        if (finalDrawList) {
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().scale(listScale, listScale);
            drawListOverlay(guiGraphics, listX, listY, isEditMode);
            guiGraphics.pose().popMatrix();
        }

        if (finalDrawSelf) {
            drawSelfOverlay(guiGraphics, selfX, selfY, selfScale, player, isEditMode);
        }

        guiGraphics.pose().popMatrix();
    }

    private static void drawSelfOverlay(GuiGraphics guiGraphics, int x, int y, float scale, LocalPlayer player, boolean isEditMode) {
        int color = ClientConfig.ensureAlphaChannel(ClientConfig.DEATH_SELF_TEXT_COLOR.get());
        int drawX = (int) (x / scale);
        int drawY = (int) (y / scale);
        int drawWidth = (int) (calcDeathSelfWidth() * scale);
        int drawHeight = (int) (calcDeathSelfHeight() * scale);

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().scale(scale, scale);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(CounterManager.getDeaths(player)), drawX, drawY, color);
        guiGraphics.pose().popMatrix();

        drawStatusAndBorder(guiGraphics, x, y, drawWidth, drawHeight, isEditMode, OverlayEditScreen.DragTarget.DEATH_SELF);
    }

    private static void drawListOverlay(GuiGraphics guiGraphics, int x, int y, boolean isEditMode) {
        Minecraft minecraft = Minecraft.getInstance();
        List<Map.Entry<UUID, Integer>> sortedDeaths = getSortedDeathCounts();
        int defaultTextColor = ClientConfig.ensureAlphaChannel(ClientConfig.DEATH_LIST_TEXT_COLOR.get());
        int backgroundColor = 0x80000000;
        int boxWidth = calcDeathListWidth();
        int boxHeight = calcDeathListHeight();

        if (ClientConfig.DEATH_OVERLAY_STYLE.get() == ClientConfig.DeathOverlayStyle.TABLE) {
            drawTableOverlay(guiGraphics, x, y, sortedDeaths, isEditMode);
        } else if (ClientConfig.DEATH_OVERLAY_STYLE.get() == ClientConfig.DeathOverlayStyle.CLASSIC) {
            if (ClientConfig.DEATH_OVERLAY_STYLE.get() == ClientConfig.DeathOverlayStyle.BOXED) {
                guiGraphics.fill(x - 5, y - 5, x + boxWidth + 5, y + boxHeight + 5, backgroundColor);
            }
            guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.deathlist"), x, y, defaultTextColor);
            drawDeathEntries(guiGraphics, x, y, sortedDeaths, false);
            drawStatusAndBorder(guiGraphics, x, y, boxWidth, boxHeight, isEditMode, OverlayEditScreen.DragTarget.DEATH_LIST);
        } else {
            Counter.logger.warn("Unbekannte Einstellung für DEATH_OVERLAY_STYLE: {}", ClientConfig.DEATH_OVERLAY_STYLE.get());
            guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.deathlist"), x, y, 0xFF0000);
            guiGraphics.drawString(minecraft.font, Component.literal("ERROR: Invalid death list style!"), x, y + 12, 0xFF0000);
        }
    }

    private static void drawTableOverlay(GuiGraphics guiGraphics, int x, int y, List<Map.Entry<UUID, Integer>> sortedDeaths, boolean isEditMode) {
        int borderColor = 0xFFFFFFFF;
        int defaultTextColor = ClientConfig.ensureAlphaChannel(ClientConfig.DEATH_LIST_TEXT_COLOR.get());
        int backgroundColor = 0x80000000;
        int tableWidth = calcDeathListWidth();
        int tableHeight = calcDeathListHeight();

        guiGraphics.fill(x - 5, y - 5, x + tableWidth + 5, y + tableHeight + 5, backgroundColor);
        guiGraphics.fill(x - 5, y - 5, x + tableWidth + 5, y - 4, borderColor);
        guiGraphics.fill(x - 5, y + tableHeight + 4, x + tableWidth + 5, y + tableHeight + 5, borderColor);
        guiGraphics.fill(x - 5, y - 5, x - 4, y + tableHeight + 5, borderColor);
        guiGraphics.fill(x + tableWidth + 4, y - 5, x + tableWidth + 5, y + tableHeight + 5, borderColor);

        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("overlay.counter.deathlist"), x + 5, y, defaultTextColor);
        guiGraphics.fill(x - 5, y + 10, x + tableWidth + 5, y + 11, 0x80FFFFFF);

        drawDeathEntries(guiGraphics, x, y, sortedDeaths, true);
        drawStatusAndBorder(guiGraphics, x - 4, y - 4, tableWidth + 8, tableHeight + 8, isEditMode, OverlayEditScreen.DragTarget.DEATH_LIST);
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

        guiGraphics.fill(iconX, iconY, iconX + iconSize, iconY + iconSize, iconColor);

        OverlayEditScreen editScreen = (mc.screen instanceof OverlayEditScreen) ? (OverlayEditScreen) mc.screen : null;

        if (editScreen != null && editScreen.getSelectedOverlay() == target) {
            CounterManager.drawBorder(guiGraphics, x, y, width, height, 0xFFFFFF00, 3);
        } else {
            CounterManager.drawBorder(guiGraphics, x, y, width, height, 0xFFFF0000, 3);
        }
    }

    private static boolean isMultiplayerGame(Minecraft minecraft) {
        return (minecraft.hasSingleplayerServer() && minecraft.getSingleplayerServer().isPublished())
                || minecraft.getCurrentServer() != null;
    }

    private static void drawDeathEntries(GuiGraphics guiGraphics, int x, int y, List<Map.Entry<UUID, Integer>> sortedDeaths, boolean isTable) {
        Minecraft minecraft = Minecraft.getInstance();
        int defaultTextColor = ClientConfig.ensureAlphaChannel(ClientConfig.DEATH_LIST_TEXT_COLOR.get());
        int lineColor = 0x80FFFFFF;

        for (int i = 0; i < sortedDeaths.size(); i++) {
            UUID uuid = sortedDeaths.get(i).getKey();
            int deaths = sortedDeaths.get(i).getValue();
            String playerName = getPlayerName(uuid);
            int rowY = y + ((i + 1) * 14);

            Component deathEntry = getDeathEntry(i, playerName, deaths);
            guiGraphics.drawString(minecraft.font, deathEntry, x + 5, rowY, defaultTextColor);

            if (isTable) {
                guiGraphics.fill(x - 5, rowY + 10, x + calcDeathListWidth() + 5, rowY + 11, lineColor);
            }
        }
    }

    public static Component getDeathEntry(int index, String playerName, int deaths) {
        int positionColor = 0xFFFFFF;
        if (index == 0) {
            positionColor = ClientConfig.FIRST_PLACE_COLOR.get();
        } else if (index == 1) {
            positionColor = ClientConfig.SECOND_PLACE_COLOR.get();
        } else if (index == 2) {
            positionColor = ClientConfig.THIRD_PLACE_COLOR.get();
        }

        Component coloredPosition = Component.literal((index + 1) + ".").setStyle(Style.EMPTY.withColor(positionColor));

        Component playerAndDeaths = (deaths == 1)
                ? Component.translatable("overlay.counter.deathlist.entry.singular", Component.literal(playerName), deaths)
                : Component.translatable("overlay.counter.deathlist.entry.plural", Component.literal(playerName), deaths);

        return Component.translatable("overlay.counter.deathlist.entry.full", coloredPosition, playerAndDeaths);
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
        float scale = ClientConfig.DEATH_SELF_SIZE.get().floatValue();
        int txtWidth = mc.font.width(Component.translatable(CounterManager.getDeaths(player)).getString());
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

    private static List<Map.Entry<UUID, Integer>> getSortedDeathCounts() {
        return getSortedDeaths(ClientCounterState.getDeathCounts());
    }

    private static String getPlayerName(UUID uuid) {
        return ClientCounterState.getNameFor(uuid);
    }
}