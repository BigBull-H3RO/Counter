package de.bigbull.counter.forge.platform;

import de.bigbull.counter.common.network.PacketBase;
import de.bigbull.counter.common.platform.services.IPlatformHelper;
import de.bigbull.counter.forge.ForgeCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.BiConsumer;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
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
        BiConsumer<T, CustomPayloadEvent.Context> clientHandler = (packet, ctx) -> {
            if(ctx.isClientSide()) {
                ctx.setPacketHandled(true);
                ctx.enqueueWork(() -> {
                    handler.accept(packet, Minecraft.getInstance().player);
                });
            }
        };

        ForgeCounter.network.messageBuilder(clazz).codec((StreamCodec<FriendlyByteBuf, T>)codec).consumerMainThread(clientHandler).add();
    }

    @Override
    public void sendPacketToAllClient(PacketBase packet, MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            ForgeCounter.network.send(packet, PacketDistributor.PLAYER.with(player));
        }
    }

    @Override
    public void sendPacketToClient(PacketBase packet, ServerPlayer player) {
        ForgeCounter.network.send(packet, PacketDistributor.PLAYER.with(player));
    }
}