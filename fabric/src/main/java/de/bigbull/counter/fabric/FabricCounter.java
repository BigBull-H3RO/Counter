package de.bigbull.counter.fabric;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.fabric.config.ClientConfig;
import de.bigbull.counter.fabric.config.ServerConfig;
import de.bigbull.counter.fabric.config.TempConfig;
import de.bigbull.counter.fabric.events.FabricGameEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricCounter implements ModInitializer {
    @Override
    public void onInitialize() {
        Counter.init();
        FabricGameEvents.registerEvents();
        Counter.registerClientPackets(false);

        TempConfig.SERVER = ServerConfig.loadConfig();

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            TempConfig.CLIENT = ClientConfig.loadConfig();
        }

        ConfigHelper.registerClientConfig(() -> TempConfig.CLIENT);
        ConfigHelper.registerServerConfig(() -> TempConfig.SERVER);
    }
}