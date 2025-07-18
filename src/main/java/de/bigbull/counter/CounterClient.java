package de.bigbull.counter;

import de.bigbull.counter.event.ClientEvents;
import de.bigbull.counter.network.ClientNetworkHandler;
import de.bigbull.counter.util.ModKeybinds;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.client.ConfigScreenFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public class CounterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientEvents.registerEvents();
        ClientNetworkHandler.register();

        KeyBindingHelper.registerKeyBinding(ModKeybinds.OPEN_EDIT_GUI);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.SHOW_OVERLAYS);

        ConfigScreenFactoryRegistry.INSTANCE.register("counter", ConfigurationScreen::new);
    }
}
