package de.bigbull.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import de.bigbull.config.ServerConfig;
import de.bigbull.data.saveddata.daycounter.PlayerDayOverlayData;
import de.bigbull.data.saveddata.deathcounter.DeathCounterData;
import de.bigbull.data.saveddata.deathcounter.PlayerDeathOverlayData;
import de.bigbull.network.DayCounterPacket;
import de.bigbull.network.DeathCounterPacket;
import de.bigbull.util.CounterManager;
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
                        .then(Commands.literal("reset")
                                .requires(source -> source.hasPermission(2))
                                .executes(context -> {
                                    MinecraftServer server = context.getSource().getServer();
                                    CounterManager.resetDayCounter(server.overworld());
                                    context.getSource().sendSuccess(() -> Component.translatable("command.daycounter.reset"), true);

                                    PacketDistributor.sendToAllPlayers(new DayCounterPacket(CounterManager.getCurrentDay(server.overworld())));
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("set")
                                .requires(source -> source.hasPermission(2))
                                .then(Commands.argument("tage", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            int newDay = IntegerArgumentType.getInteger(context, "tage");
                                            MinecraftServer server = context.getSource().getServer();
                                            CounterManager.setDayCounter(server.overworld(), newDay);
                                            context.getSource().sendSuccess(() -> Component.translatable("command.daycounter.set", newDay), true);

                                            PacketDistributor.sendToAllPlayers(new DayCounterPacket(newDay));
                                            return Command.SINGLE_SUCCESS;
                                        })))
                        .then(Commands.literal("show")
                                .requires(source -> source.hasPermission(0))
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    ServerLevel level = player.serverLevel();

                                    if (!ServerConfig.SHOW_DAY_OVERLAY.get()) {
                                        context.getSource().sendFailure(Component.translatable("command.daycounter.error"));
                                        return 0;
                                    }

                                    PlayerDayOverlayData.get(level).setOverlayState(player, true);
                                    context.getSource().sendSuccess(() -> Component.translatable("command.daycounter.show"), true);
                                    return 1;
                                }))
                        .then(Commands.literal("hide")
                                .requires(source -> source.hasPermission(0))
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    ServerLevel level = player.serverLevel();

                                    if (!ServerConfig.SHOW_DAY_OVERLAY.get()) {
                                        context.getSource().sendFailure(Component.translatable("command.daycounter.error"));
                                        return 0;
                                    }

                                    PlayerDayOverlayData.get(level).setOverlayState(player, false);
                                    context.getSource().sendSuccess(() -> Component.translatable("command.daycounter.hide"), true);
                                    return 1;
                                })))
                .then(Commands.literal("death")
                        .then(Commands.literal("deaths")
                                .requires(source -> source.hasPermission(0))
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    ServerLevel level = player.serverLevel();
                                    DeathCounterData data = DeathCounterData.get(level);

                                    int deaths = data.getDeaths(player.getUUID());
                                    context.getSource().sendSuccess(() -> Component.translatable("overlay.counter.deaths", deaths), false);
                                    return Command.SINGLE_SUCCESS;
                                }))
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
                                                        PacketDistributor.sendToPlayer(targetPlayer, new DeathCounterPacket(data.getDeathCountMap()));

                                                        context.getSource().sendSuccess(() ->
                                                                        Component.translatable("command.deathcounter.set", targetPlayer.getName().getString(), newDeathCount),
                                                                true);
                                                    }

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
                                    PacketDistributor.sendToAllPlayers(new DeathCounterPacket(data.getDeathCountMap()));
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("show")
                                .requires(source -> source.hasPermission(0))
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    ServerLevel level = player.serverLevel();

                                    if (!ServerConfig.SHOW_DEATH_OVERLAY.get()) {
                                        context.getSource().sendFailure(Component.translatable("command.deathcounter.error"));
                                        return 0;
                                    }

                                    PlayerDeathOverlayData.get(level).setOverlayState(player, true);
                                    context.getSource().sendSuccess(() -> Component.translatable("command.deathcounter.show"), true);
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("hide")
                                .requires(source -> source.hasPermission(0))
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    ServerLevel level = player.serverLevel();

                                    if (!ServerConfig.SHOW_DEATH_OVERLAY.get()) {
                                        context.getSource().sendFailure(Component.translatable("command.deathcounter.error"));
                                        return 0;
                                    }

                                    PlayerDeathOverlayData.get(level).setOverlayState(player, false);
                                    context.getSource().sendSuccess(() -> Component.translatable("command.deathcounter.hide"), true);
                                    return Command.SINGLE_SUCCESS;
                                }))));
    }
}
