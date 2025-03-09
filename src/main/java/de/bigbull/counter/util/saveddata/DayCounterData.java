package de.bigbull.counter.util.saveddata;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class DayCounterData extends SavedData {
    private static final String TAG_DAY = "DayCounter";
    private static final String TAG_OFFSET = "DayCounterOffset";
    private long dayCounter = 0;
    private long offset = 0;

    public static final Factory<DayCounterData> FACTORY = new Factory<>(DayCounterData::new, DayCounterData::load);

    public static DayCounterData load(CompoundTag tag, HolderLookup.Provider provider) {
        DayCounterData data = new DayCounterData();
        data.dayCounter = tag.getLong(TAG_DAY);
        data.offset = tag.getLong(TAG_OFFSET);
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putLong(TAG_DAY, dayCounter);
        tag.putLong(TAG_OFFSET, this.offset);
        return tag;
    }

    public static DayCounterData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, TAG_DAY);
    }

    public long getDayCounter() {
        return dayCounter;
    }

    public static long getCurrentDay(ServerLevel level) {
        return get(level).getDayCounter();
    }

    public void setDayCounter(long newValue) {
        this.dayCounter = newValue;
        this.setDirty();
    }

    public long getOffset() {
        return offset;
    }

    public static long getOffset(ServerLevel level) {
        return get(level).getOffset();
    }

    public void setOffset(long newValue) {
        this.offset = newValue;
        this.setDirty();
    }

    public static void resetDayCounter(ServerLevel level) {
        long realDay = level.getDayTime() / 24000;
        setDayCounter(level, realDay);
    }

    public static void setDayCounter(ServerLevel level, long newDay) {
        DayCounterData data = get(level);

        long realDay = level.getDayTime() / 24000;
        long newOffset = newDay - realDay;

        data.setDayCounter(newDay);
        data.setOffset(newOffset);
    }
}
