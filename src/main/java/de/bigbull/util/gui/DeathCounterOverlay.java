package de.bigbull.util.gui;

import de.bigbull.config.ClientConfig;
import de.bigbull.config.ConfigValues;
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

        if (!ConfigValues.ENABLE_DEATH_COUNTER) {
            return;
        }

        if (!ConfigValues.SHOW_DEATH_OVERLAY_ALWAYS && !isTabPressed()) {
            return;
        }

        if (!ClientDeathOverlayState.isOverlayEnabled(player)) {
            return;
        }

        boolean isMultiplayer = isMultiplayerGame(minecraft);
        Map<UUID, Integer> deathCounts = ClientDeathCounterState.getDeathCounts();

        int listX = ConfigValues.DEATH_LIST_X;
        int listY = ConfigValues.DEATH_LIST_Y;
        float listScale = (float) ConfigValues.DEATH_LIST_SIZE;

        int selfX = ConfigValues.DEATH_SELF_X;
        int selfY = ConfigValues.DEATH_SELF_Y;
        float selfScale = (float) ConfigValues.DEATH_SELF_SIZE;

        ClientConfig.DeathOverlayStyle overlayStyle = ClientConfig.DEATH_OVERLAY_STYLE.get();
        int color = 0xFFFFFF;

        guiGraphics.pose().pushPose();

        if (ConfigValues.SHOW_DEATH_OVERLAY) {
            if (isMultiplayer) {
                if (ConfigValues.DEATH_OVERLAY_MODE == ServerConfig.DeathOverlayMode.LIST || ConfigValues.DEATH_OVERLAY_MODE == ServerConfig.DeathOverlayMode.BOTH) {
                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().scale(listScale, listScale, 1.0F);

                    if (overlayStyle == ClientConfig.DeathOverlayStyle.TABLE) {
                        drawTable(guiGraphics, listX, listY, deathCounts);
                    } else {
                        drawList(guiGraphics, listX, listY, deathCounts, overlayStyle == ClientConfig.DeathOverlayStyle.BOXED);
                    }

                    guiGraphics.pose().popPose();
                }

                if (ConfigValues.DEATH_OVERLAY_MODE == ServerConfig.DeathOverlayMode.ONLY_SELF || ConfigValues.DEATH_OVERLAY_MODE == ServerConfig.DeathOverlayMode.BOTH) {
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
        }

        guiGraphics.pose().popPose();
    }

    private static boolean isMultiplayerGame(Minecraft minecraft) {
        return minecraft.hasSingleplayerServer() && minecraft.getSingleplayerServer().isPublished()
                || minecraft.getCurrentServer() != null;
    }

    private static boolean isTabPressed() {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_TAB) == GLFW.GLFW_PRESS;
    }

    private static void drawList(GuiGraphics guiGraphics, int x, int y, Map<UUID, Integer> deathCounts, boolean boxed) {
        int rowHeight = 12;
        int padding = 5;
        int textColor = 0xFFFFFF;
        int backgroundColor = 0x80000000;

        Minecraft minecraft = Minecraft.getInstance();
        int maxTextWidth = ConfigValues.DEATH_OVERLAY_WIDTH;

        List<Map.Entry<UUID, Integer>> sortedDeaths = deathCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ConfigValues.MAX_PLAYERS_SHOWN)
                .toList();

        for (Map.Entry<UUID, Integer> entry : sortedDeaths) {
            String playerName = minecraft.getConnection().getOnlinePlayers().stream()
                    .filter(p -> p.getProfile().getId().equals(entry.getKey()))
                    .findFirst()
                    .map(p -> p.getProfile().getName())
                    .orElse("Unknown");

            int textWidth = minecraft.font.width(playerName + "  " + entry.getValue());
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

            guiGraphics.drawString(minecraft.font, (i + 1) + ". " + playerName + " - " + deaths, x, y + ((i + 1) * rowHeight), textColor);
        }
    }

    private static void drawTable(GuiGraphics guiGraphics, int x, int y, Map<UUID, Integer> deathCounts) {
        int baseWidth = ConfigValues.DEATH_OVERLAY_WIDTH;
        int rowHeight = 12;
        int tablePadding = 5;
        int textPadding = 5;
        int borderColor = 0xFFFFFFFF; // Weiße Umrandung
        int headerColor = 0xFFD700; // Gold für die Überschrift
        int textColor = 0xFFFFFF; // Weißer Text
        int lineColor = 0x80FFFFFF; // Halbtransparente Linien
        int backgroundColor = 0x80000000; // Leicht transparentes Schwarz für den Hintergrund
        int maxTextWidth = 50; // Minimale Spaltenbreite für die Namen

        Minecraft minecraft = Minecraft.getInstance();

        // Bestimme die maximale Breite basierend auf den Namen
        List<Map.Entry<UUID, Integer>> sortedDeaths = deathCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ConfigValues.MAX_PLAYERS_SHOWN)
                .toList();

        for (Map.Entry<UUID, Integer> entry : sortedDeaths) {
            String playerName = minecraft.getConnection().getOnlinePlayers().stream()
                    .filter(p -> p.getProfile().getId().equals(entry.getKey()))
                    .findFirst()
                    .map(p -> p.getProfile().getName())
                    .orElse("Unknown");

            int textWidth = minecraft.font.width(playerName + "  " + entry.getValue()); // Breite berechnen
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }
        }

        // Setze die Breite der Tabelle (mindestens baseWidth, aber angepasst)
        int tableWidth = Math.max(baseWidth, maxTextWidth + textPadding * 2);
        int tableHeight = (sortedDeaths.size() * rowHeight) + 20; // Höhe der Tabelle

        // Hintergrund füllen (angepasste Größe)
        guiGraphics.fill(x - tablePadding, y - tablePadding, x + tableWidth + tablePadding, y + tableHeight + tablePadding, backgroundColor);

        // Rahmen der Tabelle zeichnen
        guiGraphics.fill(x - tablePadding, y - tablePadding, x + tableWidth + tablePadding, y - tablePadding + 1, borderColor); // Oberer Rand
        guiGraphics.fill(x - tablePadding, y + tableHeight + tablePadding - 1, x + tableWidth + tablePadding, y + tableHeight + tablePadding, borderColor); // Unterer Rand
        guiGraphics.fill(x - tablePadding, y - tablePadding, x - tablePadding + 1, y + tableHeight + tablePadding, borderColor); // Linker Rand
        guiGraphics.fill(x + tableWidth + tablePadding - 1, y - tablePadding, x + tableWidth + tablePadding, y + tableHeight + tablePadding, borderColor); // Rechter Rand

        // Überschrift
        guiGraphics.drawString(minecraft.font, Component.translatable("💀 Death Counter:"), x + textPadding, y, headerColor);

        // Trennlinie unter der Überschrift
        guiGraphics.fill(x - tablePadding, y + rowHeight, x + tableWidth + tablePadding, y + rowHeight + 1, lineColor);

        // Spielerliste
        for (int i = 0; i < sortedDeaths.size(); i++) {
            UUID uuid = sortedDeaths.get(i).getKey();
            int deaths = sortedDeaths.get(i).getValue();
            String playerName = minecraft.getConnection().getOnlinePlayers().stream()
                    .filter(p -> p.getProfile().getId().equals(uuid))
                    .findFirst()
                    .map(p -> p.getProfile().getName())
                    .orElse("Unknown");

            int rowY = y + ((i + 1) * rowHeight);

            // Spalteninhalt (links Name, rechts Zahl)
            guiGraphics.drawString(minecraft.font, (i + 1) + ". " + playerName, x + textPadding, rowY, textColor);
            guiGraphics.drawString(minecraft.font, String.valueOf(deaths), x + tableWidth - 30, rowY, textColor);

            // Trennlinie zwischen den Spielern (außer letzter Eintrag)
            if (i < sortedDeaths.size() - 1) {
                guiGraphics.fill(x - tablePadding, rowY + rowHeight - 1, x + tableWidth + tablePadding, rowY + rowHeight, lineColor);
            }
        }
    }
}