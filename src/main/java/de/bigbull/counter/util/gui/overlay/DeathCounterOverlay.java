package de.bigbull.counter.util.gui.overlay;

import de.bigbull.counter.Counter;
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

        boolean isEditMode = (minecraft.screen instanceof GuiEditScreen);

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

        boolean finalDrawList = serverWantsList && OverlayUtils.shouldShowOverlay(
                ClientConfig.SHOW_DEATH_LIST_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_DEATH_LIST_OVERLAY.get(),
                isEditMode
        );

        boolean finalDrawSelf = serverWantsSelf && OverlayUtils.shouldShowOverlay(
                ClientConfig.SHOW_DEATH_SELF_OVERLAY_ALWAYS.get(),
                ClientConfig.SHOW_DEATH_SELF_OVERLAY.get(),
                isEditMode
        );

        if (!finalDrawList && !finalDrawSelf) {
            return;
        }

        int listWidth = calcDeathListWidth();
        int listHeight = calcDeathListHeight();
        float listScale = ClientConfig.DEATH_LIST_SIZE.get().floatValue();
        int scaledListWidth = (int) (listWidth * listScale);
        int scaledListHeight = (int) (listHeight * listScale);
        OverlayUtils.Position listPos = OverlayUtils.computePosition(
                ClientConfig.DEATH_LIST_X.get(),
                ClientConfig.DEATH_LIST_Y.get(),
                listScale, scaledListWidth, scaledListHeight);

        int selfWidth = calcDeathSelfWidth();
        int selfHeight = calcDeathSelfHeight();
        float selfScale = ClientConfig.DEATH_SELF_SIZE.get().floatValue();
        int scaledSelfWidth = (int) (selfWidth * selfScale);
        int scaledSelfHeight = (int) (selfHeight * selfScale);
        OverlayUtils.Position selfPos = OverlayUtils.computePosition(
                ClientConfig.DEATH_SELF_X.get(),
                ClientConfig.DEATH_SELF_Y.get(),
                selfScale, scaledSelfWidth, scaledSelfHeight);

        guiGraphics.pose().pushPose();

        if (finalDrawList) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(listScale, listScale, 1.0F);
            drawListOverlay(guiGraphics, listPos, isEditMode);
            guiGraphics.pose().popPose();
        }

        if (finalDrawSelf) {
            drawSelfOverlay(guiGraphics, selfPos, selfScale, player, isEditMode);
        }

        guiGraphics.pose().popPose();
    }

    private static void drawSelfOverlay(GuiGraphics guiGraphics, OverlayUtils.Position pos, float scale, LocalPlayer player, boolean isEditMode) {
        int color = ClientConfig.DEATH_SELF_TEXT_COLOR.get();
        int drawWidth = calcDeathSelfWidth();
        int drawHeight = calcDeathSelfHeight();
        int scaledWidth = (int) (drawWidth * scale);
        int scaledHeight = (int) (drawHeight * scale);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal(getSelfOverlayString(player)), pos.drawX(), pos.drawY(), color);
        guiGraphics.pose().popPose();

        drawStatusAndBorder(guiGraphics, pos.x(), pos.y(), scaledWidth, scaledHeight, isEditMode, GuiEditScreen.DragTarget.DEATH_SELF);
    }

    private static void drawListOverlay(GuiGraphics guiGraphics, OverlayUtils.Position pos, boolean isEditMode) {
        Minecraft minecraft = Minecraft.getInstance();
        List<Map.Entry<UUID, Integer>> sortedDeaths = getSortedDeathCounts();
        int defaultTextColor = ClientConfig.DEATH_LIST_TEXT_COLOR.get();
        int backgroundColor = 0x80000000;
        int boxWidth = calcDeathListWidth();
        int boxHeight = calcDeathListHeight();

        if (ClientConfig.DEATH_OVERLAY_STYLE.get() == ClientConfig.DeathOverlayStyle.TABLE) {
            drawTableOverlay(guiGraphics, pos, sortedDeaths, isEditMode);
        } else if (ClientConfig.DEATH_OVERLAY_STYLE.get() == ClientConfig.DeathOverlayStyle.CLASSIC) {
            guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.deathlist"), pos.drawX(), pos.drawY(), defaultTextColor);
            drawDeathEntries(guiGraphics, pos.drawX(), pos.drawY(), sortedDeaths, false);
            drawStatusAndBorder(guiGraphics, pos.drawX(), pos.drawY(), boxWidth, boxHeight, isEditMode, GuiEditScreen.DragTarget.DEATH_LIST);
        } else if (ClientConfig.DEATH_OVERLAY_STYLE.get() == ClientConfig.DeathOverlayStyle.BOXED) {
            guiGraphics.fill(pos.drawX() - 5, pos.drawY() - 5, pos.drawX() + boxWidth + 5, pos.drawY() + boxHeight + 5, backgroundColor);
            guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.deathlist"), pos.drawX(), pos.drawY(), defaultTextColor);
            drawDeathEntries(guiGraphics, pos.drawX(), pos.drawY(), sortedDeaths, false);
            drawStatusAndBorder(guiGraphics, pos.drawX(), pos.drawY(), boxWidth, boxHeight, isEditMode, GuiEditScreen.DragTarget.DEATH_LIST);
        } else {
            Counter.logger.warn("Unknown setting for DEATH_OVERLAY_STYLE: {}", ClientConfig.DEATH_OVERLAY_STYLE.get());
            guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.deathlist"), pos.drawX(), pos.drawY(), 0xFF0000);
            guiGraphics.drawString(minecraft.font, Component.literal("ERROR: Invalid death list style!"), pos.drawX(), pos.drawY() + 12, 0xFF0000);
        }
    }

    private static void drawTableOverlay(GuiGraphics guiGraphics, OverlayUtils.Position pos, List<Map.Entry<UUID, Integer>> sortedDeaths, boolean isEditMode) {
        int borderColor = 0xFFFFFFFF;
        int defaultTextColor = ClientConfig.DEATH_LIST_TEXT_COLOR.get();
        int backgroundColor = 0x80000000;
        int tableWidth = calcDeathListWidth();
        int tableHeight = calcDeathListHeight();

        guiGraphics.fill(pos.drawX() - 5, pos.drawY() - 5, pos.drawX() + tableWidth + 5, pos.drawY() + tableHeight + 5, backgroundColor);
        guiGraphics.fill(pos.drawX() - 5, pos.drawY() - 5, pos.drawX() + tableWidth + 5, pos.drawY() - 4, borderColor);
        guiGraphics.fill(pos.drawX() - 5, pos.drawY() + tableHeight + 4, pos.drawX() + tableWidth + 5, pos.drawY() + tableHeight + 5, borderColor);
        guiGraphics.fill(pos.drawX() - 5, pos.drawY() - 5, pos.drawX() - 4, pos.drawY() + tableHeight + 5, borderColor);
        guiGraphics.fill(pos.drawX() + tableWidth + 4, pos.drawY() - 5, pos.drawX() + tableWidth + 5, pos.drawY() + tableHeight + 5, borderColor);

        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("overlay.counter.deathlist"), pos.drawX() + 5, pos.drawY(), defaultTextColor);
        guiGraphics.fill(pos.drawX() - 5, pos.drawY() + 10, pos.drawX() + tableWidth + 5, pos.drawY() + 11, 0x80FFFFFF);

        drawDeathEntries(guiGraphics, pos.drawX(), pos.drawY(), sortedDeaths, true);
        drawStatusAndBorder(guiGraphics, pos.drawX() - 4, pos.drawY() - 4, tableWidth + 8, tableHeight + 8, isEditMode, GuiEditScreen.DragTarget.DEATH_LIST);
    }

    private static void drawStatusAndBorder(GuiGraphics guiGraphics, int x, int y, int width, int height, boolean isEditMode, GuiEditScreen.DragTarget target) {
        if (!isEditMode) return;

        Minecraft mc = Minecraft.getInstance();
        boolean isEnabled = switch (target) {
            case DEATH_LIST -> ClientConfig.SHOW_DEATH_LIST_OVERLAY.get();
            case DEATH_SELF -> ClientConfig.SHOW_DEATH_SELF_OVERLAY.get();
            default -> false;
        };

        int borderColor = isEnabled ? 0xFF00FF00 : 0xFFFF0000;
        GuiEditScreen guiEditScreen = (mc.screen instanceof GuiEditScreen) ? (GuiEditScreen) mc.screen : null;

        if (guiEditScreen != null && guiEditScreen.getSelectedOverlay() == target) {
            borderColor = 0xFFFFFF00;
        }

        OverlayUtils.drawBorder(guiGraphics, x, y, width, height, borderColor, 3);
    }

    private static boolean isMultiplayerGame(Minecraft minecraft) {
        return (minecraft.hasSingleplayerServer() && minecraft.getSingleplayerServer().isPublished())
                || minecraft.getCurrentServer() != null;
    }

    private static void drawDeathEntries(GuiGraphics guiGraphics, int x, int y, List<Map.Entry<UUID, Integer>> sortedDeaths, boolean isTable) {
        Minecraft minecraft = Minecraft.getInstance();
        int defaultTextColor = ClientConfig.DEATH_LIST_TEXT_COLOR.get();
        int lineColor = 0x80FFFFFF;

        for (int i = 0; i < sortedDeaths.size(); i++) {
            UUID uuid = sortedDeaths.get(i).getKey();
            int deaths = sortedDeaths.get(i).getValue();
            String playerName = getPlayerName(uuid);
            int rowY = y + ((i + 1) * 14);

            Component deathEntry = getDeathEntry(i, uuid, playerName, deaths);
            guiGraphics.drawString(minecraft.font, deathEntry, x + 5, rowY, defaultTextColor);

            if (isTable) {
                guiGraphics.fill(x - 5, rowY + 10, x + calcDeathListWidth() + 5, rowY + 11, lineColor);
            }
        }
    }

    public static Component getDeathEntry(int index, UUID uuid, String playerName, int deaths) {
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

        if (ServerConfig.SHOW_BEST_SURVIVAL_IN_DEATH_COUNTER.get()) {
            long best = ClientCounterState.getBestTime(uuid);
            String time = CounterManager.formatSurvivalTime(best);
            Component bestComp = Component.translatable("overlay.counter.best_survival_no_emoji", time);
            playerAndDeaths = playerAndDeaths.copy().append(", ").append(bestComp);
        }

        return Component.translatable("overlay.counter.deathlist.entry.full", coloredPosition, playerAndDeaths);
    }

    public static int calcDeathListWidth() {
        Minecraft mc = Minecraft.getInstance();
        int maxTextWidth = ClientConfig.DEATH_OVERLAY_WIDTH.get();
        List<Map.Entry<UUID, Integer>> sortedDeaths = getSortedDeaths(ClientCounterState.getDeathCounts());

        for (int i = 0; i < sortedDeaths.size(); i++) {
            Map.Entry<UUID, Integer> entry = sortedDeaths.get(i);
            String playerName = getPlayerName(entry.getKey());
            Component deathEntry = getDeathEntry(i, entry.getKey(), playerName, entry.getValue());
            int textWidth = mc.font.width(deathEntry.getString());
            maxTextWidth = Math.max(maxTextWidth, textWidth);
        }
        return Math.max(maxTextWidth + 10, 50);
    }

    public static int calcDeathListHeight() {
        int rowHeight = 12;
        int totalRows = getSortedDeaths(ClientCounterState.getDeathCounts()).size();
        return totalRows * rowHeight + 20;
    }

    public static int calcDeathSelfWidth() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        int txtWidth = mc.font.width(getSelfOverlayString(player));
        return txtWidth;
    }

    public static int calcDeathSelfHeight() {
        Minecraft mc = Minecraft.getInstance();
        return mc.font.lineHeight;
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

    private static String getSelfOverlayString(LocalPlayer player) {
        String deaths = CounterManager.getDeaths(player);
        if (!ServerConfig.SHOW_BEST_SURVIVAL_IN_DEATH_COUNTER.get()) {
            return deaths;
        }
        long bestTime = ClientCounterState.getBestSurvivalTime();
        String time = CounterManager.formatSurvivalTime(bestTime);
        String surv = Component.translatable("overlay.counter.best_survival_no_emoji", time).getString();
        return deaths + ", " + surv;
    }
}