package de.bigbull.counter.util.saveddata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class DayCounterData extends SavedData {
    private long dayCounter;
    private long lastRealDay;

    public static final Codec<DayCounterData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("DayCounter").forGetter(data -> data.dayCounter),
            Codec.LONG.fieldOf("LastRealDay").forGetter(data -> data.lastRealDay))
            .apply(instance, DayCounterData::new));

    public static final SavedDataType<DayCounterData> TYPE = new SavedDataType<>(
            "day_counter",
            DayCounterData::new,
            p -> CODEC
    );

    public DayCounterData(long dayCounter, long lastRealDay) {
        this.dayCounter = dayCounter;
        this.lastRealDay = lastRealDay;
    }

    public DayCounterData(Context context) {
        this(0, 0);
    }

    public long getLastRealDay() {
        return lastRealDay;
    }

    public void setLastRealDay(long newLastRealDay) {
        this.lastRealDay = newLastRealDay;
        this.setDirty();
    }

    public static DayCounterData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public long getDayCounter() {
        return dayCounter;
    }

    public static long getCurrentDay(ServerLevel level) {
        if (level == null) {
            return 0;
        }
        return get(level).getDayCounter();
    }

    public void setDayCounter(long newValue) {
        this.dayCounter = Math.max(0, newValue);
        this.setDirty();
    }

    public static void setDayCounter(ServerLevel level, long newDay) {
        if (level == null) {
            return;
        }
        
        DayCounterData data = get(level);
        long realDay = level.getDayTime() / 24000;

        data.setDayCounter(newDay);
        data.setLastRealDay(realDay);
    }
}