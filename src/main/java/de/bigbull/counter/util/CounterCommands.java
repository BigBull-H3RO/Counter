package de.bigbull.counter.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.network.DayCounterPacket;
import de.bigbull.counter.network.DeathCounterPacket;
import de.bigbull.counter.util.saveddata.DayCounterData;
import de.bigbull.counter.util.saveddata.DeathCounterData;
import de.bigbull.counter.util.saveddata.SurvivalTimeData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CounterCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("counter")
                .then(dayCommand())
                .then(deathCommand())
                .then(timeCommand())
                .then(coordsCommand())
                .then(survivalCommand()));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> dayCommand() {
        return Commands.literal("day")
                .requires(source -> ServerConfig.ENABLE_DAY_COUNTER.get())
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
                                    PacketDistributor.sendToAllPlayers(new DayCounterPacket(newDay));
                                    return Command.SINGLE_SUCCESS;
                                })));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> deathCommand() {
        return Commands.literal("death")
                .requires(source -> ServerConfig.ENABLE_DEATH_COUNTER.get())
                .then(Commands.literal("get")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            ServerPlayer executingPlayer = context.getSource().getPlayerOrException();
                            ServerLevel level = executingPlayer.serverLevel();
                            DeathCounterData data = DeathCounterData.get(level);
                            int deaths = data.getDeaths(executingPlayer.getUUID());

                            context.getSource().sendSuccess(() -> Component.translatable("overlay.counter.deaths_with_emoji", deaths), false);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> {
                                    ServerPlayer targetPlayer = EntityArgument.getPlayer(context, "player");
                                    ServerLevel level = targetPlayer.serverLevel();
                                    DeathCounterData data = DeathCounterData.get(level);
                                    int deaths = data.getDeaths(targetPlayer.getUUID());
                                    String key = deaths == 1
                                            ? "chat.deathcounter.player_death.singular"
                                            : "chat.deathcounter.player_death.plural";

                                    Component message = deaths == 1
                                            ? Component.translatable(key, targetPlayer.getName())
                                            : Component.translatable(key, targetPlayer.getName(), deaths);

                                    context.getSource().sendSuccess(() -> message, false);
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
                                            SurvivalTimeData surv = SurvivalTimeData.get(level);
                                            PacketDistributor.sendToAllPlayers(new DeathCounterPacket(
                                                    data.getDeathCountMap(),
                                                    data.getPlayerNames(),
                                                    surv.getBestTimesMap()));
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
                            SurvivalTimeData surv = SurvivalTimeData.get(level);
                            PacketDistributor.sendToAllPlayers(new DeathCounterPacket(
                                    data.getDeathCountMap(),
                                    data.getPlayerNames(),
                                    surv.getBestTimesMap()));

                            return Command.SINGLE_SUCCESS;
                        }));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> timeCommand() {
        return Commands.literal("time")
                .requires(source -> ServerConfig.ENABLE_TIME_COUNTER.get())
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

    private static LiteralArgumentBuilder<CommandSourceStack> coordsCommand() {
        return Commands.literal("coords")
                .requires(source -> ServerConfig.ENABLE_COORDS_COUNTER.get())
                .then(Commands.literal("get")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            CounterManager.sendCoordsMessage(player, player);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.argument("target", StringArgumentType.word())
                                .suggests(PLAYER_SUGGESTIONS)
                                .executes(context -> {
                                    ServerPlayer sender = context.getSource().getPlayerOrException();
                                    String target = StringArgumentType.getString(context, "target");
                                    if (target.equalsIgnoreCase("all")) {
                                        CounterManager.sendCoordsMessageToAll(sender);
                                    } else {
                                        ServerPlayer targetPlayer = sender.getServer().getPlayerList().getPlayerByName(target);
                                        if (targetPlayer != null) {
                                            CounterManager.sendCoordsMessage(sender, targetPlayer);
                                        } else {
                                            sender.sendSystemMessage(Component.translatable("command.coords.player_not_found"));
                                        }
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> survivalCommand() {
        return Commands.literal("survival")
                .requires(src -> ServerConfig.ENABLE_SURVIVAL_COUNTER.get())
                .then(Commands.literal("history")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            return sendHistory(context.getSource(), player);
                        })
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> {
                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                    return sendHistory(context.getSource(), target);
                                })))
                .then(Commands.literal("best")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            return sendBest(context.getSource(), player);
                        })
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> {
                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                    return sendBest(context.getSource(), target);
                                })))
                .then(Commands.literal("current")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            return sendCurrent(context.getSource(), player);
                        })
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> {
                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                    return sendCurrent(context.getSource(), target);
                                })))
                .then(Commands.literal("global")
                        .executes(context -> {
                            ServerLevel level = context.getSource().getLevel();
                            SurvivalTimeData data = SurvivalTimeData.get(level);
                            long best = data.getGlobalBest();
                            String name = data.getGlobalBestPlayer();
                            String time = CounterManager.formatSurvivalTime(best);
                            context.getSource().sendSuccess(() ->
                                    Component.translatable("command.survival.global_best", name, time), false);
                            return Command.SINGLE_SUCCESS;
                        }));
    }

    private static int sendHistory(CommandSourceStack source, ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        SurvivalTimeData data = SurvivalTimeData.get(level);
        long best = data.getBestTime(player.getUUID());
        boolean foundBest = false;
        for (long t : data.getHistory(player.getUUID())) {
            String time = CounterManager.formatSurvivalTime(t);
            final Component message = t == best
                    ? Component.translatable("overlay.counter.survival_with_emoji", time)
                    .copy().withStyle(style -> style.withColor(0x00FF00))
                    : Component.translatable("overlay.counter.survival_with_emoji", time);
            if (t == best) {
                foundBest = true;
            }
            source.sendSuccess(() -> message, false);
        }
        if (!foundBest && best > 0) {
            final Component bestMsg = Component.translatable("overlay.counter.best_survival_with_emoji",
                            CounterManager.formatSurvivalTime(best))
                    .withStyle(style -> style.withColor(0x00FF00));
            source.sendSuccess(() -> bestMsg, false);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int sendBest(CommandSourceStack source, ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        SurvivalTimeData data = SurvivalTimeData.get(level);
        long best = data.getBestTime(player.getUUID());
        String time = CounterManager.formatSurvivalTime(best);
        final Component message = Component.translatable("overlay.counter.best_survival_with_emoji", time);
        source.sendSuccess(() -> message, false);
        return Command.SINGLE_SUCCESS;
    }

    private static int sendCurrent(CommandSourceStack source, ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        SurvivalTimeData data = SurvivalTimeData.get(level);
        long current = level.getGameTime() - data.getLastDeathTick(player.getUUID());
        String time = CounterManager.formatSurvivalTime(current);
        final Component message = Component.translatable("overlay.counter.survival_with_emoji", time);
        source.sendSuccess(() -> message, false);
        return Command.SINGLE_SUCCESS;
    }

    private static final SuggestionProvider<CommandSourceStack> PLAYER_SUGGESTIONS = (context, builder) -> {
        List<String> playerNames = context.getSource().getServer().getPlayerList().getPlayers().stream()
                .map(ServerPlayer::getScoreboardName)
                .collect(Collectors.toList());
        playerNames.add("all");
        return SharedSuggestionProvider.suggest(playerNames, builder);
    };
}