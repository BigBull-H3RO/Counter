package de.bigbull.counter.fabric.config;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.IClientConfig;
import de.bigbull.counter.fabric.config.toml.TomlParser;
import de.bigbull.counter.fabric.config.toml.TomlTable;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

/**
 * Client-Konfigurationsimplementierung mit TOML
 */
public class FabricClientConfig implements IClientConfig {
    private static final File CONFIG_FILE = new File(FabricTomlConfig.CONFIG_DIR, "client_config.toml");

    // Tag Counter Overlay Einstellungen
    private boolean showDayOverlay = true;
    private boolean showDayOverlayAlways = true;
    private double dayOverlayX = 0.00625;
    private double dayOverlayY = 0.015;
    private double dayOverlaySize = 1.0;
    private int dayOverlayTextColor = 0xFFFFFF;

    // Death List Overlay Einstellungen
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

    // Persönliche Death Counter Einstellungen
    private boolean showDeathSelfOverlay = true;
    private boolean showDeathSelfOverlayAlways = false;
    private double deathSelfOverlayX = 0.00625;
    private double deathSelfOverlayY = 0.068;
    private double deathSelfSize = 1.0;
    private int deathSelfTextColor = 0xFFFFFF;

    // Zeit-Overlay Einstellungen
    private boolean showTimeOverlay = false;
    private boolean showTimeOverlayAlways = true;
    private double timeOverlayX = 0.00781;
    private double timeOverlayY = 0.955;
    private double timeOverlaySize = 1.0;
    private int timeOverlayTextColor = 0xFFFFFF;

    // Koordinaten-Overlay Einstellungen
    private boolean showCoordsOverlay = false;
    private boolean showCoordsOverlayAlways = true;
    private double coordsOverlayX = 0.00781;
    private double coordsOverlayY = 0.905;
    private double coordsOverlaySize = 1.0;
    private int coordsOverlayTextColor = 0xFFFFFF;

    // Ping-Einstellungen
    private boolean showPingAsText = true;
    private int pingColorGood = 0x00FF00;
    private int pingColorMedium = 0xFF9900;
    private int pingColorBad = 0xFF0000;

    // Emoji-Einstellungen
    private boolean showEmojis = true;

