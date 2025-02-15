package de.bigbull.data.saveddata.daycounter;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class DayCounterOffsetData extends SavedData {
    private static final String TAG_NAME = "DayCounterOffset";
    private long offset = 0;

    public static final Factory<DayCounterOffsetData> FACTORY = new Factory<>(DayCounterOffsetData::new, DayCounterOffsetData::load);

    public static DayCounterOffsetData load(CompoundTag tag, HolderLookup.Provider provider) {
        DayCounterOffsetData data = new DayCounterOffsetData();
        data.offset = tag.getLong(TAG_NAME);
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putLong(TAG_NAME, offset);
        return tag;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
        this.setDirty();
    }

    public static DayCounterOffsetData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, TAG_NAME);
    }
}