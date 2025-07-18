package de.bigbull.counter.util.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.network.packet.DayCounterPacket;
import de.bigbull.counter.network.PacketDistributor;
import de.bigbull.counter.util.saveddata.DayCounterData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class DayCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("day")
                .requires(source -> ServerConfig.ENABLE_DAY_COUNTER.get() && ServerConfig.ENABLE_DAY_COMMAND.get())
                .then(Commands.literal("get")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            MinecraftServer server = context.getSource().getServer();
                            ServerLevel level = server.overworld();
                            long currentDay = DayCounterData.getCurrentDay(level);

                            context.getSource().sendSuccess(() -> Component.translatable("overlay.counter.day_with_emoji", currentDay), false);
                            return Command.SINGLE_SUCCESS;
                        }))
                .then(Commands.literal("set")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("days", IntegerArgumentType.integer(0))
                                .executes(context -> {
                                    int newDay = IntegerArgumentType.getInteger(context, "days");
                                    MinecraftServer server = context.getSource().getServer();
                                    DayCounterData.setDayCounter(server.overworld(), newDay);

                                    context.getSource().sendSuccess(() -> Component.translatable("command.daycounter.set", newDay), true);
                                    PacketDistributor.sendToAllPlayers(new DayCounterPacket(newDay), server);
                                    return Command.SINGLE_SUCCESS;
                                })));
    }
}