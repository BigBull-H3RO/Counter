package de.bigbull;

import com.mojang.logging.LogUtils;
import de.bigbull.command.CounterCommands;
import de.bigbull.config.ClientConfig;
import de.bigbull.config.ConfigValues;
import de.bigbull.config.ServerConfig;
import de.bigbull.data.DataGenerators;
import de.bigbull.network.*;
import de.bigbull.network.overlay.SyncDayOverlayConfigPacket;
import de.bigbull.network.overlay.SyncDayOverlayStatePacket;
import de.bigbull.network.overlay.SyncDeathOverlayConfigPacket;
import de.bigbull.network.overlay.SyncDeathOverlayStatePacket;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
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
        modEventBus.addListener(DataGenerators::gatherData);

        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_SPEC, "counter-client.toml");
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC, "counter-server.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        ConfigValues.SHOW_DAY_OVERLAY = ServerConfig.SHOW_OVERLAY.get();
    }


    private void registerNetwork(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID)
                .versioned("1.0.0")
                .optional();

        registrar.playToClient(DayCounterPacket.TYPE, DayCounterPacket.CODEC, DayCounterPacket::handle);
        registrar.playToClient(SyncDayOverlayStatePacket.TYPE, SyncDayOverlayStatePacket.CODEC, SyncDayOverlayStatePacket::handle);
        registrar.playToClient(SyncDayOverlayConfigPacket.TYPE, SyncDayOverlayConfigPacket.CODEC, SyncDayOverlayConfigPacket::handle);
        registrar.playToClient(DeathCounterPacket.TYPE, DeathCounterPacket.CODEC, DeathCounterPacket::handle);
        registrar.playToClient(SyncDeathOverlayConfigPacket.TYPE, SyncDeathOverlayConfigPacket.CODEC, SyncDeathOverlayConfigPacket::handle);
        registrar.playToClient(SyncDeathOverlayStatePacket.TYPE, SyncDeathOverlayStatePacket.CODEC, SyncDeathOverlayStatePacket::handle);
    }

    @SubscribeEvent
    private void registerCommands(RegisterCommandsEvent event) {
        CounterCommands.register(event.getDispatcher());
    }
}
