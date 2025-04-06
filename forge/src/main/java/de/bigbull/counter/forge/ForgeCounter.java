package de.bigbull.counter.forge;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.util.ModKeybinds;
import de.bigbull.counter.forge.config.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

@Mod(Counter.MOD_ID)
public class ForgeCounter {
    public static SimpleChannel network;

    public ForgeCounter(FMLJavaModLoadingContext context) {
        Counter.init();

        if (FMLEnvironment.dist.isClient()) {
            context.getModEventBus().addListener(this::registerKeyMappings);
        }

        context.getModEventBus().addListener(this::setup);

        context.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
        context.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        network = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(Counter.MOD_ID, "counterpackets")).simpleChannel();

        Counter.registerClientPackets();

        ConfigHelper.registerClientConfig(() -> Config.CLIENT);
        ConfigHelper.registerServerConfig(() -> Config.SERVER);
    }

    public void registerKeyMappings(RegisterKeyMappingsEvent event) {
        ModKeybinds.register(event::register);
    }
}