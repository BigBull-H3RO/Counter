package de.bigbull.counter;

import com.mojang.logging.LogUtils;
import de.bigbull.counter.util.CounterCommands;
import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.data.DataGenerators;
import de.bigbull.counter.network.*;
import de.bigbull.counter.util.ModKeybinds;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(Counter.MODID)
public class Counter {
    public static final String MODID = "counter";
    public static final Logger logger = LogUtils.getLogger();

    public Counter(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerNetwork);
        modEventBus.addListener(this::registerKeyMappings);
        modEventBus.addListener(DataGenerators::gatherData);

        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_SPEC, "counter-client.toml");
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC, "counter-server.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    private void registerNetwork(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID)
                .versioned("1.0.0")
                .optional();

        registrar.playToClient(DayCounterPacket.TYPE, DayCounterPacket.CODEC, DayCounterPacket::handle);
        registrar.playToClient(DeathCounterPacket.TYPE, DeathCounterPacket.CODEC, DeathCounterPacket::handle);
        registrar.playToClient(SurvivalTimePacket.TYPE, SurvivalTimePacket.CODEC, SurvivalTimePacket::handle);
    }

    @SubscribeEvent
    private void registerCommands(RegisterCommandsEvent event) {
        CounterCommands.register(event.getDispatcher());
    }

    public void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ModKeybinds.OPEN_EDIT_GUI);
        event.register(ModKeybinds.SHOW_OVERLAYS);
    }
}
