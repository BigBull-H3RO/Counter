package de.bigbull.counter.common.config;

import java.util.function.Supplier;

public class ConfigHelper {
    private static Supplier<IClientConfig> clientConfig;
    private static Supplier<IServerConfig> serverConfig;
    private static ConfigHelper helper;

    public static ConfigHelper get() {
        if (ConfigHelper.helper == null) {
            ConfigHelper.helper = new ConfigHelper();
        }
        return ConfigHelper.helper;
    }

    public static void registerClientConfig(Supplier<IClientConfig> clientConfig) {
        if (ConfigHelper.clientConfig == null)
        {
            ConfigHelper.clientConfig = clientConfig;
        }
    }

    public static void registerServerConfig(Supplier<IServerConfig> serverConfig) {
        if (ConfigHelper.serverConfig == null)
        {
            ConfigHelper.serverConfig = serverConfig;
        }
    }

    private ConfigHelper() {}

    public IClientConfig getClient() {
        return ConfigHelper.clientConfig.get();
    }

    public IServerConfig getServer() {
        return ConfigHelper.serverConfig.get();
    }
}
