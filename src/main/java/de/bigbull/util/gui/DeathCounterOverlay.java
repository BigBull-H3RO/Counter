package de.bigbull.util.gui;

import de.bigbull.config.ClientConfig;
import de.bigbull.config.ServerConfig;
import de.bigbull.network.client.ClientDeathCounterState;
import de.bigbull.network.client.ClientDeathOverlayState;
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

        if (minecraft.level == null || player == null || minecraft.getConnection() == null) {
            return;
        }

        if (!ServerConfig.SHOW_DEATH_OVERLAY.get() || !ServerConfig.ENABLE_DEATH_COUNTER.get()) {
            return;
        }

        if (!ServerConfig.SHOW_DEATH_OVERLAY_ALWAYS.get() && !isTabPressed()) {
            return;
        }

        if (!ClientDeathOverlayState.isOverlayEnabled(player)) {
            return;
        }

        boolean isMultiplayer = isMultiplayerGame(minecraft);
        Map<UUID, Integer> deathCounts = ClientDeathCounterState.getDeathCounts();

        int listX = ClientConfig.DEATH_LIST_X.get();
        int listY = ClientConfig.DEATH_LIST_Y.get();
        float listScale = ClientConfig.DEATH_LIST_SIZE.get().floatValue();

        int selfX = ClientConfig.DEATH_SELF_X.get();
        int selfY = ClientConfig.DEATH_SELF_Y.get();
        float selfScale = ClientConfig.DEATH_SELF_SIZE.get().floatValue();

        ClientConfig.DeathOverlayStyle overlayStyle = ClientConfig.DEATH_OVERLAY_STYLE.get();
        int color = 0xFFFFFF;

        guiGraphics.pose().pushPose();

        if (isMultiplayer) {
            if (ServerConfig.DEATH_OVERLAY_MODE.get() == ServerConfig.DeathOverlayMode.LIST ||
                    ServerConfig.DEATH_OVERLAY_MODE.get() == ServerConfig.DeathOverlayMode.BOTH) {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().scale(listScale, listScale, 1.0F);

                if (overlayStyle == ClientConfig.DeathOverlayStyle.TABLE) {
                    drawTable(guiGraphics, listX, listY, deathCounts);
                } else {
                    drawList(guiGraphics, listX, listY, deathCounts, overlayStyle == ClientConfig.DeathOverlayStyle.BOXED);
                }

                guiGraphics.pose().popPose();
            }

            if (ServerConfig.DEATH_OVERLAY_MODE.get() == ServerConfig.DeathOverlayMode.ONLY_SELF ||
                    ServerConfig.DEATH_OVERLAY_MODE.get() == ServerConfig.DeathOverlayMode.BOTH) {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().scale(selfScale, selfScale, 1.0F);
                int playerDeaths = deathCounts.getOrDefault(player.getUUID(), 0);
                guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.deaths", playerDeaths),
                        (int) (selfX / selfScale), (int) (selfY / selfScale), color);
                guiGraphics.pose().popPose();
            }
        } else {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(selfScale, selfScale, 1.0F);
            int playerDeaths = deathCounts.getOrDefault(player.getUUID(), 0);
            guiGraphics.drawString(minecraft.font, Component.translatable("overlay.counter.deaths", playerDeaths),
                    (int) (selfX / selfScale), (int) (selfY / selfScale), color);
            guiGraphics.pose().popPose();
        }

        guiGraphics.pose().popPose();
    }

    private static boolean isMultiplayerGame(Minecraft minecraft) {
        return minecraft.hasSingleplayerServer() && minecraft.getSingleplayerServer().isPublished()
                || minecraft.getCurrentServer() != null;
    }

    private static boolean isTabPressed() {
        Minecraft minecraft = Minecraft.getInstance();
        return GLFW.glfwGetKey(minecraft.getWindow().getWindow(), GLFW.GLFW_KEY_TAB) == GLFW.GLFW_PRESS
                && minecraft.screen == null;
    }

    private static void drawList(GuiGraphics guiGraphics, int x, int y, Map<UUID, Integer> deathCounts, boolean boxed) {
        int rowHeight = 12;
        int padding = 5;
        int textColor = 0xFFFFFF;
        int backgroundColor = 0x80000000;

        Minecraft minecraft = Minecraft.getInstance();
        int maxTextWidth = ClientConfig.DEATH_OVERLAY_WIDTH.get();

        List<Map.Entry<UUID, Integer>> sortedDeaths = deathCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ServerConfig.MAX_PLAYERS_SHOWN.get())
                .toList();

        for (Map.Entry<UUID, Integer> entry : sortedDeaths) {
            String playerName = minecraft.getConnection().getOnlinePlayers().stream()
                    .filter(p -> p.getProfile().getId().equals(entry.getKey()))
                    .findFirst()
                    .map(p -> p.getProfile().getName())
                    .orElse("Unknown");

            int textWidth = minecraft.font.width(Component.translatable("overlay.counter.deathlist.entry", 1, playerName, entry.getValue()).getString());
            maxTextWidth = Math.max(maxTextWidth, textWidth);
        }

        int boxWidth = maxTextWidth + padding * 2;
        int boxHeight = (sortedDeaths.size() * rowHeight) + 20;

        if (boxed) {
            guiGraphics.fill(x - padding, y - padding, x + boxWidth + padding, y + boxHeight + padding, backgroundColor);
        }

        guiGraphics.drawString(minecraft.font, Component.translatable("💀 Death Counter:"), x, y, textColor);

        for (int i = 0; i < sortedDeaths.size(); i++) {
            UUID uuid = sortedDeaths.get(i).getKey();
            int deaths = sortedDeaths.get(i).getValue();
            String playerName = minecraft.getConnection().getOnlinePlayers().stream()
                    .filter(p -> p.getProfile().getId().equals(uuid))
                    .findFirst()
                    .map(p -> p.getProfile().getName())
                    .orElse("Unknown");

            Component deathEntry = (deaths == 1) ?
                    Component.translatable("overlay.counter.deathlist.entry.singular", i + 1, playerName, deaths) :
                    Component.translatable("overlay.counter.deathlist.entry.plural", i + 1, playerName, deaths);

            guiGraphics.drawString(minecraft.font, deathEntry, x, y + ((i + 1) * rowHeight), textColor);
        }
    }

    private static void drawTable(GuiGraphics guiGraphics, int x, int y, Map<UUID, Integer> deathCounts) {
        int baseWidth = ClientConfig.DEATH_OVERLAY_WIDTH.get();
        int rowHeight = 12;
        int tablePadding = 5;
        int textPadding = 5;
        int borderColor = 0xFFFFFFFF;
        int headerColor = 0xFFD700;
        int textColor = 0xFFFFFF;
        int lineColor = 0x80FFFFFF;
        int backgroundColor = 0x80000000;
        int maxTextWidth = 50;

        Minecraft minecraft = Minecraft.getInstance();

        List<Map.Entry<UUID, Integer>> sortedDeaths = deathCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ServerConfig.MAX_PLAYERS_SHOWN.get())
                .toList();

        for (Map.Entry<UUID, Integer> entry : sortedDeaths) {
            String playerName = minecraft.getConnection().getOnlinePlayers().stream()
                    .filter(p -> p.getProfile().getId().equals(entry.getKey()))
                    .findFirst()
                    .map(p -> p.getProfile().getName())
                    .orElse("Unknown");

            int textWidth = minecraft.font.width(playerName + "  " + entry.getValue());
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }
        }

        int tableWidth = Math.max(baseWidth, maxTextWidth + textPadding * 2);
        int tableHeight = (sortedDeaths.size() * rowHeight) + 20;

        guiGraphics.fill(x - tablePadding, y - tablePadding, x + tableWidth + tablePadding, y + tableHeight + tablePadding, backgroundColor);

        guiGraphics.fill(x - tablePadding, y - tablePadding, x + tableWidth + tablePadding, y - tablePadding + 1, borderColor);
        guiGraphics.fill(x - tablePadding, y + tableHeight + tablePadding - 1, x + tableWidth + tablePadding, y + tableHeight + tablePadding, borderColor);
        guiGraphics.fill(x - tablePadding, y - tablePadding, x - tablePadding + 1, y + tableHeight + tablePadding, borderColor);
        guiGraphics.fill(x + tableWidth + tablePadding - 1, y - tablePadding, x + tableWidth + tablePadding, y + tableHeight + tablePadding, borderColor);

        guiGraphics.drawString(minecraft.font, Component.translatable("💀 Death Counter:"), x + textPadding, y, headerColor);
        guiGraphics.fill(x - tablePadding, y + rowHeight - 2, x + tableWidth + tablePadding, y + rowHeight - 1, lineColor);

        for (int i = 0; i < sortedDeaths.size(); i++) {
            UUID uuid = sortedDeaths.get(i).getKey();
            int deaths = sortedDeaths.get(i).getValue();
            String playerName = minecraft.getConnection().getOnlinePlayers().stream()
                    .filter(p -> p.getProfile().getId().equals(uuid))
                    .findFirst()
                    .map(p -> p.getProfile().getName())
                    .orElse("Unknown");

            int rowY = y + ((i + 1) * rowHeight + 1);

            Component deathEntry = (deaths == 1) ?
                    Component.translatable("overlay.counter.deathlist.entry.singular", i + 1, playerName, deaths) :
                    Component.translatable("overlay.counter.deathlist.entry.plural", i + 1, playerName, deaths);

            guiGraphics.drawString(minecraft.font, deathEntry, x + textPadding, rowY, textColor);

            if (i < sortedDeaths.size() - 1) {
                guiGraphics.fill(x - tablePadding, rowY + rowHeight - 2, x + tableWidth + tablePadding, rowY + rowHeight - 1, lineColor);
            }
        }
    }
}