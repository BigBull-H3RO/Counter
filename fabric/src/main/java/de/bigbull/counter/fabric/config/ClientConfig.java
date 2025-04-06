package de.bigbull.counter.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.bigbull.counter.common.config.IClientConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ClientConfig implements IClientConfig {
    private static final File CONFIG_FILE = new File("config/counter/client_config.json");

    public boolean showDayOverlay = true;
    public boolean showDayOverlayAlways = true;
    public double dayOverlayX = 0.00625;
    public double dayOverlayY = 0.015;
    public double dayOverlaySize = 1.0;
    public int dayOverlayTextColor = 0xFFFFFF;

    public boolean showDeathListOverlay = true;
    public boolean showDeathListOverlayAlways = false;
    public double deathListOverlayX = 0.0125;
    public double deathListOverlayY = 0.16;
    public double deathListOverlaySize = 1.0;
    public int deathListOverlayWidth = 120;
    public DeathListOverlayStyle deathListOverlayStyle = DeathListOverlayStyle.TABLE;
    public int deathListTextColor = 0xFFFFFF;
    public int firstPlaceColor = 0xFFD700;
    public int secondPlaceColor = 0xC0C0C0;
    public int thirdPlaceColor = 0xCD7F32;

    public boolean showDeathSelfOverlay = true;
    public boolean showDeathSelfOverlayAlways = false;
    public double deathSelfOverlayX = 0.00625;
    public double deathSelfOverlayY = 0.068;
    public double deathSelfSize = 1.0;
    public int deathSelfTextColor = 0xFFFFFF;

    public boolean showTimeOverlay = false;
    public boolean showTimeOverlayAlways = true;
    public double timeOverlayX = 0.00781;
    public double timeOverlayY = 0.955;
    public double timeOverlaySize = 1.0;
    public int timeOverlayTextColor = 0xFFFFFF;

    public boolean showCoordsOverlay = false;
    public boolean showCoordsOverlayAlways = true;
    public double coordsOverlayX = 0.00781;
    public double coordsOverlayY = 0.905;
    public double coordsOverlaySize = 1.0;
    public int coordsOverlayTextColor = 0xFFFFFF;

    public boolean showPingAsText = true;
    public int pingColorGood = 0x00FF00;
    public int pingColorMedium = 0xFF9900;
    public int pingColorBad = 0xFF0000;

    public boolean showEmojis = true;

    @Override
    public boolean showDayOverlay() {
        return showDayOverlay;
    }

    @Override
    public void setShowDayOverlay(boolean value) {
        this.showDayOverlay = value;
    }

    @Override
    public boolean showDayOverlayAlways() {
        return showDayOverlayAlways;
    }

    @Override
    public double dayOverlayX() {
        return dayOverlayX;
    }

    @Override
    public void setDayOverlayX(double value) {
        this.dayOverlayX = value;
    }

    @Override
    public double dayOverlayY() {
        return dayOverlayY;
    }

    @Override
    public void setDayOverlayY(double value) {
        this.dayOverlayY = value;
    }

    @Override
    public double dayOverlaySize() {
        return dayOverlaySize;
    }

    @Override
    public int dayOverlayTextColor() {
        return dayOverlayTextColor;
    }

    @Override
    public boolean showDeathListOverlay() {
        return showDeathListOverlay;
    }

    @Override
    public void setShowDeathListOverlay(boolean value) {
        this.showDeathListOverlay = value;
    }

    @Override
    public boolean showDeathListOverlayAlways() {
        return showDeathListOverlayAlways;
    }

    @Override
    public double deathListOverlayX() {
        return deathListOverlayX;
    }

    @Override
    public void setDeathListOverlayX(double value) {
        this.deathListOverlayX = value;
    }

    @Override
    public double deathListOverlayY() {
        return deathListOverlayY;
    }

    @Override
    public void setDeathListOverlayY(double value) {
        this.deathListOverlayY = value;
    }

    @Override
    public double deathListOverlaySize() {
        return deathListOverlaySize;
    }

    @Override
    public int deathListOverlayWidth() {
        return deathListOverlayWidth;
    }

    @Override
    public DeathListOverlayStyle deathListOverlayStyle() {
        return deathListOverlayStyle;
    }

    @Override
    public int deathListTextColor() {
        return deathListTextColor;
    }

    @Override
    public int firstPlaceColor() {
        return firstPlaceColor;
    }

    @Override
    public int secondPlaceColor() {
        return secondPlaceColor;
    }

    @Override
    public int thirdPlaceColor() {
        return thirdPlaceColor;
    }

    @Override
    public boolean showDeathSelfOverlay() {
        return showDeathSelfOverlay;
    }

    @Override
    public void setShowDeathSelfOverlay(boolean value) {
        this.showDeathSelfOverlay = value;
    }

    @Override
    public boolean showDeathSelfOverlayAlways() {
        return showDeathSelfOverlayAlways;
    }

    @Override
    public double deathSelfOverlayX() {
        return deathSelfOverlayX;
    }

    @Override
    public void setDeathSelfOverlayX(double value) {
        this.deathSelfOverlayX = value;
    }

    @Override
    public double deathSelfOverlayY() {
        return deathSelfOverlayY;
    }

    @Override
    public void setDeathSelfOverlayY(double value) {
        this.deathSelfOverlayY = value;
    }

    @Override
    public double deathSelfSize() {
        return deathSelfSize;
    }

    @Override
    public int deathSelfTextColor() {
        return deathSelfTextColor;
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
    public boolean showTimeOverlayAlways() {
        return showTimeOverlayAlways;
    }

    @Override
    public double timeOverlayX() {
        return timeOverlayX;
    }

    @Override
    public void setTimeOverlayX(double value) {
        this.timeOverlayX = value;
    }

    @Override
    public double timeOverlayY() {
        return timeOverlayY;
    }

    @Override
    public void setTimeOverlayY(double value) {
        this.timeOverlayY = value;
    }

    @Override
    public double timeOverlaySize() {
        return timeOverlaySize;
    }

    @Override
    public int timeOverlayTextColor() {
        return timeOverlayTextColor;
    }

    @Override
    public boolean showCoordsOverlay() {
        return showCoordsOverlay;
    }

    @Override
    public void setShowCoordsOverlay(boolean value) {
        this.showCoordsOverlay = value;
    }

    @Override
    public boolean showCoordsOverlayAlways() {
        return showCoordsOverlayAlways;
    }

    @Override
    public double coordsOverlayX() {
        return coordsOverlayX;
    }

    @Override
    public void setCoordsOverlayX(double value) {
        this.coordsOverlayX = value;
    }

    @Override
    public double coordsOverlayY() {
        return coordsOverlayY;
    }

    @Override
    public void setCoordsOverlayY(double value) {
        this.coordsOverlayY = value;
    }

    @Override
    public double coordsOverlaySize() {
        return coordsOverlaySize;
    }

    @Override
    public int coordsOverlayTextColor() {
        return coordsOverlayTextColor;
    }

    @Override
    public boolean showPingAsText() {
        return showPingAsText;
    }

    @Override
    public int pingColorGood() {
        return pingColorGood;
    }

    @Override
    public int pingColorMedium() {
        return pingColorMedium;
    }

    @Override
    public int pingColorBad() {
        return pingColorBad;
    }

    @Override
    public boolean showEmojis() {
        return showEmojis;
    }

    public static ClientConfig loadConfig() {
        try {
            File configDir = new File("config/counter");
            if (!configDir.exists()) {
                configDir.mkdirs();
            }
            if (!CONFIG_FILE.exists()) {
                ClientConfig config = new ClientConfig();
                config.save();
                return config;
            }
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                Gson gson = new Gson();
                return gson.fromJson(reader, ClientConfig.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ClientConfig();
    }

    @Override
    public void save() {
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
