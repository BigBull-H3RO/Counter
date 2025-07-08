package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import de.bigbull.counter.util.gui.overlay.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class GuiEditScreen extends Screen {
    public enum DragTarget { NONE, DAY, DEATH_LIST, DEATH_SELF, TIME, COORDS, SURVIVAL }
    private DragTarget selectedOverlay = DragTarget.NONE;
    private DragTarget currentDrag = DragTarget.NONE;
    private int dragOffsetX = 0, dragOffsetY = 0;

    private final Map<DragTarget, OverlayState> oldStates = new EnumMap<>(DragTarget.class);

    private boolean doneClicked = false;
    private double previousChatBackgroundOpacity;
    private double previousChatOpacity;

    public GuiEditScreen() {
        super(Component.translatable("screen.overlay_edit"));
    }

    @Override
    protected void init() {
        Minecraft mc = Minecraft.getInstance();

        previousChatOpacity = mc.options.chatOpacity().get();
        previousChatBackgroundOpacity = mc.options.textBackgroundOpacity().get();
        mc.options.chatOpacity().set(0.0);
        mc.options.textBackgroundOpacity().set(0.0);

        for (DragTarget target : DragTarget.values()) {
            if (target == DragTarget.NONE) continue;
            var e = OverlayConfigMapping.get(target);
            oldStates.put(target, new OverlayState(
                    e.x().get(),
                    e.y().get(),
                    e.size().get(),
                    e.align().get(),
                    e.show().get()
            ));
        }

        int centerX = this.width / 2;
        int bottomY = this.height - 80;

        this.addRenderableWidget(
                Button.builder(Component.translatable("screen.overlay_edit.done"), b -> {
                    doneClicked = true;
                    ClientConfig.CLIENT_SPEC.save();
                    onClose();
                }).bounds(centerX - 110, bottomY, 100, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.translatable("screen.overlay_edit.cancel"), b -> {
                    onClose();
                }).bounds(centerX + 10, bottomY, 100, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.translatable("screen.overlay_edit.toggle_overlay"), b -> {
                    toggleSelectedOverlay();
                }).bounds(centerX - 50, bottomY - 30, 100, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("+"), b -> {
                    increaseSelectedOverlaySize();
                }).bounds(centerX - 80, bottomY - 30, 20, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("-"), b -> {
                    decreaseSelectedOverlaySize();
                }).bounds(centerX + 60, bottomY - 30, 20, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("◀"), b -> setSelectedOverlayAlignment(ClientConfig.RIGHT))
                        .bounds(centerX - 40, bottomY - 52, 20, 12).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("●"), b -> setSelectedOverlayAlignment(OverlayAlignment.CENTER))
                        .bounds(centerX - 10, bottomY - 52, 20, 12).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("▶"), b -> setSelectedOverlayAlignment(OverlayAlignment.LEFT))
                        .bounds(centerX + 20, bottomY - 52, 20, 12).build()
        );

        super.init();
    }

    @Override
    public void onClose() {
        if (!doneClicked) {
            revertPositions();
            revertOverlayStates();
            revertSizes();
            revertAlignments();
        }

        Minecraft mc = Minecraft.getInstance();
        mc.options.chatOpacity().set(previousChatOpacity);
        mc.options.textBackgroundOpacity().set(previousChatBackgroundOpacity);

        super.onClose();
    }

    private void revertPositions() {
        for (var entry : oldStates.entrySet()) {
            var map = OverlayConfigMapping.get(entry.getKey());
            if (map == null) continue;
            map.x().set(entry.getValue().x());
            map.y().set(entry.getValue().y());
        }
    }

    private void revertOverlayStates() {
        for (var entry : oldStates.entrySet()) {
            var map = OverlayConfigMapping.get(entry.getKey());
            if (map == null) continue;
            map.show().set(entry.getValue().enabled());
        }
    }

    private void revertSizes() {
        for (var entry : oldStates.entrySet()) {
            var map = OverlayConfigMapping.get(entry.getKey());
            if (map == null) continue;
            map.size().set(entry.getValue().size());
        }
    }

    private void revertAlignments() {
        for (var entry : oldStates.entrySet()) {
            var map = OverlayConfigMapping.get(entry.getKey());
            if (map == null) continue;
            map.align().set(entry.getValue().align());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        DayCounterOverlay.render(guiGraphics);
        DeathCounterOverlay.render(guiGraphics);
        SurvivalTimeOverlay.render(guiGraphics);
        TimeOverlay.render(guiGraphics);
        CoordsOverlay.render(guiGraphics);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (DragTarget target : DragTarget.values()) {
                if (target == DragTarget.NONE) continue;
                if (hitOverlay(mouseX, mouseY, target)) {
                    selectedOverlay = target;
                    currentDrag = target;
                    computeDragOffset(mouseX, mouseY, target);
                    return true;
                }
            }
        }
        boolean widgetHandled = super.mouseClicked(mouseX, mouseY, button);
        if (!widgetHandled && button == 0) {
            selectedOverlay = DragTarget.NONE;
        }
        return widgetHandled;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (button == 0 && currentDrag != DragTarget.NONE) {
            int newPx = (int) (mouseX - dragOffsetX);
            int newPy = (int) (mouseY - dragOffsetY);

            updateOverlayPosition(newPx, newPy, currentDrag);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dx, dy);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && currentDrag != DragTarget.NONE) {
            currentDrag = DragTarget.NONE;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private boolean hitOverlay(double mouseX, double mouseY, DragTarget target) {
        if (isOverlayBlockedByServer(target)) {
            return false;
        }

        var map = OverlayConfigMapping.get(target);
        double scale = map.size().get();

        int w = (int) (map.widthSupplier().get() * scale);
        int h = (int) (map.heightSupplier().get() * scale);

        OverlayAlignment align = map.align().get();

        OverlayUtils.Position pos = OverlayUtils.computePosition(
                map.x().get(), map.y().get(), w, h, align);
        int px = pos.x();
        int py = pos.y();

        int padding = Math.max(1, (int) Math.ceil(3 * scale));

        return mouseX >= px - padding && mouseX <= px + w + padding
                && mouseY >= py - padding && mouseY <= py + h + padding;
    }

    private void computeDragOffset(double mouseX, double mouseY, DragTarget target) {
        var map = OverlayConfigMapping.get(target);

        double scale = map.size().get();

        int width = Math.round((float) (map.widthSupplier().get() * scale));
        int height = Math.round((float) (map.heightSupplier().get() * scale));

        OverlayAlignment align = map.align().get();

        OverlayUtils.Position pos = OverlayUtils.computePosition(
                map.x().get(), map.y().get(), width, height, align);
        int px = pos.x();
        int py = pos.y();

        dragOffsetX = (int) Mth.clamp(mouseX - px, 0, width);
        dragOffsetY = (int) Mth.clamp(mouseY - py, 0, height);
    }

    private void updateOverlayPosition(int newPx, int newPy, DragTarget target) {
        var map = OverlayConfigMapping.get(target);
        double scale = map.size().get();

        int width = Math.round((float) (map.widthSupplier().get() * scale));
        int height = Math.round((float) (map.heightSupplier().get() * scale));

        OverlayAlignment align = map.align().get();

        int shift = switch (align) {
            case CENTER -> width / 2;
            case RIGHT -> width;
            default -> 0;
        };

        newPx = Mth.clamp(newPx, 0, this.width - width);
        newPy = Mth.clamp(newPy, 0, this.height - height);
        map.x().set((double) (newPx + shift) / this.width);
        map.y().set((double) newPy / this.height);
    }

    public void toggleSelectedOverlay() {
        if (isOverlayBlockedByServer(selectedOverlay)) {
            return;
        }

        var map = OverlayConfigMapping.get(selectedOverlay);
        if (map != null) {
            map.show().set(!map.show().get());
        }
    }

    public void increaseSelectedOverlaySize() {
        if (isOverlayBlockedByServer(selectedOverlay)) {
            return;
        }

        adjustOverlaySize(selectedOverlay, 0.1);
    }

    public void decreaseSelectedOverlaySize() {
        if (isOverlayBlockedByServer(selectedOverlay)) {
            return;
        }

        adjustOverlaySize(selectedOverlay, -0.1);
    }

    public void setSelectedOverlayAlignment(OverlayAlignment align) {
        if (isOverlayBlockedByServer(selectedOverlay)) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        var map = OverlayConfigMapping.get(selectedOverlay);
        if (map == null) {
            return;
        }

        ModConfigSpec.DoubleValue xConfig = map.x();
        ModConfigSpec.EnumValue<OverlayAlignment> alignConfig = map.align();
        Supplier<Integer> widthSupplier = map.widthSupplier();
        double scale = map.size().get();

        OverlayAlignment oldAlign = alignConfig.get();
        if (oldAlign == align) {
            return;
        }

        int width = Math.round((float) (widthSupplier.get() * scale));
        int shiftOld = switch (oldAlign) {
            case CENTER -> width / 2;
            case RIGHT -> width;
            default -> 0;
        };
        int shiftNew = switch (align) {
            case CENTER -> width / 2;
            case RIGHT -> width;
            default -> 0;
        };

        double screenWidth = mc.getWindow().getGuiScaledWidth();
        double diff = (double) (shiftNew - shiftOld) / screenWidth;
        xConfig.set(Mth.clamp(xConfig.get() + diff, 0.0, 1.0));

        alignConfig.set(align);
    }

    private void adjustOverlaySize(DragTarget target, double delta) {
        var map = OverlayConfigMapping.get(target);
        if (map != null) {
            double current = map.size().get();
            map.size().set(Mth.clamp(current + delta, 0.1, 5.0));
        }
    }

    private boolean isOverlayBlockedByServer(DragTarget target) {
        return !switch (target) {
            case DAY -> ServerConfig.SHOW_DAY_OVERLAY.get() && ServerConfig.ENABLE_DAY_COUNTER.get();
            case DEATH_LIST, DEATH_SELF -> ServerConfig.SHOW_DEATH_OVERLAY.get() && ServerConfig.ENABLE_DEATH_COUNTER.get();
            case TIME -> ServerConfig.SHOW_TIME_OVERLAY.get() && ServerConfig.ENABLE_TIME_COUNTER.get() && !ServerConfig.SHOW_COMBINED_DAY_TIME.get();
            case COORDS -> ServerConfig.SHOW_COORDS_OVERLAY.get() && ServerConfig.ENABLE_COORDS_COUNTER.get();
            case SURVIVAL -> ServerConfig.SHOW_SURVIVAL_OVERLAY.get() && ServerConfig.ENABLE_SURVIVAL_COUNTER.get();
            default -> false;
        };
    }

    public DragTarget getSelectedOverlay() {
        return selectedOverlay;
    }

    @Override
    protected void renderBlurredBackground(float partialTick) {}
}