package de.bigbull.counter.data.lang;

import de.bigbull.counter.Counter;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModDeLangProvider extends LanguageProvider {
    public ModDeLangProvider(PackOutput output) {
        super(output, Counter.MODID, "de_de");
    }

    @Override
    protected void addTranslations() {
        add("overlay.counter.day_with_emoji", "🌞 Tag: %s");
        add("overlay.counter.day_no_emoji", "Tag: %s");
        add("command.daycounter.set", "🌞 Tageszähler auf %s gesetzt!");
        add("chat.daycounter.new_day", "🌞 Es ist jetzt Tag %s!");
        add("combined.daytime_with_emoji", "🌞 Tag: %s, %s");
        add("combined.daytime_no_emoji", "Tag: %s, %s");

        add("overlay.counter.deaths_with_emoji", "💀 Tode: %s");
        add("overlay.counter.deaths_no_emoji", "Tode: %s");
        add("chat.deathcounter.player_death.singular", "💀 %s ist %s mal gestorben!");
        add("chat.deathcounter.player_death.plural", "💀 %s ist %s mal gestorben!");
        add("overlay.counter.deathlist", "💀 Todeszähler:");
        add("overlay.counter.deathlist.entry.full", "%s %s");
        add("overlay.counter.deathlist.entry.singular", "%s: %s Tod");
        add("overlay.counter.deathlist.entry.plural", "%s: %s Tode");
        add("command.deathcounter.get", "💀 %s ist %s mal gestorben!");
        add("command.deathcounter.reset", "💀 Todeszähler wurde zurückgesetzt!");
        add("command.deathcounter.set", "💀 Setze die Tode von %s auf %s!");

        add("overlay.counter.survival_with_emoji", "⏳ Überlebt: %s");
        add("overlay.counter.survival_no_emoji", "Überlebt: %s");
        add("overlay.counter.best_survival_with_emoji", "⏳ Beste: %s");
        add("overlay.counter.best_survival_no_emoji", "Beste: %s");

        add("command.coords.broadcast", "%s hat seine Koordinaten geteilt: X:%s, Y:%s, Z:%s");
        add("command.coords.player_not_found", "❌ Spieler nicht gefunden!");

        add("key.counter.edit_mode", "Bearbeitungsmodus");
        add("key.categories.counter", "Zähler");

        add("screen.overlay_edit", "Overlay-Bearbeitung");
        add("screen.overlay_edit.done", "Fertig");
        add("screen.overlay_edit.cancel", "Abbrechen");
        add("screen.overlay_edit.toggle_overlay", "Overlay umschalten");
    }
}