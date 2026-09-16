package de.bigbull.counter.util;

import com.mojang.blaze3d.platform.InputConstants;
import de.bigbull.counter.Counter;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class ModKeybinds {
    public static final KeyMapping.Category COUNTER_CATEGORY = new KeyMapping.Category(
            Identifier.fromNamespaceAndPath(Counter.MODID, "main")
    );

    public static final KeyMapping OPEN_EDIT_GUI = new KeyMapping(
            "key.counter.edit_mode",
            InputConstants.KEY_U,
            COUNTER_CATEGORY
    );

    public static final KeyMapping SHOW_OVERLAYS = new KeyMapping(
            "key.counter.show_overlays",
            InputConstants.KEY_TAB,
            COUNTER_CATEGORY
    );
}
