package de.bigbull.counter.common.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import de.bigbull.counter.common.config.ConfigHelper;
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
        if (!ConfigHelper.get().getClient().showPingAsText()) {
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
        if (!ConfigHelper.get().getClient().showPingAsText()) {
            return;
        }

        ci.cancel();

        RenderSystem.enableBlend();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 100);

        int ping = playerInfo.getLatency();
        if (ping < 0) ping = 0;

        String pingText = ping + "ms";

        int color;
        if (ping < 100) {
            color = ConfigHelper.get().getClient().pingColorGood();
        } else if (ping < 250) {
            color = ConfigHelper.get().getClient().pingColorMedium();
        } else {
            color = ConfigHelper.get().getClient().pingColorBad();
        }

        var font = Minecraft.getInstance().font;
        int textWidth = font.width(pingText);
        int textX = (x + width) - textWidth;

        guiGraphics.drawString(font, Component.literal(pingText), textX, y, color);

        guiGraphics.pose().popPose();
        RenderSystem.disableBlend();
    }
}