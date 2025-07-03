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
    public static final ModConfigSpec.IntValue DEATH_OVERLAY_MIN_WIDTH;
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

    public static final ModConfigSpec.BooleanValue SHOW_SURVIVAL_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_SURVIVAL_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue SURVIVAL_OVERLAY_X;
    public static final ModConfigSpec.DoubleValue SURVIVAL_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue SURVIVAL_OVERLAY_SIZE;
    public static final ModConfigSpec.IntValue SURVIVAL_OVERLAY_TEXT_COLOR;

    public static final ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_TIME_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue TIME_OVERLAY_X;
    public static final ModConfigSpec.DoubleValue TIME_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue TIME_OVERLAY_SIZE;
    public static final ModConfigSpec.IntValue TIME_OVERLAY_TEXT_COLOR;

    public static final ModConfigSpec.BooleanValue SHOW_COORDS_OVERLAY;
    public static final ModConfigSpec.BooleanValue SHOW_COORDS_OVERLAY_ALWAYS;
    public static final ModConfigSpec.DoubleValue COORDS_OVERLAY_X;
    public static final ModConfigSpec.DoubleValue COORDS_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue COORDS_OVERLAY_SIZE;
    public static final ModConfigSpec.IntValue COORDS_OVERLAY_TEXT_COLOR;

    public static final ModConfigSpec.BooleanValue SHOW_PING_AS_TEXT;
    public static final ModConfigSpec.IntValue PING_COLOR_GOOD;
    public static final ModConfigSpec.IntValue PING_COLOR_MEDIUM;
    public static final ModConfigSpec.IntValue PING_COLOR_BAD;

    public static final ModConfigSpec.BooleanValue SHOW_EMOJIS;

    private record OverlayValues(ModConfigSpec.BooleanValue show,
                                 ModConfigSpec.BooleanValue always,
                                 ModConfigSpec.DoubleValue x,
                                 ModConfigSpec.DoubleValue y,
                                 ModConfigSpec.DoubleValue size,
                                 ModConfigSpec.IntValue color) {}

    private static ModConfigSpec.BooleanValue defineBool(String key, boolean def, String comment) {
        return CLIENT_BUILDER.comment(comment).define(key, def);
    }

    private static ModConfigSpec.DoubleValue definePosition(String key, double def, String comment) {
        return CLIENT_BUILDER.comment(comment).defineInRange(key, def, 0.0, 1.0);
    }

    private static ModConfigSpec.DoubleValue defineSize(String key, double def, String comment) {
        return CLIENT_BUILDER.comment(comment).defineInRange(key, def, 0.1, 5.0);
    }

    private static ModConfigSpec.IntValue defineColor(String key, int def, String comment) {
        return CLIENT_BUILDER.comment(comment).defineInRange(key, def, 0x000000, 0xFFFFFF);
    }

    private static OverlayValues overlay(String showKey, String showComment, boolean showDef,
                                         String alwaysKey, String alwaysComment, boolean alwaysDef,
                                         String xKey, String xComment, double xDef,
                                         String yKey, String yComment, double yDef,
                                         String sizeKey, String sizeComment, double sizeDef,
                                         String colorKey, String colorComment, int colorDef) {
        var show = defineBool(showKey, showDef, showComment);
        var always = defineBool(alwaysKey, alwaysDef, alwaysComment);
        var x = definePosition(xKey, xDef, xComment);
        var y = definePosition(yKey, yDef, yComment);
        var size = defineSize(sizeKey, sizeDef, sizeComment);
        var color = defineColor(colorKey, colorDef, colorComment);
        return new OverlayValues(show, always, x, y, size, color);
    }

    public enum DeathOverlayStyle {
        CLASSIC, BOXED, TABLE
    }

    static {
        CLIENT_BUILDER.push("Day Counter Overlay Settings");
        var day = overlay(
                "showDayOverlay", "Enable/disable the day overlay on the client side.", true,
                "showOverlayAlways", "Should the day counter overlay always be visible? If false, it is only visible while holding the Tab key.", true,
                "dayOverlayX", "Relative X position (0.0 = left, 1.0 = right).", 0.00625,
                "dayOverlayY", "Relative Y position (0.0 = top, 1.0 = bottom).", 0.015,
                "dayOverlaySize", "Scale factor for the day counter text size.", 1.0,
                "dayOverlayTextColor", "Color for the day overlay text.", 0xFFFFFF);
        SHOW_DAY_OVERLAY = day.show();
        SHOW_DAY_OVERLAY_ALWAYS = day.always();
        DAY_OVERLAY_X = day.x();
        DAY_OVERLAY_Y = day.y();
        DAY_OVERLAY_SIZE = day.size();
        DAY_OVERLAY_TEXT_COLOR = day.color();
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Death Counter List Settings");
        var deathList = overlay(
                "showDeathListOverlay", "Enable/disable the death counter list overlay on the client side.", true,
                "showListOverlayAlways", "Should the death counter list overlay always be visible? If false, it is only visible while holding the Tab key.", false,
                "deathListX", "Relative X position (0.0 = left, 1.0 = right) of the death list overlay.", 0.0125,
                "deathListY", "Relative Y position (0.0 = top, 1.0 = bottom) of the death list overlay.", 0.18,
                "deathListSize", "Scale factor for the death list text size.", 1.0,
                "deathListTextColor", "Default text color for the death list overlay.", 0xFFFFFF);
        SHOW_DEATH_LIST_OVERLAY = deathList.show();
        SHOW_DEATH_LIST_OVERLAY_ALWAYS = deathList.always();
        DEATH_LIST_X = deathList.x();
        DEATH_LIST_Y = deathList.y();
        DEATH_LIST_SIZE = deathList.size();
        DEATH_LIST_TEXT_COLOR = deathList.color();
        DEATH_OVERLAY_STYLE = CLIENT_BUILDER.comment("Which style to use for displaying the death list?")
                .defineEnum("deathOverlayStyle", DeathOverlayStyle.TABLE);
        DEATH_OVERLAY_MIN_WIDTH = CLIENT_BUILDER.comment("Minimum width (in pixels) for the death counter list overlay.")
                .defineInRange("deathOverlayMinWidth", 120, 0, 600);
        FIRST_PLACE_COLOR = CLIENT_BUILDER.comment("Color for the first place in the death list.")
                .defineInRange("firstPlaceColor", 0xFFD700, 0x000000, 0xFFFFFF);
        SECOND_PLACE_COLOR = CLIENT_BUILDER.comment("Color for the second place in the death list.")
                .defineInRange("secondPlaceColor", 0xC0C0C0, 0x000000, 0xFFFFFF);
        THIRD_PLACE_COLOR = CLIENT_BUILDER.comment("Color for the third place in the death list.")
                .defineInRange("thirdPlaceColor", 0xCD7F32, 0x000000, 0xFFFFFF);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Death Counter Self Settings");
        var deathSelf = overlay(
                "showDeathSelfOverlay", "Enable/disable the personal death counter overlay on the client side.", true,
                "showSelfOverlayAlways", "Should the personal death counter overlay always be visible? If false, it is only visible while holding the Tab key.", false,
                "deathSelfX", "Relative X position for your personal death overlay.", 0.00625,
                "deathSelfY", "Relative Y position for your personal death overlay.", 0.068,
                "deathSelfSize", "Scale factor for the personal death counter text size.", 1.0,
                "deathSelfTextColor", "Color for your personal death counter text.", 0xFFFFFF);
        SHOW_DEATH_SELF_OVERLAY = deathSelf.show();
        SHOW_DEATH_SELF_OVERLAY_ALWAYS = deathSelf.always();
        DEATH_SELF_X = deathSelf.x();
        DEATH_SELF_Y = deathSelf.y();
        DEATH_SELF_SIZE = deathSelf.size();
        DEATH_SELF_TEXT_COLOR = deathSelf.color();
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Survival Counter Overlay Settings");
        var survival = overlay(
                "showSurvivalOverlay", "Enable/disable the survival time overlay.", false,
                "showSurvivalOverlayAlways", "Should the survival overlay always be visible? If false, it is only visible while holding the Tab key.", true,
                "survivalOverlayX", "Relative X position (0.0 = left, 1.0 = right).", 0.00625,
                "survivalOverlayY", "Relative Y position (0.0 = top, 1.0 = bottom).", 0.115,
                "survivalOverlaySize", "Scale factor for the survival overlay text size.", 1.0,
                "survivalOverlayTextColor", "Color for the survival overlay text.", 0xFFFFFF);
        SHOW_SURVIVAL_OVERLAY = survival.show();
        SHOW_SURVIVAL_OVERLAY_ALWAYS = survival.always();
        SURVIVAL_OVERLAY_X = survival.x();
        SURVIVAL_OVERLAY_Y = survival.y();
        SURVIVAL_OVERLAY_SIZE = survival.size();
        SURVIVAL_OVERLAY_TEXT_COLOR = survival.color();
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Time Overlay Settings");
        var time = overlay(
                "showTimeOverlay", "Enable/disable the time overlay on the client side.", false,
                "showTimeOverlayAlways", "Should the time overlay always be visible? If false, it is only visible while holding the Tab key.", true,
                "timeOverlayX", "Relative X position (0.0 = left, 1.0 = right).", 0.00781,
                "timeOverlayY", "Relative Y position (0.0 = top, 1.0 = bottom).", 0.955,
                "timeOverlaySize", "Scale factor for the ingame time overlay text size.", 1.0,
                "timeOverlayTextColor", "Color for the ingame time overlay text.", 0xFFFFFF);
        SHOW_TIME_OVERLAY = time.show();
        SHOW_TIME_OVERLAY_ALWAYS = time.always();
        TIME_OVERLAY_X = time.x();
        TIME_OVERLAY_Y = time.y();
        TIME_OVERLAY_SIZE = time.size();
        TIME_OVERLAY_TEXT_COLOR = time.color();
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Coordinates Overlay Settings");
        var coords = overlay(
                "showCoordsOverlay", "Enable/disable the coordinates overlay.", false,
                "showCoordsOverlayAlways", "Should the coordinates overlay always be visible? If false, it is only visible while holding the Tab key.", true,
                "coordsOverlayX", "Relative X position (0.0 = left, 1.0 = right).", 0.00781,
                "coordsOverlayY", "Relative Y position (0.0 = top, 1.0 = bottom).", 0.905,
                "coordsOverlaySize", "Scale factor for the coordinates overlay text size.", 1.0,
                "coordsOverlayTextColor", "Color for the coordinates overlay text.", 0xFFFFFF);
        SHOW_COORDS_OVERLAY = coords.show();
        SHOW_COORDS_OVERLAY_ALWAYS = coords.always();
        COORDS_OVERLAY_X = coords.x();
        COORDS_OVERLAY_Y = coords.y();
        COORDS_OVERLAY_SIZE = coords.size();
        COORDS_OVERLAY_TEXT_COLOR = coords.color();
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
