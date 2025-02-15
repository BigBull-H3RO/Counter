package de.bigbull.data.saveddata.deathcounter;

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

    public static final Factory<DeathCounterData> FACTORY = new Factory<>(DeathCounterData::new, DeathCounterData::load);

    public static DeathCounterData load(CompoundTag tag, HolderLookup.Provider provider) {
        DeathCounterData data = new DeathCounterData();
        for (String key : tag.getAllKeys()) {
            data.deathCounts.put(UUID.fromString(key), tag.getInt(key));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        for (Map.Entry<UUID, Integer> entry : deathCounts.entrySet()) {
            tag.putInt(entry.getKey().toString(), entry.getValue());
        }
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

    public static DeathCounterData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, TAG_NAME);
    }
}
