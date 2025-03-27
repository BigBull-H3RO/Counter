package de.bigbull.counter.fabric;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.fabric.config.TempConfig;
import de.bigbull.counter.fabric.events.FabricGameEvents;
import net.fabricmc.api.ModInitializer;

public class FabricCounter implements ModInitializer {
    @Override
    public void onInitialize() {
        Counter.init();
        FabricGameEvents.registerEvents();
        Counter.registerClientPackets(false);

        ConfigHelper.registerClientConfig(() -> TempConfig.CLIENT);
        ConfigHelper.registerServerConfig(() -> TempConfig.SERVER);
    }
}
