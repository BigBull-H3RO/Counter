package de.bigbull.counter.data.saveddata;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeathCounterData extends SavedData {
    private static final String TAG_NAME = "DeathCounter";
    private final Map<UUID, Integer> deathCounts = new HashMap<>();
    private final Map<UUID, String> playerNames = new HashMap<>();

    public static final Factory<DeathCounterData> FACTORY = new Factory<>(DeathCounterData::new, DeathCounterData::load);

    public static DeathCounterData load(CompoundTag tag, HolderLookup.Provider provider) {
        DeathCounterData data = new DeathCounterData();

        CompoundTag deathCountTag = tag.getCompound("DeathCounts");
        for (String key : deathCountTag.getAllKeys()) {
            data.deathCounts.put(UUID.fromString(key), deathCountTag.getInt(key));
        }

        CompoundTag namesTag = tag.getCompound("PlayerNames");
        for (String key : namesTag.getAllKeys()) {
            data.playerNames.put(UUID.fromString(key), namesTag.getString(key));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        CompoundTag deathCountTag = new CompoundTag();
        for (Map.Entry<UUID, Integer> entry : deathCounts.entrySet()) {
            deathCountTag.putInt(entry.getKey().toString(), entry.getValue());
        }
        tag.put("DeathCounts", deathCountTag);

        CompoundTag namesTag = new CompoundTag();
        for (Map.Entry<UUID, String> entry : playerNames.entrySet()) {
            namesTag.putString(entry.getKey().toString(), entry.getValue());
        }
        tag.put("PlayerNames", namesTag);
        return tag;
    }

    public void setDeaths(UUID playerUUID, int deathCount) {
        deathCounts.put(playerUUID, deathCount);
        this.setDirty();
    }

    public int getDeaths(UUID playerUUID) {
        return deathCounts.getOrDefault(playerUUID, 0);
    }

    public void addDeath(UUID playerUUID) {
        deathCounts.put(playerUUID, getDeaths(playerUUID) + 1);
        this.setDirty();
    }

    public void resetAllDeaths() {
        deathCounts.clear();
        this.setDirty();
    }

    public Map<UUID, Integer> getDeathCountMap() {
        return new HashMap<>(deathCounts);
    }

    public void updatePlayerName(UUID playerUUID, String name) {
        playerNames.put(playerUUID, name);
        this.setDirty();
    }

    public Map<UUID, String> getPlayerNames() {
        return new HashMap<>(playerNames);
    }

    public static DeathCounterData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, TAG_NAME);
    }
}
