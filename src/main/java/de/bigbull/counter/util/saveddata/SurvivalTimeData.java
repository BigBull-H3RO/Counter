package de.bigbull.counter.util.saveddata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.bigbull.counter.config.ServerConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.*;
import java.util.stream.Collectors;

public class SurvivalTimeData extends SavedData {
    private static final String TAG_NAME = "SurvivalTime";
    private final Map<UUID, Long> lastDeathTicks = new HashMap<>();
    private final Map<UUID, List<Long>> history = new HashMap<>();
    private final Map<UUID, Long> bestTimes = new HashMap<>();
    private static final int MAX_HISTORY_SIZE = 10;
    private long globalBest = 0;
    private String globalBestPlayer = "";

    public static final Codec<SurvivalTimeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Codec.LONG)
                    .fieldOf("LastDeaths")
                    .forGetter(data -> data.lastDeathTicks.entrySet().stream()
                            .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue))),
            Codec.unboundedMap(Codec.STRING, Codec.list(Codec.LONG))
                    .fieldOf("History")
                    .forGetter(data -> data.history.entrySet().stream()
                            .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue))),
            Codec.unboundedMap(Codec.STRING, Codec.LONG)
                    .fieldOf("BestTimes")
                    .forGetter(data -> data.bestTimes.entrySet().stream()
                            .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue))),
            Codec.LONG.fieldOf("GlobalBest").forGetter(data -> data.globalBest),
            Codec.STRING.fieldOf("GlobalBestPlayer").forGetter(data -> data.globalBestPlayer)
    ).apply(instance, SurvivalTimeData::fromCodec));

    public static final SavedDataType<SurvivalTimeData> TYPE = new SavedDataType<>(
            TAG_NAME,
            SurvivalTimeData::new,
            p -> CODEC
    );

    public SurvivalTimeData(Context context) {
        this();
    }

    public SurvivalTimeData() {}

    private static SurvivalTimeData fromCodec(
            Map<String, Long> lastDeaths,
            Map<String, List<Long>> history,
            Map<String, Long> bestTimes,
            long globalBest,
            String globalBestPlayer) {

        SurvivalTimeData data = new SurvivalTimeData();
        lastDeaths.forEach((key, value) -> data.lastDeathTicks.put(UUID.fromString(key), value));
        history.forEach((key, value) -> data.history.put(UUID.fromString(key), value));
        bestTimes.forEach((key, value) -> data.bestTimes.put(UUID.fromString(key), value));
        data.globalBest = globalBest;
        data.globalBestPlayer = globalBestPlayer;
        return data;
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

    public Map<UUID, Long> getBestTimesMap() {
        return new HashMap<>(bestTimes);
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
        return level.getDataStorage().computeIfAbsent(TYPE);
    }
}