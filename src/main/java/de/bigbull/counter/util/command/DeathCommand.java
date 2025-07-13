package de.bigbull.counter.util.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.network.DeathCounterPacket;
import de.bigbull.counter.util.saveddata.DeathCounterData;
import de.bigbull.counter.util.saveddata.SurvivalTimeData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DeathCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("death")
                .requires(source -> ServerConfig.ENABLE_DEATH_COUNTER.get() && ServerConfig.ENABLE_DEATH_COMMAND.get())
                .then(Commands.literal("get")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            ServerPlayer executingPlayer = context.getSource().getPlayerOrException();
                            ServerLevel level = executingPlayer.level();
                            DeathCounterData data = DeathCounterData.get(level);
                            int deaths = data.getDeaths(executingPlayer.getUUID());

                            context.getSource().sendSuccess(() -> Component.translatable("overlay.counter.deaths_with_emoji", deaths), false);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.argument("option", StringArgumentType.word())
                                .suggests(DEATH_GET_SUGGESTIONS)
                                .executes(context -> {
                                    String option = StringArgumentType.getString(context, "option");
                                    CommandSourceStack source = context.getSource();
                                    ServerPlayer player = source.getPlayerOrException();
                                    ServerLevel level = player.level();
                                    DeathCounterData data = DeathCounterData.get(level);

                                    if (option.equalsIgnoreCase("list")) {
                                        sendDeathListToPlayer(source, level, data);
                                    } else {
                                        ServerPlayer targetPlayer = level.getServer().getPlayerList().getPlayerByName(option);
                                        if (targetPlayer != null) {
                                            int deaths = data.getDeaths(targetPlayer.getUUID());
                                            Component message = getComponent(deaths, targetPlayer);
                                            source.sendSuccess(() -> message, false);
                                        } else {
                                            source.sendFailure(Component.translatable("command.player_not_found"));
                                        }
                                    }
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
                                                        Component.translatable("command.deathcounter.set", targetPlayer.getName().getString(), newDeathCount), true);
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

    private static Component getComponent(int deaths, ServerPlayer targetPlayer) {
        String key = deaths == 1
                ? "chat.deathcounter.player_death.singular"
                : "chat.deathcounter.player_death.plural";
        return deaths == 1
                ? Component.translatable(key, targetPlayer.getName())
                : Component.translatable(key, targetPlayer.getName(), deaths);
    }

    private static final SuggestionProvider<CommandSourceStack> DEATH_GET_SUGGESTIONS = (context, builder) -> {
        List<String> playerNames = context.getSource().getServer().getPlayerList().getPlayers().stream()
                .map(ServerPlayer::getScoreboardName)
                .collect(Collectors.toList());
        playerNames.add("list");
        return SharedSuggestionProvider.suggest(playerNames, builder);
    };
    
    private static void sendDeathListToPlayer(CommandSourceStack source, ServerLevel level, DeathCounterData data) {
        int textColor = ServerConfig.DEATH_LIST_CHATTEXT_COLOR.get();
        List<Map.Entry<UUID, Integer>> sortedDeaths = data.getDeathCountMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(ServerConfig.MAX_PLAYERS_SHOWN.get())
                .toList();

        if (sortedDeaths.isEmpty()) {
            source.sendSuccess(() -> Component.translatable("command.counter.no_data"), false);
            return;
        }

        Component header = Component.translatable("overlay.counter.deathlist").withStyle(style -> style.withColor(textColor));
        AtomicInteger counter = new AtomicInteger(0);

        List<MutableComponent> deathEntries = sortedDeaths.stream().map(entry -> {
            String playerName = data.getPlayerNames().getOrDefault(entry.getKey(), "Unknown");
            int deaths = entry.getValue();
            Component positionComponent = Component.literal(counter.incrementAndGet() + ".")
                    .withStyle(style -> style.withColor(0xFFFFFF));
            return Component.literal(positionComponent.getString() + " " + playerName + ": " + deaths);
        }).toList();

        source.sendSuccess(() -> header, false);
        deathEntries.forEach(msg -> source.sendSuccess(() -> msg, false));
    }
}