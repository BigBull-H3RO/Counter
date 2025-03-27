package de.bigbull.counter.common.util.gui;

import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.config.IClientConfig;
import de.bigbull.counter.common.config.IServerConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class OverlayEditScreen extends Screen {
    IClientConfig client = ConfigHelper.get().getClient();
    IServerConfig server = ConfigHelper.get().getServer();

    public enum DragTarget { NONE, DAY, DEATH_LIST, DEATH_SELF, TIME, COORDS }
    private DragTarget selectedOverlay = DragTarget.NONE;
    private DragTarget currentDrag = DragTarget.NONE;
    private int dragOffsetX = 0, dragOffsetY = 0;

    private double oldDayFracX, oldDayFracY;
    private double oldListFracX, oldListFracY;
    private double oldSelfFracX, oldSelfFracY;
    private double oldTimeFracX, oldTimeFracY;
    private double oldCoordsFracX, oldCoordsFracY;

    private boolean doneClicked = false;
    private double previousChatBackgroundOpacity;
    private double previousChatOpacity;

    private boolean oldDayOverlayState;
    private boolean oldDeathListOverlayState;
    private boolean oldDeathSelfOverlayState;
    private boolean oldTimeOverlayState;
    private boolean oldCoordsOverlayState;

    public OverlayEditScreen() {
        super(Component.translatable("screen.overlay_edit"));
    }

    @Override
    protected void init() {
        Minecraft mc = Minecraft.getInstance();

        previousChatOpacity = mc.options.chatOpacity().get();
        previousChatBackgroundOpacity = mc.options.textBackgroundOpacity().get();
        mc.options.chatOpacity().set(0.0);
        mc.options.textBackgroundOpacity().set(0.0);

        oldDayOverlayState = client.showDayOverlay();
        oldDeathListOverlayState = client.showDeathListOverlay();
        oldDeathSelfOverlayState = client.showDeathSelfOverlay();
        oldTimeOverlayState = client.showTimeOverlay();
        oldCoordsOverlayState = client.showCoordsOverlay();

        oldDayFracX = client.dayOverlayX();
        oldDayFracY = client.dayOverlayY();
        oldListFracX = client.deathListOverlayX();
        oldListFracY = client.deathListOverlayY();
        oldSelfFracX = client.deathSelfOverlayX();
        oldSelfFracY = client.deathSelfOverlayY();
        oldTimeFracX = client.timeOverlayX();
        oldTimeFracY = client.timeOverlayY();
        oldCoordsFracX = client.coordsOverlayX();
        oldCoordsFracY = client.coordsOverlayY();

        int centerX = this.width / 2;
        int bottomY = this.height - 80;

        this.addRenderableWidget(
                Button.builder(Component.literal("Done"), b -> {
                    doneClicked = true;
                    client.save();
                    onClose();
                }).bounds(centerX - 110, bottomY, 100, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("Cancel"), b -> {
                    revertPositions();
                    onClose();
                }).bounds(centerX + 10, bottomY, 100, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("Toggle Overlay"), b -> {
                    toggleSelectedOverlay();
                }).bounds(centerX - 50, bottomY - 30, 100, 20).build()
        );

        super.init();
    }

    @Override
    public void onClose() {
        if (!doneClicked) {
            revertPositions();
            revertOverlayStates();
        }

        Minecraft mc = Minecraft.getInstance();
        mc.options.chatOpacity().set(previousChatOpacity);
        mc.options.textBackgroundOpacity().set(previousChatBackgroundOpacity);

        super.onClose();
    }

    private void revertPositions() {
        client.setDayOverlayX(oldDayFracX);
        client.setDayOverlayY(oldDayFracY);
        client.setDeathListOverlayX(oldListFracX);
        client.setDeathListOverlayY(oldListFracY);
        client.setDeathSelfOverlayX(oldSelfFracX);
        client.setDeathSelfOverlayY(oldSelfFracY);
        client.setTimeOverlayX(oldTimeFracX);
        client.setTimeOverlayY(oldTimeFracY);
        client.setCoordsOverlayX(oldCoordsFracX);
        client.setCoordsOverlayY(oldCoordsFracY);
    }

    private void revertOverlayStates() {
        client.setShowDayOverlay(oldDayOverlayState);
        client.setShowDeathListOverlay(oldDeathListOverlayState);
        client.setShowDeathSelfOverlay(oldDeathSelfOverlayState);
        client.setShowTimeOverlay(oldTimeOverlayState);
        client.setShowCoordsOverlay(oldCoordsOverlayState);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        DayCounterOverlay.render(guiGraphics);
        DeathCounterOverlay.render(guiGraphics);
        TimeOverlay.render(guiGraphics);
        CoordsOverlay.render(guiGraphics);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (hitOverlay(mouseX, mouseY, DragTarget.DAY,
                    () -> client.dayOverlayX(), () -> client.dayOverlayY(),
                    DayCounterOverlay::calcDayWidth, DayCounterOverlay::calcDayHeight)) {
                selectedOverlay = DragTarget.DAY;
                currentDrag = DragTarget.DAY;
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.DEATH_LIST,
                    () -> client.deathListOverlayX(), () -> client.deathListOverlayY(),
                    DeathCounterOverlay::calcDeathListWidth, DeathCounterOverlay::calcDeathListHeight)) {
                selectedOverlay = DragTarget.DEATH_LIST;
                currentDrag = DragTarget.DEATH_LIST;
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.DEATH_SELF,
                    () -> client.deathSelfOverlayX(), () -> client.deathSelfOverlayY(),
                    DeathCounterOverlay::calcDeathSelfWidth, DeathCounterOverlay::calcDeathSelfHeight)) {
                selectedOverlay = DragTarget.DEATH_SELF;
                currentDrag = DragTarget.DEATH_SELF;
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.TIME,
                    () -> client.timeOverlayX(), () -> client.timeOverlayY(),
                    TimeOverlay::calcTimeWidth, TimeOverlay::calcTimeHeight)) {
                selectedOverlay = DragTarget.TIME;
                currentDrag = DragTarget.TIME;
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.COORDS,
                    () -> client.coordsOverlayX(), () -> client.coordsOverlayY(),
                    CoordsOverlay::calcCoordsWidth, CoordsOverlay::calcCoordsHeight)) {
                selectedOverlay = DragTarget.COORDS;
                currentDrag = DragTarget.COORDS;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (button == 0 && currentDrag != DragTarget.NONE) {
            int newPx = (int) (mouseX - dragOffsetX);
            int newPy = (int) (mouseY - dragOffsetY);

            if (currentDrag == DragTarget.DAY) {
                updateOverlayPosition(newPx, newPy, DayCounterOverlay::calcDayWidth, DayCounterOverlay::calcDayHeight, client::setDayOverlayX, client::setDayOverlayY);
            } else if (currentDrag == DragTarget.DEATH_LIST) {
                updateOverlayPosition(newPx, newPy, DeathCounterOverlay::calcDeathListWidth, DeathCounterOverlay::calcDeathListHeight, client::setDeathListOverlayX, client::setDeathListOverlayY);
            } else if (currentDrag == DragTarget.DEATH_SELF) {
                updateOverlayPosition(newPx, newPy, DeathCounterOverlay::calcDeathSelfWidth, DeathCounterOverlay::calcDeathSelfHeight, client::setDeathSelfOverlayX, client::setDeathSelfOverlayY);
            } else if (currentDrag == DragTarget.TIME) {
                updateOverlayPosition(newPx, newPy, TimeOverlay::calcTimeWidth, TimeOverlay::calcTimeHeight, client::setTimeOverlayX, client::setTimeOverlayY);
            } else if (currentDrag == DragTarget.COORDS) {
                updateOverlayPosition(newPx, newPy, CoordsOverlay::calcCoordsWidth, CoordsOverlay::calcCoordsHeight, client::setCoordsOverlayX, client::setCoordsOverlayY);
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

    private boolean hitOverlay(double mouseX, double mouseY, DragTarget target, Supplier<Double> xConfig, Supplier<Double> yConfig, Supplier<Integer> widthSupplier, Supplier<Integer> heightSupplier) {
        if (isOverlayAllowedByServer(target)) {
            return false;
        }

        int px = (int) (xConfig.get() * this.width);
        int py = (int) (yConfig.get() * this.height);
        int w = widthSupplier.get();
        int h = heightSupplier.get();

        if (mouseX >= px && mouseX <= px + w && mouseY >= py && mouseY <= py + h) {
            currentDrag = target;
            dragOffsetX = (int) (mouseX - px);
            dragOffsetY = (int) (mouseY - py);
            return true;
        }
        return false;
    }

    private void updateOverlayPosition(int newPx, int newPy, Supplier<Integer> widthSupplier, Supplier<Integer> heightSupplier, Consumer<Double> xConfig, Consumer<Double> yConfig) {
        int w = widthSupplier.get();
        int h = heightSupplier.get();
        newPx = Mth.clamp(newPx, 0, this.width - w);
        newPy = Mth.clamp(newPy, 0, this.height - h);
        double newXFrac = (double) newPx / this.width;
        double newYFrac = (double) newPy / this.height;
        xConfig.accept(newXFrac);
        yConfig.accept(newYFrac);
    }

    public void toggleSelectedOverlay() {
        if (isOverlayAllowedByServer(selectedOverlay)) {
            return;
        }

        switch (selectedOverlay) {
            case DAY -> {
                boolean newState = !client.showDayOverlay();
                client.setShowDayOverlay(newState);
            }
            case DEATH_LIST -> {
                boolean newState = !client.showDeathListOverlay();
                client.setShowDeathListOverlay(newState);
            }
            case DEATH_SELF -> {
                boolean newState = !client.showDeathSelfOverlay();
                client.setShowDeathSelfOverlay(newState);
            }
            case TIME -> {
                boolean newState = !client.showTimeOverlay();
                client.setShowTimeOverlay(newState);
            }
            case COORDS -> {
                boolean newState = !client.showCoordsOverlay();
                client.setShowCoordsOverlay(newState);
            }
        }
    }

    private boolean isOverlayAllowedByServer(DragTarget target) {
        return !switch (target) {
            case DAY -> server.showDayOverlay() && server.enabledDayCounter();
            case DEATH_LIST, DEATH_SELF ->
                    server.showDeathOverlay() && server.enableDeathCounter();
            case TIME -> server.showTimeOverlay() && server.enableTimeCounter();
            case COORDS -> server.showCoordsOverlay() && server.enableCoordsCounter();
            default -> false;
        };
    }

    public DragTarget getSelectedOverlay() {
        return selectedOverlay;
    }

    @Override
    protected void renderBlurredBackground() {
    }

}