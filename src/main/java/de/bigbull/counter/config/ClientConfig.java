package de.bigbull.counter.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    public static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CLIENT_SPEC;

    public static final ModConfigSpec.BooleanValue SHOW_DAY_OVERLAY;
    public static final ModConfigSpec.DoubleValue DAY_OVERLAY_X;
    public static final ModConfigSpec.DoubleValue DAY_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue DAY_OVERLAY_SIZE;

    public static final ModConfigSpec.BooleanValue SHOW_DEATH_LIST_OVERLAY;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_X;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_Y;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_SIZE;
    public static final ModConfigSpec.IntValue DEATH_OVERLAY_WIDTH;
    public static final ModConfigSpec.EnumValue<DeathOverlayStyle> DEATH_OVERLAY_STYLE;

    public static final ModConfigSpec.BooleanValue SHOW_DEATH_SELF_OVERLAY;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_X;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_Y;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_SIZE;

    public static final ModConfigSpec.BooleanValue SHOW_PING_AS_TEXT;

    public enum DeathOverlayStyle {
        CLASSIC, BOXED, TABLE
    }

    static {
        CLIENT_BUILDER.push("Day Counter Overlay Settings");
        SHOW_DAY_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the day overlay on the client side.")
                .define("showDayOverlay", true);
        DAY_OVERLAY_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right).")
                .defineInRange("dayOverlayX", 0.00625, 0.0, 1.0);
        DAY_OVERLAY_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom).")
                .defineInRange("dayOverlayY", 0.015, 0.0, 1.0);
        DAY_OVERLAY_SIZE = CLIENT_BUILDER.comment("Font size scaling factor for the day counter.")
                .defineInRange("dayOverlaySize", 1.0, 0.1, 5.0);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Death Counter List Settings");
        SHOW_DEATH_LIST_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the death list overlay (list of all players' deaths).")
                .define("showDeathListOverlay", true);
        DEATH_LIST_X = CLIENT_BUILDER.comment("Relative X position (0.0 = left, 1.0 = right) of the death list overlay.")
                .defineInRange("deathListX", 0.01, 0.0, 1.0);
        DEATH_LIST_Y = CLIENT_BUILDER.comment("Relative Y position (0.0 = top, 1.0 = bottom) of the death list overlay.")
                .defineInRange("deathListY", 0.16, 0.0, 1.0);
        DEATH_LIST_SIZE = CLIENT_BUILDER.comment("Font size scaling factor for the death list overlay.")
                .defineInRange("deathListSize", 1, 0.1, 5);
        DEATH_OVERLAY_STYLE = CLIENT_BUILDER.comment("Which style to use for displaying the death list: CLASSIC, BOXED, or TABLE.")
                .defineEnum("deathOverlayStyle", DeathOverlayStyle.TABLE);
        DEATH_OVERLAY_WIDTH = CLIENT_BUILDER.comment("Max width (in pixels) for the death list overlay (affects layout).")
                .defineInRange("deathOverlayWidth", 120, 0, 1920);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Death Counter Self Settings");
        SHOW_DEATH_SELF_OVERLAY = CLIENT_BUILDER.comment("Enable/disable the personal death counter overlay.")
                .define("showDeathSelfOverlay", true);
        DEATH_SELF_X = CLIENT_BUILDER.comment("Relative X position for your personal death overlay.")
                .defineInRange("deathSelfX", 0.00625, 0.0, 1.0);
        DEATH_SELF_Y = CLIENT_BUILDER.comment("Relative Y position for your personal death overlay.")
                .defineInRange("deathSelfY", 0.068, 0.0, 1.0);
        DEATH_SELF_SIZE = CLIENT_BUILDER.comment("Font size scaling factor for your personal death counter.")
                .defineInRange("deathSelfSize", 1, 0.1, 5);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Ping Settings");
        SHOW_PING_AS_TEXT = CLIENT_BUILDER.comment("Show the ping as text (e.g. '123ms') instead of the default bars in the tab list?")
                .define("showPingAsText", true);
        CLIENT_BUILDER.pop();

        CLIENT_SPEC = CLIENT_BUILDER.build();
    }
}