    /**
     * Konstruktor - lädt die Konfiguration
     */
    public FabricClientConfig() {
        // Nur auf dem Client laden
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            loadConfig();
        }
    }

    /**
     * Lädt die Konfiguration aus der Datei
     */
    public void loadConfig() {
        // Prüfe, ob wir auf dem Client sind
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            return;
        }

        try {
            if (!CONFIG_FILE.exists()) {
                saveConfig();
                return;
            }

            TomlTable rootTable = TomlParser.parseFile(CONFIG_FILE);

            // Tag Counter Overlay Einstellungen
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

            // Death Counter List Einstellungen
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

            // Death Counter Self Einstellungen
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

            // Time Overlay Einstellungen
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

            // Coords Overlay Einstellungen
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

            // Ping Einstellungen
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

            // Emoji Einstellungen
            if (rootTable.contains("emoji")) {
                TomlTable emojiSection = rootTable.get("emoji").asTable();
                if (emojiSection.contains("showEmojis"))
                    showEmojis = emojiSection.get("showEmojis").asBoolean();
            }

        } catch (Exception e) {
            Counter.logger.error("Fehler beim Laden der Client-Konfiguration", e);
        }
    }

    /**
     * Speichert die Konfiguration in der Datei
     */
    public void saveConfig() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            return;
        }

        try {
            TomlTable rootTable = new TomlTable();

            // Tag Counter Overlay Einstellungen
            TomlTable daySection = new TomlTable();
            daySection.putBoolean("showDayOverlay", showDayOverlay);
            daySection.putBoolean("showOverlayAlways", showDayOverlayAlways);
            daySection.putDouble("dayOverlayX", dayOverlayX);
            daySection.putDouble("dayOverlayY", dayOverlayY);
            daySection.putDouble("dayOverlaySize", dayOverlaySize);
            daySection.putInteger("dayOverlayTextColor", dayOverlayTextColor);
            rootTable.put("dayCounterOverlay", daySection);

            // Death Counter List Einstellungen
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

            // Death Counter Self Einstellungen
            TomlTable deathSelfSection = new TomlTable();
            deathSelfSection.putBoolean("showDeathSelfOverlay", showDeathSelfOverlay);
            deathSelfSection.putBoolean("showSelfOverlayAlways", showDeathSelfOverlayAlways);
            deathSelfSection.putDouble("deathSelfX", deathSelfOverlayX);
            deathSelfSection.putDouble("deathSelfY", deathSelfOverlayY);
            deathSelfSection.putDouble("deathSelfSize", deathSelfSize);
            deathSelfSection.putInteger("deathSelfTextColor", deathSelfTextColor);
            rootTable.put("deathCounterSelf", deathSelfSection);

            // Time Overlay Einstellungen
            TomlTable timeSection = new TomlTable();
            timeSection.putBoolean("showTimeOverlay", showTimeOverlay);
            timeSection.putBoolean("showTimeOverlayAlways", showTimeOverlayAlways);
            timeSection.putDouble("timeOverlayX", timeOverlayX);
            timeSection.putDouble("timeOverlayY", timeOverlayY);
            timeSection.putDouble("timeOverlaySize", timeOverlaySize);
            timeSection.putInteger("timeOverlayTextColor", timeOverlayTextColor);
            rootTable.put("timeOverlay", timeSection);

            // Coords Overlay Einstellungen
            TomlTable coordsSection = new TomlTable();
            coordsSection.putBoolean("showCoordsOverlay", showCoordsOverlay);
            coordsSection.putBoolean("showCoordsOverlayAlways", showCoordsOverlayAlways);
            coordsSection.putDouble("coordsOverlayX", coordsOverlayX);
            coordsSection.putDouble("coordsOverlayY", coordsOverlayY);
            coordsSection.putDouble("coordsOverlaySize", coordsOverlaySize);
            coordsSection.putInteger("coordsOverlayTextColor", coordsOverlayTextColor);
            rootTable.put("coordsOverlay", coordsSection);

            // Ping Einstellungen
            TomlTable pingSection = new TomlTable();
            pingSection.putBoolean("showPingAsText", showPingAsText);
            pingSection.putInteger("pingColorGood", pingColorGood);
            pingSection.putInteger("pingColorMedium", pingColorMedium);
            pingSection.putInteger("pingColorBad", pingColorBad);
            rootTable.put("ping", pingSection);

            // Emoji Einstellungen
            TomlTable emojiSection = new TomlTable();
            emojiSection.putBoolean("showEmojis", showEmojis);
            rootTable.put("emoji", emojiSection);

            // Ausführliche Kommentare für alle Einstellungen hinzufügen

            // Tag Counter Overlay-Kommentare
            daySection.setComment("showDayOverlay", "Aktiviert/deaktiviert das Tag-Overlay auf der Client-Seite");
            daySection.setComment("showOverlayAlways", "Soll das Tag-Overlay immer sichtbar sein? Wenn false, dann nur beim Drücken der Tab-Taste");
            daySection.setComment("dayOverlayX", "Relative X-Position (0.0 = links, 1.0 = rechts) für das Tag-Overlay");
            daySection.setComment("dayOverlayY", "Relative Y-Position (0.0 = oben, 1.0 = unten) für das Tag-Overlay");
            daySection.setComment("dayOverlaySize", "Skalierungsfaktor für die Tag-Overlay-Textgröße (1.0 = normal)");
            daySection.setComment("dayOverlayTextColor", "Textfarbe für das Tag-Overlay in Hexadezimal (0xFFFFFF ist weiß)");

            // Death Counter List-Kommentare
            deathListSection.setComment("showDeathListOverlay", "Aktiviert/deaktiviert die Todesliste (zeigt alle Spielertode)");
            deathListSection.setComment("showListOverlayAlways", "Soll die Todesliste immer sichtbar sein? Wenn false, dann nur beim Drücken der Tab-Taste");
            deathListSection.setComment("deathListX", "Relative X-Position (0.0 = links, 1.0 = rechts) für die Todesliste");
            deathListSection.setComment("deathListY", "Relative Y-Position (0.0 = oben, 1.0 = unten) für die Todesliste");
            deathListSection.setComment("deathListSize", "Skalierungsfaktor für die Todeslisten-Textgröße (1.0 = normal)");
            deathListSection.setComment("deathOverlayStyle", "Anzeigestil für die Todesliste: CLASSIC, BOXED oder TABLE");
            deathListSection.setComment("deathOverlayWidth", "Maximale Breite (in Pixeln) für die Todesliste");
            deathListSection.setComment("deathListTextColor", "Standard-Textfarbe für die Todesliste in Hexadezimal");
            deathListSection.setComment("firstPlaceColor", "Farbmarkierung für den ersten Platz in der Todesliste (0xFFD700 ist Gold)");
            deathListSection.setComment("secondPlaceColor", "Farbmarkierung für den zweiten Platz in der Todesliste (0xC0C0C0 ist Silber)");
            deathListSection.setComment("thirdPlaceColor", "Farbmarkierung für den dritten Platz in der Todesliste (0xCD7F32 ist Bronze)");

            // Death Counter Self-Kommentare
            deathSelfSection.setComment("showDeathSelfOverlay", "Aktiviert/deaktiviert den persönlichen Todeszähler");
            deathSelfSection.setComment("showSelfOverlayAlways", "Soll der persönliche Todeszähler immer sichtbar sein? Wenn false, dann nur beim Drücken der Tab-Taste");
            deathSelfSection.setComment("deathSelfX", "Relative X-Position für deinen persönlichen Todeszähler");
            deathSelfSection.setComment("deathSelfY", "Relative Y-Position für deinen persönlichen Todeszähler");
            deathSelfSection.setComment("deathSelfSize", "Skalierungsfaktor für die Textgröße des persönlichen Todeszählers");
            deathSelfSection.setComment("deathSelfTextColor", "Textfarbe für deinen persönlichen Todeszähler");

            // Time Overlay-Kommentare
            timeSection.setComment("showTimeOverlay", "Aktiviert/deaktiviert die Spielzeit-Anzeige");
            timeSection.setComment("showTimeOverlayAlways", "Soll die Spielzeit-Anzeige immer sichtbar sein? Wenn false, dann nur beim Drücken der Tab-Taste");
            timeSection.setComment("timeOverlayX", "Relative X-Position für die Spielzeit-Anzeige");
            timeSection.setComment("timeOverlayY", "Relative Y-Position für die Spielzeit-Anzeige");
            timeSection.setComment("timeOverlaySize", "Skalierungsfaktor für die Textgröße der Spielzeit-Anzeige");
            timeSection.setComment("timeOverlayTextColor", "Textfarbe für die Spielzeit-Anzeige");

            // Coords Overlay-Kommentare
            coordsSection.setComment("showCoordsOverlay", "Aktiviert/deaktiviert die Koordinatenanzeige");
            coordsSection.setComment("showCoordsOverlayAlways", "Soll die Koordinatenanzeige immer sichtbar sein? Wenn false, dann nur beim Drücken der Tab-Taste");
            coordsSection.setComment("coordsOverlayX", "Relative X-Position für die Koordinatenanzeige");
            coordsSection.setComment("coordsOverlayY", "Relative Y-Position für die Koordinatenanzeige");
            coordsSection.setComment("coordsOverlaySize", "Skalierungsfaktor für die Textgröße der Koordinatenanzeige");
            coordsSection.setComment("coordsOverlayTextColor", "Textfarbe für die Koordinatenanzeige");

            // Ping-Kommentare
            pingSection.setComment("showPingAsText", "Zeigt den Ping als Text (z.B. '123ms') statt als Balken an");
            pingSection.setComment("pingColorGood", "Farbe für niedrigen Ping (<100ms) in Hexadezimal (0x00FF00 ist Grün)");
            pingSection.setComment("pingColorMedium", "Farbe für mittleren Ping (100-249ms) in Hexadezimal (0xFF9900 ist Orange)");
            pingSection.setComment("pingColorBad", "Farbe für hohen Ping (>=250ms) in Hexadezimal (0xFF0000 ist Rot)");

            // Emoji-Kommentare
            emojiSection.setComment("showEmojis", "Aktiviert/deaktiviert die Emojis in den Overlays");

            // Hauptdokumentation der Sektionen
            rootTable.setComment("dayCounterOverlay", "Einstellungen für das Tag-Overlay");
            rootTable.setComment("deathCounterList", "Einstellungen für die Todesliste (zeigt alle Spielertode)");
            rootTable.setComment("deathCounterSelf", "Einstellungen für den persönlichen Todeszähler");
            rootTable.setComment("timeOverlay", "Einstellungen für die Spielzeit-Anzeige");
            rootTable.setComment("coordsOverlay", "Einstellungen für die Koordinatenanzeige");
            rootTable.setComment("ping", "Einstellungen für die Ping-Anzeige in der Tab-Liste");
            rootTable.setComment("emoji", "Einstellungen für Emojis in den Overlays");

            // In Datei speichern
            TomlParser.writeToFile(rootTable, CONFIG_FILE);

        } catch (IOException e) {
            Counter.logger.error("Fehler beim Speichern der Client-Konfiguration", e);
        }
    }

    // Implementierung des IClientConfig-Interfaces
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