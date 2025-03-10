package de.bigbull.counter.util.gui;

import de.bigbull.counter.config.ClientConfig;
import de.bigbull.counter.config.ServerConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.function.Supplier;

public class OverlayEditScreen extends Screen {
    public enum DragTarget { NONE, DAY, DEATH_LIST, DEATH_SELF }
    private DragTarget selectedOverlay = DragTarget.NONE;
    private DragTarget currentDrag = DragTarget.NONE;
    private int dragOffsetX = 0, dragOffsetY = 0;

    private double oldDayFracX, oldDayFracY;
    private double oldListFracX, oldListFracY;
    private double oldSelfFracX, oldSelfFracY;

    private boolean doneClicked = false;
    private double previousChatBackgroundOpacity;
    private double previousChatOpacity;

    private boolean oldDayOverlayState;
    private boolean oldDeathListOverlayState;
    private boolean oldDeathSelfOverlayState;

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

        oldDayOverlayState = ClientConfig.SHOW_DAY_OVERLAY.get();
        oldDeathListOverlayState = ClientConfig.SHOW_DEATH_LIST_OVERLAY.get();
        oldDeathSelfOverlayState = ClientConfig.SHOW_DEATH_SELF_OVERLAY.get();

        oldDayFracX = ClientConfig.DAY_OVERLAY_X.get();
        oldDayFracY = ClientConfig.DAY_OVERLAY_Y.get();
        oldListFracX = ClientConfig.DEATH_LIST_X.get();
        oldListFracY = ClientConfig.DEATH_LIST_Y.get();
        oldSelfFracX = ClientConfig.DEATH_SELF_X.get();
        oldSelfFracY = ClientConfig.DEATH_SELF_Y.get();

        int centerX = this.width / 2;
        int bottomY = this.height - 80;

        this.addRenderableWidget(
                Button.builder(Component.literal("Done"), b -> {
                    doneClicked = true;
                    ClientConfig.CLIENT_SPEC.save();
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
        ClientConfig.DAY_OVERLAY_X.set(oldDayFracX);
        ClientConfig.DAY_OVERLAY_Y.set(oldDayFracY);
        ClientConfig.DEATH_LIST_X.set(oldListFracX);
        ClientConfig.DEATH_LIST_Y.set(oldListFracY);
        ClientConfig.DEATH_SELF_X.set(oldSelfFracX);
        ClientConfig.DEATH_SELF_Y.set(oldSelfFracY);
    }

    private void revertOverlayStates() {
        ClientConfig.SHOW_DAY_OVERLAY.set(oldDayOverlayState);
        ClientConfig.SHOW_DEATH_LIST_OVERLAY.set(oldDeathListOverlayState);
        ClientConfig.SHOW_DEATH_SELF_OVERLAY.set(oldDeathSelfOverlayState);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        DayCounterOverlay.render(guiGraphics);
        DeathCounterOverlay.render(guiGraphics);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (hitOverlay(mouseX, mouseY, DragTarget.DAY,
                    ClientConfig.DAY_OVERLAY_X, ClientConfig.DAY_OVERLAY_Y,
                    DayCounterOverlay::calcDayWidth, DayCounterOverlay::calcDayHeight)) {
                selectedOverlay = DragTarget.DAY;
                currentDrag = DragTarget.DAY;
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.DEATH_LIST,
                    ClientConfig.DEATH_LIST_X, ClientConfig.DEATH_LIST_Y,
                    DeathCounterOverlay::calcDeathListWidth, DeathCounterOverlay::calcDeathListHeight)) {
                selectedOverlay = DragTarget.DEATH_LIST;
                currentDrag = DragTarget.DEATH_LIST;
                return true;
            }
            if (hitOverlay(mouseX, mouseY, DragTarget.DEATH_SELF,
                    ClientConfig.DEATH_SELF_X, ClientConfig.DEATH_SELF_Y,
                    DeathCounterOverlay::calcDeathSelfWidth, DeathCounterOverlay::calcDeathSelfHeight)) {
                selectedOverlay = DragTarget.DEATH_SELF;
                currentDrag = DragTarget.DEATH_SELF;
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
                updateOverlayPosition(newPx, newPy, DayCounterOverlay::calcDayWidth, DayCounterOverlay::calcDayHeight, ClientConfig.DAY_OVERLAY_X, ClientConfig.DAY_OVERLAY_Y);
            } else if (currentDrag == DragTarget.DEATH_LIST) {
                updateOverlayPosition(newPx, newPy, DeathCounterOverlay::calcDeathListWidth, DeathCounterOverlay::calcDeathListHeight, ClientConfig.DEATH_LIST_X, ClientConfig.DEATH_LIST_Y);
            } else if (currentDrag == DragTarget.DEATH_SELF) {
                updateOverlayPosition(newPx, newPy, DeathCounterOverlay::calcDeathSelfWidth, DeathCounterOverlay::calcDeathSelfHeight, ClientConfig.DEATH_SELF_X, ClientConfig.DEATH_SELF_Y);
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

    private void updateOverlayPosition(int newPx, int newPy, Supplier<Integer> widthSupplier, Supplier<Integer> heightSupplier, ModConfigSpec.DoubleValue xConfig, ModConfigSpec.DoubleValue yConfig) {
        int w = widthSupplier.get();
        int h = heightSupplier.get();
        newPx = Mth.clamp(newPx, 0, this.width - w);
        newPy = Mth.clamp(newPy, 0, this.height - h);
        xConfig.set((double) newPx / this.width);
        yConfig.set((double) newPy / this.height);
    }

    public void toggleSelectedOverlay() {
        if (isOverlayAllowedByServer(selectedOverlay)) {
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
        }
    }

    private boolean isOverlayAllowedByServer(DragTarget target) {
        return !switch (target) {
            case DAY -> ServerConfig.SHOW_DAY_OVERLAY.get() && ServerConfig.ENABLE_DAY_COUNTER.get();
            case DEATH_LIST, DEATH_SELF ->
                    ServerConfig.SHOW_DEATH_OVERLAY.get() && ServerConfig.ENABLE_DEATH_COUNTER.get();
            default -> false;
        };
    }

    public DragTarget getSelectedOverlay() {
        return selectedOverlay;
    }

    @Override
    protected void renderBlurredBackground() {}
}