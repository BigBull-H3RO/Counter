package de.bigbull.counter.common.config;

public interface IServerConfig {
    boolean enabledDayCounter();
    boolean showDayOverlay();
    boolean enableDayMessage();
    boolean showDayInChat();
    int dayChatTextColor();

    boolean enableDeathCounter();
    boolean showDeathOverlay();
    int maxPlayersShown();
    DeathOverlayMode deathOverlayMode();
    enum DeathOverlayMode {ONLY_SELF, LIST, BOTH}

    boolean enableDeathInChat();
    DeathInChatMode deathInChatMode();
    enum DeathInChatMode {ON_JOIN, ON_DEATH, BOTH}
    DeathChatMode deathChatMode();
    enum DeathChatMode {ONLY_SELF, LIST}
    boolean showDeathListOnDeathGlobal();
    int deathListChatTextColor();
    int deathSelfChatTextColor();

    boolean enableTimeCounter();
    boolean showTimeOverlay();
    void setShowTimeOverlay(boolean value);
    boolean showCombinedDayTime();
    boolean timeFormat24h();

    boolean enableCoordsCounter();
    boolean showCoordsOverlay();
}
