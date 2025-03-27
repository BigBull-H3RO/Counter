package de.bigbull.counter.common;

import com.mojang.brigadier.CommandDispatcher;
import de.bigbull.counter.common.network.packets.DayCounterPacket;
import de.bigbull.counter.common.network.packets.DeathCounterPacket;
import de.bigbull.counter.common.platform.Services;
import de.bigbull.counter.common.util.CounterCommands;
import net.minecraft.commands.CommandSourceStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Counter {
    public static final String MOD_ID = "counter";
    public static final String MOD_NAME = "Counter";
    public static final Logger logger = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {}

    public static void registerClientPackets(Object... args) {
        Services.PLATFORM.registerClientboundPacket(
                DayCounterPacket.TYPE,
                DayCounterPacket.class,
                DayCounterPacket.CODEC,
                DayCounterPacket::handle, args);

        Services.PLATFORM.registerClientboundPacket(
                DeathCounterPacket.TYPE,
                DeathCounterPacket.class,
                DeathCounterPacket.CODEC,
                DeathCounterPacket::handle, args);
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        CounterCommands.register(dispatcher);
    }
}