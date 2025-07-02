package de.bigbull.counter.util;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final KeyMapping OPEN_EDIT_GUI = new KeyMapping(
            "key.counter.edit_mode",
            GLFW.GLFW_KEY_O,
            "key.categories.counter"
    );

    public static final KeyMapping SHOW_OVERLAYS = new KeyMapping(
            "key.counter.show_overlays",
            GLFW.GLFW_KEY_TAB,
            "key.categories.counter"
    );
}
