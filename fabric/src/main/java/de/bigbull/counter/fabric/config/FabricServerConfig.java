package de.bigbull.counter.fabric.config;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.IServerConfig;
import de.bigbull.counter.fabric.config.toml.TomlParser;
import de.bigbull.counter.fabric.config.toml.TomlTable;

import java.io.File;
import java.io.IOException;

/**
 * Server-Konfigurationsimplementierung mit TOML
 */
public class FabricServerConfig implements IServerConfig {

    private static final File CONFIG_FILE = new File(FabricTomlConfig.CONFIG_DIR, "server_config.toml");

    // Tag Counter Einstellungen
    private boolean enabledDayCounter = true;
    private boolean showDayOverlay = true;
    private boolean enableDayMessage = true;
    private boolean showDayInChat = true;
    private int dayChatTextColor = 0xFFFFFF;

    // Death Counter Einstellungen
    private boolean enableDeathCounter = true;
    private boolean showDeathOverlay = true;
    private int maxPlayersShown = 5;
    private DeathOverlayMode deathOverlayMode = DeathOverlayMode.LIST;

    // Death Counter Chat Einstellungen
    private boolean enableDeathInChat = true;
    private DeathInChatMode deathInChatMode = DeathInChatMode.ON_DEATH;
    private DeathChatMode deathChatMode = DeathChatMode.ONLY_SELF;
    private boolean showDeathListOnDeathGlobal = false;
    private int deathListChatTextColor = 0xFFFFFF;
    private int deathSelfChatTextColor = 0xFFFFFF;

    // Time Counter Einstellungen
    private boolean enableTimeCounter = true;
    private boolean showTimeOverlay = true;
    private boolean showCombinedDayTime = false;
    private boolean timeFormat24h = true;

    // Coords Counter Einstellungen
    private boolean enableCoordsCounter = true;
    private boolean showCoordsOverlay = true;

    /**
     * Konstruktor - lädt die Konfiguration
     */
    public FabricServerConfig() {
        loadConfig();
    }

    /**
     * Lädt die Konfiguration aus der Datei
     */
    public void loadConfig() {
        try {
            if (!CONFIG_FILE.exists()) {
                saveConfig();
                return;
            }

            TomlTable rootTable = TomlParser.parseFile(CONFIG_FILE);

            // Tag Counter Einstellungen
            if (rootTable.contains("dayCounter")) {
                TomlTable daySection = rootTable.get("dayCounter").asTable();
                if (daySection.contains("enableDayCounter"))
                    enabledDayCounter = daySection.get("enableDayCounter").asBoolean();
                if (daySection.contains("showDayOverlay"))
                    showDayOverlay = daySection.get("showDayOverlay").asBoolean();
                if (daySection.contains("enableDayMessage"))
                    enableDayMessage = daySection.get("enableDayMessage").asBoolean();
                if (daySection.contains("showDayInChat"))
                    showDayInChat = daySection.get("showDayInChat").asBoolean();
                if (daySection.contains("dayChatTextColor"))
                    dayChatTextColor = daySection.get("dayChatTextColor").asInteger();
            }

            // Death Counter Einstellungen
            if (rootTable.contains("deathCounter")) {
                TomlTable deathSection = rootTable.get("deathCounter").asTable();
                if (deathSection.contains("enableDeathCounter"))
                    enableDeathCounter = deathSection.get("enableDeathCounter").asBoolean();
                if (deathSection.contains("showDeathOverlay"))
                    showDeathOverlay = deathSection.get("showDeathOverlay").asBoolean();
                if (deathSection.contains("maxPlayersShown"))
                    maxPlayersShown = deathSection.get("maxPlayersShown").asInteger();
                if (deathSection.contains("deathOverlayMode"))
                    deathOverlayMode = DeathOverlayMode.valueOf(deathSection.get("deathOverlayMode").asString());
            }

            // Death Counter Chat Einstellungen
            if (rootTable.contains("deathCounterChat")) {
                TomlTable deathChatSection = rootTable.get("deathCounterChat").asTable();
                if (deathChatSection.contains("enableDeathInChat"))
                    enableDeathInChat = deathChatSection.get("enableDeathInChat").asBoolean();
                if (deathChatSection.contains("deathInChatMode"))
                    deathInChatMode = DeathInChatMode.valueOf(deathChatSection.get("deathInChatMode").asString());
                if (deathChatSection.contains("deathChatMode"))
                    deathChatMode = DeathChatMode.valueOf(deathChatSection.get("deathChatMode").asString());
                if (deathChatSection.contains("showDeathListOnDeathGlobal"))
                    showDeathListOnDeathGlobal = deathChatSection.get("showDeathListOnDeathGlobal").asBoolean();
                if (deathChatSection.contains("deathListChatTextColor"))
                    deathListChatTextColor = deathChatSection.get("deathListChatTextColor").asInteger();
                if (deathChatSection.contains("deathSelfChatTextColor"))
                    deathSelfChatTextColor = deathChatSection.get("deathSelfChatTextColor").asInteger();
            }

            // Time Counter Einstellungen
            if (rootTable.contains("timeCounter")) {
                TomlTable timeSection = rootTable.get("timeCounter").asTable();
                if (timeSection.contains("enableTimeCounter"))
                    enableTimeCounter = timeSection.get("enableTimeCounter").asBoolean();
                if (timeSection.contains("showTimeOverlay"))
                    showTimeOverlay = timeSection.get("showTimeOverlay").asBoolean();
                if (timeSection.contains("showCombinedDayTime"))
                    showCombinedDayTime = timeSection.get("showCombinedDayTime").asBoolean();
                if (timeSection.contains("timeFormat24h"))
                    timeFormat24h = timeSection.get("timeFormat24h").asBoolean();
            }

            // Coords Counter Einstellungen
            if (rootTable.contains("coordsCounter")) {
                TomlTable coordsSection = rootTable.get("coordsCounter").asTable();
                if (coordsSection.contains("enableCoordsCounter"))
                    enableCoordsCounter = coordsSection.get("enableCoordsCounter").asBoolean();
                if (coordsSection.contains("showCoordsOverlay"))
                    showCoordsOverlay = coordsSection.get("showCoordsOverlay").asBoolean();
            }

        } catch (Exception e) {
            Counter.logger.error("Fehler beim Laden der Server-Konfiguration", e);
        }
    }

