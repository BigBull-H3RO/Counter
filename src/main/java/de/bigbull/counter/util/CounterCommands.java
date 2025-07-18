package de.bigbull.counter.util;

import com.mojang.brigadier.CommandDispatcher;
import de.bigbull.counter.util.command.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CounterCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("counter")
                .then(DayCommand.register())
                .then(DeathCommand.register())
                .then(TimeCommand.register())
                .then(CoordsCommand.register())
                .then(SurvivalCommand.register()));
    }
}