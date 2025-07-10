package de.bigbull.counter.util.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.CounterManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class CoordsCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("coords")
                .requires(source -> ServerConfig.ENABLE_COORDS_COUNTER.get())
                .then(Commands.literal("get")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            CounterManager.sendCoordsMessage(player, player);
                            return Command.SINGLE_SUCCESS;
                        }))
                .then(Commands.literal("send")
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
                                            sender.sendSystemMessage(Component.translatable("command.player_not_found"));
                                        }
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })));
    }

    private static final SuggestionProvider<CommandSourceStack> PLAYER_SUGGESTIONS = (context, builder) -> {
        List<String> playerNames = context.getSource().getServer().getPlayerList().getPlayers().stream()
                .map(ServerPlayer::getScoreboardName)
                .collect(Collectors.toList());
        playerNames.add("all");
        return SharedSuggestionProvider.suggest(playerNames, builder);
    };
}