package de.bigbull.counter.neoforge.platform;

import de.bigbull.counter.common.network.PacketBase;
import de.bigbull.counter.common.platform.services.IPlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.BiConsumer;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public <T extends PacketBase, B extends FriendlyByteBuf> void registerClientboundPacket(CustomPacketPayload.Type<T> type, Class<T> clazz, StreamCodec<B, T> codec, BiConsumer<T, Player> handler, Object... args) {
        PayloadRegistrar registrar = (PayloadRegistrar) args[0];

        IPayloadHandler<T> clientHandler = (packet, context) -> {
            context.enqueueWork(() -> {
                handler.accept(packet, Minecraft.getInstance().player);
            });
        };

        registrar.playToClient(type, (StreamCodec<RegistryFriendlyByteBuf, T>)codec, clientHandler);
    }

    @Override
    public void sendPacketToAllClient(PacketBase packet, MinecraftServer server) {
        PacketDistributor.sendToAllPlayers(packet);
    }

    @Override
    public void sendPacketToClient(PacketBase packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }
}

