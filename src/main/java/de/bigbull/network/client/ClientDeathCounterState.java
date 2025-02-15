package de.bigbull.network.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientDeathCounterState {
    private static final Map<UUID, Integer> deathCounts = new HashMap<>();

    public static Map<UUID, Integer> getDeathCounts() {
        return new HashMap<>(deathCounts);
    }

    public static void updateDeathCounts(Map<UUID, Integer> newDeathCounts) {
        deathCounts.clear();
        deathCounts.putAll(newDeathCounts);
    }
}
