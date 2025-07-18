package de.bigbull.counter.config;

import de.bigbull.counter.util.gui.OverlayAlignment;
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
    public static final ModConfigSpec.EnumValue<OverlayAlignment> DAY_OVERLAY_ALIGN;

    public static final ModConfigSpec.BooleanValue SHOW_DEATH_LIST_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_LIST_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_X;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_Y;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_SIZE;
    public static final ModConfigSpec.IntValue DEATH_OVERLAY_MIN_WIDTH;
    public static final ModConfigSpec.EnumValue<DeathOverlayStyle> DEATH_OVERLAY_STYLE;
    public static final ModConfigSpec.IntValue DEATH_LIST_TEXT_COLOR;
    public static final ModConfigSpec.EnumValue<OverlayAlignment> DEATH_LIST_ALIGN;
    public static final ModConfigSpec.IntValue FIRST_PLACE_COLOR;
    public static final ModConfigSpec.IntValue SECOND_PLACE_COLOR;
    public static final ModConfigSpec.IntValue THIRD_PLACE_COLOR;

    public static final ModConfigSpec.BooleanValue SHOW_DEATH_SELF_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_DEATH_SELF_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_X;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_Y;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_SIZE;
    public static final ModConfigSpec.IntValue DEATH_SELF_TEXT_COLOR;
    public static final ModConfigSpec.EnumValue<OverlayAlignment> DEATH_SELF_ALIGN;

    public static final ModConfigSpec.BooleanValue SHOW_SURVIVAL_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_SURVIVAL_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue SURVIVAL_OVERLAY_X;
    public static final ModConfigSpec.DoubleValue SURVIVAL_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue SURVIVAL_OVERLAY_SIZE;
    public static final ModConfigSpec.IntValue SURVIVAL_OVERLAY_TEXT_COLOR;
    public static final ModConfigSpec.EnumValue<OverlayAlignment> SURVIVAL_OVERLAY_ALIGN;

    public static final ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue TIME_OVERLAY_X;
    public static final ModConfigSpec.DoubleValue TIME_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue TIME_OVERLAY_SIZE;
    public static final ModConfigSpec.IntValue TIME_OVERLAY_TEXT_COLOR;
    public static final ModConfigSpec.EnumValue<OverlayAlignment> TIME_OVERLAY_ALIGN;

    public static final ModConfigSpec.BooleanValue SHOW_COORDS_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_COORDS_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue COORDS_OVERLAY_X;
    public static final ModConfigSpec.DoubleValue COORDS_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue COORDS_OVERLAY_SIZE;
    public static final ModConfigSpec.IntValue COORDS_OVERLAY_TEXT_COLOR;
    public static final ModConfigSpec.EnumValue<OverlayAlignment> COORDS_OVERLAY_ALIGN;

    public static final ModConfigSpec.BooleanValue SHOW_PING_AS_TEXT;
    public static final ModConfigSpec.IntValue PING_COLOR_GOOD;
    public static final ModConfigSpec.IntValue PING_COLOR_MEDIUM;
    public static final ModConfigSpec.IntValue PING_COLOR_BAD;

    public static final ModConfigSpec.BooleanValue SHOW_EMOJIS;

    public enum DeathOverlayStyle {
        CLASSIC, BOXED, TABLE
    }

    static {
        CLIENT_BUILDER.translation("counter.config.title.dayOverlay").push("Day Counter Overlay Settings");
        SHOW_DAY_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the day overlay on the client side.")
                .translation("counter.config.showDayOverlay").define("showDayOverlay", true);
        SHOW_DAY_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the day counter overlay always be visible? If false, it is only visible while holding the Tab key.")
                .translation("counter.config.showOverlayAlways").define("showOverlayAlways", true);
        DAY_OVERLAY_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right).")
                .translation("counter.config.dayOverlayX").defineInRange("dayOverlayX", 0.00625, 0.0, 1.0);
        DAY_OVERLAY_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                .translation("counter.config.dayOverlayY").defineInRange("dayOverlayY", 0.015, 0.0, 1.0);
        DAY_OVERLAY_SIZE = CLIENT_BUILDER.comment("Scale factor for the day counter text size.")
                .translation("counter.config.dayOverlaySize").defineInRange("dayOverlaySize", 1.0, 0.1, 5.0);
        DAY_OVERLAY_ALIGN = CLIENT_BUILDER.comment("Alignment for the day overlay.")
                .translation("counter.config.dayOverlayAlign").defineEnum("dayOverlayAlign", OverlayAlignment.LEFT);
        DAY_OVERLAY_TEXT_COLOR = CLIENT_BUILDER.comment("Color for the day overlay text.")
                .translation("counter.config.dayOverlayTextColor").defineInRange("dayOverlayTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.translation("counter.config.title.deathList").push("Death Counter List Settings");
        SHOW_DEATH_LIST_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the death counter list overlay on the client side.")
                .translation("counter.config.showDeathListOverlay").define("showDeathListOverlay", true);
        SHOW_DEATH_LIST_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the death counter list overlay always be visible? If false, it is only visible while holding the Tab key.")
                .translation("counter.config.showListOverlayAlways").define("showListOverlayAlways", false);
        DEATH_LIST_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right) of the death list overlay.")
                .translation("counter.config.deathListX").defineInRange("deathListX", 0.0125, 0.0, 1.0);
        DEATH_LIST_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom) of the death list overlay.")
                .translation("counter.config.deathListY").defineInRange("deathListY", 0.18, 0.0, 1.0);
        DEATH_LIST_SIZE = CLIENT_BUILDER.comment("Scale factor for the death list text size.")
                .translation("counter.config.deathListSize").defineInRange("deathListSize", 1, 0.1, 5);
        DEATH_OVERLAY_STYLE = CLIENT_BUILDER.comment("Which style to use for displaying the death list?")
                .translation("counter.config.deathOverlayStyle").defineEnum("deathOverlayStyle", DeathOverlayStyle.TABLE);
        DEATH_OVERLAY_MIN_WIDTH = CLIENT_BUILDER.comment("Minimum width (in pixels) for the death counter list overlay.")
                .translation("counter.config.deathOverlayMinWidth").defineInRange("deathOverlayMinWidth", 120, 0, 600);
        DEATH_LIST_ALIGN = CLIENT_BUILDER.comment("Alignment for the death list overlay.")
                .translation("counter.config.deathListAlign").defineEnum("deathListAlign", OverlayAlignment.LEFT);
        DEATH_LIST_TEXT_COLOR = CLIENT_BUILDER.comment("Default text color for the death list overlay.")
                .translation("counter.config.deathListTextColor").defineInRange("deathListTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        FIRST_PLACE_COLOR = CLIENT_BUILDER.comment("Color for the first place in the death list.")
                .translation("counter.config.firstPlaceColor").defineInRange("firstPlaceColor", 0xFFD700, 0x000000, 0xFFFFFF);
        SECOND_PLACE_COLOR = CLIENT_BUILDER.comment("Color for the second place in the death list.")
                .translation("counter.config.secondPlaceColor").defineInRange("secondPlaceColor", 0xC0C0C0, 0x000000, 0xFFFFFF);
        THIRD_PLACE_COLOR = CLIENT_BUILDER.comment("Color for the third place in the death list.")
                .translation("counter.config.thirdPlaceColor").defineInRange("thirdPlaceColor", 0xCD7F32, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.translation("counter.config.title.deathSelf").push("Death Counter Self Settings");
        SHOW_DEATH_SELF_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the personal death counter overlay on the client side.")
                .translation("counter.config.showDeathSelfOverlay").define("showDeathSelfOverlay", true);
        SHOW_DEATH_SELF_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the personal death counter overlay always be visible? If false, it is only visible while holding the Tab key.")
                .translation("counter.config.showSelfOverlayAlways").define("showSelfOverlayAlways", false);
        DEATH_SELF_X = CLIENT_BUILDER.comment("Relative X position for your personal death overlay.")
                .translation("counter.config.deathSelfX").defineInRange("deathSelfX", 0.00625, 0.0, 1.0);
        DEATH_SELF_Y = CLIENT_BUILDER.comment("Relative Y position for your personal death overlay.")
                .translation("counter.config.deathSelfY").defineInRange("deathSelfY", 0.122, 0.0, 1.0);
        DEATH_SELF_SIZE = CLIENT_BUILDER.comment("Scale factor for the personal death counter text size.")
                .translation("counter.config.deathSelfSize").defineInRange("deathSelfSize", 1, 0.1, 5);
        DEATH_SELF_ALIGN = CLIENT_BUILDER.comment("Alignment for your personal death overlay.")
                .translation("counter.config.deathSelfAlign").defineEnum("deathSelfAlign", OverlayAlignment.LEFT);
        DEATH_SELF_TEXT_COLOR = CLIENT_BUILDER.comment("Color for your personal death counter text.")
                .translation("counter.config.deathSelfTextColor").defineInRange("deathSelfTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.translation("counter.config.title.survivalOverlay").push("Survival Counter Overlay Settings");
        SHOW_SURVIVAL_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the survival time overlay.")
                .translation("counter.config.showSurvivalOverlay").define("showSurvivalOverlay", false);
        SHOW_SURVIVAL_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the survival overlay always be visible? If false, it is only visible while holding the Tab key.")
                .translation("counter.config.showSurvivalOverlayAlways").define("showSurvivalOverlayAlways", true);
        SURVIVAL_OVERLAY_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right).")
                .translation("counter.config.survivalOverlayX").defineInRange("survivalOverlayX", 0.00625, 0.0, 1.0);
        SURVIVAL_OVERLAY_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                .translation("counter.config.survivalOverlayY").defineInRange("survivalOverlayY", 0.068, 0.0, 1.0);
        SURVIVAL_OVERLAY_SIZE = CLIENT_BUILDER.comment("Scale factor for the survival overlay text size.")
                .translation("counter.config.survivalOverlaySize").defineInRange("survivalOverlaySize", 1.0, 0.1, 5.0);
        SURVIVAL_OVERLAY_ALIGN = CLIENT_BUILDER.comment("Alignment for the survival overlay.")
                .translation("counter.config.survivalOverlayAlign").defineEnum("survivalOverlayAlign", OverlayAlignment.LEFT);
        SURVIVAL_OVERLAY_TEXT_COLOR = CLIENT_BUILDER.comment("Color for the survival overlay text.")
                .translation("counter.config.survivalOverlayTextColor").defineInRange("survivalOverlayTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.translation("counter.config.title.timeOverlay").push("Time Overlay Settings");
        SHOW_TIME_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the time overlay on the client side.")
                .translation("counter.config.showTimeOverlay").define("showTimeOverlay", false);
        SHOW_TIME_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the time overlay always be visible? If false, it is only visible while holding the Tab key.")
                .translation("counter.config.showTimeOverlayAlways").define("showTimeOverlayAlways", true);
        TIME_OVERLAY_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right).")
                .translation("counter.config.timeOverlayX").defineInRange("timeOverlayX", 0.00625, 0.0, 1.0);
        TIME_OVERLAY_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                .translation("counter.config.timeOverlayY").defineInRange("timeOverlayY", 0.955, 0.0, 1.0);
        TIME_OVERLAY_SIZE = CLIENT_BUILDER.comment("Scale factor for the ingame time overlay text size.")
                .translation("counter.config.timeOverlaySize").defineInRange("timeOverlaySize", 1.0, 0.1, 5.0);
        TIME_OVERLAY_ALIGN = CLIENT_BUILDER.comment("Alignment for the time overlay.")
                .translation("counter.config.timeOverlayAlign").defineEnum("timeOverlayAlign", OverlayAlignment.LEFT);
        TIME_OVERLAY_TEXT_COLOR = CLIENT_BUILDER.comment("Color for the ingame time overlay text.")
                .translation("counter.config.timeOverlayTextColor").defineInRange("timeOverlayTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.translation("counter.config.title.coordsOverlay").push("Coordinates Overlay Settings");
        SHOW_COORDS_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the coordinates overlay.")
                .translation("counter.config.showCoordsOverlay").define("showCoordsOverlay", false);
        SHOW_COORDS_OVERLAY_ALWAYS = CLIENT_BUILDER.comment("Should the coordinates overlay always be visible? If false, it is only visible while holding the Tab key.")
                .translation("counter.config.showCoordsOverlayAlways").define("showCoordsOverlayAlways", true);
        COORDS_OVERLAY_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right).")
                .translation("counter.config.coordsOverlayX").defineInRange("coordsOverlayX", 0.00625, 0.0, 1.0);
        COORDS_OVERLAY_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                .translation("counter.config.coordsOverlayY").defineInRange("coordsOverlayY", 0.905, 0.0, 1.0);
        COORDS_OVERLAY_SIZE = CLIENT_BUILDER.comment("Scale factor for the coordinates overlay text size.")
                .translation("counter.config.coordsOverlaySize").defineInRange("coordsOverlaySize", 1.0, 0.1, 5.0);
        COORDS_OVERLAY_ALIGN = CLIENT_BUILDER.comment("Alignment for the coordinates overlay.")
                .translation("counter.config.coordsOverlayAlign").defineEnum("coordsOverlayAlign", OverlayAlignment.LEFT);
        COORDS_OVERLAY_TEXT_COLOR = CLIENT_BUILDER.comment("Color for the coordinates overlay text.")
                .translation("counter.config.coordsOverlayTextColor").defineInRange("coordsOverlayTextColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.translation("counter.config.title.ping").push("Ping Settings");
        SHOW_PING_AS_TEXT = CLIENT_BUILDER.comment("Show the ping as text (e.g. '123ms') instead of the default bars in the tab list?")
                .translation("counter.config.showPingAsText").define("showPingAsText", true);
        PING_COLOR_GOOD = CLIENT_BUILDER.comment("Color for low ping (<100ms).")
                .translation("counter.config.pingColorGood").defineInRange("pingColorGood", 0x00FF00, 0x000000, 0xFFFFFF);
        PING_COLOR_MEDIUM = CLIENT_BUILDER.comment("Color for medium ping (100-249ms).")
                .translation("counter.config.pingColorMedium").defineInRange("pingColorMedium", 0xFF9900, 0x000000, 0xFFFFFF);
        PING_COLOR_BAD = CLIENT_BUILDER.comment("Color for high ping (>=250ms).")
                .translation("counter.config.pingColorBad").defineInRange("pingColorBad", 0xFF0000, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.translation("counter.config.title.emote").push("Emote Settings");
        SHOW_EMOJIS = CLIENT_BUILDER.comment("Enable or disable emojis in overlays.")
                .translation("counter.config.showEmojis").define("showEmojis", true);
        CLIENT_BUILDER.pop();

        CLIENT_SPEC = CLIENT_BUILDER.build();
    }
}
