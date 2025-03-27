package de.bigbull.counter.neoforge;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.util.ModKeybinds;
import de.bigbull.counter.neoforge.config.Config;
import de.bigbull.counter.neoforge.datagen.DataGenerators;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Counter.MOD_ID)
public class NeoForgeCounter {
    public NeoForgeCounter(IEventBus eventBus, ModContainer modContainer) {
        Counter.init();

        eventBus.addListener(this::registerKeyMappings);
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::registerNetwork);
        eventBus.addListener(DataGenerators::gatherData);

        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ConfigHelper.registerClientConfig(() -> Config.CLIENT);
        ConfigHelper.registerServerConfig(() -> Config.SERVER);
    }

    public void registerKeyMappings(RegisterKeyMappingsEvent event) {
        ModKeybinds.register(event::register);
    }

    public void registerNetwork(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0.0");
        Counter.registerClientPackets(registrar);
    }
}