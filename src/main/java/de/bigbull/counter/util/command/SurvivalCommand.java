package de.bigbull.counter.util.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import de.bigbull.counter.util.saveddata.SurvivalTimeData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class SurvivalCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("survival")
                .requires(source -> ServerConfig.ENABLE_SURVIVAL_COUNTER.get() && ServerConfig.ENABLE_SURVIVAL_COMMAND.get())
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
}