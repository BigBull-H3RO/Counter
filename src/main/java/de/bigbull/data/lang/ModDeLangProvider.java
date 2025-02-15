package de.bigbull.data.lang;

import de.bigbull.Counter;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModDeLangProvider extends LanguageProvider {
    public ModDeLangProvider(PackOutput output) {
        super(output, Counter.MODID, "de_de");
    }

    @Override
    protected void addTranslations() {
        add("overlay.counter.day", "🌞 Tag: %s");
        add("command.daycounter.reset", "🌞 Day Counter wurde auf den echten Tag zurückgesetzt!");
        add("command.daycounter.set", "🌞 Day Counter auf %s gesetzt!");
        add("command.daycounter.show", "🌞 Overlay aktiviert!");
        add("command.daycounter.hide", "🌑 Overlay deaktiviert!");
        add("command.daycounter.error", "❌ Das Overlay ist global deaktiviert!");
        add("chat.daycounter.new_day", "🌞 Es ist jetzt Tag %s!");

        add("overlay.counter.deaths", "💀 Tode: %s");
        add("overlay.counter.deathlist", "☠ Todeszähler:");
        add("overlay.counter.deathlist.entry", "%s. %s: %s Tode");
        add("command.deathcounter.reset", "💀 Death Counter wurde zurückgesetzt!");
        add("command.deathcounter.set", "💀 Setze %s's Tode auf %s!");
        add("command.deathcounter.error", "❌ Der Death Counter ist global deaktiviert!");
        add("command.deathcounter.show", "💀 Overlay aktiviert!");
        add("command.deathcounter.hide", "🌑 Overlay deaktiviert!");
    }
}