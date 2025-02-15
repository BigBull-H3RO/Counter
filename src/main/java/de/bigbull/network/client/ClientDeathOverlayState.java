package de.bigbull.network.client;

import net.minecraft.client.player.LocalPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientDeathOverlayState {
    private static final Map<UUID, Boolean> overlayStates = new HashMap<>();

    public static boolean isOverlayEnabled(LocalPlayer player) {
        return overlayStates.getOrDefault(player.getUUID(), true);
    }

    public static void setOverlayState(UUID playerUUID, boolean enabled) {
        overlayStates.put(playerUUID, enabled);
    }

    public static void reset() {
        overlayStates.clear();
    }
}