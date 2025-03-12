package de.bigbull.counter.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    public static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CLIENT_SPEC;

    public static final ModConfigSpec.BooleanValue SHOW_DAY_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_DAY_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue DAY_OVERLAY_X;
    public static final ModConfigSpec.DoubleValue DAY_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue DAY_OVERLAY_SIZE;
    public static final ModConfigSpec.IntValue DAY_OVERLAY_TEXT_COLOR;

    public static final ModConfigSpec.BooleanValue SHOW_DEATH_LIST_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_LIST_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_X;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_Y;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_SIZE;
    public static final ModConfigSpec.IntValue DEATH_OVERLAY_WIDTH;
    public static final ModConfigSpec.EnumValue<DeathOverlayStyle> DEATH_OVERLAY_STYLE;
    public static final ModConfigSpec.IntValue DEATH_LIST_TEXT_COLOR;
    public static final ModConfigSpec.IntValue FIRST_PLACE_COLOR;
    public static final ModConfigSpec.IntValue SECOND_PLACE_COLOR;
    public static final ModConfigSpec.IntValue THIRD_PLACE_COLOR;

    public static final ModConfigSpec.BooleanValue SHOW_DEATH_SELF_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_SELF_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_X;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_Y;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_SIZE;
    public static final ModConfigSpec.IntValue DEATH_SELF_TEXT_COLOR;

    public static final ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue TIME_OVERLAY_X;
    public static final ModConfigSpec.DoubleValue TIME_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue TIME_OVERLAY_SIZE;
    public static final ModConfigSpec.IntValue TIME_OVERLAY_TEXT_COLOR;

    public static final ModConfigSpec.BooleanValue SHOW_PING_AS_TEXT;
    public static final ModConfigSpec.IntValue PING_COLOR_GOOD;
    public static final ModConfigSpec.IntValue PING_COLOR_MEDIUM;
    public static final ModConfigSpec.IntValue PING_COLOR_BAD;

    public static final ModConfigSpec.BooleanValue SHOW_EMOJIS;

    public enum DeathOverlayStyle {
        CLASSIC, BOXED, TABLE
    }

    static {
        CLIENT_BUILDER.push("Day Counter Overlay Settings");
        SHOW_DAY_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the day overlay on the client side.")
                .define("showDayOverlay", true);
        SHOW_DAY_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the day counter overlay always be visible?")
                .define("showOverlayAlways", true);
        DAY_OVERLAY_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right).")
                .defineInRange("dayOverlayX", 0.00625, 0.0, 1.0);
        DAY_OVERLAY_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                .defineInRange("dayOverlayY", 0.015, 0.0, 1.0);
        DAY_OVERLAY_SIZE = CLIENT_BUILDER.comment("Scale factor for the day counter text size.")
                .defineInRange("dayOverlaySize", 1.0, 0.1, 5.0);
        DAY_OVERLAY_TEXT_COLOR = CLIENT_BUILDER.comment("Color for the day overlay text.")
                .defineInRange("dayOverlayTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Death Counter List Settings");
        SHOW_DEATH_LIST_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the death counter list overlay (shows all player deaths).")
                .define("showDeathListOverlay", true);
        SHOW_DEATH_LIST_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the death counter list overlay always be visible?")
                .define("showListOverlayAlways", false);
        DEATH_LIST_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right) of the death list overlay.")
                .defineInRange("deathListX", 0.0125, 0.0, 1.0);
        DEATH_LIST_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom) of the death list overlay.")
                .defineInRange("deathListY", 0.16, 0.0, 1.0);
        DEATH_LIST_SIZE = CLIENT_BUILDER.comment("Scale factor for the death list text size.")
                .defineInRange("deathListSize", 1, 0.1, 5);
        DEATH_OVERLAY_STYLE = CLIENT_BUILDER.comment("Which style to use for displaying the death list: CLASSIC, BOXED, or TABLE.")
                .defineEnum("deathOverlayStyle", DeathOverlayStyle.TABLE);
        DEATH_OVERLAY_WIDTH = CLIENT_BUILDER.comment("Maximum width (in pixels) for the death counter list overlay.")
                .defineInRange("deathOverlayWidth", 120, 0, 600);
        DEATH_LIST_TEXT_COLOR = CLIENT_BUILDER.comment("Default text color for the death list overlay.")
                .defineInRange("deathListTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        FIRST_PLACE_COLOR = CLIENT_BUILDER.comment("Color for the first place in the death list.")
                .defineInRange("firstPlaceColor", 0xFFD700, 0x000000, 0xFFFFFF);
        SECOND_PLACE_COLOR = CLIENT_BUILDER.comment("Color for the second place in the death list.")
                .defineInRange("secondPlaceColor", 0xC0C0C0, 0x000000, 0xFFFFFF);
        THIRD_PLACE_COLOR = CLIENT_BUILDER.comment("Color for the third place in the death list.")
                .defineInRange("thirdPlaceColor", 0xCD7F32, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Death Counter Self Settings");
        SHOW_DEATH_SELF_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the personal death counter overlay.")
                .define("showDeathSelfOverlay", true);
        SHOW_DEATH_SELF_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the personal death counter overlay always be visible?")
                .define("showSelfOverlayAlways", false);
        DEATH_SELF_X = CLIENT_BUILDER.comment("Relative X position for your personal death overlay.")
                .defineInRange("deathSelfX", 0.00625, 0.0, 1.0);
        DEATH_SELF_Y = CLIENT_BUILDER.comment("Relative Y position for your personal death overlay.")
                .defineInRange("deathSelfY", 0.068, 0.0, 1.0);
        DEATH_SELF_SIZE = CLIENT_BUILDER.comment("Scale factor for the personal death counter text size.")
                .defineInRange("deathSelfSize", 1, 0.1, 5);
        DEATH_SELF_TEXT_COLOR = CLIENT_BUILDER.comment("Color for your personal death counter text.")
                .defineInRange("deathSelfTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Ingame Time Overlay Settings");
        SHOW_TIME_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the ingame time overlay.")
                .define("showIngameTimeOverlay", false);
        SHOW_TIME_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the ingame time overlay always be visible?")
                .define("showIngameTimeOverlayAlways", true);
        TIME_OVERLAY_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right).")
                .defineInRange("ingameTimeOverlayX", 0.00781, 0.0, 1.0);
        TIME_OVERLAY_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                .defineInRange("ingameTimeOverlayY", 0.955, 0.0, 1.0);
        TIME_OVERLAY_SIZE = CLIENT_BUILDER.comment("Scale factor for the ingame time overlay text size.")
                .defineInRange("ingameTimeOverlaySize", 1.0, 0.1, 5.0);
        TIME_OVERLAY_TEXT_COLOR = CLIENT_BUILDER.comment("Color for the ingame time overlay text.")
                .defineInRange("ingameTimeOverlayTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Ping Settings");
        SHOW_PING_AS_TEXT = CLIENT_BUILDER.comment("Show the ping as text (e.g. '123ms') instead of the default bars in the tab list?")
                .define("showPingAsText", true);
        PING_COLOR_GOOD = CLIENT_BUILDER.comment("Color for low ping (<100ms).")
                .defineInRange("pingColorGood", 0x00FF00, 0x000000, 0xFFFFFF);
        PING_COLOR_MEDIUM = CLIENT_BUILDER.comment("Color for medium ping (100-249ms).")
                .defineInRange("pingColorMedium", 0xFF9900, 0x000000, 0xFFFFFF);
        PING_COLOR_BAD = CLIENT_BUILDER.comment("Color for high ping (>=250ms).")
                .defineInRange("pingColorBad", 0xFF0000, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Emote Settings");
        SHOW_EMOJIS = CLIENT_BUILDER.comment("Enable or disable emojis in overlays.")
                .define("showEmojis", true);
        CLIENT_BUILDER.pop();

        CLIENT_SPEC = CLIENT_BUILDER.build();
    }
}
