package de.bigbull.counter.forge.datagen.lang;

import de.bigbull.counter.common.Counter;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnLangProvider extends LanguageProvider {
    public ModEnLangProvider(PackOutput output) {
        super(output, Counter.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("overlay.counter.day_with_emoji", "🌞 Day: %s");
        add("overlay.counter.day_no_emoji", "Day: %s");
        add("command.daycounter.reset", "🌞 Day Counter has been reset to the current Minecraft day!");
        add("command.daycounter.set", "🌞 Day Counter set to %s!");
        add("chat.daycounter.new_day", "🌞 It's now day %s!");
        add("combined.daytime_with_emoji", "🌞 Day: %s, %s");
        add("combined.daytime_no_emoji", "Day: %s, %s");

        add("overlay.counter.deaths_with_emoji", "💀 Deaths: %s");
        add("overlay.counter.deaths_no_emoji", "Deaths: %s");
        add("chat.deathcounter.player_death.singular", "💀 %s has died once!");
        add("chat.deathcounter.player_death.plural", "💀 %s has died %s times!");
        add("overlay.counter.deathlist", "💀 Death Counter:");
        add("overlay.counter.deathlist.entry.full", "%s %s");
        add("overlay.counter.deathlist.entry.singular", "%s: %s death");
        add("overlay.counter.deathlist.entry.plural", "%s: %s deaths");
        add("command.deathcounter.get", "💀 %s has died %s time!");
        add("command.deathcounter.reset", "💀 Death Counter has been reset!");
        add("command.deathcounter.set", "💀 Set the death count of %s to %s!");

        add("command.coords.broadcast", "%s has shared their coordinates: X:%s, Y:%s, Z:%s");
        add("command.coords.player_not_found", "❌ Player not found!");

        add("key.counter.edit_mode", "Edit Mode");
        add("key.categories.counter", "Counter");
    }
}