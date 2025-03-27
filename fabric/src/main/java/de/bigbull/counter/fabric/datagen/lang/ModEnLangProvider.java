package de.bigbull.counter.fabric.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModEnLangProvider extends FabricLanguageProvider {
    public ModEnLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add("overlay.counter.day_with_emoji", "🌞 Day: %s");
        translationBuilder.add("overlay.counter.day_no_emoji", "Day: %s");
        translationBuilder.add("command.daycounter.reset", "🌞 Day Counter has been reset to the current Minecraft day!");
        translationBuilder.add("command.daycounter.set", "🌞 Day Counter set to %s!");
        translationBuilder.add("chat.daycounter.new_day", "🌞 It's now day %s!");
        translationBuilder.add("combined.daytime_with_emoji", "🌞 Day: %s, %s");
        translationBuilder.add("combined.daytime_no_emoji", "Day: %s, %s");

        translationBuilder.add("overlay.counter.deaths_with_emoji", "💀 Deaths: %s");
        translationBuilder.add("overlay.counter.deaths_no_emoji", "Deaths: %s");
        translationBuilder.add("chat.deathcounter.player_death.singular", "💀 %s has died once!");
        translationBuilder.add("chat.deathcounter.player_death.plural", "💀 %s has died %s times!");
        translationBuilder.add("overlay.counter.deathlist", "💀 Death Counter:");
        translationBuilder.add("overlay.counter.deathlist.entry.full", "%s %s");
        translationBuilder.add("overlay.counter.deathlist.entry.singular", "%s: %s death");
        translationBuilder.add("overlay.counter.deathlist.entry.plural", "%s: %s deaths");
        translationBuilder.add("command.deathcounter.get", "💀 %s has died %s time!");
        translationBuilder.add("command.deathcounter.reset", "💀 Death Counter has been reset!");
        translationBuilder.add("command.deathcounter.set", "💀 Set the death count of %s to %s!");

        translationBuilder.add("command.coords.broadcast", "%s has shared their coordinates: X:%s, Y:%s, Z:%s");
        translationBuilder.add("command.coords.player_not_found", "❌ Player not found!");

        translationBuilder.add("key.counter.edit_mode", "Edit Mode");
        translationBuilder.add("key.categories.counter", "Counter");
    }
}
