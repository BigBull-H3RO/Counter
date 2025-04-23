package de.bigbull.counter.fabric.config;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.common.config.IServerConfig;
import de.bigbull.counter.fabric.config.toml.TomlParser;
import de.bigbull.counter.fabric.config.toml.TomlTable;

import java.io.File;
import java.io.IOException;

public class FabricServerConfig implements IServerConfig {
    private static final File CONFIG_FILE = new File(FabricTomlConfig.CONFIG_DIR, "server_config.toml");

    private boolean enabledDayCounter = true;
    private boolean showDayOverlay = true;
    private boolean enableDayMessage = true;
    private boolean showDayInChat = true;
    private int dayChatTextColor = 0xFFFFFF;

    private boolean enableDeathCounter = true;
    private boolean showDeathOverlay = true;
    private int maxPlayersShown = 5;
    private DeathOverlayMode deathOverlayMode = DeathOverlayMode.LIST;

    private boolean enableDeathInChat = true;
    private DeathInChatMode deathInChatMode = DeathInChatMode.ON_DEATH;
    private DeathChatMode deathChatMode = DeathChatMode.ONLY_SELF;
    private boolean showDeathListOnDeathGlobal = false;
    private int deathListChatTextColor = 0xFFFFFF;
    private int deathSelfChatTextColor = 0xFFFFFF;

    private boolean enableTimeCounter = true;
    private boolean showTimeOverlay = true;
    private boolean showCombinedDayTime = false;
    private boolean timeFormat24h = true;

    private boolean enableCoordsCounter = true;
    private boolean showCoordsOverlay = true;

    public FabricServerConfig() {
        loadConfig();
    }

    public void loadConfig() {
        try {
            if (!CONFIG_FILE.exists()) {
                saveConfig();
                return;
            }

            TomlTable rootTable = TomlParser.parseFile(CONFIG_FILE);

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

            if (rootTable.contains("coordsCounter")) {
                TomlTable coordsSection = rootTable.get("coordsCounter").asTable();
                if (coordsSection.contains("enableCoordsCounter"))
                    enableCoordsCounter = coordsSection.get("enableCoordsCounter").asBoolean();
                if (coordsSection.contains("showCoordsOverlay"))
                    showCoordsOverlay = coordsSection.get("showCoordsOverlay").asBoolean();
            }

        } catch (Exception e) {
            Counter.logger.error("Error loading server configuration", e);
        }
    }

    public void saveConfig() {
        try {
            TomlTable rootTable = new TomlTable();

            TomlTable daySection = new TomlTable();
            daySection.putBoolean("enableDayCounter", enabledDayCounter);
            daySection.putBoolean("showDayOverlay", showDayOverlay);
            daySection.putBoolean("enableDayMessage", enableDayMessage);
            daySection.putBoolean("showDayInChat", showDayInChat);
            daySection.putInteger("dayChatTextColor", dayChatTextColor);
            rootTable.put("dayCounter", daySection);

            TomlTable deathSection = new TomlTable();
            deathSection.putBoolean("enableDeathCounter", enableDeathCounter);
            deathSection.putBoolean("showDeathOverlay", showDeathOverlay);
            deathSection.putInteger("maxPlayersShown", maxPlayersShown);
            deathSection.putString("deathOverlayMode", deathOverlayMode.name());
            rootTable.put("deathCounter", deathSection);

            TomlTable deathChatSection = new TomlTable();
            deathChatSection.putBoolean("enableDeathInChat", enableDeathInChat);
            deathChatSection.putString("deathInChatMode", deathInChatMode.name());
            deathChatSection.putString("deathChatMode", deathChatMode.name());
            deathChatSection.putBoolean("showDeathListOnDeathGlobal", showDeathListOnDeathGlobal);
            deathChatSection.putInteger("deathListChatTextColor", deathListChatTextColor);
            deathChatSection.putInteger("deathSelfChatTextColor", deathSelfChatTextColor);
            rootTable.put("deathCounterChat", deathChatSection);

            TomlTable timeSection = new TomlTable();
            timeSection.putBoolean("enableTimeCounter", enableTimeCounter);
            timeSection.putBoolean("showTimeOverlay", showTimeOverlay);
            timeSection.putBoolean("showCombinedDayTime", showCombinedDayTime);
            timeSection.putBoolean("timeFormat24h", timeFormat24h);
            rootTable.put("timeCounter", timeSection);

            TomlTable coordsSection = new TomlTable();
            coordsSection.putBoolean("enableCoordsCounter", enableCoordsCounter);
            coordsSection.putBoolean("showCoordsOverlay", showCoordsOverlay);
            rootTable.put("coordsCounter", coordsSection);

            // Day Counter comments
            daySection.setComment("enableDayCounter", "If disabled, the day counter will not be tracked or displayed on the server");
            daySection.setComment("showDayOverlay", "Allow the day overlay for players (server-side override)");
            daySection.setComment("enableDayMessage", "Send a chat message when a new Minecraft day begins");
            daySection.setComment("showDayInChat", "Show the current Minecraft day in chat when a player joins");
            daySection.setComment("dayChatTextColor", "Text color for the day counter chat message in hexadecimal (0xFFFFFF is white)");

            // Death Counter comments
            deathSection.setComment("enableDeathCounter", "If disabled, player deaths will not be tracked or displayed");
            deathSection.setComment("showDeathOverlay", "Allow players to see the death counter overlay (server-side override)");
            deathSection.setComment("maxPlayersShown", "Maximum number of players displayed in the death counter list");
            deathSection.setComment("deathOverlayMode", "Which death counter types are allowed? ONLY_SELF (personal), LIST (global), BOTH (both)");

            // Death Counter Chat comments
            deathChatSection.setComment("enableDeathInChat", "Enable death counter messages in chat (on join or on death)");
            deathChatSection.setComment("deathInChatMode", "When to show the death counter in chat: ON_JOIN (when joining), ON_DEATH (when dying) or BOTH (both)");
            deathChatSection.setComment("deathChatMode", "Death counter chat mode: ONLY_SELF (personal), LIST (global ranking)");
            deathChatSection.setComment("showDeathListOnDeathGlobal", "Send death list to all players when someone dies");
            deathChatSection.setComment("deathListChatTextColor", "Text color for death counter messages in chat (hexadecimal)");
            deathChatSection.setComment("deathSelfChatTextColor", "Text color for personal death messages in chat (hexadecimal)");

            // Time Counter comments
            timeSection.setComment("enableTimeCounter", "If disabled, the time counter will not be tracked or displayed");
            timeSection.setComment("showTimeOverlay", "Allow the time overlay for players (server-side override)");
            timeSection.setComment("showCombinedDayTime", "Show the day along with the time. Disables the standard time overlay");
            timeSection.setComment("timeFormat24h", "Use 24-hour format (true) or 12-hour format (false)");

            // Coords Counter comments
            coordsSection.setComment("enableCoordsCounter", "If disabled, the coordinates display will not be shown");
            coordsSection.setComment("showCoordsOverlay", "Allow the coordinates overlay for players (server-side override)");

            // Main documentation
            rootTable.setComment("dayCounter", "Settings for the day counter");
            rootTable.setComment("deathCounter", "Settings for the death counter");
            rootTable.setComment("deathCounterChat", "Chat settings for the death counter");
            rootTable.setComment("timeCounter", "Settings for the time counter");
            rootTable.setComment("coordsCounter", "Settings for the coordinates display");

            TomlParser.writeToFile(rootTable, CONFIG_FILE);

        } catch (IOException e) {
            Counter.logger.error("Fehler beim Speichern der Server-Konfiguration", e);
        }
    }

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

