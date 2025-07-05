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

        oldStates.put(DragTarget.DAY, new OverlayState(
                ClientConfig.DAY_OVERLAY_X.get(),
                ClientConfig.DAY_OVERLAY_Y.get(),
                ClientConfig.DAY_OVERLAY_SIZE.get(),
                ClientConfig.DAY_OVERLAY_ALIGN.get(),
                ClientConfig.SHOW_DAY_OVERLAY.get()
        ));
        oldStates.put(DragTarget.DEATH_LIST, new OverlayState(
                ClientConfig.DEATH_LIST_X.get(),
                ClientConfig.DEATH_LIST_Y.get(),
                ClientConfig.DEATH_LIST_SIZE.get(),
                ClientConfig.DEATH_LIST_ALIGN.get(),
                ClientConfig.SHOW_DEATH_LIST_OVERLAY.get()
        ));
        oldStates.put(DragTarget.DEATH_SELF, new OverlayState(
                ClientConfig.DEATH_SELF_X.get(),
                ClientConfig.DEATH_SELF_Y.get(),
                ClientConfig.DEATH_SELF_SIZE.get(),
                ClientConfig.DEATH_SELF_ALIGN.get(),
                ClientConfig.SHOW_DEATH_SELF_OVERLAY.get()
        ));
        oldStates.put(DragTarget.TIME, new OverlayState(
                ClientConfig.TIME_OVERLAY_X.get(),
                ClientConfig.TIME_OVERLAY_Y.get(),
                ClientConfig.TIME_OVERLAY_SIZE.get(),
                ClientConfig.TIME_OVERLAY_ALIGN.get(),
                ClientConfig.SHOW_TIME_OVERLAY.get()
        ));
        oldStates.put(DragTarget.COORDS, new OverlayState(
                ClientConfig.COORDS_OVERLAY_X.get(),
                ClientConfig.COORDS_OVERLAY_Y.get(),
                ClientConfig.COORDS_OVERLAY_SIZE.get(),
                ClientConfig.COORDS_OVERLAY_ALIGN.get(),
                ClientConfig.SHOW_COORDS_OVERLAY.get()
        ));
        oldStates.put(DragTarget.SURVIVAL, new OverlayState(
                ClientConfig.SURVIVAL_OVERLAY_X.get(),
                ClientConfig.SURVIVAL_OVERLAY_Y.get(),
                ClientConfig.SURVIVAL_OVERLAY_SIZE.get(),
                ClientConfig.SURVIVAL_OVERLAY_ALIGN.get(),
                ClientConfig.SHOW_SURVIVAL_OVERLAY.get()
        ));

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
                Button.builder(Component.literal("◀"), b -> setSelectedOverlayAlignment(OverlayAlignment.LEFT))
                        .bounds(centerX - 40, bottomY - 52, 20, 12).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("●"), b -> setSelectedOverlayAlignment(OverlayAlignment.CENTER))
                        .bounds(centerX - 10, bottomY - 52, 20, 12).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("▶"), b -> setSelectedOverlayAlignment(OverlayAlignment.RIGHT))
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
            switch (entry.getKey()) {
                case DAY -> {
                    ClientConfig.DAY_OVERLAY_X.set(entry.getValue().x());
                    ClientConfig.DAY_OVERLAY_Y.set(entry.getValue().y());
                }
                case DEATH_LIST -> {
                    ClientConfig.DEATH_LIST_X.set(entry.getValue().x());
                    ClientConfig.DEATH_LIST_Y.set(entry.getValue().y());
                }
                case DEATH_SELF -> {
                    ClientConfig.DEATH_SELF_X.set(entry.getValue().x());
                    ClientConfig.DEATH_SELF_Y.set(entry.getValue().y());
                }
                case TIME -> {
                    ClientConfig.TIME_OVERLAY_X.set(entry.getValue().x());
                    ClientConfig.TIME_OVERLAY_Y.set(entry.getValue().y());
                }
                case COORDS -> {
                    ClientConfig.COORDS_OVERLAY_X.set(entry.getValue().x());
                    ClientConfig.COORDS_OVERLAY_Y.set(entry.getValue().y());
                }
                case SURVIVAL -> {
                    ClientConfig.SURVIVAL_OVERLAY_X.set(entry.getValue().x());
                    ClientConfig.SURVIVAL_OVERLAY_Y.set(entry.getValue().y());
                }
                default -> {}
            }
        }
    }

    private void revertOverlayStates() {
        for (var entry : oldStates.entrySet()) {
            switch (entry.getKey()) {
                case DAY -> ClientConfig.SHOW_DAY_OVERLAY.set(entry.getValue().enabled());
                case DEATH_LIST -> ClientConfig.SHOW_DEATH_LIST_OVERLAY.set(entry.getValue().enabled());
                case DEATH_SELF -> ClientConfig.SHOW_DEATH_SELF_OVERLAY.set(entry.getValue().enabled());
                case TIME -> ClientConfig.SHOW_TIME_OVERLAY.set(entry.getValue().enabled());
                case COORDS -> ClientConfig.SHOW_COORDS_OVERLAY.set(entry.getValue().enabled());
                case SURVIVAL -> ClientConfig.SHOW_SURVIVAL_OVERLAY.set(entry.getValue().enabled());
                default -> {}
            }
        }
    }

    private void revertSizes() {
        for (var entry : oldStates.entrySet()) {
            switch (entry.getKey()) {
                case DAY -> ClientConfig.DAY_OVERLAY_SIZE.set(entry.getValue().size());
                case DEATH_LIST -> ClientConfig.DEATH_LIST_SIZE.set(entry.getValue().size());
                case DEATH_SELF -> ClientConfig.DEATH_SELF_SIZE.set(entry.getValue().size());
                case TIME -> ClientConfig.TIME_OVERLAY_SIZE.set(entry.getValue().size());
                case COORDS -> ClientConfig.COORDS_OVERLAY_SIZE.set(entry.getValue().size());
                case SURVIVAL -> ClientConfig.SURVIVAL_OVERLAY_SIZE.set(entry.getValue().size());
                default -> {}
            }
        }
    }

    private void revertAlignments() {
        for (var entry : oldStates.entrySet()) {
            switch (entry.getKey()) {
                case DAY -> ClientConfig.DAY_OVERLAY_ALIGN.set(entry.getValue().align());
                case DEATH_LIST -> ClientConfig.DEATH_LIST_ALIGN.set(entry.getValue().align());
                case DEATH_SELF -> ClientConfig.DEATH_SELF_ALIGN.set(entry.getValue().align());
                case TIME -> ClientConfig.TIME_OVERLAY_ALIGN.set(entry.getValue().align());
                case COORDS -> ClientConfig.COORDS_OVERLAY_ALIGN.set(entry.getValue().align());
                case SURVIVAL -> ClientConfig.SURVIVAL_OVERLAY_ALIGN.set(entry.getValue().align());
                default -> {}
            }
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
            if (hitOverlay(mouseX, mouseY, DragTarget.COORDS,
                    ClientConfig.COORDS_OVERLAY_X, ClientConfig.COORDS_OVERLAY_Y,
                    CoordsOverlay::calcCoordsWidth, CoordsOverlay::calcCoordsHeight)) {
                selectedOverlay = DragTarget.COORDS;
                currentDrag = DragTarget.COORDS;
                computeDragOffset(mouseX, mouseY, DragTarget.COORDS,
                        ClientConfig.COORDS_OVERLAY_X, ClientConfig.COORDS_OVERLAY_Y,
                        CoordsOverlay::calcCoordsWidth, CoordsOverlay::calcCoordsHeight);
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.TIME,
                    ClientConfig.TIME_OVERLAY_X, ClientConfig.TIME_OVERLAY_Y,
                    TimeOverlay::calcTimeWidth, TimeOverlay::calcTimeHeight)) {
                selectedOverlay = DragTarget.TIME;
                currentDrag = DragTarget.TIME;
                computeDragOffset(mouseX, mouseY, DragTarget.TIME,
                        ClientConfig.TIME_OVERLAY_X, ClientConfig.TIME_OVERLAY_Y,
                        TimeOverlay::calcTimeWidth, TimeOverlay::calcTimeHeight);
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.SURVIVAL,
                    ClientConfig.SURVIVAL_OVERLAY_X, ClientConfig.SURVIVAL_OVERLAY_Y,
                    SurvivalTimeOverlay::calcSurvivalWidth, SurvivalTimeOverlay::calcSurvivalHeight)) {
                selectedOverlay = DragTarget.SURVIVAL;
                currentDrag = DragTarget.SURVIVAL;
                computeDragOffset(mouseX, mouseY, DragTarget.SURVIVAL,
                        ClientConfig.SURVIVAL_OVERLAY_X, ClientConfig.SURVIVAL_OVERLAY_Y,
                        SurvivalTimeOverlay::calcSurvivalWidth, SurvivalTimeOverlay::calcSurvivalHeight);
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.DEATH_SELF,
                    ClientConfig.DEATH_SELF_X, ClientConfig.DEATH_SELF_Y,
                    DeathCounterOverlay::calcDeathSelfWidth, DeathCounterOverlay::calcDeathSelfHeight)) {
                selectedOverlay = DragTarget.DEATH_SELF;
                currentDrag = DragTarget.DEATH_SELF;
                computeDragOffset(mouseX, mouseY, DragTarget.DEATH_SELF,
                        ClientConfig.DEATH_SELF_X, ClientConfig.DEATH_SELF_Y,
                        DeathCounterOverlay::calcDeathSelfWidth, DeathCounterOverlay::calcDeathSelfHeight);
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.DEATH_LIST,
                    ClientConfig.DEATH_LIST_X, ClientConfig.DEATH_LIST_Y,
                    DeathCounterOverlay::calcDeathListWidth, DeathCounterOverlay::calcDeathListHeight)) {
                selectedOverlay = DragTarget.DEATH_LIST;
                currentDrag = DragTarget.DEATH_LIST;
                computeDragOffset(mouseX, mouseY, DragTarget.DEATH_LIST,
                        ClientConfig.DEATH_LIST_X, ClientConfig.DEATH_LIST_Y,
                        DeathCounterOverlay::calcDeathListWidth, DeathCounterOverlay::calcDeathListHeight);
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.DAY,
                    ClientConfig.DAY_OVERLAY_X, ClientConfig.DAY_OVERLAY_Y,
                    DayCounterOverlay::calcDayWidth, DayCounterOverlay::calcDayHeight)) {
                selectedOverlay = DragTarget.DAY;
                currentDrag = DragTarget.DAY;
                computeDragOffset(mouseX, mouseY, DragTarget.DAY,
                        ClientConfig.DAY_OVERLAY_X, ClientConfig.DAY_OVERLAY_Y,
                        DayCounterOverlay::calcDayWidth, DayCounterOverlay::calcDayHeight);
                return true;
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

            if (currentDrag == DragTarget.DAY) {
                updateOverlayPosition(newPx, newPy, DayCounterOverlay::calcDayWidth, DayCounterOverlay::calcDayHeight, ClientConfig.DAY_OVERLAY_X, ClientConfig.DAY_OVERLAY_Y);
            } else if (currentDrag == DragTarget.DEATH_LIST) {
                updateOverlayPosition(newPx, newPy, DeathCounterOverlay::calcDeathListWidth, DeathCounterOverlay::calcDeathListHeight, ClientConfig.DEATH_LIST_X, ClientConfig.DEATH_LIST_Y);
            } else if (currentDrag == DragTarget.DEATH_SELF) {
                updateOverlayPosition(newPx, newPy, DeathCounterOverlay::calcDeathSelfWidth, DeathCounterOverlay::calcDeathSelfHeight, ClientConfig.DEATH_SELF_X, ClientConfig.DEATH_SELF_Y);
            } else if (currentDrag == DragTarget.TIME) {
                updateOverlayPosition(newPx, newPy, TimeOverlay::calcTimeWidth, TimeOverlay::calcTimeHeight, ClientConfig.TIME_OVERLAY_X, ClientConfig.TIME_OVERLAY_Y);
            } else if (currentDrag == DragTarget.COORDS) {
                updateOverlayPosition(newPx, newPy, CoordsOverlay::calcCoordsWidth, CoordsOverlay::calcCoordsHeight, ClientConfig.COORDS_OVERLAY_X, ClientConfig.COORDS_OVERLAY_Y);
            } else if (currentDrag == DragTarget.SURVIVAL) {
                updateOverlayPosition(newPx, newPy, SurvivalTimeOverlay::calcSurvivalWidth, SurvivalTimeOverlay::calcSurvivalHeight, ClientConfig.SURVIVAL_OVERLAY_X, ClientConfig.SURVIVAL_OVERLAY_Y);
            }
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

    private boolean hitOverlay(double mouseX, double mouseY, DragTarget target, ModConfigSpec.DoubleValue xConfig, ModConfigSpec.DoubleValue yConfig, Supplier<Integer> widthSupplier, Supplier<Integer> heightSupplier) {
        if (!isOverlayAllowedByServer(target)) {
            return false;
        }

        double scale = switch (target) {
            case DAY -> ClientConfig.DAY_OVERLAY_SIZE.get();
            case DEATH_LIST -> ClientConfig.DEATH_LIST_SIZE.get();
            case DEATH_SELF -> ClientConfig.DEATH_SELF_SIZE.get();
            case TIME -> ClientConfig.TIME_OVERLAY_SIZE.get();
            case COORDS -> ClientConfig.COORDS_OVERLAY_SIZE.get();
            case SURVIVAL -> ClientConfig.SURVIVAL_OVERLAY_SIZE.get();
            default -> 1.0;
        };

        int w = (int) (widthSupplier.get() * scale);
        int h = (int) (heightSupplier.get() * scale);

        OverlayAlignment align = switch (target) {
            case DAY -> ClientConfig.DAY_OVERLAY_ALIGN.get();
            case DEATH_LIST -> ClientConfig.DEATH_LIST_ALIGN.get();
            case DEATH_SELF -> ClientConfig.DEATH_SELF_ALIGN.get();
            case TIME -> ClientConfig.TIME_OVERLAY_ALIGN.get();
            case COORDS -> ClientConfig.COORDS_OVERLAY_ALIGN.get();
            case SURVIVAL -> ClientConfig.SURVIVAL_OVERLAY_ALIGN.get();
            default -> OverlayAlignment.LEFT;
        };

        OverlayUtils.Position pos = OverlayUtils.computePosition(
                xConfig.get(), yConfig.get(), w, h, align);
        int px = pos.x();
        int py = pos.y();

        int padding = Math.max(1, (int) Math.ceil(3 * scale));

        return mouseX >= px - padding && mouseX <= px + w + padding
                && mouseY >= py - padding && mouseY <= py + h + padding;
    }

    private void computeDragOffset(double mouseX, double mouseY, DragTarget target,
                                   ModConfigSpec.DoubleValue xConfig, ModConfigSpec.DoubleValue yConfig,
                                   Supplier<Integer> widthSupplier, Supplier<Integer> heightSupplier) {
        double scale = switch (target) {
            case DAY -> ClientConfig.DAY_OVERLAY_SIZE.get();
            case DEATH_LIST -> ClientConfig.DEATH_LIST_SIZE.get();
            case DEATH_SELF -> ClientConfig.DEATH_SELF_SIZE.get();
            case TIME -> ClientConfig.TIME_OVERLAY_SIZE.get();
            case COORDS -> ClientConfig.COORDS_OVERLAY_SIZE.get();
            case SURVIVAL -> ClientConfig.SURVIVAL_OVERLAY_SIZE.get();
            default -> 1.0;
        };

        int w = (int) (widthSupplier.get() * scale);
        int h = (int) (heightSupplier.get() * scale);

        OverlayAlignment align = switch (target) {
            case DAY -> ClientConfig.DAY_OVERLAY_ALIGN.get();
            case DEATH_LIST -> ClientConfig.DEATH_LIST_ALIGN.get();
            case DEATH_SELF -> ClientConfig.DEATH_SELF_ALIGN.get();
            case TIME -> ClientConfig.TIME_OVERLAY_ALIGN.get();
            case COORDS -> ClientConfig.COORDS_OVERLAY_ALIGN.get();
            case SURVIVAL -> ClientConfig.SURVIVAL_OVERLAY_ALIGN.get();
            default -> OverlayAlignment.LEFT;
        };

        OverlayUtils.Position pos = OverlayUtils.computePosition(
                xConfig.get(), yConfig.get(), w, h, align);
        int px = pos.x();
        int py = pos.y();

        dragOffsetX = (int) Mth.clamp(mouseX - px, 0, w);
        dragOffsetY = (int) Mth.clamp(mouseY - py, 0, h);
    }

    private void updateOverlayPosition(int newPx, int newPy, Supplier<Integer> widthSupplier, Supplier<Integer> heightSupplier, ModConfigSpec.DoubleValue xConfig, ModConfigSpec.DoubleValue yConfig) {
        double scale = switch (currentDrag) {
            case DAY -> ClientConfig.DAY_OVERLAY_SIZE.get();
            case DEATH_LIST -> ClientConfig.DEATH_LIST_SIZE.get();
            case DEATH_SELF -> ClientConfig.DEATH_SELF_SIZE.get();
            case TIME -> ClientConfig.TIME_OVERLAY_SIZE.get();
            case COORDS -> ClientConfig.COORDS_OVERLAY_SIZE.get();
            case SURVIVAL -> ClientConfig.SURVIVAL_OVERLAY_SIZE.get();
            default -> 1.0;
        };

        int w = (int) (widthSupplier.get() * scale);
        int h = (int) (heightSupplier.get() * scale);

        OverlayAlignment align = switch (currentDrag) {
            case DAY -> ClientConfig.DAY_OVERLAY_ALIGN.get();
            case DEATH_LIST -> ClientConfig.DEATH_LIST_ALIGN.get();
            case DEATH_SELF -> ClientConfig.DEATH_SELF_ALIGN.get();
            case TIME -> ClientConfig.TIME_OVERLAY_ALIGN.get();
            case COORDS -> ClientConfig.COORDS_OVERLAY_ALIGN.get();
            case SURVIVAL -> ClientConfig.SURVIVAL_OVERLAY_ALIGN.get();
            default -> OverlayAlignment.LEFT;
        };

        int shift = switch (align) {
            case CENTER -> w / 2;
            case RIGHT -> w;
            default -> 0;
        };

        newPx = Mth.clamp(newPx, 0, this.width - w);
        newPy = Mth.clamp(newPy, 0, this.height - h);
        xConfig.set((double) (newPx + shift) / this.width);
        yConfig.set((double) newPy / this.height);
    }

    public void toggleSelectedOverlay() {
        if (!isOverlayAllowedByServer(selectedOverlay)) {
            return;
        }

        switch (selectedOverlay) {
            case DAY -> {
                boolean newState = !ClientConfig.SHOW_DAY_OVERLAY.get();
                ClientConfig.SHOW_DAY_OVERLAY.set(newState);
            }
            case DEATH_LIST -> {
                boolean newState = !ClientConfig.SHOW_DEATH_LIST_OVERLAY.get();
                ClientConfig.SHOW_DEATH_LIST_OVERLAY.set(newState);
            }
            case DEATH_SELF -> {
                boolean newState = !ClientConfig.SHOW_DEATH_SELF_OVERLAY.get();
                ClientConfig.SHOW_DEATH_SELF_OVERLAY.set(newState);
            }
            case TIME -> {
                boolean newState = !ClientConfig.SHOW_TIME_OVERLAY.get();
                ClientConfig.SHOW_TIME_OVERLAY.set(newState);
            }
            case COORDS -> {
                boolean newState = !ClientConfig.SHOW_COORDS_OVERLAY.get();
                ClientConfig.SHOW_COORDS_OVERLAY.set(newState);
            }
            case SURVIVAL -> {
                boolean newState = !ClientConfig.SHOW_SURVIVAL_OVERLAY.get();
                ClientConfig.SHOW_SURVIVAL_OVERLAY.set(newState);
            }
        }
    }

    public void increaseSelectedOverlaySize() {
        if (!isOverlayAllowedByServer(selectedOverlay)) {
            return;
        }

        adjustOverlaySize(selectedOverlay, 0.1);
    }

    public void decreaseSelectedOverlaySize() {
        if (!isOverlayAllowedByServer(selectedOverlay)) {
            return;
        }

        adjustOverlaySize(selectedOverlay, -0.1);
    }

    public void setSelectedOverlayAlignment(OverlayAlignment align) {
        if (!isOverlayAllowedByServer(selectedOverlay)) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();

        ModConfigSpec.DoubleValue xConfig;
        ModConfigSpec.EnumValue<OverlayAlignment> alignConfig;
        Supplier<Integer> widthSupplier;
        double scale;

        switch (selectedOverlay) {
            case DAY -> {
                xConfig = ClientConfig.DAY_OVERLAY_X;
                alignConfig = ClientConfig.DAY_OVERLAY_ALIGN;
                widthSupplier = DayCounterOverlay::calcDayWidth;
                scale = ClientConfig.DAY_OVERLAY_SIZE.get();
            }
            case DEATH_LIST -> {
                xConfig = ClientConfig.DEATH_LIST_X;
                alignConfig = ClientConfig.DEATH_LIST_ALIGN;
                widthSupplier = DeathCounterOverlay::calcDeathListWidth;
                scale = ClientConfig.DEATH_LIST_SIZE.get();
            }
            case DEATH_SELF -> {
                xConfig = ClientConfig.DEATH_SELF_X;
                alignConfig = ClientConfig.DEATH_SELF_ALIGN;
                widthSupplier = DeathCounterOverlay::calcDeathSelfWidth;
                scale = ClientConfig.DEATH_SELF_SIZE.get();
            }
            case TIME -> {
                xConfig = ClientConfig.TIME_OVERLAY_X;
                alignConfig = ClientConfig.TIME_OVERLAY_ALIGN;
                widthSupplier = TimeOverlay::calcTimeWidth;
                scale = ClientConfig.TIME_OVERLAY_SIZE.get();
            }
            case COORDS -> {
                xConfig = ClientConfig.COORDS_OVERLAY_X;
                alignConfig = ClientConfig.COORDS_OVERLAY_ALIGN;
                widthSupplier = CoordsOverlay::calcCoordsWidth;
                scale = ClientConfig.COORDS_OVERLAY_SIZE.get();
            }
            case SURVIVAL -> {
                xConfig = ClientConfig.SURVIVAL_OVERLAY_X;
                alignConfig = ClientConfig.SURVIVAL_OVERLAY_ALIGN;
                widthSupplier = SurvivalTimeOverlay::calcSurvivalWidth;
                scale = ClientConfig.SURVIVAL_OVERLAY_SIZE.get();
            }
            default -> {
                return;
            }
        }

        OverlayAlignment oldAlign = alignConfig.get();
        if (oldAlign == align) {
            return;
        }

        int width = (int) (widthSupplier.get() * scale);
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
        switch (target) {
            case DAY -> {
                double current = ClientConfig.DAY_OVERLAY_SIZE.get();
                ClientConfig.DAY_OVERLAY_SIZE.set(Mth.clamp(current + delta, 0.1, 5.0));
            }
            case DEATH_LIST -> {
                double current = ClientConfig.DEATH_LIST_SIZE.get();
                ClientConfig.DEATH_LIST_SIZE.set(Mth.clamp(current + delta, 0.1, 5.0));
            }
            case DEATH_SELF -> {
                double current = ClientConfig.DEATH_SELF_SIZE.get();
                ClientConfig.DEATH_SELF_SIZE.set(Mth.clamp(current + delta, 0.1, 5.0));
            }
            case TIME -> {
                double current = ClientConfig.TIME_OVERLAY_SIZE.get();
                ClientConfig.TIME_OVERLAY_SIZE.set(Mth.clamp(current + delta, 0.1, 5.0));
            }
            case COORDS -> {
                double current = ClientConfig.COORDS_OVERLAY_SIZE.get();
                ClientConfig.COORDS_OVERLAY_SIZE.set(Mth.clamp(current + delta, 0.1, 5.0));
            }
            case SURVIVAL -> {
                double current = ClientConfig.SURVIVAL_OVERLAY_SIZE.get();
                ClientConfig.SURVIVAL_OVERLAY_SIZE.set(Mth.clamp(current + delta, 0.1, 5.0));
            }
        }
    }

    private boolean isOverlayAllowedByServer(DragTarget target) {
        return switch (target) {
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