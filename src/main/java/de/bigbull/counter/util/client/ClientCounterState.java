package de.bigbull.counter.util.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientCounterState {
    private static long dayCounter;
    private static final Map<UUID, Integer> deathCounts = new HashMap<>();

    public static long getDayCounter() {
        return dayCounter;
    }
    public static void setDayCounter(long val) {
        dayCounter = val;
    }

    public static Map<UUID, Integer> getDeathCounts() {
        return new HashMap<>(deathCounts);
    }
    public static void updateDeathCounts(Map<UUID, Integer> newData) {
        deathCounts.clear();
        deathCounts.putAll(newData);
    }
}
