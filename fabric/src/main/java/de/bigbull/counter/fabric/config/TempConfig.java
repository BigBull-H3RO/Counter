package de.bigbull.counter.fabric.config;

public class TempConfig {
    public static final ClientConfig CLIENT;
    public static final ServerConfig SERVER;

    static
    {
        CLIENT = new ClientConfig();
        SERVER = new ServerConfig();
    }
}
