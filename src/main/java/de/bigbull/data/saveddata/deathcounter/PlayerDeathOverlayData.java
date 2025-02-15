package de.bigbull.data.saveddata.deathcounter;

import de.bigbull.network.overlay.SyncDeathOverlayStatePacket;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDeathOverlayData extends SavedData {
    private static final String TAG_NAME = "PlayerDeathOverlayState";
    private final Map<UUID, Boolean> overlayStates = new HashMap<>();

    public static final Factory<PlayerDeathOverlayData> FACTORY = new Factory<>(PlayerDeathOverlayData::new, PlayerDeathOverlayData::load);

    public static PlayerDeathOverlayData load(CompoundTag tag, HolderLookup.Provider provider) {
        PlayerDeathOverlayData data = new PlayerDeathOverlayData();
        for (String key : tag.getAllKeys()) {
            data.overlayStates.put(UUID.fromString(key), tag.getBoolean(key));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        for (Map.Entry<UUID, Boolean> entry : overlayStates.entrySet()) {
            tag.putBoolean(entry.getKey().toString(), entry.getValue());
        }
        return tag;
    }

    public boolean isOverlayEnabled(ServerPlayer player) {
        return overlayStates.getOrDefault(player.getUUID(), true);
    }

    public void setOverlayState(ServerPlayer player, boolean state) {
        overlayStates.put(player.getUUID(), state);
        this.setDirty();

        PacketDistributor.sendToPlayer(player, new SyncDeathOverlayStatePacket(player.getUUID(), state));
    }

    public static PlayerDeathOverlayData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, TAG_NAME);
    }
}