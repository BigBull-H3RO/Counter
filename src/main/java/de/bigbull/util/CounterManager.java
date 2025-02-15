package de.bigbull.util;

import de.bigbull.data.saveddata.daycounter.DayCounterData;
import de.bigbull.data.saveddata.daycounter.DayCounterOffsetData;
import net.minecraft.server.level.ServerLevel;

public class CounterManager {
    public static long getCurrentDay(ServerLevel level) {
        return DayCounterData.get(level).getDayCounter();
    }

    public static long getOffset(ServerLevel level) {
        return DayCounterOffsetData.get(level).getOffset();
    }

    public static void resetDayCounter(ServerLevel level) {
        long realDay = level.getDayTime() / 24000;
        setDayCounter(level, realDay);
    }

    public static void setDayCounter(ServerLevel level, long newDay) {
        long realDay = level.getDayTime() / 24000;
        long offset = newDay - realDay;

        DayCounterData data = DayCounterData.get(level);
        data.setDayCounter(newDay);

        DayCounterOffsetData.get(level).setOffset(offset);
    }
}