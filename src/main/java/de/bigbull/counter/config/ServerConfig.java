package de.bigbull.counter.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SERVER_SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_DAY_COUNTER;
    public static final ModConfigSpec.BooleanValue SHOW_DAY_OVERLAY;
    public static final ModConfigSpec.BooleanValue ENABLE_DAY_MESSAGE;
    public static final ModConfigSpec.BooleanValue SHOW_DAY_IN_CHAT;

    public static final ModConfigSpec.BooleanValue ENABLE_DEATH_COUNTER;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_OVERLAY;
    public static final ModConfigSpec.IntValue MAX_PLAYERS_SHOWN;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_OVERLAY_ALWAYS;
    public static final ModConfigSpec.EnumValue<DeathOverlayMode> DEATH_OVERLAY_MODE;

    public static final ModConfigSpec.BooleanValue ENABLE_DEATH_IN_CHAT;
    public static final ModConfigSpec.EnumValue<DeathInChatMode> SHOW_DEATH_IN_CHAT_MODE;
    public static final ModConfigSpec.EnumValue<DeathChatMode> DEATH_CHAT_MODE_TYPE;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_LIST_ON_DEATH_GLOBAL;

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
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("Death Counter Settings");
        ENABLE_DEATH_COUNTER = SERVER_BUILDER.comment("If false, the death counter feature is fully disabled on the server.")
                .define("enableDeathCounter", true);
        SHOW_DEATH_OVERLAY = SERVER_BUILDER.comment("If true, the server allows displaying death overlays (self or list).")
                .define("showOverlay", true);
        MAX_PLAYERS_SHOWN = SERVER_BUILDER.comment("Maximum number of players displayed in the death counter list.")
                .defineInRange("maxPlayersShown", 5, 1, 20);
        SHOW_DEATH_OVERLAY_ALWAYS = SERVER_BUILDER.comment("Should the death overlay be visible at all times? If false, it only appears when holding TAB or in Edit Mode.")
                .define("showOverlayAlways", false);
        DEATH_OVERLAY_MODE = SERVER_BUILDER.comment("Defines which type(s) of death overlay are allowed: ONLY_SELF = personal only, LIST = list only, BOTH = both overlays.")
                .defineEnum("deathOverlayMode", DeathOverlayMode.LIST);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("Death Counter Chat Settings");
        ENABLE_DEATH_IN_CHAT = SERVER_BUILDER.comment("If true, the server will send death counter information to chat (e.g. on join or on death).")
                .define("showDeathInChat", true);
        SHOW_DEATH_IN_CHAT_MODE = SERVER_BUILDER.comment("When should the death list appear in chat? ON_JOIN, ON_DEATH, or BOTH?")
                .defineEnum("showDeathInChatMode", DeathInChatMode.BOTH);
        DEATH_CHAT_MODE_TYPE = SERVER_BUILDER.comment("How should deaths be shown in chat? ONLY_SELF = each player sees their own deaths, LIST = show top players (or all) in a list.")
                .defineEnum("deathChatMode", DeathChatMode.LIST);
        SHOW_DEATH_LIST_ON_DEATH_GLOBAL = SERVER_BUILDER.comment("If true, the death list is broadcast to all players whenever someone dies. Otherwise only the dying player sees it.")
                .define("showDeathListOnDeathGlobal", false);
        SERVER_BUILDER.pop();

        SERVER_SPEC = SERVER_BUILDER.build();
    }
}