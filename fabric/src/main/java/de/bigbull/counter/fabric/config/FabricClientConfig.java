package de.bigbull.counter.fabric.config;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.IClientConfig;
import de.bigbull.counter.fabric.config.toml.TomlParser;
import de.bigbull.counter.fabric.config.toml.TomlTable;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

public class FabricClientConfig implements IClientConfig {
    private static final File CONFIG_FILE = new File(FabricTomlConfig.CONFIG_DIR, "client_config.toml");

    private boolean showDayOverlay = true;
    private boolean showDayOverlayAlways = true;
    private double dayOverlayX = 0.00625;
    private double dayOverlayY = 0.015;
    private double dayOverlaySize = 1.0;
    private int dayOverlayTextColor = 0xFFFFFF;

    private boolean showDeathListOverlay = true;
    private boolean showDeathListOverlayAlways = false;
    private double deathListOverlayX = 0.0125;
    private double deathListOverlayY = 0.16;
    private double deathListOverlaySize = 1.0;
    private DeathListOverlayStyle deathListOverlayStyle = DeathListOverlayStyle.TABLE;
    private int deathListOverlayWidth = 120;
    private int deathListTextColor = 0xFFFFFF;
    private int firstPlaceColor = 0xFFD700;
    private int secondPlaceColor = 0xC0C0C0;
    private int thirdPlaceColor = 0xCD7F32;

    private boolean showDeathSelfOverlay = true;
    private boolean showDeathSelfOverlayAlways = false;
    private double deathSelfOverlayX = 0.00625;
    private double deathSelfOverlayY = 0.068;
    private double deathSelfSize = 1.0;
    private int deathSelfTextColor = 0xFFFFFF;

    private boolean showTimeOverlay = false;
    private boolean showTimeOverlayAlways = true;
    private double timeOverlayX = 0.00781;
    private double timeOverlayY = 0.955;
    private double timeOverlaySize = 1.0;
    private int timeOverlayTextColor = 0xFFFFFF;

    private boolean showCoordsOverlay = false;
    private boolean showCoordsOverlayAlways = true;
    private double coordsOverlayX = 0.00781;
    private double coordsOverlayY = 0.905;
    private double coordsOverlaySize = 1.0;
    private int coordsOverlayTextColor = 0xFFFFFF;

    private boolean showPingAsText = true;
    private int pingColorGood = 0x00FF00;
    private int pingColorMedium = 0xFF9900;
    private int pingColorBad = 0xFF0000;

    private boolean showEmojis = true;

