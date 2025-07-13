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
        // --- Overlay Texts ---
        add("overlay.counter.day_with_emoji", "🌞 Tag: %s");
        add("overlay.counter.day_no_emoji", "Tag: %s");
        add("combined.daytime_with_emoji", "🌞 Tag: %s, %s");
        add("combined.daytime_no_emoji", "Tag: %s, %s");

        add("overlay.counter.deaths_with_emoji", "💀 Tode: %s");
        add("overlay.counter.deaths_no_emoji", "Tode: %s");
        add("overlay.counter.deathlist", "💀 Todeszähler:");
        add("overlay.counter.deathlist.entry.full", "%s %s");
        add("overlay.counter.deathlist.entry.singular", "%s: %s Tod");
        add("overlay.counter.deathlist.entry.plural", "%s: %s Tode");
        add("overlay.counter.deathlist.style.invalid", "FEHLER: Ungültiger Todeslisten-Stil!");

        add("overlay.counter.survival_with_emoji", "⏳ Überlebt: %s");
        add("overlay.counter.survival_no_emoji", "Überlebt: %s");
        add("overlay.counter.best_survival_with_emoji", "⏳ Beste: %s");
        add("overlay.counter.best_survival_no_emoji", "Beste: %s");

        // --- Key Bindings ---
        add("key.counter.edit_mode", "Bearbeitungsmodus");
        add("key.counter.show_overlays", "Overlays anzeigen");
        add("key.categories.counter", "Zähler");

        // --- Overlay Edit Screen ---
        add("screen.overlay_edit", "Overlay-Bearbeitung");
        add("screen.overlay_edit.done", "Fertig");
        add("screen.overlay_edit.cancel", "Abbrechen");
        add("screen.overlay_edit.toggle_overlay", "Overlay umschalten");

        // --- Chat translations ---
        add("chat.daycounter.new_day", "🌞 Es ist jetzt Tag %s!");
        add("chat.deathcounter.player_death.singular", "💀 %s ist einmal gestorben!");
        add("chat.deathcounter.player_death.plural", "💀 %s ist %s mal gestorben!");
        add("chat.survivalcounter.personal", "⏳ Überlebt: %s");
        add("chat.survivalcounter.personal.best", "⏳ Überlebt: %s (%s)");
        add("chat.survivalcounter.broadcast", "⏳ %s hat %s lang überlebt");
        add("chat.survivalcounter.broadcast.best", "⏳ %s hat %s lang überlebt (%s)");

        // --- Command translations ---
        add("command.daycounter.set", "🌞 Tageszähler auf %s gesetzt!");
        add("command.deathcounter.reset", "💀 Todeszähler wurde zurückgesetzt!");
        add("command.deathcounter.set", "💀 Setze die Tode von %s auf %s!");
        add("command.survival.global_best", "⏳ %s hält die beste Zeit: %s");
        add("command.coords.broadcast", "%s hat seine Koordinaten geteilt: X: %s, Y: %s, Z: %s");
        add("command.player_not_found", "❌ Spieler nicht gefunden!");
        add("command.counter.no_data", "Es sind noch keine Daten vorhanden.");

        // --- Config translations: Day Overlay ---
        add("counter.config.showDayOverlay", "Tages-Overlay anzeigen");
        add("counter.config.showOverlayAlways", "Overlay immer anzeigen");
        add("counter.config.dayOverlayX", "Tages-Overlay X");
        add("counter.config.dayOverlayY", "Tages-Overlay Y");
        add("counter.config.dayOverlaySize", "Tages-Overlay Größe");
        add("counter.config.dayOverlayAlign", "Tages-Overlay Ausrichtung");
        add("counter.config.dayOverlayTextColor", "Tages-Overlay Textfarbe");

        // --- Config translations: Death List Overlay ---
        add("counter.config.showDeathListOverlay", "Todesliste anzeigen");
        add("counter.config.showListOverlayAlways", "Todesliste immer anzeigen");
        add("counter.config.deathListX", "Todesliste X");
        add("counter.config.deathListY", "Todesliste Y");
        add("counter.config.deathListSize", "Todesliste Größe");
        add("counter.config.deathOverlayStyle", "Todeslisten-Stil");
        add("counter.config.deathOverlayMinWidth", "Todesliste Mindestbreite");
        add("counter.config.deathListAlign", "Todesliste Ausrichtung");
        add("counter.config.deathListTextColor", "Todesliste Textfarbe");
        add("counter.config.firstPlaceColor", "Farbe für Platz 1");
        add("counter.config.secondPlaceColor", "Farbe für Platz 2");
        add("counter.config.thirdPlaceColor", "Farbe für Platz 3");

        // --- Config translations: Personal Death Counter ---
        add("counter.config.showDeathSelfOverlay", "Eigenen Todeszähler anzeigen");
        add("counter.config.showSelfOverlayAlways", "Eigenen Todeszähler immer anzeigen");
        add("counter.config.deathSelfX", "Eigener Tod X");
        add("counter.config.deathSelfY", "Eigener Tod Y");
        add("counter.config.deathSelfSize", "Eigener Tod Größe");
        add("counter.config.deathSelfAlign", "Eigener Tod Ausrichtung");
        add("counter.config.deathSelfTextColor", "Eigener Tod Textfarbe");

        // --- Config translations: Survival Overlay ---
        add("counter.config.showSurvivalOverlay", "Überlebens-Overlay anzeigen");
        add("counter.config.showSurvivalOverlayAlways", "Überlebens-Overlay immer anzeigen");
        add("counter.config.survivalOverlayX", "Überlebens-Overlay X");
        add("counter.config.survivalOverlayY", "Überlebens-Overlay Y");
        add("counter.config.survivalOverlaySize", "Überlebens-Overlay Größe");
        add("counter.config.survivalOverlayAlign", "Überlebens-Overlay Ausrichtung");
        add("counter.config.survivalOverlayTextColor", "Überlebens-Overlay Textfarbe");

        // --- Config translations: Time Overlay ---
        add("counter.config.showTimeOverlay", "Zeit-Overlay anzeigen");
        add("counter.config.showTimeOverlayAlways", "Zeit-Overlay immer anzeigen");
        add("counter.config.timeOverlayX", "Zeit-Overlay X");
        add("counter.config.timeOverlayY", "Zeit-Overlay Y");
        add("counter.config.timeOverlaySize", "Zeit-Overlay Größe");
        add("counter.config.timeOverlayAlign", "Zeit-Overlay Ausrichtung");
        add("counter.config.timeOverlayTextColor", "Zeit-Overlay Textfarbe");

        // --- Config translations: Coordinates Overlay ---
        add("counter.config.showCoordsOverlay", "Koordinaten-Overlay anzeigen");
        add("counter.config.showCoordsOverlayAlways", "Koordinaten-Overlay immer anzeigen");
        add("counter.config.coordsOverlayX", "Koordinaten-Overlay X");
        add("counter.config.coordsOverlayY", "Koordinaten-Overlay Y");
        add("counter.config.coordsOverlaySize", "Koordinaten-Overlay Größe");
        add("counter.config.coordsOverlayAlign", "Koordinaten-Overlay Ausrichtung");
        add("counter.config.coordsOverlayTextColor", "Koordinaten-Overlay Textfarbe");

        // --- Config translations: Ping & Emojis ---
        add("counter.config.showPingAsText", "Ping als Text anzeigen");
        add("counter.config.pingColorGood", "Farbe für guten Ping");
        add("counter.config.pingColorMedium", "Farbe für mittleren Ping");
        add("counter.config.pingColorBad", "Farbe für schlechten Ping");
        add("counter.config.showEmojis", "Emojis anzeigen");

        // --- Config translations: General & Misc ---
        add("counter.config.enableDayCounter", "Tageszähler aktivieren");
        add("counter.config.showOverlay", "Overlay anzeigen");
        add("counter.config.enableDayMessage", "Tagesnachricht aktivieren");
        add("counter.config.showDayInChat", "Tag im Chat anzeigen");
        add("counter.config.enableDeathCounter", "Todeszähler aktivieren");
        add("counter.config.maxPlayersShown", "Maximale Spielerzahl");
        add("counter.config.deathOverlayMode", "Todeslistenmodus");
        add("counter.config.showDeathInChat", "Tod im Chat anzeigen");
        add("counter.config.showDeathInChatMode", "Todes-Chat Auslöser");
        add("counter.config.deathChatMode", "Todes-Chat Modus");
        add("counter.config.showDeathListOnDeathGlobal", "Todesliste global senden");
        add("counter.config.deathListChatTextColor", "Todeslisten-Chatfarbe");
        add("counter.config.deathSelfChatTextColor", "Eigene Todes-Chatfarbe");

        add("counter.config.enableSurvivalCounter", "Überlebenszähler aktivieren");
        add("counter.config.survivalUseRealTime", "Reale Zeit nutzen");
        add("counter.config.survivalTimeFormat", "Format der Überlebenszeit");
        add("counter.config.showBestSurvivalTime", "Beste Überlebenszeit anzeigen");
        add("counter.config.showBestSurvivalInDeathCounter", "Beste Zeit im Todeszähler");
        add("counter.config.survivalHistorySize", "Größe der Überlebenshistorie");
        add("counter.config.showSurvivalInChat", "Überlebenszeit im Chat");
        add("counter.config.showSurvivalInChatMode", "Überlebens-Chat Auslöser");
        add("counter.config.showSurvivalInChatGlobal", "Überlebenszeit global senden");
        add("counter.config.showBestSurvivalInChat", "Beste Zeit im Chat anzeigen");

        add("counter.config.enableTimeCounter", "Zeitzähler aktivieren");
        add("counter.config.showCombinedDayTime", "Tag und Zeit kombiniert anzeigen");
        add("counter.config.timeFormat24h", "24h-Format verwenden");

        add("counter.config.enableCoordsCounter", "Koordinatenzähler aktivieren");

        // --- Config translations: Commands ---
        add("counter.config.enableDayCommand", "Tagesbefehl aktivieren");
        add("counter.config.enableDeathCommand", "Todesbefehl aktivieren");
        add("counter.config.enableSurvivalCommand", "Überlebensbefehl aktivieren");
        add("counter.config.enableTimeCommand", "Zeitbefehl aktivieren");
        add("counter.config.enableCoordsCommand", "Koordinatenbefehl aktivieren");

        // --- Config translations: Titles ---
        add("counter.config.title.dayOverlay", "Tages-Overlay Einstellungen");
        add("counter.config.title.deathList", "Todesliste Einstellungen");
        add("counter.config.title.deathSelf", "Eigener Todeszähler Einstellungen");
        add("counter.config.title.survivalOverlay", "Überlebens-Overlay Einstellungen");
        add("counter.config.title.timeOverlay", "Zeit-Overlay Einstellungen");
        add("counter.config.title.coordsOverlay", "Koordinaten-Overlay Einstellungen");
        add("counter.config.title.ping", "Ping Einstellungen");
        add("counter.config.title.emote", "Emote Einstellungen");

        add("counter.config.title.dayCounter", "Tageszähler Einstellungen");
        add("counter.config.title.deathCounter", "Todeszähler Einstellungen");
        add("counter.config.title.deathCounterChat", "Todes-Chat Einstellungen");
        add("counter.config.title.survivalCounter", "Überlebenszähler Einstellungen");
        add("counter.config.title.survivalCounterChat", "Überlebens-Chat Einstellungen");
        add("counter.config.title.timeCounter", "Zeitzähler Einstellungen");
        add("counter.config.title.coordsCounter", "Koordinaten-Overlay Einstellungen");
        add("counter.config.title.commands", "Befehle Einstellungen");
    }
}