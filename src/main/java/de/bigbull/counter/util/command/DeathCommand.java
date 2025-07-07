package de.bigbull.counter.util.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.network.DeathCounterPacket;
import de.bigbull.counter.util.saveddata.DeathCounterData;
import de.bigbull.counter.util.saveddata.SurvivalTimeData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;

public class DeathCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
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
}