    public FabricClientConfig() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            loadConfig();
        }
    }

    public void loadConfig() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            return;
        }

        try {
            if (!CONFIG_FILE.exists()) {
                saveConfig();
                return;
            }

            TomlTable rootTable = TomlParser.parseFile(CONFIG_FILE);

            if (rootTable.contains("dayCounterOverlay")) {
                TomlTable daySection = rootTable.get("dayCounterOverlay").asTable();
                if (daySection.contains("showDayOverlay"))
                    showDayOverlay = daySection.get("showDayOverlay").asBoolean();
                if (daySection.contains("showOverlayAlways"))
                    showDayOverlayAlways = daySection.get("showOverlayAlways").asBoolean();
                if (daySection.contains("dayOverlayX"))
                    dayOverlayX = daySection.get("dayOverlayX").asDouble();
                if (daySection.contains("dayOverlayY"))
                    dayOverlayY = daySection.get("dayOverlayY").asDouble();
                if (daySection.contains("dayOverlaySize"))
                    dayOverlaySize = daySection.get("dayOverlaySize").asDouble();
                if (daySection.contains("dayOverlayTextColor"))
                    dayOverlayTextColor = daySection.get("dayOverlayTextColor").asInteger();
            }

            if (rootTable.contains("deathCounterList")) {
                TomlTable deathListSection = rootTable.get("deathCounterList").asTable();
                if (deathListSection.contains("showDeathListOverlay"))
                    showDeathListOverlay = deathListSection.get("showDeathListOverlay").asBoolean();
                if (deathListSection.contains("showListOverlayAlways"))
                    showDeathListOverlayAlways = deathListSection.get("showListOverlayAlways").asBoolean();
                if (deathListSection.contains("deathListX"))
                    deathListOverlayX = deathListSection.get("deathListX").asDouble();
                if (deathListSection.contains("deathListY"))
                    deathListOverlayY = deathListSection.get("deathListY").asDouble();
                if (deathListSection.contains("deathListSize"))
                    deathListOverlaySize = deathListSection.get("deathListSize").asDouble();
                if (deathListSection.contains("deathOverlayStyle"))
                    deathListOverlayStyle = DeathListOverlayStyle.valueOf(deathListSection.get("deathOverlayStyle").asString());
                if (deathListSection.contains("deathOverlayWidth"))
                    deathListOverlayWidth = deathListSection.get("deathOverlayWidth").asInteger();
                if (deathListSection.contains("deathListTextColor"))
                    deathListTextColor = deathListSection.get("deathListTextColor").asInteger();
                if (deathListSection.contains("firstPlaceColor"))
                    firstPlaceColor = deathListSection.get("firstPlaceColor").asInteger();
                if (deathListSection.contains("secondPlaceColor"))
                    secondPlaceColor = deathListSection.get("secondPlaceColor").asInteger();
                if (deathListSection.contains("thirdPlaceColor"))
                    thirdPlaceColor = deathListSection.get("thirdPlaceColor").asInteger();
            }

            if (rootTable.contains("deathCounterSelf")) {
                TomlTable deathSelfSection = rootTable.get("deathCounterSelf").asTable();
                if (deathSelfSection.contains("showDeathSelfOverlay"))
                    showDeathSelfOverlay = deathSelfSection.get("showDeathSelfOverlay").asBoolean();
                if (deathSelfSection.contains("showSelfOverlayAlways"))
                    showDeathSelfOverlayAlways = deathSelfSection.get("showSelfOverlayAlways").asBoolean();
                if (deathSelfSection.contains("deathSelfX"))
                    deathSelfOverlayX = deathSelfSection.get("deathSelfX").asDouble();
                if (deathSelfSection.contains("deathSelfY"))
                    deathSelfOverlayY = deathSelfSection.get("deathSelfY").asDouble();
                if (deathSelfSection.contains("deathSelfSize"))
                    deathSelfSize = deathSelfSection.get("deathSelfSize").asDouble();
                if (deathSelfSection.contains("deathSelfTextColor"))
                    deathSelfTextColor = deathSelfSection.get("deathSelfTextColor").asInteger();
            }

            if (rootTable.contains("timeOverlay")) {
                TomlTable timeSection = rootTable.get("timeOverlay").asTable();
                if (timeSection.contains("showTimeOverlay"))
                    showTimeOverlay = timeSection.get("showTimeOverlay").asBoolean();
                if (timeSection.contains("showTimeOverlayAlways"))
                    showTimeOverlayAlways = timeSection.get("showTimeOverlayAlways").asBoolean();
                if (timeSection.contains("timeOverlayX"))
                    timeOverlayX = timeSection.get("timeOverlayX").asDouble();
                if (timeSection.contains("timeOverlayY"))
                    timeOverlayY = timeSection.get("timeOverlayY").asDouble();
                if (timeSection.contains("timeOverlaySize"))
                    timeOverlaySize = timeSection.get("timeOverlaySize").asDouble();
                if (timeSection.contains("timeOverlayTextColor"))
                    timeOverlayTextColor = timeSection.get("timeOverlayTextColor").asInteger();
            }

            if (rootTable.contains("coordsOverlay")) {
                TomlTable coordsSection = rootTable.get("coordsOverlay").asTable();
                if (coordsSection.contains("showCoordsOverlay"))
                    showCoordsOverlay = coordsSection.get("showCoordsOverlay").asBoolean();
                if (coordsSection.contains("showCoordsOverlayAlways"))
                    showCoordsOverlayAlways = coordsSection.get("showCoordsOverlayAlways").asBoolean();
                if (coordsSection.contains("coordsOverlayX"))
                    coordsOverlayX = coordsSection.get("coordsOverlayX").asDouble();
                if (coordsSection.contains("coordsOverlayY"))
                    coordsOverlayY = coordsSection.get("coordsOverlayY").asDouble();
                if (coordsSection.contains("coordsOverlaySize"))
                    coordsOverlaySize = coordsSection.get("coordsOverlaySize").asDouble();
                if (coordsSection.contains("coordsOverlayTextColor"))
                    coordsOverlayTextColor = coordsSection.get("coordsOverlayTextColor").asInteger();
            }

            if (rootTable.contains("ping")) {
                TomlTable pingSection = rootTable.get("ping").asTable();
                if (pingSection.contains("showPingAsText"))
                    showPingAsText = pingSection.get("showPingAsText").asBoolean();
                if (pingSection.contains("pingColorGood"))
                    pingColorGood = pingSection.get("pingColorGood").asInteger();
                if (pingSection.contains("pingColorMedium"))
                    pingColorMedium = pingSection.get("pingColorMedium").asInteger();
                if (pingSection.contains("pingColorBad"))
                    pingColorBad = pingSection.get("pingColorBad").asInteger();
            }

            if (rootTable.contains("emoji")) {
                TomlTable emojiSection = rootTable.get("emoji").asTable();
                if (emojiSection.contains("showEmojis"))
                    showEmojis = emojiSection.get("showEmojis").asBoolean();
            }

        } catch (Exception e) {
            Counter.logger.error("Fehler beim Laden der Client-Konfiguration", e);
        }
    }

    public void saveConfig() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            return;
        }

        try {
            TomlTable rootTable = new TomlTable();

            TomlTable daySection = new TomlTable();
            daySection.putBoolean("showDayOverlay", showDayOverlay);
            daySection.putBoolean("showOverlayAlways", showDayOverlayAlways);
            daySection.putDouble("dayOverlayX", dayOverlayX);
            daySection.putDouble("dayOverlayY", dayOverlayY);
            daySection.putDouble("dayOverlaySize", dayOverlaySize);
            daySection.putInteger("dayOverlayTextColor", dayOverlayTextColor);
            rootTable.put("dayCounterOverlay", daySection);

            TomlTable deathListSection = new TomlTable();
            deathListSection.putBoolean("showDeathListOverlay", showDeathListOverlay);
            deathListSection.putBoolean("showListOverlayAlways", showDeathListOverlayAlways);
            deathListSection.putDouble("deathListX", deathListOverlayX);
            deathListSection.putDouble("deathListY", deathListOverlayY);
            deathListSection.putDouble("deathListSize", deathListOverlaySize);
            deathListSection.putString("deathOverlayStyle", deathListOverlayStyle.name());
            deathListSection.putInteger("deathOverlayWidth", deathListOverlayWidth);
            deathListSection.putInteger("deathListTextColor", deathListTextColor);
            deathListSection.putInteger("firstPlaceColor", firstPlaceColor);
            deathListSection.putInteger("secondPlaceColor", secondPlaceColor);
            deathListSection.putInteger("thirdPlaceColor", thirdPlaceColor);
            rootTable.put("deathCounterList", deathListSection);

            TomlTable deathSelfSection = new TomlTable();
            deathSelfSection.putBoolean("showDeathSelfOverlay", showDeathSelfOverlay);
            deathSelfSection.putBoolean("showSelfOverlayAlways", showDeathSelfOverlayAlways);
            deathSelfSection.putDouble("deathSelfX", deathSelfOverlayX);
            deathSelfSection.putDouble("deathSelfY", deathSelfOverlayY);
            deathSelfSection.putDouble("deathSelfSize", deathSelfSize);
            deathSelfSection.putInteger("deathSelfTextColor", deathSelfTextColor);
            rootTable.put("deathCounterSelf", deathSelfSection);

            TomlTable timeSection = new TomlTable();
            timeSection.putBoolean("showTimeOverlay", showTimeOverlay);
            timeSection.putBoolean("showTimeOverlayAlways", showTimeOverlayAlways);
            timeSection.putDouble("timeOverlayX", timeOverlayX);
            timeSection.putDouble("timeOverlayY", timeOverlayY);
            timeSection.putDouble("timeOverlaySize", timeOverlaySize);
            timeSection.putInteger("timeOverlayTextColor", timeOverlayTextColor);
            rootTable.put("timeOverlay", timeSection);

            TomlTable coordsSection = new TomlTable();
            coordsSection.putBoolean("showCoordsOverlay", showCoordsOverlay);
            coordsSection.putBoolean("showCoordsOverlayAlways", showCoordsOverlayAlways);
            coordsSection.putDouble("coordsOverlayX", coordsOverlayX);
            coordsSection.putDouble("coordsOverlayY", coordsOverlayY);
            coordsSection.putDouble("coordsOverlaySize", coordsOverlaySize);
            coordsSection.putInteger("coordsOverlayTextColor", coordsOverlayTextColor);
            rootTable.put("coordsOverlay", coordsSection);

            TomlTable pingSection = new TomlTable();
            pingSection.putBoolean("showPingAsText", showPingAsText);
            pingSection.putInteger("pingColorGood", pingColorGood);
            pingSection.putInteger("pingColorMedium", pingColorMedium);
            pingSection.putInteger("pingColorBad", pingColorBad);
            rootTable.put("ping", pingSection);

            TomlTable emojiSection = new TomlTable();
            emojiSection.putBoolean("showEmojis", showEmojis);
            rootTable.put("emoji", emojiSection);

            // Day Counter Overlay comments
            daySection.setComment("showDayOverlay", "Enable/disable the day overlay on the client side");
            daySection.setComment("showOverlayAlways", "Should the day overlay always be visible? If false, only when pressing the Tab key");
            daySection.setComment("dayOverlayX", "Relative X position (0.0 = left, 1.0 = right) for the day overlay");
            daySection.setComment("dayOverlayY", "Relative Y position (0.0 = top, 1.0 = bottom) for the day overlay");
            daySection.setComment("dayOverlaySize", "Scale factor for the day overlay text size (1.0 = normal)");
            daySection.setComment("dayOverlayTextColor", "Text color for the day overlay in hexadecimal (0xFFFFFF is white)");

            // Death Counter List comments
            deathListSection.setComment("showDeathListOverlay", "Enable/disable the death list (shows all player deaths)");
            deathListSection.setComment("showListOverlayAlways", "Should the death list always be visible? If false, only when pressing the Tab key");
            deathListSection.setComment("deathListX", "Relative X position (0.0 = left, 1.0 = right) for the death list");
            deathListSection.setComment("deathListY", "Relative Y position (0.0 = top, 1.0 = bottom) for the death list");
            deathListSection.setComment("deathListSize", "Scale factor for the death list text size (1.0 = normal)");
            deathListSection.setComment("deathOverlayStyle", "Display style for the death list: CLASSIC, BOXED or TABLE");
            deathListSection.setComment("deathOverlayWidth", "Maximum width (in pixels) for the death list");
            deathListSection.setComment("deathListTextColor", "Default text color for the death list in hexadecimal");
            deathListSection.setComment("firstPlaceColor", "Color for first place in the death list (0xFFD700 is gold)");
            deathListSection.setComment("secondPlaceColor", "Color for second place in the death list (0xC0C0C0 is silver)");
            deathListSection.setComment("thirdPlaceColor", "Color for third place in the death list (0xCD7F32 is bronze)");

            // Death Counter Self comments
            deathSelfSection.setComment("showDeathSelfOverlay", "Enable/disable the personal death counter");
            deathSelfSection.setComment("showSelfOverlayAlways", "Should the personal death counter always be visible? If false, only when pressing the Tab key");
            deathSelfSection.setComment("deathSelfX", "Relative X position for your personal death counter");
            deathSelfSection.setComment("deathSelfY", "Relative Y position for your personal death counter");
            deathSelfSection.setComment("deathSelfSize", "Scale factor for the text size of the personal death counter");
            deathSelfSection.setComment("deathSelfTextColor", "Text color for your personal death counter");

            // Time Overlay comments
            timeSection.setComment("showTimeOverlay", "Enable/disable the game time display");
            timeSection.setComment("showTimeOverlayAlways", "Should the game time display always be visible? If false, only when pressing the Tab key");
            timeSection.setComment("timeOverlayX", "Relative X position for the game time display");
            timeSection.setComment("timeOverlayY", "Relative Y position for the game time display");
            timeSection.setComment("timeOverlaySize", "Scale factor for the text size of the game time display");
            timeSection.setComment("timeOverlayTextColor", "Text color for the game time display");

            // Coords Overlay comments
            coordsSection.setComment("showCoordsOverlay", "Enable/disable the coordinates display");
            coordsSection.setComment("showCoordsOverlayAlways", "Should the coordinates display always be visible? If false, only when pressing the Tab key");
            coordsSection.setComment("coordsOverlayX", "Relative X position for the coordinates display");
            coordsSection.setComment("coordsOverlayY", "Relative Y position for the coordinates display");
            coordsSection.setComment("coordsOverlaySize", "Scale factor for the text size of the coordinates display");
            coordsSection.setComment("coordsOverlayTextColor", "Text color for the coordinates display");

            // Ping comments
            pingSection.setComment("showPingAsText", "Show ping as text (e.g. '123ms') instead of bars");
            pingSection.setComment("pingColorGood", "Color for low ping (<100ms) in hexadecimal (0x00FF00 is green)");
            pingSection.setComment("pingColorMedium", "Color for medium ping (100-249ms) in hexadecimal (0xFF9900 is orange)");
            pingSection.setComment("pingColorBad", "Color for high ping (>=250ms) in hexadecimal (0xFF0000 is red)");

            // Emoji comments
            emojiSection.setComment("showEmojis", "Enable/disable emojis in the overlays");

            // Main section documentation
            rootTable.setComment("dayCounterOverlay", "Settings for the day overlay");
            rootTable.setComment("deathCounterList", "Settings for the death list (shows all player deaths)");
            rootTable.setComment("deathCounterSelf", "Settings for the personal death counter");
            rootTable.setComment("timeOverlay", "Settings for the game time display");
            rootTable.setComment("coordsOverlay", "Settings for the coordinates display");
            rootTable.setComment("ping", "Settings for the ping display in the tab list");
            rootTable.setComment("emoji", "Settings for emojis in the overlays");

            TomlParser.writeToFile(rootTable, CONFIG_FILE);

        } catch (IOException e) {
            Counter.logger.error("Error saving client configuration", e);
        }
    }

    @Override
    public boolean showDayOverlay() {
        return showDayOverlay;
    }

    @Override
    public void setShowDayOverlay(boolean value) {
        this.showDayOverlay = value;
        saveConfig();
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
        saveConfig();
    }

    @Override
    public double dayOverlayY() {
        return dayOverlayY;
    }

    @Override
    public void setDayOverlayY(double value) {
        this.dayOverlayY = value;
        saveConfig();
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
        saveConfig();
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
        saveConfig();
    }

    @Override
    public double deathListOverlayY() {
        return deathListOverlayY;
    }

    @Override
    public void setDeathListOverlayY(double value) {
        this.deathListOverlayY = value;
        saveConfig();
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
        saveConfig();
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
        saveConfig();
    }

    @Override
    public double deathSelfOverlayY() {
        return deathSelfOverlayY;
    }

    @Override
    public void setDeathSelfOverlayY(double value) {
        this.deathSelfOverlayY = value;
        saveConfig();
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
        saveConfig();
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
        saveConfig();
    }

    @Override
    public double timeOverlayY() {
        return timeOverlayY;
    }

    @Override
    public void setTimeOverlayY(double value) {
        this.timeOverlayY = value;
        saveConfig();
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
        saveConfig();
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
        saveConfig();
    }

    @Override
    public double coordsOverlayY() {
        return coordsOverlayY;
    }

    @Override
    public void setCoordsOverlayY(double value) {
        this.coordsOverlayY = value;
        saveConfig();
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

    @Override
    public void save() {
        saveConfig();
    }
}