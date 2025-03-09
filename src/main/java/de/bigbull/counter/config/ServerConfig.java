package de.bigbull.counter.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SERVER_SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_DAY_COUNTER;
    public static final ModConfigSpec.BooleanValue SHOW_DAY_OVERLAY;
    public static final ModConfigSpec.BooleanValue ENABLE_DAY_MESSAGE;
    public static final ModConfigSpec.BooleanValue SHOW_DAY_IN_CHAT;
    public static final ModConfigSpec.IntValue DAY_CHATTEXT_COLOR;

    public static final ModConfigSpec.BooleanValue ENABLE_DEATH_COUNTER;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_OVERLAY;
    public static final ModConfigSpec.IntValue MAX_PLAYERS_SHOWN;
    public static final ModConfigSpec.BooleanValue SHOW_LIST_OVERLAY_ALWAYS;
    public static final ModConfigSpec.BooleanValue SHOW_SELF_OVERLAY_ALWAYS;
    public static final ModConfigSpec.EnumValue<DeathOverlayMode> DEATH_OVERLAY_MODE;

    public static final ModConfigSpec.BooleanValue ENABLE_DEATH_IN_CHAT;
    public static final ModConfigSpec.EnumValue<DeathInChatMode> SHOW_DEATH_IN_CHAT_MODE;
    public static final ModConfigSpec.EnumValue<DeathChatMode> DEATH_CHAT_MODE_TYPE;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_LIST_ON_DEATH_GLOBAL;
    public static final ModConfigSpec.IntValue DEATH_LIST_CHATTEXT_COLOR;
    public static final ModConfigSpec.IntValue DEATH_SELF_CHATTEXT_COLOR;

    public enum DeathOverlayMode {
        ONLY_SELF, LIST, BOTH
    }

    public enum DeathInChatMode {
        ON_JOIN, ON_DEATH, BOTH
    }

    public enum DeathChatMode {
        ONLY_SELF, LIST
    }

    static {
        SERVER_BUILDER.push("Day Counter Settings");
        ENABLE_DAY_COUNTER = SERVER_BUILDER.comment("If false, the day counter is entirely disabled on the server.")
                .define("enableDayCounter", true);
        ENABLE_DAY_MESSAGE = SERVER_BUILDER.comment("Should a chat message appear whenever a new day begins?")
                .define("enableDayMessage", true);
        SHOW_DAY_OVERLAY = SERVER_BUILDER.comment("Should the day overlay be shown on client side? (Server override)")
                .define("showOverlay", true);
        SHOW_DAY_IN_CHAT = SERVER_BUILDER.comment("Should the day count be posted in chat for each player when they join?")
                .define("showDayInChat", true);
        DAY_CHATTEXT_COLOR = SERVER_BUILDER.comment("Color of the day chat text.")
                .defineInRange("dayChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("Death Counter Settings");
        ENABLE_DEATH_COUNTER = SERVER_BUILDER.comment("If false, the death counter feature is fully disabled on the server.")
                .define("enableDeathCounter", true);
        SHOW_DEATH_OVERLAY = SERVER_BUILDER.comment("If true, the server allows displaying death overlays (self or list).")
                .define("showOverlay", true);
        MAX_PLAYERS_SHOWN = SERVER_BUILDER.comment("Maximum number of players displayed in the death counter list.")
                .defineInRange("maxPlayersShown", 5, 1, 20);
        SHOW_LIST_OVERLAY_ALWAYS = SERVER_BUILDER.comment("Should the death list overlay be visible at all times?")
                .define("showListOverlayAlways", false);
        SHOW_SELF_OVERLAY_ALWAYS = SERVER_BUILDER.comment("Should the self death counter overlay be visible at all times?")
                .define("showSelfOverlayAlways", false);
        DEATH_OVERLAY_MODE = SERVER_BUILDER.comment("Defines which type(s) of death overlay are allowed: ONLY_SELF = personal only, LIST = list only, BOTH = both overlays.")
                .defineEnum("deathOverlayMode", DeathOverlayMode.LIST);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("Death Counter Chat Settings");
        ENABLE_DEATH_IN_CHAT = SERVER_BUILDER.comment("If true, the server will send death counter information to chat (e.g. on join or on death).")
                .define("showDeathInChat", true);
        SHOW_DEATH_IN_CHAT_MODE = SERVER_BUILDER.comment("When should the death list appear in chat? ON_JOIN, ON_DEATH, or BOTH?")
                .defineEnum("showDeathInChatMode", DeathInChatMode.ON_DEATH);
        DEATH_CHAT_MODE_TYPE = SERVER_BUILDER.comment("How should deaths be shown in chat? ONLY_SELF = each player sees their own deaths, LIST = show top players (or all) in a list.")
                .defineEnum("deathChatMode", DeathChatMode.ONLY_SELF);
        SHOW_DEATH_LIST_ON_DEATH_GLOBAL = SERVER_BUILDER.comment("If true, the death list is broadcast to all players whenever someone dies. Otherwise only the dying player sees it.")
                .define("showDeathListOnDeathGlobal", false);
        DEATH_LIST_CHATTEXT_COLOR = SERVER_BUILDER.comment("Color of the death list chat text.")
                .defineInRange("deathListChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        DEATH_SELF_CHATTEXT_COLOR = SERVER_BUILDER.comment("Color of the death self chat text.")
                .defineInRange("deathSelfChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        SERVER_BUILDER.pop();

        SERVER_SPEC = SERVER_BUILDER.build();
    }
}