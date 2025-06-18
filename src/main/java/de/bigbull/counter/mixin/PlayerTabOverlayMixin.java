package de.bigbull.counter.mixin;

import de.bigbull.counter.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
public abstract class PlayerTabOverlayMixin {
    @ModifyConstant(
            method = "render(Lnet/minecraft/client/gui/GuiGraphics;ILnet/minecraft/world/scores/Scoreboard;Lnet/minecraft/world/scores/Objective;)V",
            constant = @Constant(intValue = 13),
            require = 1
    )
    private int modifyPingSlotWidth(int original) {
        if (!ClientConfig.SHOW_PING_AS_TEXT.get()) {
            return original;
        }
        return original + 25;
    }

    @Inject(method = "renderPingIcon", at = @At("HEAD"), cancellable = true)
    private void onRenderPingIcon(
            GuiGraphics guiGraphics,
            int width,
            int x,
            int y,
            PlayerInfo playerInfo,
            CallbackInfo ci
    ) {
        if (!ClientConfig.SHOW_PING_AS_TEXT.get()) {
            return;
        }

        ci.cancel();

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0, 0);

        int ping = playerInfo.getLatency();
        if (ping < 0) ping = 0;

        String pingText = ping + "ms";

        int color;
        if (ping < 100) {
            color = ClientConfig.PING_COLOR_GOOD.get();
        } else if (ping < 250) {
            color = ClientConfig.PING_COLOR_MEDIUM.get();
        } else {
            color = ClientConfig.PING_COLOR_BAD.get();
        }

        var font = Minecraft.getInstance().font;
        int textWidth = font.width(pingText);
        int textX = (x + width) - textWidth;

        guiGraphics.drawString(font, Component.literal(pingText), textX, y, color);

        guiGraphics.pose().popMatrix();
    }
}