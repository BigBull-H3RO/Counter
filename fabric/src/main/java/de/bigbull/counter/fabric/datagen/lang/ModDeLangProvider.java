package de.bigbull.counter.fabric.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModDeLangProvider extends FabricLanguageProvider {
    public ModDeLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "de_de", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add("overlay.counter.day_with_emoji", "🌞 Tag: %s");
        translationBuilder.add("overlay.counter.day_no_emoji", "Tag: %s");
        translationBuilder.add("command.daycounter.set", "🌞 Tageszähler auf %s gesetzt!");
        translationBuilder.add("chat.daycounter.new_day", "🌞 Es ist jetzt Tag %s!");
        translationBuilder.add("combined.daytime_with_emoji", "🌞 Tag: %s, %s");
        translationBuilder.add("combined.daytime_no_emoji", "Tag: %s, %s");

        translationBuilder.add("overlay.counter.deaths_with_emoji", "💀 Tode: %s");
        translationBuilder.add("overlay.counter.deaths_no_emoji", "Tode: %s");
        translationBuilder.add("chat.deathcounter.player_death.singular", "💀 %s ist %s mal gestorben!");
        translationBuilder.add("chat.deathcounter.player_death.plural", "💀 %s ist %s mal gestorben!");
        translationBuilder.add("overlay.counter.deathlist", "💀 Todeszähler:");
        translationBuilder.add("overlay.counter.deathlist.entry.full", "%s %s");
        translationBuilder.add("overlay.counter.deathlist.entry.singular", "%s: %s Tod");
        translationBuilder.add("overlay.counter.deathlist.entry.plural", "%s: %s Tode");
        translationBuilder.add("command.deathcounter.get", "💀 %s ist %s mal gestorben!");
        translationBuilder.add("command.deathcounter.reset", "💀 Todeszähler wurde zurückgesetzt!");
        translationBuilder.add("command.deathcounter.set", "💀 Setze die Tode von %s auf %s!");

        translationBuilder.add("command.coords.broadcast", "%s hat seine Koordinaten geteilt: X:%s, Y:%s, Z:%s");
        translationBuilder.add("command.coords.player_not_found", "❌ Spieler nicht gefunden!");

        translationBuilder.add("key.counter.edit_mode", "Bearbeitungsmodus");
        translationBuilder.add("key.categories.counter", "Zähler");
    }
}
