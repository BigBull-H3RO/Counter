package de.bigbull.network.client;

public class ClientDayCounterState {
    private static long dayCounter = 0;

    public static long getDayCounter() {
        return dayCounter;
    }

    public static void setDayCounter(long dayCounter) {
        ClientDayCounterState.dayCounter = dayCounter;
    }
}