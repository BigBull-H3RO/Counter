package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.util.gui.overlay.*;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class OverlayConfigMapping {

    public record Entry(
            ModConfigSpec.DoubleValue x,
            ModConfigSpec.DoubleValue y,
            ModConfigSpec.DoubleValue size,
            ModConfigSpec.EnumValue<OverlayAlignment> align,
            ModConfigSpec.BooleanValue show,
            Supplier<Integer> widthSupplier,
            Supplier<Integer> heightSupplier
    ) {
    }

    private static final Map<GuiEditScreen.DragTarget, Entry> MAP = new EnumMap<>(GuiEditScreen.DragTarget.class);

    static {
        MAP.put(GuiEditScreen.DragTarget.DAY,
                new Entry(ClientConfig.DAY_OVERLAY_X, ClientConfig.DAY_OVERLAY_Y,
                        ClientConfig.DAY_OVERLAY_SIZE, ClientConfig.DAY_OVERLAY_ALIGN,
                        ClientConfig.SHOW_DAY_OVERLAY,
                        DayCounterOverlay::calcDayWidth, DayCounterOverlay::calcDayHeight));

        MAP.put(GuiEditScreen.DragTarget.DEATH_LIST,
                new Entry(ClientConfig.DEATH_LIST_X, ClientConfig.DEATH_LIST_Y,
                        ClientConfig.DEATH_LIST_SIZE, ClientConfig.DEATH_LIST_ALIGN,
                        ClientConfig.SHOW_DEATH_LIST_OVERLAY,
                        DeathCounterOverlay::calcDeathListWidth, DeathCounterOverlay::calcDeathListHeight));

        MAP.put(GuiEditScreen.DragTarget.DEATH_SELF,
                new Entry(ClientConfig.DEATH_SELF_X, ClientConfig.DEATH_SELF_Y,
                        ClientConfig.DEATH_SELF_SIZE, ClientConfig.DEATH_SELF_ALIGN,
                        ClientConfig.SHOW_DEATH_SELF_OVERLAY,
                        DeathCounterOverlay::calcDeathSelfWidth, DeathCounterOverlay::calcDeathSelfHeight));

        MAP.put(GuiEditScreen.DragTarget.SURVIVAL,
                new Entry(ClientConfig.SURVIVAL_OVERLAY_X, ClientConfig.SURVIVAL_OVERLAY_Y,
                        ClientConfig.SURVIVAL_OVERLAY_SIZE, ClientConfig.SURVIVAL_OVERLAY_ALIGN,
                        ClientConfig.SHOW_SURVIVAL_OVERLAY,
                        SurvivalTimeOverlay::calcSurvivalWidth, SurvivalTimeOverlay::calcSurvivalHeight));

        MAP.put(GuiEditScreen.DragTarget.TIME,
                new Entry(ClientConfig.TIME_OVERLAY_X, ClientConfig.TIME_OVERLAY_Y,
                        ClientConfig.TIME_OVERLAY_SIZE, ClientConfig.TIME_OVERLAY_ALIGN,
                        ClientConfig.SHOW_TIME_OVERLAY,
                        TimeOverlay::calcTimeWidth, TimeOverlay::calcTimeHeight));

        MAP.put(GuiEditScreen.DragTarget.COORDS,
                new Entry(ClientConfig.COORDS_OVERLAY_X, ClientConfig.COORDS_OVERLAY_Y,
                        ClientConfig.COORDS_OVERLAY_SIZE, ClientConfig.COORDS_OVERLAY_ALIGN,
                        ClientConfig.SHOW_COORDS_OVERLAY,
                        CoordsOverlay::calcCoordsWidth, CoordsOverlay::calcCoordsHeight));
    }

    public static Entry get(GuiEditScreen.DragTarget target) {
        return MAP.get(target);
    }
}