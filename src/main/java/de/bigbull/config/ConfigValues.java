package de.bigbull.config;

import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigValues {
    static Path CLIENT_CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("counter-client.toml");
    static Path SERVER_CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("counter-server.toml");
    private static final boolean CLIENT_CONFIG_FILE = Files.exists(CLIENT_CONFIG_PATH);
    private static final boolean SERVER_CONFIG_FILE = Files.exists(SERVER_CONFIG_PATH);

    public static boolean ENABLE_DAY_COUNTER = SERVER_CONFIG_FILE ? ServerConfig.ENABLE_DAY_COUNTER.get() : ServerConfig.ENABLE_DAY_COUNTER.getDefault();
    public static boolean ENABLE_DAY_MESSAGE = SERVER_CONFIG_FILE ? ServerConfig.ENABLE_DAY_MESSAGE.get() : ServerConfig.ENABLE_DAY_MESSAGE.getDefault();
    public static boolean SHOW_DAY_OVERLAY = SERVER_CONFIG_FILE ? ServerConfig.SHOW_DAY_OVERLAY.get() : ServerConfig.SHOW_DAY_OVERLAY.getDefault();
    public static int DAY_OVERLAY_X = CLIENT_CONFIG_FILE ? ClientConfig.DAY_OVERLAY_X.get() : ClientConfig.DAY_OVERLAY_X.getDefault();
    public static int DAY_OVERLAY_Y = CLIENT_CONFIG_FILE ? ClientConfig.DAY_OVERLAY_Y.get() : ClientConfig.DAY_OVERLAY_Y.getDefault();
    public static double DAY_OVERLAY_SIZE = CLIENT_CONFIG_FILE ? ClientConfig.DAY_OVERLAY_SIZE.get() : ClientConfig.DAY_OVERLAY_SIZE.getDefault();

    public static boolean ENABLE_DEATH_COUNTER = SERVER_CONFIG_FILE ? ServerConfig.ENABLE_DEATH_COUNTER.get() : ServerConfig.ENABLE_DEATH_COUNTER.getDefault();
    public static boolean SHOW_DEATH_OVERLAY = SERVER_CONFIG_FILE ? ServerConfig.SHOW_DEATH_OVERLAY.get() : ServerConfig.SHOW_DEATH_OVERLAY.getDefault();
    public static int MAX_PLAYERS_SHOWN = SERVER_CONFIG_FILE ? ServerConfig.MAX_PLAYERS_SHOWN.get() : ServerConfig.MAX_PLAYERS_SHOWN.getDefault();
    public static int DEATH_LIST_X = CLIENT_CONFIG_FILE ? ClientConfig.DEATH_LIST_X.get() : ClientConfig.DEATH_LIST_X.getDefault();
    public static int DEATH_LIST_Y = CLIENT_CONFIG_FILE ? ClientConfig.DEATH_LIST_Y.get() : ClientConfig.DEATH_LIST_Y.getDefault();
    public static double DEATH_LIST_SIZE = CLIENT_CONFIG_FILE ? ClientConfig.DEATH_LIST_SIZE.get() : ClientConfig.DEATH_LIST_SIZE.getDefault();
    public static int DEATH_OVERLAY_WIDTH = CLIENT_CONFIG_FILE ? ClientConfig.DEATH_OVERLAY_WIDTH.get() : ClientConfig.DEATH_OVERLAY_WIDTH.getDefault();
    public static int DEATH_SELF_X = CLIENT_CONFIG_FILE ? ClientConfig.DEATH_SELF_X.get() : ClientConfig.DEATH_SELF_X.getDefault();
    public static int DEATH_SELF_Y = CLIENT_CONFIG_FILE ? ClientConfig.DEATH_SELF_Y.get() : ClientConfig.DEATH_SELF_Y.getDefault();
    public static double DEATH_SELF_SIZE = CLIENT_CONFIG_FILE ? ClientConfig.DEATH_SELF_SIZE.get() : ClientConfig.DEATH_SELF_SIZE.getDefault();
    public static boolean SHOW_DEATH_OVERLAY_ALWAYS = SERVER_CONFIG_FILE ? ServerConfig.SHOW_DEATH_OVERLAY_ALWAYS.get() : ServerConfig.SHOW_DEATH_OVERLAY_ALWAYS.getDefault();
    public static ServerConfig.DeathOverlayMode DEATH_OVERLAY_MODE = SERVER_CONFIG_FILE ? ServerConfig.DEATH_OVERLAY_MODE.get() : ServerConfig.DEATH_OVERLAY_MODE.getDefault();
    public static ServerConfig.DeathInChatMode SHOW_DEATH_LIST_IN_CHAT = SERVER_CONFIG_FILE ? ServerConfig.SHOW_DEATH_IN_CHAT_MODE.get() : ServerConfig.SHOW_DEATH_IN_CHAT_MODE.getDefault();
    public static ServerConfig.DeathChatMode DEATH_LIST_CHAT_MODE = SERVER_CONFIG_FILE ? ServerConfig.DEATH_CHAT_MODE_TYPE.get() : ServerConfig.DEATH_CHAT_MODE_TYPE.getDefault();
    public static boolean SHOW_DEATH_LIST_ON_DEATH_GLOBAL = SERVER_CONFIG_FILE ? ServerConfig.SHOW_DEATH_LIST_ON_DEATH_GLOBAL.get() : ServerConfig.SHOW_DEATH_LIST_ON_DEATH_GLOBAL.getDefault();
}
