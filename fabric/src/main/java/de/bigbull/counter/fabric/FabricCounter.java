package de.bigbull.counter.fabric;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.fabric.config.FabricTomlConfig;
import de.bigbull.counter.fabric.config.TempConfig;
import de.bigbull.counter.fabric.events.FabricGameEvents;
import de.bigbull.counter.fabric.network.ConfigSyncManager;
import net.fabricmc.api.ModInitializer;

public class FabricCounter implements ModInitializer {
    @Override
    public void onInitialize() {
        Counter.init();
        Counter.registerClientPackets(false);

        // Initialisiere die TOML-Konfiguration
        FabricTomlConfig.initialize();

        // Registriere die Konfiguration
        ConfigHelper.registerClientConfig(() -> TempConfig.CLIENT);
        ConfigHelper.registerServerConfig(() -> TempConfig.SERVER);

        // Initialisiere die Netzwerkkommunikation
        ConfigSyncManager.init();

        // Registriere die Ereignishandler
        FabricGameEvents.registerEvents();
    }
}