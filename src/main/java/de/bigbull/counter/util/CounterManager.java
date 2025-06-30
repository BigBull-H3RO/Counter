package de.bigbull.counter.util;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.lwjgl.glfw.GLFW;

import java.util.Map;
import java.util.UUID;

public class CounterManager {
    public static String getTime() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return "N/A";

        long time = minecraft.level.getDayTime() % 24000;
        int hours = (int) ((time / 1000 + 6) % 24);
        int minutes = (int) ((time % 1000) / 1000.0 * 60);

        boolean is24Hour = ServerConfig.TIME_FORMAT_24H.get();
        return is24Hour
                ? String.format("%02d:%02d", hours, minutes)
                : String.format("%02d:%02d %s", (hours % 12 == 0 ? 12 : hours % 12), minutes, hours < 12 ? "AM" : "PM");
    }

    public static String getCombinedDayTime() {
        long dayCount = ClientCounterState.getDayCounter();
        String timeString = getTime();
        String timeKey = ClientConfig.SHOW_EMOJIS.get()
                ? "combined.daytime_with_emoji"
                : "combined.daytime_no_emoji";
        return Component.translatable(timeKey, dayCount, timeString).getString();
    }

    public static String getDay() {
        long dayCount = ClientCounterState.getDayCounter();
        String dayKey = ClientConfig.SHOW_EMOJIS.get()
                ? "overlay.counter.day_with_emoji"
                : "overlay.counter.day_no_emoji";
        return Component.translatable(dayKey, dayCount).getString();
    }

    public static String getDeaths(LocalPlayer player) {
        Map<UUID, Integer> deathCounts = ClientCounterState.getDeathCounts();
        int playerDeaths = deathCounts.getOrDefault(player.getUUID(), 0);
        String deathKey = ClientConfig.SHOW_EMOJIS.get()
                ? "overlay.counter.deaths_with_emoji"
                : "overlay.counter.deaths_no_emoji";
        return Component.translatable(deathKey, playerDeaths).getString();
    }

    public static void sendCoordsMessage(ServerPlayer sender, ServerPlayer receiver) {
        int x = (int) sender.getX();
        int y = (int) sender.getY();
        int z = (int) sender.getZ();

        Component message = Component.literal(sender.getScoreboardName() + " → X: " + x + ", Y: " + y + ", Z: " + z);
        receiver.sendSystemMessage(message);
    }

    public static void sendCoordsMessageToAll(ServerPlayer sender) {
        int x = (int) sender.getX();
        int y = (int) sender.getY();
        int z = (int) sender.getZ();

        Component message = Component.translatable("command.coords.broadcast", sender.getScoreboardName(), x, y, z);
        sender.getServer().getPlayerList().broadcastSystemMessage(message, false);
    }

    public static boolean isTabPressed() {
        Minecraft minecraft = Minecraft.getInstance();
        return GLFW.glfwGetKey(minecraft.getWindow().getWindow(), GLFW.GLFW_KEY_TAB) == GLFW.GLFW_PRESS
                && minecraft.screen == null;
    }
}