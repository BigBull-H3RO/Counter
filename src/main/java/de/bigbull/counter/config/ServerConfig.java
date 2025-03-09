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
        ENABLE_DAY_COUNTER = SERVER_BUILDER.comment("If disabled, the day counter will not be tracked or displayed on the server.")
                .define("enableDayCounter", true);
        ENABLE_DAY_MESSAGE = SERVER_BUILDER.comment("Send a chat message when a new Minecraft day starts?")
                .define("enableDayMessage", true);
        SHOW_DAY_OVERLAY = SERVER_BUILDER.comment("Allow the day counter overlay to be displayed for players? (Server-side override)")
                .define("showOverlay", true);
        SHOW_DAY_IN_CHAT = SERVER_BUILDER.comment("Show the current Minecraft day in chat when a player joins?")
                .define("showDayInChat", true);
        DAY_CHATTEXT_COLOR = SERVER_BUILDER.comment("Text color for the day counter chat message.")
                .defineInRange("dayChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("Death Counter Settings");
        ENABLE_DEATH_COUNTER = SERVER_BUILDER.comment("If disabled, player deaths will not be tracked or displayed.")
                .define("enableDeathCounter", true);
        SHOW_DEATH_OVERLAY = SERVER_BUILDER.comment("Allow players to see the death counter overlay? (Server-side override)")
                .define("showOverlay", true);
        MAX_PLAYERS_SHOWN = SERVER_BUILDER.comment("Maximum number of players displayed in the death counter list.")
                .defineInRange("maxPlayersShown", 5, 1, 20);
        DEATH_OVERLAY_MODE = SERVER_BUILDER.comment("Which death overlay types are allowed? ONLY_SELF = personal, LIST = global, BOTH = both.")
                .defineEnum("deathOverlayMode", DeathOverlayMode.LIST);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("Death Counter Chat Settings");
        ENABLE_DEATH_IN_CHAT = SERVER_BUILDER.comment("Enable death counter messages in chat (on join or on death).")
                .define("showDeathInChat", true);
        SHOW_DEATH_IN_CHAT_MODE = SERVER_BUILDER.comment("Show the death counter in chat: ON_JOIN (when joining), ON_DEATH (when dying), or BOTH?")
                .defineEnum("showDeathInChatMode", DeathInChatMode.ON_DEATH);
        DEATH_CHAT_MODE_TYPE = SERVER_BUILDER.comment("Death counter chat mode: ONLY_SELF (personal), LIST (global ranking).")
                .defineEnum("deathChatMode", DeathChatMode.ONLY_SELF);
        SHOW_DEATH_LIST_ON_DEATH_GLOBAL = SERVER_BUILDER.comment("Broadcast the death list to all players when someone dies?")
                .define("showDeathListOnDeathGlobal", false);
        DEATH_LIST_CHATTEXT_COLOR = SERVER_BUILDER.comment("Text color for death counter messages in chat.")
                .defineInRange("deathListChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        DEATH_SELF_CHATTEXT_COLOR = SERVER_BUILDER.comment("Text color for personal death messages in chat.")
                .defineInRange("deathSelfChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        SERVER_BUILDER.pop();

        SERVER_SPEC = SERVER_BUILDER.build();
    }
}