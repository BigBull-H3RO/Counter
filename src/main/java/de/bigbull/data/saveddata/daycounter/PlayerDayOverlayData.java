package de.bigbull.data.saveddata.daycounter;

import de.bigbull.network.overlay.SyncDayOverlayStatePacket;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDayOverlayData extends SavedData {
    private static final String TAG_NAME = "PlayerOverlayState";
    private final Map<UUID, Boolean> overlayStates = new HashMap<>();

    public static final Factory<PlayerDayOverlayData> FACTORY = new Factory<>(PlayerDayOverlayData::new, PlayerDayOverlayData::load);

    public static PlayerDayOverlayData load(CompoundTag tag, HolderLookup.Provider provider) {
        PlayerDayOverlayData data = new PlayerDayOverlayData();
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

        PacketDistributor.sendToPlayer(player, new SyncDayOverlayStatePacket(player.getUUID(), state));
    }

    public static PlayerDayOverlayData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, TAG_NAME);
    }
}