    // Day Counter
    public void setEnabledDayCounter(boolean enabledDayCounter) {
        this.enabledDayCounter = enabledDayCounter;
    }

    public void setShowDayOverlay(boolean showDayOverlay) {
        this.showDayOverlay = showDayOverlay;
    }

    public void setEnableDayMessage(boolean enableDayMessage) {
        this.enableDayMessage = enableDayMessage;
    }

    public void setShowDayInChat(boolean showDayInChat) {
        this.showDayInChat = showDayInChat;
    }

    public void setDayChatTextColor(int dayChatTextColor) {
        this.dayChatTextColor = dayChatTextColor;
    }

    // Death Counter
    public void setEnableDeathCounter(boolean enableDeathCounter) {
        this.enableDeathCounter = enableDeathCounter;
    }

    public void setShowDeathOverlay(boolean showDeathOverlay) {
        this.showDeathOverlay = showDeathOverlay;
    }

    public void setMaxPlayersShown(int maxPlayersShown) {
        this.maxPlayersShown = maxPlayersShown;
    }

    public void setDeathOverlayMode(DeathOverlayMode deathOverlayMode) {
        this.deathOverlayMode = deathOverlayMode;
    }

    // Death Chat
    public void setEnableDeathInChat(boolean enableDeathInChat) {
        this.enableDeathInChat = enableDeathInChat;
    }

    public void setDeathInChatMode(DeathInChatMode deathInChatMode) {
        this.deathInChatMode = deathInChatMode;
    }

    public void setDeathChatMode(DeathChatMode deathChatMode) {
        this.deathChatMode = deathChatMode;
    }

    public void setShowDeathListOnDeathGlobal(boolean showDeathListOnDeathGlobal) {
        this.showDeathListOnDeathGlobal = showDeathListOnDeathGlobal;
    }

    public void setDeathListChatTextColor(int deathListChatTextColor) {
        this.deathListChatTextColor = deathListChatTextColor;
    }

    public void setDeathSelfChatTextColor(int deathSelfChatTextColor) {
        this.deathSelfChatTextColor = deathSelfChatTextColor;
    }

    // Time Counter
    public void setEnableTimeCounter(boolean enableTimeCounter) {
        this.enableTimeCounter = enableTimeCounter;
    }

    public void setShowCombinedDayTime(boolean showCombinedDayTime) {
        this.showCombinedDayTime = showCombinedDayTime;
    }

    public void setTimeFormat24h(boolean timeFormat24h) {
        this.timeFormat24h = timeFormat24h;
    }

    // Coords Counter
    public void setEnableCoordsCounter(boolean enableCoordsCounter) {
        this.enableCoordsCounter = enableCoordsCounter;
    }

    public void setShowCoordsOverlay(boolean showCoordsOverlay) {
        this.showCoordsOverlay = showCoordsOverlay;
    }
}