    /**
     * Speichert die Konfiguration in der Datei
     */
    public void saveConfig() {
        try {
            TomlTable rootTable = new TomlTable();

            // Tag Counter Einstellungen
            TomlTable daySection = new TomlTable();
            daySection.putBoolean("enableDayCounter", enabledDayCounter);
            daySection.putBoolean("showDayOverlay", showDayOverlay);
            daySection.putBoolean("enableDayMessage", enableDayMessage);
            daySection.putBoolean("showDayInChat", showDayInChat);
            daySection.putInteger("dayChatTextColor", dayChatTextColor);
            rootTable.put("dayCounter", daySection);

            // Death Counter Einstellungen
            TomlTable deathSection = new TomlTable();
            deathSection.putBoolean("enableDeathCounter", enableDeathCounter);
            deathSection.putBoolean("showDeathOverlay", showDeathOverlay);
            deathSection.putInteger("maxPlayersShown", maxPlayersShown);
            deathSection.putString("deathOverlayMode", deathOverlayMode.name());
            rootTable.put("deathCounter", deathSection);

            // Death Counter Chat Einstellungen
            TomlTable deathChatSection = new TomlTable();
            deathChatSection.putBoolean("enableDeathInChat", enableDeathInChat);
            deathChatSection.putString("deathInChatMode", deathInChatMode.name());
            deathChatSection.putString("deathChatMode", deathChatMode.name());
            deathChatSection.putBoolean("showDeathListOnDeathGlobal", showDeathListOnDeathGlobal);
            deathChatSection.putInteger("deathListChatTextColor", deathListChatTextColor);
            deathChatSection.putInteger("deathSelfChatTextColor", deathSelfChatTextColor);
            rootTable.put("deathCounterChat", deathChatSection);

            // Time Counter Einstellungen
            TomlTable timeSection = new TomlTable();
            timeSection.putBoolean("enableTimeCounter", enableTimeCounter);
            timeSection.putBoolean("showTimeOverlay", showTimeOverlay);
            timeSection.putBoolean("showCombinedDayTime", showCombinedDayTime);
            timeSection.putBoolean("timeFormat24h", timeFormat24h);
            rootTable.put("timeCounter", timeSection);

            // Coords Counter Einstellungen
            TomlTable coordsSection = new TomlTable();
            coordsSection.putBoolean("enableCoordsCounter", enableCoordsCounter);
            coordsSection.putBoolean("showCoordsOverlay", showCoordsOverlay);
            rootTable.put("coordsCounter", coordsSection);

            // Kommentare hinzufügen
            // Tag Counter Kommentare
            daySection.setComment("enableDayCounter", "Wenn deaktiviert, wird der Tageszähler auf dem Server nicht verfolgt oder angezeigt");
            daySection.setComment("showDayOverlay", "Erlaube das Tag-Overlay für Spieler (Server-seitige Übersteuerung)");
            daySection.setComment("enableDayMessage", "Sendet eine Chat-Nachricht, wenn ein neuer Minecraft-Tag beginnt");
            daySection.setComment("showDayInChat", "Zeigt den aktuellen Minecraft-Tag im Chat an, wenn ein Spieler beitritt");
            daySection.setComment("dayChatTextColor", "Textfarbe für die Tageszähler-Chatnachricht in Hexadezimal (0xFFFFFF ist weiß)");

            // Death Counter Kommentare
            deathSection.setComment("enableDeathCounter", "Wenn deaktiviert, werden Spielertode nicht verfolgt oder angezeigt");
            deathSection.setComment("showDeathOverlay", "Erlaubt Spielern, das Todeszähler-Overlay zu sehen (Server-seitige Übersteuerung)");
            deathSection.setComment("maxPlayersShown", "Maximale Anzahl an Spielern, die in der Todeszähler-Liste angezeigt werden");
            deathSection.setComment("deathOverlayMode", "Welche Todeszähler-Typen sind erlaubt? ONLY_SELF (persönlich), LIST (global), BOTH (beides)");

            // Death Counter Chat Kommentare
            deathChatSection.setComment("enableDeathInChat", "Aktiviert Todeszähler-Nachrichten im Chat (beim Beitreten oder beim Tod)");
            deathChatSection.setComment("deathInChatMode", "Wann soll der Todeszähler im Chat angezeigt werden: ON_JOIN (beim Beitreten), ON_DEATH (beim Sterben) oder BOTH (beides)");
            deathChatSection.setComment("deathChatMode", "Todeszähler-Chat-Modus: ONLY_SELF (persönlich), LIST (globale Rangliste)");
            deathChatSection.setComment("showDeathListOnDeathGlobal", "Todesliste an alle Spieler senden, wenn jemand stirbt");
            deathChatSection.setComment("deathListChatTextColor", "Textfarbe für Todeszähler-Nachrichten im Chat (Hexadezimal)");
            deathChatSection.setComment("deathSelfChatTextColor", "Textfarbe für persönliche Todesnachrichten im Chat (Hexadezimal)");

            // Time Counter Kommentare
            timeSection.setComment("enableTimeCounter", "Wenn deaktiviert, wird der Zeitzähler nicht verfolgt oder angezeigt");
            timeSection.setComment("showTimeOverlay", "Erlaube das Zeit-Overlay für Spieler (Server-seitige Übersteuerung)");
            timeSection.setComment("showCombinedDayTime", "Zeige den Tag zusammen mit der Zeit an. Deaktiviert das Standard-Zeit-Overlay");
            timeSection.setComment("timeFormat24h", "24-Stunden-Format (true) oder 12-Stunden-Format (false) verwenden");

            // Coords Counter Kommentare
            coordsSection.setComment("enableCoordsCounter", "Wenn deaktiviert, wird der Koordinatenanzeiger nicht angezeigt");
            coordsSection.setComment("showCoordsOverlay", "Erlaube das Koordinaten-Overlay für Spieler (Server-seitige Übersteuerung)");

            // Hauptdokumentation
            rootTable.setComment("dayCounter", "Einstellungen für den Tageszähler");
            rootTable.setComment("deathCounter", "Einstellungen für den Todeszähler");
            rootTable.setComment("deathCounterChat", "Chat-Einstellungen für den Todeszähler");
            rootTable.setComment("timeCounter", "Einstellungen für den Zeitzähler");
            rootTable.setComment("coordsCounter", "Einstellungen für den Koordinatenanzeiger");

            // In Datei speichern
            TomlParser.writeToFile(rootTable, CONFIG_FILE);

        } catch (IOException e) {
            Counter.logger.error("Fehler beim Speichern der Server-Konfiguration", e);
        }
    }

    // Implementierung des IServerConfig-Interfaces
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
        saveConfig();
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
}