package de.bigbull.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SERVER_SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_DAY_COUNTER;
    public static final ModConfigSpec.BooleanValue SHOW_OVERLAY;
    public static final ModConfigSpec.BooleanValue ENABLE_DAY_MESSAGE;
    public static final ModConfigSpec.BooleanValue SHOW_DAY_IN_CHAT;

    public static final ModConfigSpec.BooleanValue ENABLE_DEATH_COUNTER;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_OVERLAY;
    public static final ModConfigSpec.IntValue MAX_PLAYERS_SHOWN;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_OVERLAY_ALWAYS;
    public static final ModConfigSpec.EnumValue<DeathOverlayMode> DEATH_OVERLAY_MODE;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_LIST_IN_CHAT;

    public enum DeathOverlayMode {
        ONLY_SELF, LIST, BOTH
    }

    static {
        SERVER_BUILDER.push("Day Counter Settings");
        ENABLE_DAY_COUNTER = SERVER_BUILDER.comment("Should the day counter be enabled?")
                .define("enableDayCounter", true);
        ENABLE_DAY_MESSAGE = SERVER_BUILDER.comment("Should a message appear in chat when a new day begins?")
                .define("enableDayMessage", true);
        SHOW_OVERLAY = SERVER_BUILDER.comment("Should the overlay be displayed?")
                .define("showOverlay", true);
        SHOW_DAY_IN_CHAT = SERVER_BUILDER.comment("Should the day counter message be sent in chat when a player joins?")
                .define("showDayInChat", false);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("Death Counter Settings");
        ENABLE_DEATH_COUNTER = SERVER_BUILDER.comment("Should the death counter be enabled?")
                .define("enableDeathCounter", true);
        SHOW_DEATH_OVERLAY = SERVER_BUILDER.comment("Should the death counter overlay be displayed?")
                .define("showOverlay", true);
        MAX_PLAYERS_SHOWN = SERVER_BUILDER.comment("Maximum number of players shown in the death counter overlay")
                .defineInRange("maxPlayersShown", 5, 1, 20);
        SHOW_DEATH_OVERLAY_ALWAYS = SERVER_BUILDER.comment("Should the death counter overlay always be visible? If false, it will only be shown when holding the Tab key.")
                .define("showOverlayAlways", false);
        DEATH_OVERLAY_MODE = SERVER_BUILDER.comment("Death overlay display mode: ONLY_SELF = show only own deaths, LIST = show only the player list, BOTH = show both")
                .defineEnum("deathOverlayMode", DeathOverlayMode.LIST);
        SHOW_DEATH_LIST_IN_CHAT = SERVER_BUILDER.comment("Should the death counter leaderboard be shown in chat when a player joins or dies?")
                .define("showDeathListInChat", false);
        SERVER_BUILDER.pop();

        SERVER_SPEC = SERVER_BUILDER.build();
    }
}
