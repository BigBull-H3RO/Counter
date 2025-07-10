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

    public static final ModConfigSpec.BooleanValue ENABLE_SURVIVAL_COUNTER;
    public static final ModConfigSpec.BooleanValue SHOW_SURVIVAL_OVERLAY;
    public static final ModConfigSpec.BooleanValue SURVIVAL_USE_REAL_TIME;
    public static final ModConfigSpec.EnumValue<SurvivalTimeFormat> SURVIVAL_TIME_FORMAT;
    public static final ModConfigSpec.BooleanValue SHOW_BEST_SURVIVAL_TIME;
    public static final ModConfigSpec.BooleanValue SHOW_BEST_SURVIVAL_IN_DEATH_COUNTER;
    public static final ModConfigSpec.IntValue SURVIVAL_HISTORY_SIZE;

    public static final ModConfigSpec.BooleanValue ENABLE_SURVIVAL_IN_CHAT;
    public static final ModConfigSpec.EnumValue<SurvivalInChatMode> SHOW_SURVIVAL_IN_CHAT_MODE;
    public static final ModConfigSpec.BooleanValue SHOW_SURVIVAL_IN_CHAT_GLOBAL;
    public static final ModConfigSpec.BooleanValue SHOW_BEST_SURVIVAL_IN_CHAT;

    public static final ModConfigSpec.BooleanValue ENABLE_TIME_COUNTER;
    public static final ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_COMBINED_DAY_TIME;
    public static final ModConfigSpec.BooleanValue TIME_FORMAT_24H;

    public static final ModConfigSpec.BooleanValue ENABLE_COORDS_COUNTER;
    public static final ModConfigSpec.BooleanValue SHOW_COORDS_OVERLAY;

    public static final ModConfigSpec.BooleanValue ENABLE_DAY_COMMAND;
    public static final ModConfigSpec.BooleanValue ENABLE_DEATH_COMMAND;
    public static final ModConfigSpec.BooleanValue ENABLE_SURVIVAL_COMMAND;
    public static final ModConfigSpec.BooleanValue ENABLE_TIME_COMMAND;
    public static final ModConfigSpec.BooleanValue ENABLE_COORDS_COMMAND;

    public enum DeathOverlayMode {
        ONLY_SELF, LIST, BOTH
    }

    public enum DeathInChatMode {
        ON_JOIN, ON_DEATH, BOTH
    }

    public enum DeathChatMode {
        ONLY_SELF, LIST
    }

    public enum SurvivalInChatMode {
        ON_JOIN, ON_DEATH, BOTH
    }

    public enum SurvivalTimeFormat {
        FULL, DAYS_HOURS, HOURS_MINUTES, DAYS, HOURS, MINUTES
    }

    private static ModConfigSpec.BooleanValue defineEnable(String key, String feature) {
        return SERVER_BUILDER.comment("If disabled, " + feature + " will not be tracked or displayed.")
                .translation("counter.config." + key)
                .define(key, true);
    }

    private static ModConfigSpec.BooleanValue defineOverlay(String key, String overlayName) {
        return SERVER_BUILDER.comment("Allow the " + overlayName + " overlay to be displayed for players? (Server-side override)")
                .translation("counter.config." + key)
                .define(key, true);
    }

    static {
        SERVER_BUILDER.translation("counter.config.title.dayCounter").push("Day Counter Settings");
        ENABLE_DAY_COUNTER = defineEnable("enableDayCounter", "day counter");
        SHOW_DAY_OVERLAY = defineOverlay("showOverlay", "day counter");
        ENABLE_DAY_MESSAGE = SERVER_BUILDER.comment("Send a chat message when a new Minecraft day starts?")
                .translation("counter.config.enableDayMessage").define("enableDayMessage", true);
        SHOW_DAY_IN_CHAT = SERVER_BUILDER.comment("Show the current Minecraft day in chat when a player joins?")
                .translation("counter.config.showDayInChat").define("showDayInChat", true);
        DAY_CHATTEXT_COLOR = SERVER_BUILDER.comment("Text color for the day counter chat message.")
                .translation("counter.config.dayChatTextColor").defineInRange("dayChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.translation("counter.config.title.deathCounter").push("Death Counter Settings");
        ENABLE_DEATH_COUNTER = defineEnable("enableDeathCounter", "death counter");
        SHOW_DEATH_OVERLAY = defineOverlay("showOverlay", "death counter");
        MAX_PLAYERS_SHOWN = SERVER_BUILDER.comment("Maximum number of players displayed in the death counter list.")
                .translation("counter.config.maxPlayersShown").defineInRange("maxPlayersShown", 5, 1, 20);
        DEATH_OVERLAY_MODE = SERVER_BUILDER.comment("Which death overlay types are allowed? ONLY_SELF = personal, LIST = global, BOTH = both.")
                .translation("counter.config.deathOverlayMode").defineEnum("deathOverlayMode", DeathOverlayMode.LIST);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.translation("counter.config.title.deathCounterChat").push("Death Counter Chat Settings");
        ENABLE_DEATH_IN_CHAT = SERVER_BUILDER.comment("Enable death counter messages in chat (on join or on death).")
                .translation("counter.config.showDeathInChat").define("showDeathInChat", true);
        SHOW_DEATH_IN_CHAT_MODE = SERVER_BUILDER.comment("Show the death counter in chat: ON_JOIN (when joining), ON_DEATH (when dying), or BOTH?")
                .translation("counter.config.showDeathInChatMode").defineEnum("showDeathInChatMode", DeathInChatMode.ON_DEATH);
        DEATH_CHAT_MODE_TYPE = SERVER_BUILDER.comment("Death counter chat mode: ONLY_SELF (personal), LIST (global ranking).")
                .translation("counter.config.deathChatMode").defineEnum("deathChatMode", DeathChatMode.ONLY_SELF);
        SHOW_DEATH_LIST_ON_DEATH_GLOBAL = SERVER_BUILDER.comment("Broadcast the death list to all players when someone dies?")
                .translation("counter.config.showDeathListOnDeathGlobal").define("showDeathListOnDeathGlobal", false);
        DEATH_LIST_CHATTEXT_COLOR = SERVER_BUILDER.comment("Text color for death counter messages in chat.")
                .translation("counter.config.deathListChatTextColor").defineInRange("deathListChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        DEATH_SELF_CHATTEXT_COLOR = SERVER_BUILDER.comment("Text color for personal death messages in chat.")
                .translation("counter.config.deathSelfChatTextColor").defineInRange("deathSelfChatTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.translation("counter.config.title.survivalCounter").push("Survival Counter Settings");
        ENABLE_SURVIVAL_COUNTER = defineEnable("enableSurvivalCounter", "survival counter");
        SHOW_SURVIVAL_OVERLAY = defineOverlay("showSurvivalOverlay", "survival");
        SURVIVAL_USE_REAL_TIME = SERVER_BUILDER.comment("Measure survival time in real life instead of Minecraft days?")
                .translation("counter.config.survivalUseRealTime").define("survivalUseRealTime", false);
        SURVIVAL_TIME_FORMAT = SERVER_BUILDER.comment("Format used for displaying survival time.")
                .translation("counter.config.survivalTimeFormat").defineEnum("survivalTimeFormat", SurvivalTimeFormat.FULL);
        SHOW_BEST_SURVIVAL_TIME = SERVER_BUILDER.comment("Display the best survival time instead of the last one?")
                .translation("counter.config.showBestSurvivalTime").define("showBestSurvivalTime", false);
        SHOW_BEST_SURVIVAL_IN_DEATH_COUNTER = SERVER_BUILDER.comment("Append the best survival time to the death counter?")
                .translation("counter.config.showBestSurvivalInDeathCounter").define("showBestSurvivalInDeathCounter", false);
        SURVIVAL_HISTORY_SIZE = SERVER_BUILDER.comment("Maximum number of survival history entries per player.")
                .translation("counter.config.survivalHistorySize").defineInRange("survivalHistorySize", 10, 1, 20);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.translation("counter.config.title.survivalCounterChat").push("Survival Counter Chat Settings");
        ENABLE_SURVIVAL_IN_CHAT = SERVER_BUILDER.comment("Send a chat message with the survival time when a player diesEnable death counter messages in chat (on join or on death)?")
                .translation("counter.config.showSurvivalInChat").define("showSurvivalInChat", false);
        SHOW_SURVIVAL_IN_CHAT_MODE = SERVER_BUILDER.comment("Show the survival time in chat: ON_JOIN (when joining), ON_DEATH (when dying), or BOTH?")
                .translation("counter.config.showSurvivalInChatMode").defineEnum("showSurvivalInChatMode", SurvivalInChatMode.ON_DEATH);
        SHOW_SURVIVAL_IN_CHAT_GLOBAL = SERVER_BUILDER.comment("Broadcast the survival time message to all players?")
                .translation("counter.config.showSurvivalInChatGlobal").define("showSurvivalInChatGlobal", false);
        SHOW_BEST_SURVIVAL_IN_CHAT = SERVER_BUILDER.comment("Include the best survival time in parentheses after the survival time?")
                .translation("counter.config.showBestSurvivalInChat").define("showBestSurvivalInChat", false);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.translation("counter.config.title.timeCounter").push("Time Counter Settings");
        ENABLE_TIME_COUNTER = defineEnable("enableTimeCounter", "time counter");
        SHOW_TIME_OVERLAY = defineOverlay("showTimeOverlay", "time");
        SHOW_COMBINED_DAY_TIME = SERVER_BUILDER.comment("Show the day count combined with the inGame time. Disables the standard Time Overlay.")
                .translation("counter.config.showCombinedDayTime").define("showCombinedDayTime", false);
        TIME_FORMAT_24H = SERVER_BUILDER.comment("Use 24-hour format instead of 12-hour format.")
                .translation("counter.config.timeFormat24h").define("timeFormat24h", true);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.translation("counter.config.title.coordsCounter").push("Coordinates Overlay Settings");
        ENABLE_COORDS_COUNTER = defineEnable("enableCoordsCounter", "coordinates overlay");
        SHOW_COORDS_OVERLAY = defineOverlay("showCoordsOverlay", "coordinates");
        SERVER_BUILDER.pop();

        SERVER_BUILDER.translation("counter.config.title.commands").push("Commands Settings");
        ENABLE_DAY_COMMAND = SERVER_BUILDER.comment("Enable /day command?")
                .translation("counter.config.enableDayCommand").define("enableDayCommand", true);
        ENABLE_DEATH_COMMAND = SERVER_BUILDER.comment("Enable /death command?")
                .translation("counter.config.enableDeathCommand").define("enableDeathCommand", true);
        ENABLE_SURVIVAL_COMMAND = SERVER_BUILDER.comment("Enable /survival command?")
                .translation("counter.config.enableSurvivalCommand").define("enableSurvivalCommand", true);
        ENABLE_TIME_COMMAND = SERVER_BUILDER.comment("Enable /time command?")
                .translation("counter.config.enableTimeCommand").define("enableTimeCommand", true);
        ENABLE_COORDS_COMMAND = SERVER_BUILDER.comment("Enable /coords command?")
                .translation("counter.config.enableCoordsCommand").define("enableCoordsCommand", true);
        SERVER_BUILDER.pop();

        SERVER_SPEC = SERVER_BUILDER.build();
    }
}