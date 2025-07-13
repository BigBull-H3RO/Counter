package de.bigbull.counter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientCounterState {
    private static long dayCounter;
    private static long lastDeathTick;
    private static long bestSurvivalTime;
    private static final Map<UUID, Integer> deathCounts = new HashMap<>();
    private static final Map<UUID, String> nameMap = new HashMap<>();
    private static final Map<UUID, Long> bestTimes = new HashMap<>();

    public static long getDayCounter() {
        return dayCounter;
    }
    public static void setDayCounter(long val) {
        dayCounter = val;
    }

    public static long getLastDeathTick() {
        return lastDeathTick;
    }

    public static void setLastDeathTick(long tick) {
        lastDeathTick = tick;
    }

    public static long getBestSurvivalTime() {
        return bestSurvivalTime;
    }

    public static void setBestSurvivalTime(long time) {
        bestSurvivalTime = time;
    }

    public static Map<UUID, Integer> getDeathCounts() {
        return Map.copyOf(deathCounts);
    }
    public static void updateDeathCounts(Map<UUID, Integer> newData) {
        deathCounts.clear();
        deathCounts.putAll(newData);
    }

    public static void updateNameMap(Map<UUID, String> newMap) {
        nameMap.clear();
        nameMap.putAll(newMap);
    }

    public static void updateBestTimes(Map<UUID, Long> newMap) {
        bestTimes.clear();
        bestTimes.putAll(newMap);
    }

    public static long getBestTime(UUID uuid) {
        return bestTimes.getOrDefault(uuid, 0L);
    }

    public static String getNameFor(UUID uuid) {
        String name = nameMap.get(uuid);
        if (name != null) {
            return name;
        }
        if (net.minecraft.client.Minecraft.getInstance().getConnection() != null) {
            var info = net.minecraft.client.Minecraft.getInstance().getConnection().getPlayerInfo(uuid);
            if (info != null) {
                return info.getProfile().getName();
            }
        }
        return "Unknown";
    }
}
