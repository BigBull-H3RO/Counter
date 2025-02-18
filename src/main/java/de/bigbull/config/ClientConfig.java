package de.bigbull.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    public static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CLIENT_SPEC;

    public static final ModConfigSpec.IntValue DAY_OVERLAY_X;
    public static final ModConfigSpec.IntValue DAY_OVERLAY_Y;
    public static final ModConfigSpec.DoubleValue DAY_OVERLAY_SIZE;

    public static final ModConfigSpec.IntValue DEATH_LIST_X;
    public static final ModConfigSpec.IntValue DEATH_LIST_Y;
    public static final ModConfigSpec.DoubleValue DEATH_LIST_SIZE;
    public static final ModConfigSpec.IntValue DEATH_OVERLAY_WIDTH;
    public static final ModConfigSpec.EnumValue<DeathOverlayStyle> DEATH_OVERLAY_STYLE;

    public static final ModConfigSpec.IntValue DEATH_SELF_X;
    public static final ModConfigSpec.IntValue DEATH_SELF_Y;
    public static final ModConfigSpec.DoubleValue DEATH_SELF_SIZE;

    public enum DeathOverlayStyle {
        CLASSIC, BOXED, TABLE
    }

    static {
        CLIENT_BUILDER.push("Day Counter Overlay Settings");
        DAY_OVERLAY_X = CLIENT_BUILDER.comment("X position of the day counter overlay")
                .defineInRange("dayOverlayX", 10, 0, 1920);
        DAY_OVERLAY_Y = CLIENT_BUILDER.comment("Y position of the day counter overlay")
                .defineInRange("dayOverlayY", 10, 0, 1080);
        DAY_OVERLAY_SIZE = CLIENT_BUILDER.comment("Font size of the day counter overlay")
                .defineInRange("dayOverlaySize", 1, 0.1, 5);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Death Counter List Settings");
        DEATH_LIST_X = CLIENT_BUILDER.comment("X position of the death counter list overlay")
                .defineInRange("deathListX", 10, 0, 1920);
        DEATH_LIST_Y = CLIENT_BUILDER.comment("Y position of the death counter list overlay")
                .defineInRange("deathListY", 50, 0, 1080);
        DEATH_LIST_SIZE = CLIENT_BUILDER.comment("Font size of the death counter list overlay")
                .defineInRange("deathListSize", 1, 0.1, 5);
        DEATH_OVERLAY_STYLE = CLIENT_BUILDER.comment("Death Counter Style: CLASSIC = Standard list, BOXED = Background box, TABLE = Modern table layout")
                .defineEnum("deathOverlayStyle", ClientConfig.DeathOverlayStyle.CLASSIC);
        DEATH_OVERLAY_WIDTH = CLIENT_BUILDER.comment("Width of the death counter list overlay")
                .defineInRange("deathOverlayWidth", 120, 0, 1920);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push("Death Counter Self Settings");
        DEATH_SELF_X = CLIENT_BUILDER.comment("X position of the personal death counter overlay")
                .defineInRange("deathSelfX", 10, 0, 1920);
        DEATH_SELF_Y = CLIENT_BUILDER.comment("Y position of the personal death counter overlay")
                .defineInRange("deathSelfY", 100, 0, 1080);
        DEATH_SELF_SIZE = CLIENT_BUILDER.comment("Font size of the personal death counter overlay")
                .defineInRange("deathSelfSize", 1, 0.1, 5);
        CLIENT_BUILDER.pop();

        CLIENT_SPEC = CLIENT_BUILDER.build();
    }
}
