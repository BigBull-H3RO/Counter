package de.bigbull.counter.util.saveddata;

import de.bigbull.counter.config.ServerConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class SurvivalTimeData extends SavedData {
    private static final String TAG_NAME = "SurvivalTime";
    private final Map<UUID, Long> lastDeathTicks = new HashMap<>();
    private final Map<UUID, List<Long>> history = new HashMap<>();
    private final Map<UUID, Long> bestTimes = new HashMap<>();
    private static final int MAX_HISTORY_SIZE = 10;
    private long globalBest = 0;
    private String globalBestPlayer = "";

    public static final Factory<SurvivalTimeData> FACTORY = new Factory<>(SurvivalTimeData::new, SurvivalTimeData::load);

    public static SurvivalTimeData load(CompoundTag tag, HolderLookup.Provider provider) {
        SurvivalTimeData data = new SurvivalTimeData();

        CompoundTag lastDeathTag = tag.getCompound("LastDeaths");
        for (String key : lastDeathTag.getAllKeys()) {
            data.lastDeathTicks.put(UUID.fromString(key), lastDeathTag.getLong(key));
        }

        CompoundTag historyTag = tag.getCompound("History");
        for (String key : historyTag.getAllKeys()) {
            ListTag list = historyTag.getList(key, 4); // long tag type
            List<Long> values = new ArrayList<>();
            list.forEach(n -> values.add(((LongTag) n).getAsLong()));
            data.history.put(UUID.fromString(key), values);
        }

        CompoundTag bestTag = tag.getCompound("BestTimes");
        for (String key : bestTag.getAllKeys()) {
            data.bestTimes.put(UUID.fromString(key), bestTag.getLong(key));
        }

        if (tag.contains("GlobalBest")) {
            data.globalBest = tag.getLong("GlobalBest");
            data.globalBestPlayer = tag.getString("GlobalBestPlayer");
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        CompoundTag lastDeathTag = new CompoundTag();
        for (var e : lastDeathTicks.entrySet()) {
            lastDeathTag.putLong(e.getKey().toString(), e.getValue());
        }
        tag.put("LastDeaths", lastDeathTag);

        CompoundTag historyTag = new CompoundTag();
        for (var e : history.entrySet()) {
            ListTag list = new ListTag();
            for (Long l : e.getValue()) list.add(LongTag.valueOf(l));
            historyTag.put(e.getKey().toString(), list);
        }
        tag.put("History", historyTag);

        CompoundTag bestTag = new CompoundTag();
        for (var e : bestTimes.entrySet()) {
            bestTag.putLong(e.getKey().toString(), e.getValue());
        }
        tag.put("BestTimes", bestTag);

        tag.putLong("GlobalBest", globalBest);
        tag.putString("GlobalBestPlayer", globalBestPlayer);
        return tag;
    }

    public long getLastDeathTick(UUID uuid) {
        return lastDeathTicks.getOrDefault(uuid, 0L);
    }

    public void setLastDeathTick(UUID uuid, long tick) {
        lastDeathTicks.put(uuid, tick);
        setDirty();
    }

    public List<Long> getHistory(UUID uuid) {
        return new ArrayList<>(history.getOrDefault(uuid, Collections.emptyList()));
    }

    public long getBestTime(UUID uuid) {
        return bestTimes.getOrDefault(uuid, 0L);
    }

    public long getGlobalBest() {
        return globalBest;
    }

    public String getGlobalBestPlayer() {
        return globalBestPlayer;
    }

    public void recordSurvival(UUID uuid, long durationTicks, String playerName) {
        List<Long> list = history.computeIfAbsent(uuid, k -> new ArrayList<>());
        list.add(durationTicks);
        int maxSize = ServerConfig.SURVIVAL_HISTORY_SIZE.get();
        if (maxSize <= 0) maxSize = MAX_HISTORY_SIZE;
        while (list.size() > maxSize) {
            list.remove(0);
        }
        if (durationTicks > bestTimes.getOrDefault(uuid, 0L)) {
            bestTimes.put(uuid, durationTicks);
        }
        if (durationTicks > globalBest) {
            globalBest = durationTicks;
            globalBestPlayer = playerName;
        }
        setDirty();
    }

    public static SurvivalTimeData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, TAG_NAME);
    }
}