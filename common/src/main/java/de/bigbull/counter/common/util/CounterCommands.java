package de.bigbull.counter.common.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.network.packets.DayCounterPacket;
import de.bigbull.counter.common.network.packets.DeathCounterPacket;
import de.bigbull.counter.common.platform.Services;
import de.bigbull.counter.common.util.saveddata.DayCounterData;
import de.bigbull.counter.common.util.saveddata.DeathCounterData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CounterCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("counter")
                .then(Commands.literal("day")
                        .requires(source -> ConfigHelper.get().getServer().enabledDayCounter())
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
                        .then(Commands.literal("set")
                                .requires(source -> source.hasPermission(2))
                                .then(Commands.argument("days", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            int newDay = IntegerArgumentType.getInteger(context, "days");
                                            MinecraftServer server = context.getSource().getServer();
                                            DayCounterData.setDayCounter(server.overworld(), newDay);
                                            context.getSource().sendSuccess(() -> Component.translatable("command.daycounter.set", newDay), true);

                                            Services.PLATFORM.sendPacketToAllClient(new DayCounterPacket(newDay), server);
                                            return Command.SINGLE_SUCCESS;
                                        }))))
                .then(Commands.literal("death")
                        .requires(source -> ConfigHelper.get().getServer().enableDeathCounter())
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

                                                    Services.PLATFORM.sendPacketToAllClient(new DeathCounterPacket(data.getDeathCountMap(), data.getPlayerNames()), level.getServer());

                                                    return Command.SINGLE_SUCCESS;
                                                }))))
                        .then(Commands.literal("reset")
                                .requires(source -> source.hasPermission(2))
                                .executes(context -> {
                                    ServerLevel level = context.getSource().getServer().overworld();
                                    DeathCounterData data = DeathCounterData.get(level);
                                    data.resetAllDeaths();

                                    context.getSource().sendSuccess(() -> Component.translatable("command.deathcounter.reset"), true);
                                    Services.PLATFORM.sendPacketToAllClient(new DeathCounterPacket(data.getDeathCountMap(), data.getPlayerNames()), level.getServer());
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("time")
                        .requires(source -> ConfigHelper.get().getServer().enableTimeCounter())
                        .then(Commands.literal("get")
                                .requires(source -> source.hasPermission(0))
                                .executes(context -> {
                                    MinecraftServer server = context.getSource().getServer();
                                    ServerLevel level = server.overworld();
                                    long time = level.getDayTime() % 24000;

                                    int hours = (int) ((time / 1000 + 6) % 24);
                                    int minutes = (int) ((time % 1000) / 1000.0 * 60);

                                    boolean is24Hour = ConfigHelper.get().getServer().timeFormat24h();
                                    String timeString = is24Hour
                                            ? String.format("%02d:%02d", hours, minutes)
                                            : String.format("%02d:%02d %s", (hours % 12 == 0 ? 12 : hours % 12), minutes, hours < 12 ? "AM" : "PM");

                                    context.getSource().sendSuccess(() -> Component.literal("⏰ " + timeString), false);
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("coords")
                        .requires(source -> ConfigHelper.get().getServer().enableCoordsCounter())
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
                                        })))));
    }

    private static final SuggestionProvider<CommandSourceStack> PLAYER_SUGGESTIONS = (context, builder) -> {
        List<String> playerNames = context.getSource().getServer().getPlayerList().getPlayers().stream()
                .map(ServerPlayer::getScoreboardName)
                .collect(Collectors.toList());
        playerNames.add("all");
        return SharedSuggestionProvider.suggest(playerNames, builder);
    };
}
