package de.bigbull.counter.util.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class TimeCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("time")
                .requires(source -> ServerConfig.ENABLE_TIME_COUNTER.get() && ServerConfig.ENABLE_TIME_COMMAND.get())
                .then(Commands.literal("get")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            MinecraftServer server = context.getSource().getServer();
                            ServerLevel level = server.overworld();

                            String timeString = CounterManager.formatTime(level.getDayTime());

                            context.getSource().sendSuccess(() -> Component.literal("⏰ " + timeString), false);
                            return Command.SINGLE_SUCCESS;
                        }));
    }
}