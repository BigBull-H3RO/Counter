package de.bigbull.counter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCounterState {
    private static volatile long dayCounter;
    private static final Map<UUID, Integer> deathCounts = new ConcurrentHashMap<>();
    private static final Map<UUID, String> nameMap = new ConcurrentHashMap<>();

    public static long getDayCounter() {
        return dayCounter;
    }
    
    public static void setDayCounter(long val) {
        dayCounter = val;
    }

    public static Map<UUID, Integer> getDeathCounts() {
        return new HashMap<>(deathCounts);
    }
    
    public static synchronized void updateDeathCounts(Map<UUID, Integer> newData) {
        deathCounts.clear();
        deathCounts.putAll(newData);
    }

    public static synchronized void updateNameMap(Map<UUID, String> newMap) {
        nameMap.clear();
        nameMap.putAll(newMap);
    }

    public static String getNameFor(UUID uuid) {
        if (uuid == null) {
            return "Unknown";
        }
        
        String name = nameMap.get(uuid);
        if (name != null) {
            return name;
        }
        
        net.minecraft.client.Minecraft minecraft = net.minecraft.client.Minecraft.getInstance();
        if (minecraft.getConnection() != null) {
            var info = minecraft.getConnection().getPlayerInfo(uuid);
            if (info != null) {
                return info.getProfile().getName();
            }
        }
        return "Unknown";
    }
}
