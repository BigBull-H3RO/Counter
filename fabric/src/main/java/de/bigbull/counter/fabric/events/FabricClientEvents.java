package de.bigbull.counter.fabric.events;

import de.bigbull.counter.common.Events.ClientEvents;
import de.bigbull.counter.common.util.ModKeybinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class FabricClientEvents {
    public static void registerEvents() {
        HudRenderCallback.EVENT.register(((guiGraphics, deltaTracker) -> {
            ClientEvents.onRenderGui(guiGraphics);
        }));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientEvents.onClientTick();
        });

        ModKeybinds.register(KeyBindingHelper::registerKeyBinding);
    }
}
