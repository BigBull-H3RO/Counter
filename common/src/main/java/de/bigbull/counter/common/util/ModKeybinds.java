package de.bigbull.counter.common.util;

import de.bigbull.counter.common.util.gui.OverlayEditScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModKeybinds {
    private static final List<KeyMapping> KEYBINDS = new ArrayList<>();

    public static final KeyMapping OPEN_EDIT_GUI = createKeybind(
            "key.counter.edit_mode",
            GLFW.GLFW_KEY_O,
            "key.categories.counter"
    );

    private static KeyMapping createKeybind(String translationKey, int key, String category) {
        KeyMapping keybind = new KeyMapping(translationKey, key, category);
        KEYBINDS.add(keybind);
        return keybind;
    }

    public static void register(Consumer<KeyMapping> registerFunction) {
        KEYBINDS.forEach(registerFunction);
    }

    public static void handleKeyInputs() {
        Minecraft mc = Minecraft.getInstance();

        if (ModKeybinds.OPEN_EDIT_GUI.isDown()) {
            mc.setScreen(new OverlayEditScreen());
        }
    }
}
