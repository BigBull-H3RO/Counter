package de.bigbull.util;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final KeyMapping OPEN_EDIT_GUI = new KeyMapping(
            "key.counter.edit_mode",
            GLFW.GLFW_KEY_O,
            "key.categories.counter"
    );
}
