package de.bigbull.counter.util.saveddata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class DeathCounterData extends SavedData {
    private final Map<UUID, Integer> deathCounts = new HashMap<>();
    private final Map<UUID, String> playerNames = new HashMap<>();

    public static final Codec<DeathCounterData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Codec.INT)
                    .fieldOf("DeathCounts")
                    .forGetter(data -> data.deathCounts.entrySet().stream()
                            .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue))),
            Codec.unboundedMap(Codec.STRING, Codec.STRING)
                    .fieldOf("PlayerNames")
                    .forGetter(data -> data.playerNames.entrySet().stream()
                            .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue)))
    ).apply(instance, DeathCounterData::fromCodec));

    public static final SavedDataType<DeathCounterData> TYPE = new SavedDataType<>(
            "death_counter",
            DeathCounterData::new,
            context -> CODEC
    );

    public DeathCounterData(Context context) {
        this();
    }

    public DeathCounterData() {}

    private static DeathCounterData fromCodec(Map<String, Integer> deaths, Map<String, String> names) {
        DeathCounterData data = new DeathCounterData();
        deaths.forEach((key, value) -> data.deathCounts.put(UUID.fromString(key), value));
        names.forEach((key, value) -> data.playerNames.put(UUID.fromString(key), value));
        return data;
    }

    public static DeathCounterData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
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
}
