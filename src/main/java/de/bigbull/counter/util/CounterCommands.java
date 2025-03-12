package de.bigbull.counter.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.saveddata.DayCounterData;
import de.bigbull.counter.util.saveddata.DeathCounterData;
import de.bigbull.counter.network.DayCounterPacket;
import de.bigbull.counter.network.DeathCounterPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;

public class CounterCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("counter")
                .then(Commands.literal("day")
                        .requires(source -> ServerConfig.ENABLE_DAY_COUNTER.get())
                        .then(Commands.literal("get")
                                .requires(source -> source.hasPermission(0))
                                .executes(context -> {
                                    MinecraftServer server = context.getSource().getServer();
                                    ServerLevel level = server.overworld();
                                    long currentDay = DayCounterData.getCurrentDay(level);

                                    context.getSource().sendSuccess(
                                            () -> Component.translatable("overlay.counter.day_with_emoji", currentDay),
                                            false
                                    );
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("reset")
                                .requires(source -> source.hasPermission(2))
                                .executes(context -> {
                                    MinecraftServer server = context.getSource().getServer();
                                    DayCounterData.resetDayCounter(server.overworld());
                                    context.getSource().sendSuccess(() -> Component.translatable("command.daycounter.reset"), true);

                                    PacketDistributor.sendToAllPlayers(new DayCounterPacket(DayCounterData.getCurrentDay(server.overworld())));
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("set")
                                .requires(source -> source.hasPermission(2))
                                .then(Commands.argument("tage", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            int newDay = IntegerArgumentType.getInteger(context, "tage");
                                            MinecraftServer server = context.getSource().getServer();
                                            DayCounterData.setDayCounter(server.overworld(), newDay);
                                            context.getSource().sendSuccess(() -> Component.translatable("command.daycounter.set", newDay), true);

                                            PacketDistributor.sendToAllPlayers(new DayCounterPacket(newDay));
                                            return Command.SINGLE_SUCCESS;
                                        }))))
                .then(Commands.literal("death")
                        .requires(source -> ServerConfig.ENABLE_DEATH_COUNTER.get())
                        .then(Commands.literal("get")
                                .requires(source -> source.hasPermission(0))
                                .executes(context -> {
                                    ServerPlayer executingPlayer = context.getSource().getPlayerOrException();
                                    ServerLevel level = executingPlayer.serverLevel();
                                    DeathCounterData data = DeathCounterData.get(level);

                                    int deaths = data.getDeaths(executingPlayer.getUUID());
                                    context.getSource().sendSuccess(
                                            () -> Component.translatable("overlay.counter.deaths_with_emoji", deaths),
                                            false
                                    );
                                    return Command.SINGLE_SUCCESS;
                                })
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> {
                                            ServerPlayer targetPlayer = EntityArgument.getPlayer(context, "player");
                                            ServerLevel level = targetPlayer.serverLevel();
                                            DeathCounterData data = DeathCounterData.get(level);

                                            int deaths = data.getDeaths(targetPlayer.getUUID());
                                            String translationKey = deaths == 1 ? "chat.deathcounter.player_death.singular" : "chat.deathcounter.player_death.plural";
                                            context.getSource().sendSuccess(
                                                    () -> Component.translatable(translationKey, targetPlayer.getName(), deaths),
                                                    false
                                            );
                                            return Command.SINGLE_SUCCESS;
                                        })))
                        .then(Commands.literal("set")
                                .requires(source -> source.hasPermission(2))
                                .then(Commands.argument("player", EntityArgument.players())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(context -> {
                                                    Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "player");
                                                    int newDeathCount = IntegerArgumentType.getInteger(context, "amount");

                                                    ServerLevel level = context.getSource().getServer().overworld();
                                                    DeathCounterData data = DeathCounterData.get(level);

                                                    for (ServerPlayer targetPlayer : players) {
                                                        data.setDeaths(targetPlayer.getUUID(), newDeathCount);
                                                        context.getSource().sendSuccess(() ->
                                                                        Component.translatable("command.deathcounter.set", targetPlayer.getName().getString(), newDeathCount),
                                                                true);
                                                    }

                                                    PacketDistributor.sendToAllPlayers(new DeathCounterPacket(data.getDeathCountMap(), data.getPlayerNames()));

                                                    return Command.SINGLE_SUCCESS;
                                                }))))
                        .then(Commands.literal("reset")
                                .requires(source -> source.hasPermission(2))
                                .executes(context -> {
                                    MinecraftServer server = context.getSource().getServer();
                                    ServerLevel level = server.overworld();
                                    DeathCounterData data = DeathCounterData.get(level);
                                    data.resetAllDeaths();

                                    context.getSource().sendSuccess(() -> Component.translatable("command.deathcounter.reset"), true);
                                    PacketDistributor.sendToAllPlayers(new DeathCounterPacket(data.getDeathCountMap(), data.getPlayerNames()));
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("time")
                        .requires(source -> ServerConfig.ENABLE_TIME_Counter.get())
                        .then(Commands.literal("get")
                                .requires(source -> source.hasPermission(0))
                                .executes(context -> {
                                    MinecraftServer server = context.getSource().getServer();
                                    ServerLevel level = server.overworld();
                                    long time = level.getDayTime() % 24000;

                                    int hours = (int) ((time / 1000 + 6) % 24);
                                    int minutes = (int) ((time % 1000) / 1000.0 * 60);

                                    boolean is24Hour = ServerConfig.TIME_FORMAT_24H.get();
                                    String timeString = is24Hour
                                            ? String.format("%02d:%02d", hours, minutes)
                                            : String.format("%02d:%02d %s", (hours % 12 == 0 ? 12 : hours % 12), minutes, hours < 12 ? "AM" : "PM");

                                    context.getSource().sendSuccess(() -> Component.literal("⏰ " + timeString), false);
                                    return Command.SINGLE_SUCCESS;
                                }))));
    }
}
