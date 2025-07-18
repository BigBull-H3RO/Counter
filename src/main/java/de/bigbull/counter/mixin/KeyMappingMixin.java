package de.bigbull.counter.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import de.bigbull.counter.util.ModKeybinds;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin {
    @Inject(method = "set(Lcom/mojang/blaze3d/platform/InputConstants$Key;Z)V", at = @At("HEAD"))
    private static void counter$onKeySet(InputConstants.Key key, boolean held, CallbackInfo ci) {
        // Zugriff auf dynamische Tastenbelegung beider KeyBinds
        InputConstants.Key overlayKey = ((KeyMappingAccessor) ModKeybinds.SHOW_OVERLAYS).getBoundKey();

        // Wenn die Overlay-Taste gedrückt oder losgelassen wurde
        if (key.equals(overlayKey)) {
            Minecraft mc = Minecraft.getInstance();

            for (KeyMapping mapping : mc.options.keyMappings) {
                if ("key.playerlist".equals(mapping.getName())) {
                    InputConstants.Key playerListKey = ((KeyMappingAccessor) mapping).getBoundKey();

                    // Setze nur dann den Zustand der PlayerList-Taste, wenn der Spieler sie auch tatsächlich drückt
                    boolean isPlayerListKeyPressed = InputConstants.isKeyDown(mc.getWindow().getWindow(), playerListKey.getValue());
                    mapping.setDown(held && isPlayerListKeyPressed);
                }
            }
        }
    }
}

