package de.bigbull.counter.data.lang;

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
        // --- Overlay Texts ---
        translationBuilder.add("overlay.counter.day_with_emoji", "🌞 Tag: %s");
        translationBuilder.add("overlay.counter.day_no_emoji", "Tag: %s");
        translationBuilder.add("combined.daytime_with_emoji", "🌞 Tag: %s, %s");
        translationBuilder.add("combined.daytime_no_emoji", "Tag: %s, %s");

        translationBuilder.add("overlay.counter.deaths_with_emoji", "💀 Tode: %s");
        translationBuilder.add("overlay.counter.deaths_no_emoji", "Tode: %s");
        translationBuilder.add("overlay.counter.deathlist", "💀 Todeszähler:");
        translationBuilder.add("overlay.counter.deathlist.entry.full", "%s %s");
        translationBuilder.add("overlay.counter.deathlist.entry.singular", "%s: %s Tod");
        translationBuilder.add("overlay.counter.deathlist.entry.plural", "%s: %s Tode");
        translationBuilder.add("overlay.counter.deathlist.style.invalid", "FEHLER: Ungültiger Todeslisten-Stil!");

        translationBuilder.add("overlay.counter.survival_with_emoji", "⏳ Überlebt: %s");
        translationBuilder.add("overlay.counter.survival_no_emoji", "Überlebt: %s");
        translationBuilder.add("overlay.counter.best_survival_with_emoji", "⏳ Beste: %s");
        translationBuilder.add("overlay.counter.best_survival_no_emoji", "Beste: %s");

        // --- Key Bindings ---
        translationBuilder.add("key.counter.edit_mode", "Bearbeitungsmodus");
        translationBuilder.add("key.counter.show_overlays", "Overlays anzeigen");
        translationBuilder.add("key.categories.counter", "Zähler");

        // --- Overlay Edit Screen ---
        translationBuilder.add("screen.overlay_edit", "Overlay-Bearbeitung");
        translationBuilder.add("screen.overlay_edit.done", "Fertig");
        translationBuilder.add("screen.overlay_edit.cancel", "Abbrechen");
        translationBuilder.add("screen.overlay_edit.toggle_overlay", "Overlay umschalten");

        // --- Chat translations ---
        translationBuilder.add("chat.daycounter.new_day", "🌞 Es ist jetzt Tag %s!");
        translationBuilder.add("chat.deathcounter.player_death.singular", "💀 %s ist einmal gestorben!");
        translationBuilder.add("chat.deathcounter.player_death.plural", "💀 %s ist %s mal gestorben!");
        translationBuilder.add("chat.survivalcounter.personal", "⏳ Überlebt: %s");
        translationBuilder.add("chat.survivalcounter.personal.best", "⏳ Überlebt: %s (%s)");
        translationBuilder.add("chat.survivalcounter.broadcast", "⏳ %s hat %s lang überlebt");
        translationBuilder.add("chat.survivalcounter.broadcast.best", "⏳ %s hat %s lang überlebt (%s)");

        // --- Command translations ---
        translationBuilder.add("command.daycounter.set", "🌞 Tageszähler auf %s gesetzt!");
        translationBuilder.add("command.deathcounter.reset", "💀 Todeszähler wurde zurückgesetzt!");
        translationBuilder.add("command.deathcounter.set", "💀 Setze die Tode von %s auf %s!");
        translationBuilder.add("command.survival.global_best", "⏳ %s hält die beste Zeit: %s");
        translationBuilder.add("command.coords.broadcast", "%s hat seine Koordinaten geteilt: X: %s, Y: %s, Z: %s");
        translationBuilder.add("command.player_not_found", "❌ Spieler nicht gefunden!");
        translationBuilder.add("command.counter.no_data", "Es sind noch keine Daten vorhanden.");

        // --- Config translations: Day Overlay ---
        translationBuilder.add("counter.config.showDayOverlay", "Tages-Overlay anzeigen");
        translationBuilder.add("counter.config.showOverlayAlways", "Overlay immer anzeigen");
        translationBuilder.add("counter.config.dayOverlayX", "Tages-Overlay X");
        translationBuilder.add("counter.config.dayOverlayY", "Tages-Overlay Y");
        translationBuilder.add("counter.config.dayOverlaySize", "Tages-Overlay Größe");
        translationBuilder.add("counter.config.dayOverlayAlign", "Tages-Overlay Ausrichtung");
        translationBuilder.add("counter.config.dayOverlayTextColor", "Tages-Overlay Textfarbe");

        // --- Config translations: Death List Overlay ---
        translationBuilder.add("counter.config.showDeathListOverlay", "Todesliste anzeigen");
        translationBuilder.add("counter.config.showListOverlayAlways", "Todesliste immer anzeigen");
        translationBuilder.add("counter.config.deathListX", "Todesliste X");
        translationBuilder.add("counter.config.deathListY", "Todesliste Y");
        translationBuilder.add("counter.config.deathListSize", "Todesliste Größe");
        translationBuilder.add("counter.config.deathOverlayStyle", "Todeslisten-Stil");
        translationBuilder.add("counter.config.deathOverlayMinWidth", "Todesliste Mindestbreite");
        translationBuilder.add("counter.config.deathListAlign", "Todesliste Ausrichtung");
        translationBuilder.add("counter.config.deathListTextColor", "Todesliste Textfarbe");
        translationBuilder.add("counter.config.firstPlaceColor", "Farbe für Platz 1");
        translationBuilder.add("counter.config.secondPlaceColor", "Farbe für Platz 2");
        translationBuilder.add("counter.config.thirdPlaceColor", "Farbe für Platz 3");

        // --- Config translations: Personal Death Counter ---
        translationBuilder.add("counter.config.showDeathSelfOverlay", "Eigenen Todeszähler anzeigen");
        translationBuilder.add("counter.config.showSelfOverlayAlways", "Eigenen Todeszähler immer anzeigen");
        translationBuilder.add("counter.config.deathSelfX", "Eigener Tod X");
        translationBuilder.add("counter.config.deathSelfY", "Eigener Tod Y");
        translationBuilder.add("counter.config.deathSelfSize", "Eigener Tod Größe");
        translationBuilder.add("counter.config.deathSelfAlign", "Eigener Tod Ausrichtung");
        translationBuilder.add("counter.config.deathSelfTextColor", "Eigener Tod Textfarbe");

        // --- Config translations: Survival Overlay ---
        translationBuilder.add("counter.config.showSurvivalOverlay", "Überlebens-Overlay anzeigen");
        translationBuilder.add("counter.config.showSurvivalOverlayAlways", "Überlebens-Overlay immer anzeigen");
        translationBuilder.add("counter.config.survivalOverlayX", "Überlebens-Overlay X");
        translationBuilder.add("counter.config.survivalOverlayY", "Überlebens-Overlay Y");
        translationBuilder.add("counter.config.survivalOverlaySize", "Überlebens-Overlay Größe");
        translationBuilder.add("counter.config.survivalOverlayAlign", "Überlebens-Overlay Ausrichtung");
        translationBuilder.add("counter.config.survivalOverlayTextColor", "Überlebens-Overlay Textfarbe");

        // --- Config translations: Time Overlay ---
        translationBuilder.add("counter.config.showTimeOverlay", "Zeit-Overlay anzeigen");
        translationBuilder.add("counter.config.showTimeOverlayAlways", "Zeit-Overlay immer anzeigen");
        translationBuilder.add("counter.config.timeOverlayX", "Zeit-Overlay X");
        translationBuilder.add("counter.config.timeOverlayY", "Zeit-Overlay Y");
        translationBuilder.add("counter.config.timeOverlaySize", "Zeit-Overlay Größe");
        translationBuilder.add("counter.config.timeOverlayAlign", "Zeit-Overlay Ausrichtung");
        translationBuilder.add("counter.config.timeOverlayTextColor", "Zeit-Overlay Textfarbe");

        // --- Config translations: Coordinates Overlay ---
        translationBuilder.add("counter.config.showCoordsOverlay", "Koordinaten-Overlay anzeigen");
        translationBuilder.add("counter.config.showCoordsOverlayAlways", "Koordinaten-Overlay immer anzeigen");
        translationBuilder.add("counter.config.coordsOverlayX", "Koordinaten-Overlay X");
        translationBuilder.add("counter.config.coordsOverlayY", "Koordinaten-Overlay Y");
        translationBuilder.add("counter.config.coordsOverlaySize", "Koordinaten-Overlay Größe");
        translationBuilder.add("counter.config.coordsOverlayAlign", "Koordinaten-Overlay Ausrichtung");
        translationBuilder.add("counter.config.coordsOverlayTextColor", "Koordinaten-Overlay Textfarbe");

        // --- Config translations: Ping & Emojis ---
        translationBuilder.add("counter.config.showPingAsText", "Ping als Text anzeigen");
        translationBuilder.add("counter.config.pingColorGood", "Farbe für guten Ping");
        translationBuilder.add("counter.config.pingColorMedium", "Farbe für mittleren Ping");
        translationBuilder.add("counter.config.pingColorBad", "Farbe für schlechten Ping");
        translationBuilder.add("counter.config.showEmojis", "Emojis anzeigen");

        // --- Config translations: General & Misc ---
        translationBuilder.add("counter.config.enableDayCounter", "Tageszähler aktivieren");
        translationBuilder.add("counter.config.showOverlay", "Overlay anzeigen");
        translationBuilder.add("counter.config.enableDayMessage", "Tagesnachricht aktivieren");
        translationBuilder.add("counter.config.showDayInChat", "Tag im Chat anzeigen");
        translationBuilder.add("counter.config.enableDeathCounter", "Todeszähler aktivieren");
        translationBuilder.add("counter.config.maxPlayersShown", "Maximale Spielerzahl");
        translationBuilder.add("counter.config.deathOverlayMode", "Todeslistenmodus");
        translationBuilder.add("counter.config.showDeathInChat", "Tod im Chat anzeigen");
        translationBuilder.add("counter.config.showDeathInChatMode", "Todes-Chat Auslöser");
        translationBuilder.add("counter.config.deathChatMode", "Todes-Chat Modus");
        translationBuilder.add("counter.config.showDeathListOnDeathGlobal", "Todesliste global senden");
        translationBuilder.add("counter.config.deathListChatTextColor", "Todeslisten-Chatfarbe");
        translationBuilder.add("counter.config.deathSelfChatTextColor", "Eigene Todes-Chatfarbe");

        translationBuilder.add("counter.config.enableSurvivalCounter", "Überlebenszähler aktivieren");
        translationBuilder.add("counter.config.survivalUseRealTime", "Reale Zeit nutzen");
        translationBuilder.add("counter.config.survivalTimeFormat", "Format der Überlebenszeit");
        translationBuilder.add("counter.config.showBestSurvivalTime", "Beste Überlebenszeit anzeigen");
        translationBuilder.add("counter.config.showBestSurvivalInDeathCounter", "Beste Zeit im Todeszähler");
        translationBuilder.add("counter.config.survivalHistorySize", "Größe der Überlebenshistorie");
        translationBuilder.add("counter.config.showSurvivalInChat", "Überlebenszeit im Chat");
        translationBuilder.add("counter.config.showSurvivalInChatMode", "Überlebens-Chat Auslöser");
        translationBuilder.add("counter.config.showSurvivalInChatGlobal", "Überlebenszeit global senden");
        translationBuilder.add("counter.config.showBestSurvivalInChat", "Beste Zeit im Chat anzeigen");

        translationBuilder.add("counter.config.enableTimeCounter", "Zeitzähler aktivieren");
        translationBuilder.add("counter.config.showCombinedDayTime", "Tag und Zeit kombiniert anzeigen");
        translationBuilder.add("counter.config.timeFormat24h", "24h-Format verwenden");

        translationBuilder.add("counter.config.enableCoordsCounter", "Koordinatenzähler aktivieren");

        // --- Config translations: Commands ---
        translationBuilder.add("counter.config.enableDayCommand", "Tagesbefehl aktivieren");
        translationBuilder.add("counter.config.enableDeathCommand", "Todesbefehl aktivieren");
        translationBuilder.add("counter.config.enableSurvivalCommand", "Überlebensbefehl aktivieren");
        translationBuilder.add("counter.config.enableTimeCommand", "Zeitbefehl aktivieren");
        translationBuilder.add("counter.config.enableCoordsCommand", "Koordinatenbefehl aktivieren");

        // --- Config translations: Titles ---
        translationBuilder.add("counter.config.title.dayOverlay", "Tages-Overlay Einstellungen");
        translationBuilder.add("counter.config.title.deathList", "Todesliste Einstellungen");
        translationBuilder.add("counter.config.title.deathSelf", "Eigener Todeszähler Einstellungen");
        translationBuilder.add("counter.config.title.survivalOverlay", "Überlebens-Overlay Einstellungen");
        translationBuilder.add("counter.config.title.timeOverlay", "Zeit-Overlay Einstellungen");
        translationBuilder.add("counter.config.title.coordsOverlay", "Koordinaten-Overlay Einstellungen");
        translationBuilder.add("counter.config.title.ping", "Ping Einstellungen");
        translationBuilder.add("counter.config.title.emote", "Emote Einstellungen");

        translationBuilder.add("counter.config.title.dayCounter", "Tageszähler Einstellungen");
        translationBuilder.add("counter.config.title.deathCounter", "Todeszähler Einstellungen");
        translationBuilder.add("counter.config.title.deathCounterChat", "Todes-Chat Einstellungen");
        translationBuilder.add("counter.config.title.survivalCounter", "Überlebenszähler Einstellungen");
        translationBuilder.add("counter.config.title.survivalCounterChat", "Überlebens-Chat Einstellungen");
        translationBuilder.add("counter.config.title.timeCounter", "Zeitzähler Einstellungen");
        translationBuilder.add("counter.config.title.coordsCounter", "Koordinaten-Overlay Einstellungen");
        translationBuilder.add("counter.config.title.commands", "Befehle Einstellungen");
    }
}