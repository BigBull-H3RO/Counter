package de.bigbull.counter.util;

import de.bigbull.counter.Counter;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final KeyMapping.Category COUNTER_CATEGORY = new KeyMapping.Category(
            ResourceLocation.fromNamespaceAndPath(Counter.MODID, "main")
    );

    public static final KeyMapping OPEN_EDIT_GUI = new KeyMapping(
            "key.counter.edit_mode",
            GLFW.GLFW_KEY_O,
            COUNTER_CATEGORY
    );

    public static final KeyMapping SHOW_OVERLAYS = new KeyMapping(
            "key.counter.show_overlays",
            GLFW.GLFW_KEY_TAB,
            COUNTER_CATEGORY
    );
}
