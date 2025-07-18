package de.bigbull.counter.util.saveddata;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;

public class DayCounterData extends SavedData {
    private static final String TAG_DAY = "DayCounter";
    private static final String TAG_LAST_REAL_DAY = "LastRealDay";
    private long dayCounter = 0;
    private long lastRealDay = 0;

    public static final Factory<DayCounterData> FACTORY = new Factory<>(DayCounterData::new, DayCounterData::load, DataFixTypes.LEVEL);

    public static DayCounterData load(CompoundTag tag, HolderLookup.Provider provider) {
        DayCounterData data = new DayCounterData();
        data.dayCounter = tag.getLong(TAG_DAY);
        data.lastRealDay = tag.contains(TAG_LAST_REAL_DAY) ? tag.getLong(TAG_LAST_REAL_DAY) : 0;
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putLong(TAG_DAY, dayCounter);
        tag.putLong(TAG_LAST_REAL_DAY, lastRealDay);
        return tag;
    }

    public long getLastRealDay() {
        return lastRealDay;
    }

    public void setLastRealDay(long newLastRealDay) {
        this.lastRealDay = newLastRealDay;
        this.setDirty();
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

    public static void setDayCounter(ServerLevel level, long newDay) {
        DayCounterData data = get(level);
        long realDay = level.getDayTime() / 24000;

        data.setDayCounter(newDay);
        data.setLastRealDay(realDay);
    }
}
