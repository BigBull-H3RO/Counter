package de.bigbull.data.lang;

import de.bigbull.Counter;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnLangProvider extends LanguageProvider {
    public ModEnLangProvider(PackOutput output) {
        super(output, Counter.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("overlay.counter.day", "🌞 Day: %s");
        add("command.daycounter.reset", "🌞 Day Counter has been reset to the real world day!");
        add("command.daycounter.set", "🌞 Day Counter set to %s!");
        add("command.daycounter.show", "🌞 Overlay enabled!");
        add("command.daycounter.hide", "🌑 Overlay disabled!");
        add("command.daycounter.error", "❌ The Overlay is globally disabled!");
        add("chat.daycounter.new_day", "🌞 It's now day %s!");

        add("overlay.counter.deaths", "💀 Deaths: %s");
        add("chat.deathcounter.player_death.singular", "💀 %s has died %s time!");
        add("chat.deathcounter.player_death.plural", "💀 %s has died %s times!");
        add("overlay.counter.deathlist", "💀 Death Counter:");
        add("overlay.counter.deathlist.entry.singular", "%s. %s: %s death");
        add("overlay.counter.deathlist.entry.plural", "%s. %s: %s deaths");
        add("command.deathcounter.get", "💀 %s has died %s time!");
        add("command.deathcounter.reset", "💀 Death Counter has been reset!");
        add("command.deathcounter.set", "💀 Set %s's deaths to %s!");
        add("command.deathcounter.error", "❌ The Death Counter is globally disabled!");
        add("command.deathcounter.show", "💀 Overlay enabled!");
        add("command.deathcounter.hide", "🌑 Overlay disabled!");

        add("key.counter.edit_mode", "Edit Mode");
        add("key.categories.counter", "Counter");
    }
}