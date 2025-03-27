package de.bigbull.counter.fabric.config;

import de.bigbull.counter.common.config.IServerConfig;

public class ServerConfig implements IServerConfig {
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
}
