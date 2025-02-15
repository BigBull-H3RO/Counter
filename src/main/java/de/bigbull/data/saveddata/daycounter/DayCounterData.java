package de.bigbull.data.saveddata.daycounter;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class DayCounterData extends SavedData {
    private static final String TAG_NAME = "DayCounter";
    private long dayCounter = 0;

    public static final Factory<DayCounterData> FACTORY = new Factory<>(DayCounterData::new, DayCounterData::load);

    public static DayCounterData load(CompoundTag tag, HolderLookup.Provider provider) {
        DayCounterData data = new DayCounterData();
        data.dayCounter = tag.getLong(TAG_NAME);
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putLong(TAG_NAME, dayCounter);
        return tag;
    }

    public long getDayCounter() {
        return dayCounter;
    }

    public void setDayCounter(long dayCounter) {
        this.dayCounter = dayCounter;
        this.setDirty();
    }

    public static DayCounterData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, TAG_NAME);
    }
}
