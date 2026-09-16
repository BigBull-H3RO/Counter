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
        // --- Overlay-Texte ---
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
        add("overlay.counter.best_survival_with_emoji", "⏳ Rekord: %s");
        add("overlay.counter.best_survival_no_emoji", "Rekord: %s");

        add("overlay.counter.fps", "FPS: %s");

        // --- Tastenbelegungen ---
        add("key.counter.edit_mode", "Bearbeitungsmodus");
        add("key.counter.show_overlays", "Overlays anzeigen");
        add("key.category.counter.main", "Counter");

        // --- Overlay-Bearbeitungsbildschirm ---
        add("screen.overlay_edit", "Overlay-Bearbeitungsbildschirm");
        add("screen.overlay_edit.done", "Fertig");
        add("screen.overlay_edit.cancel", "Abbrechen");
        add("screen.overlay_edit.toggle_overlay", "Overlay umschalten");

        // --- Chat-Übersetzungen ---
        add("chat.daycounter.new_day", "🌞 Es ist jetzt Tag %s!");
        add("chat.deathcounter.player_death.singular", "💀 %s ist 1 Mal gestorben!");
        add("chat.deathcounter.player_death.plural", "💀 %s ist %s Mal gestorben!");
        add("chat.survivalcounter.personal", "⏳ Überlebt: %s");
        add("chat.survivalcounter.personal.best", "⏳ Überlebt: %s (%s)");
        add("chat.survivalcounter.broadcast", "⏳ %s hat %s überlebt");
        add("chat.survivalcounter.broadcast.best", "⏳ %s hat %s überlebt (%s)");

        // --- Befehls-Übersetzungen ---
        add("command.daycounter.set", "🌞 Tageszähler auf %s gesetzt!");
        add("command.deathcounter.reset", "💀 Todeszähler wurde zurückgesetzt!");
        add("command.deathcounter.set", "💀 Todesanzahl von %s auf %s gesetzt!");
        add("command.survival.global_best", "⏳ %s hält die beste Zeit: %s");
        add("command.coords.broadcast", "%s hat seine Koordinaten geteilt: X: %s, Y: %s, Z: %s");
        add("command.player_not_found", "❌ Spieler nicht gefunden!");
        add("command.counter.no_data", "Noch keine Daten verfügbar.");

        // --- Konfigurations-Übersetzungen: Tages-Overlay ---
        add("counter.config.showDayOverlay", "Tages-Overlay anzeigen");
        add("counter.config.showOverlayAlways", "Overlay immer anzeigen");
        add("counter.config.dayOverlayX", "Tages-Overlay X-Position");
        add("counter.config.dayOverlayY", "Tages-Overlay Y-Position");
        add("counter.config.dayOverlaySize", "Tages-Overlay-Größe");
        add("counter.config.dayOverlayAlign", "Tages-Overlay-Ausrichtung");
        add("counter.config.dayOverlayTextColor", "Tages-Overlay-Textfarbe");

        // --- Konfigurations-Übersetzungen: Todeslisten-Overlay ---
        add("counter.config.showDeathListOverlay", "Todeslisten-Overlay anzeigen");
        add("counter.config.showListOverlayAlways", "Todesliste immer anzeigen");
        add("counter.config.deathListX", "Todeslisten-Overlay X-Position");
        add("counter.config.deathListY", "Todeslisten-Overlay Y-Position");
        add("counter.config.deathListSize", "Todeslisten-Overlay-Größe");
        add("counter.config.deathOverlayStyle", "Todeslisten-Stil");
        add("counter.config.deathOverlayMinWidth", "Mindestbreite der Todesliste");
        add("counter.config.deathListAlign", "Todeslisten-Ausrichtung");
        add("counter.config.deathListTextColor", "Todeslisten-Textfarbe");
        add("counter.config.firstPlaceColor", "Farbe für den 1. Platz");
        add("counter.config.secondPlaceColor", "Farbe für den 2. Platz");
        add("counter.config.thirdPlaceColor", "Farbe für den 3. Platz");

        // --- Konfigurations-Übersetzungen: Persönlicher Todeszähler ---
        add("counter.config.showDeathSelfOverlay", "Eigenen Todes-Overlay anzeigen");
        add("counter.config.showSelfOverlayAlways", "Eigenen Todes-Overlay immer anzeigen");
        add("counter.config.deathSelfX", "Eigener Todes-Overlay X-Position");
        add("counter.config.deathSelfY", "Eigener Todes-Overlay Y-Position");
        add("counter.config.deathSelfSize", "Eigener Todes-Overlay-Größe");
        add("counter.config.deathSelfAlign", "Eigene Todes-Ausrichtung");
        add("counter.config.deathSelfTextColor", "Eigene Todes-Overlay-Textfarbe");

        // --- Konfigurations-Übersetzungen: Überlebens-Overlay ---
        add("counter.config.showSurvivalOverlay", "Überlebens-Overlay anzeigen");
        add("counter.config.showSurvivalOverlayAlways", "Überlebens-Overlay immer anzeigen");
        add("counter.config.survivalOverlayX", "Überlebens-Overlay X-Position");
        add("counter.config.survivalOverlayY", "Überlebens-Overlay Y-Position");
        add("counter.config.survivalOverlaySize", "Überlebens-Overlay-Größe");
        add("counter.config.survivalOverlayAlign", "Überlebens-Overlay-Ausrichtung");
        add("counter.config.survivalOverlayTextColor", "Überlebens-Overlay-Textfarbe");

        // --- Konfigurations-Übersetzungen: Zeit-Overlay ---
        add("counter.config.showTimeOverlay", "Zeit-Overlay anzeigen");
        add("counter.config.showTimeOverlayAlways", "Zeit-Overlay immer anzeigen");
        add("counter.config.timeOverlayX", "Zeit-Overlay X-Position");
        add("counter.config.timeOverlayY", "Zeit-Overlay Y-Position");
        add("counter.config.timeOverlaySize", "Zeit-Overlay-Größe");
        add("counter.config.timeOverlayAlign", "Zeit-Overlay-Ausrichtung");
        add("counter.config.timeOverlayTextColor", "Zeit-Overlay-Textfarbe");

        // --- Konfigurations-Übersetzungen: Koordinaten-Overlay ---
        add("counter.config.showCoordsOverlay", "Koordinaten-Overlay anzeigen");
        add("counter.config.showCoordsOverlayAlways", "Koordinaten-Overlay immer anzeigen");
        add("counter.config.coordsOverlayX", "Koordinaten-Overlay X-Position");
        add("counter.config.coordsOverlayY", "Koordinaten-Overlay Y-Position");
        add("counter.config.coordsOverlaySize", "Koordinaten-Overlay-Größe");
        add("counter.config.coordsOverlayAlign", "Koordinaten-Overlay-Ausrichtung");
        add("counter.config.coordsOverlayTextColor", "Koordinaten-Overlay-Textfarbe");

        // --- Konfigurations-Übersetzungen: FPS-Overlay ---
        add("counter.config.showFpsOverlay", "FPS-Overlay anzeigen");
        add("counter.config.showFpsOverlayAlways", "FPS-Overlay immer anzeigen");
        add("counter.config.fpsOverlayX", "FPS-Overlay X-Position");
        add("counter.config.fpsOverlayY", "FPS-Overlay Y-Position");
        add("counter.config.fpsOverlaySize", "FPS-Overlay-Größe");
        add("counter.config.fpsOverlayAlign", "FPS-Overlay-Ausrichtung");
        add("counter.config.fpsOverlayTextColor", "FPS-Overlay-Textfarbe");

        // --- Konfigurations-Übersetzungen: Ping & Emojis ---
        add("counter.config.showPingAsText", "Ping als Text anzeigen");
        add("counter.config.pingColorGood", "Farbe für guten Ping");
        add("counter.config.pingColorMedium", "Farbe für mittleren Ping");
        add("counter.config.pingColorBad", "Farbe für schlechten Ping");
        add("counter.config.showEmojis", "Emojis anzeigen");

        // --- Konfigurations-Übersetzungen: Allgemein & Server ---
        add("counter.config.enableDayCounter", "Tageszähler aktivieren");
        add("counter.config.showOverlay", "Overlay anzeigen");
        add("counter.config.enableDayMessage", "Tages-Chatnachricht aktivieren");
        add("counter.config.showDayInChat", "Tag beim Beitreten im Chat anzeigen");
        add("counter.config.enableDeathCounter", "Todeszähler aktivieren");
        add("counter.config.maxPlayersShown", "Maximal angezeigte Spieler");
        add("counter.config.deathOverlayMode", "Todes-Overlay-Modus");
        add("counter.config.showDeathInChat", "Todesnachrichten im Chat anzeigen");
        add("counter.config.showDeathInChatMode", "Chatnachrichten-Auslöser");
        add("counter.config.deathChatMode", "Todes-Chat-Modus");
        add("counter.config.showDeathListOnDeathGlobal", "Todesliste beim Tod an alle senden");
        add("counter.config.deathListChatTextColor", "Todeslisten-Chat-Textfarbe");
        add("counter.config.deathSelfChatTextColor", "Eigene Todes-Chat-Textfarbe");

        add("counter.config.enableSurvivalCounter", "Überlebenszähler aktivieren");
        add("counter.config.survivalUseRealTime", "Echtzeit verwenden");
        add("counter.config.survivalTimeFormat", "Überlebenszeit-Format");
        add("counter.config.showBestSurvivalTime", "Beste Überlebenszeit anzeigen");
        add("counter.config.showBestSurvivalInDeathCounter", "Beste Zeit an Todesnachricht anhängen");
        add("counter.config.survivalHistorySize", "Verlaufsgröße für Überlebenszeit");
        add("counter.config.showSurvivalInChat", "Überlebenszeit im Chat anzeigen");
        add("counter.config.showSurvivalInChatMode", "Überlebens-Chat-Auslöser");
        add("counter.config.showSurvivalInChatGlobal", "Überlebenszeit an alle senden");
        add("counter.config.showBestSurvivalInChat", "Beste Zeit im Chat anzeigen");

        add("counter.config.enableTimeCounter", "Zeitzähler aktivieren");
        add("counter.config.showCombinedDayTime", "Tag und Zeit kombiniert anzeigen");
        add("counter.config.timeFormat24h", "24-Stunden-Format verwenden");

        add("counter.config.enableCoordsCounter", "Koordinatenzähler aktivieren");

        add("counter.config.enableFpsCounter", "FPS-Zähler aktivieren");

        // --- Konfigurations-Übersetzungen: Befehle ---
        add("counter.config.enableDayCommand", "Tages-Befehl aktivieren");
        add("counter.config.enableDeathCommand", "Todes-Befehl aktivieren");
        add("counter.config.enableSurvivalCommand", "Überlebens-Befehl aktivieren");
        add("counter.config.enableTimeCommand", "Zeit-Befehl aktivieren");
        add("counter.config.enableCoordsCommand", "Koordinaten-Befehl aktivieren");

        // --- Konfigurations-Übersetzungen: Titel ---
        add("counter.config.title.dayOverlay", "Tages-Overlay-Einstellungen");
        add("counter.config.title.deathList", "Todeslisten-Overlay-Einstellungen");
        add("counter.config.title.deathSelf", "Einstellungen für eigenen Todes-Overlay");
        add("counter.config.title.survivalOverlay", "Überlebens-Overlay-Einstellungen");
        add("counter.config.title.timeOverlay", "Zeit-Overlay-Einstellungen");
        add("counter.config.title.coordsOverlay", "Koordinaten-Overlay-Einstellungen");
        add("counter.config.title.fpsOverlay", "FPS-Overlay-Einstellungen");
        add("counter.config.title.ping", "Ping-Einstellungen");
        add("counter.config.title.emote", "Emote-Einstellungen");

        add("counter.config.title.dayCounter", "Tageszähler-Einstellungen");
        add("counter.config.title.deathCounter", "Todeszähler-Einstellungen");
        add("counter.config.title.deathCounterChat", "Todeszähler-Chat-Einstellungen");
        add("counter.config.title.survivalCounter", "Überlebenszähler-Einstellungen");
        add("counter.config.title.survivalCounterChat", "Überlebenszähler-Chat-Einstellungen");
        add("counter.config.title.timeCounter", "Zeitzähler-Einstellungen");
        add("counter.config.title.coordsCounter", "Koordinaten-Overlay-Einstellungen");
        add("counter.config.title.fpsCounter", "FPS-Zähler-Einstellungen");
        add("counter.config.title.commands", "Befehlseinstellungen");
    }
}
