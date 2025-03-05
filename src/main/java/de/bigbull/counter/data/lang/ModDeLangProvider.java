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
        add("command.daycounter.reset", "🌞 Day Counter wurde auf den echten Tag zurückgesetzt!");
        add("command.daycounter.set", "🌞 Day Counter auf %s gesetzt!");
        add("command.daycounter.show", "🌞 Overlay aktiviert!");
        add("command.daycounter.hide", "🌑 Overlay deaktiviert!");
        add("command.daycounter.error", "❌ Das Overlay ist global deaktiviert!");
        add("chat.daycounter.new_day", "🌞 Es ist jetzt Tag %s!");

        add("overlay.counter.deaths_with_emoji", "💀 Tode: %s");
        add("overlay.counter.deaths_no_emoji", "Tode: %s");
        add("chat.deathcounter.player_death.singular", "💀 %s ist %s mal gestorben!");
        add("chat.deathcounter.player_death.plural", "💀 %s ist %s mal gestorben!");
        add("overlay.counter.deathlist", "💀 Todeszähler:");
        add("overlay.counter.deathlist.entry.full", "%s %s");
        add("overlay.counter.deathlist.entry.singular", "%s: %s Tod");
        add("overlay.counter.deathlist.entry.plural", "%s: %s Tode");
        add("command.deathcounter.get", "💀 %s ist %s mal gestorben!");
        add("command.deathcounter.reset", "💀 Death Counter wurde zurückgesetzt!");
        add("command.deathcounter.set", "💀 Setze %s's Tode auf %s!");
        add("command.deathcounter.error", "❌ Der Death Counter ist global deaktiviert!");
        add("command.deathcounter.show", "💀 Overlay aktiviert!");
        add("command.deathcounter.hide", "🌑 Overlay deaktiviert!");

        add("key.counter.edit_mode", "Bearbeitungsmodus");
        add("key.categories.counter", "Counter");
    }
}