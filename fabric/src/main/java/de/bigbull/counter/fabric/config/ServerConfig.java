package de.bigbull.counter.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.bigbull.counter.common.config.IServerConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ServerConfig implements IServerConfig {
    private static final File CONFIG_FILE = new File("config/counter/server_config.json");

    public boolean enabledDayCounter = true;
    public boolean showDayOverlay = true;
    public boolean enableDayMessage = true;
    public boolean showDayInChat = true;
    public int dayChatTextColor = 0xFFFFFF;

    public boolean enableDeathCounter = true;
    public boolean showDeathOverlay = true;
    public int maxPlayersShown = 5;
    public DeathOverlayMode deathOverlayMode = IServerConfig.DeathOverlayMode.LIST;

    public boolean enableDeathInChat = true;
    public DeathInChatMode deathInChatMode = IServerConfig.DeathInChatMode.ON_DEATH;
    public DeathChatMode deathChatMode = IServerConfig.DeathChatMode.ONLY_SELF;
    public boolean showDeathListOnDeathGlobal = false;
    public int deathListChatTextColor = 0xFFFFFF;
    public int deathSelfChatTextColor = 0xFFFFFF;

    public boolean enableTimeCounter = true;
    public boolean showTimeOverlay = true;
    public boolean showCombinedDayTime = false;
    public boolean timeFormat24h = true;

    public boolean enableCoordsCounter = true;
    public boolean showCoordsOverlay = true;

    @Override
    public boolean enabledDayCounter() {
        return enabledDayCounter;
    }

    @Override
    public boolean showDayOverlay() {
        return showDayOverlay;
    }

    @Override
    public boolean enableDayMessage() {
        return enableDayMessage;
    }

    @Override
    public boolean showDayInChat() {
        return showDayInChat;
    }

    @Override
    public int dayChatTextColor() {
        return dayChatTextColor;
    }

    @Override
    public boolean enableDeathCounter() {
        return enableDeathCounter;
    }

    @Override
    public boolean showDeathOverlay() {
        return showDeathOverlay;
    }

    @Override
    public int maxPlayersShown() {
        return maxPlayersShown;
    }

    @Override
    public DeathOverlayMode deathOverlayMode() {
        return deathOverlayMode;
    }

    @Override
    public boolean enableDeathInChat() {
        return enableDeathInChat;
    }

    @Override
    public DeathInChatMode deathInChatMode() {
        return deathInChatMode;
    }

    @Override
    public DeathChatMode deathChatMode() {
        return deathChatMode;
    }

    @Override
    public boolean showDeathListOnDeathGlobal() {
        return showDeathListOnDeathGlobal;
    }

    @Override
    public int deathListChatTextColor() {
        return deathListChatTextColor;
    }

    @Override
    public int deathSelfChatTextColor() {
        return deathSelfChatTextColor;
    }

    @Override
    public boolean enableTimeCounter() {
        return enableTimeCounter;
    }

    @Override
    public boolean showTimeOverlay() {
        return showTimeOverlay;
    }

    @Override
    public void setShowTimeOverlay(boolean value) {
        this.showTimeOverlay = value;
    }

    @Override
    public boolean showCombinedDayTime() {
        return showCombinedDayTime;
    }

    @Override
    public boolean timeFormat24h() {
        return timeFormat24h;
    }

    @Override
    public boolean enableCoordsCounter() {
        return enableCoordsCounter;
    }

    @Override
    public boolean showCoordsOverlay() {
        return showCoordsOverlay;
    }

    public static ServerConfig loadConfig() {
        try {
            File configDir = new File("config/counter");
            if (!configDir.exists()) {
                configDir.mkdirs();
            }
            if (!CONFIG_FILE.exists()) {
                ServerConfig config = new ServerConfig();
                config.saveConfig();
                return config;
            }
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                Gson gson = new Gson();
                return gson.fromJson(reader, ServerConfig.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ServerConfig();
    }

    public void saveConfig() {
        try {
            File configDir = new File("config/counter");
            if (!configDir.exists()) {
                configDir.mkdirs();
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write(gson.toJson(this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
