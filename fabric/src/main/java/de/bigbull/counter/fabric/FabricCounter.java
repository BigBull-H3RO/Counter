package de.bigbull.counter.fabric;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.fabric.config.FabricTomlConfig;
import de.bigbull.counter.fabric.config.TempConfig;
import de.bigbull.counter.fabric.events.FabricGameEvents;
import de.bigbull.counter.fabric.network.NetworkManager;
import net.fabricmc.api.ModInitializer;

public class FabricCounter implements ModInitializer {
    @Override
    public void onInitialize() {
        Counter.init();
        Counter.registerClientPackets(false);

        NetworkManager.init();

        FabricTomlConfig.initialize();

        ConfigHelper.registerClientConfig(() -> TempConfig.CLIENT);
        ConfigHelper.registerServerConfig(() -> TempConfig.SERVER);

        FabricGameEvents.registerEvents();
    }
}