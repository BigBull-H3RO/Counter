package de.bigbull.counter.data.lang;

import de.bigbull.counter.Counter;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModFrLangProvider extends LanguageProvider {
    public ModFrLangProvider(PackOutput output) {
        super(output, Counter.MODID, "fr_fr");
    }

    @Override
    protected void addTranslations() {
        // --- Overlay Texts ---
        add("overlay.counter.day_with_emoji", "🌞 Jour : %s");
        add("overlay.counter.day_no_emoji", "Jour : %s");
        add("combined.daytime_with_emoji", "🌞 Jour : %s, %s");
        add("combined.daytime_no_emoji", "Jour : %s, %s");

        add("overlay.counter.deaths_with_emoji", "💀 Morts : %s");
        add("overlay.counter.deaths_no_emoji", "Morts : %s");
        add("overlay.counter.deathlist", "💀 Compteur de morts :");
        add("overlay.counter.deathlist.entry.full", "%s %s");
        add("overlay.counter.deathlist.entry.singular", "%s : %s mort");
        add("overlay.counter.deathlist.entry.plural", "%s : %s morts");
        add("overlay.counter.deathlist.style.invalid", "ERREUR : Style de liste de morts invalide !");

        add("overlay.counter.fps", "FPS : %s");

        add("overlay.counter.survival_with_emoji", "⏳ Survécu : %s");
        add("overlay.counter.survival_no_emoji", "Survécu : %s");
        add("overlay.counter.best_survival_with_emoji", "⏳ Meilleur : %s");
        add("overlay.counter.best_survival_no_emoji", "Meilleur : %s");

        // --- Key Bindings ---
        add("key.counter.edit_mode", "Mode édition");
        add("key.counter.show_overlays", "Afficher les overlays");
        add("key.categories.counter", "Compteur");

        // --- Overlay Edit Screen ---
        add("screen.overlay_edit", "Écran d'édition d'overlay");
        add("screen.overlay_edit.done", "Terminé");
        add("screen.overlay_edit.cancel", "Annuler");
        add("screen.overlay_edit.toggle_overlay", "Basculer l'overlay");

        // --- Chat translations ---
        add("chat.daycounter.new_day", "🌞 C'est maintenant le jour %s !");
        add("chat.deathcounter.player_death.singular", "💀 %s est mort une fois !");
        add("chat.deathcounter.player_death.plural", "💀 %s est mort %s fois !");
        add("chat.survivalcounter.personal", "⏳ Survécu : %s");
        add("chat.survivalcounter.personal.best", "⏳ Survécu : %s (%s)");
        add("chat.survivalcounter.broadcast", "⏳ %s a survécu pendant %s");
        add("chat.survivalcounter.broadcast.best", "⏳ %s a survécu pendant %s (%s)");

        // --- Command translations ---
        add("command.daycounter.set", "🌞 Compteur de jours réglé à %s !");
        add("command.deathcounter.reset", "💀 Compteur de morts réinitialisé !");
        add("command.deathcounter.set", "💀 Nombre de morts de %s réglé à %s !");
        add("command.survival.global_best", "⏳ %s détient le meilleur temps : %s");
        add("command.coords.broadcast", "%s a partagé ses coordonnées : X: %s, Y: %s, Z: %s");
        add("command.player_not_found", "❌ Joueur non trouvé !");
        add("command.counter.no_data", "Aucune donnée disponible pour le moment.");

        // --- Config translations: Day Overlay ---
        add("counter.config.showDayOverlay", "Afficher l'overlay du jour");
        add("counter.config.showOverlayAlways", "Toujours afficher l'overlay");
        add("counter.config.dayOverlayX", "Jour Overlay X");
        add("counter.config.dayOverlayY", "Jour Overlay Y");
        add("counter.config.dayOverlaySize", "Taille de l'overlay du jour");
        add("counter.config.dayOverlayAlign", "Alignement de l'overlay du jour");
        add("counter.config.dayOverlayTextColor", "Couleur du texte de l'overlay du jour");

        // --- Config translations: Death List Overlay ---
        add("counter.config.showDeathListOverlay", "Afficher la liste des morts");
        add("counter.config.showListOverlayAlways", "Toujours afficher la liste des morts");
        add("counter.config.deathListX", "Liste des morts X");
        add("counter.config.deathListY", "Liste des morts Y");
        add("counter.config.deathListSize", "Taille de la liste des morts");
        add("counter.config.deathOverlayStyle", "Style de la liste des morts");
        add("counter.config.deathOverlayMinWidth", "Largeur minimale de la liste des morts");
        add("counter.config.deathListAlign", "Alignement de la liste des morts");
        add("counter.config.deathListTextColor", "Couleur du texte de la liste des morts");
        add("counter.config.firstPlaceColor", "Couleur de la première place");
        add("counter.config.secondPlaceColor", "Couleur de la deuxième place");
        add("counter.config.thirdPlaceColor", "Couleur de la troisième place");

        // --- Config translations: Personal Death Counter ---
        add("counter.config.showDeathSelfOverlay", "Afficher le compteur de morts personnel");
        add("counter.config.showSelfOverlayAlways", "Toujours afficher le compteur personnel");
        add("counter.config.deathSelfX", "Mort personnel X");
        add("counter.config.deathSelfY", "Mort personnel Y");
        add("counter.config.deathSelfSize", "Taille du compteur personnel");
        add("counter.config.deathSelfAlign", "Alignement du compteur personnel");
        add("counter.config.deathSelfTextColor", "Couleur du texte du compteur personnel");

        // --- Config translations: FPS Overlay ---
        add("counter.config.showFpsOverlay", "Afficher l'overlay des FPS");
        add("counter.config.showFpsOverlayAlways", "Toujours afficher les FPS");
        add("counter.config.fpsOverlayX", "FPS Overlay X");
        add("counter.config.fpsOverlayY", "FPS Overlay Y");
        add("counter.config.fpsOverlaySize", "Taille de l'overlay des FPS");
        add("counter.config.fpsOverlayAlign", "Alignement de l'overlay des FPS");
        add("counter.config.fpsOverlayTextColor", "Couleur du texte des FPS");
        add("counter.config.enableFpsCounter", "Activer le compteur de FPS");
        add("counter.config.title.fpsOverlay", "Paramètres de l'overlay des FPS");
        add("counter.config.title.fpsCounter", "Paramètres du compteur de FPS");

        // --- Config translations: Survival Overlay ---
        add("counter.config.showSurvivalOverlay", "Afficher l'overlay de survie");
        add("counter.config.showSurvivalOverlayAlways", "Toujours afficher l'overlay de survie");
        add("counter.config.survivalOverlayX", "Survie Overlay X");
        add("counter.config.survivalOverlayY", "Survie Overlay Y");
        add("counter.config.survivalOverlaySize", "Taille de l'overlay de survie");
        add("counter.config.survivalOverlayAlign", "Alignement de l'overlay de survie");
        add("counter.config.survivalOverlayTextColor", "Couleur du texte de l'overlay de survie");

        // --- Config translations: Time Overlay ---
        add("counter.config.showTimeOverlay", "Afficher l'overlay de l'heure");
        add("counter.config.showTimeOverlayAlways", "Toujours afficher l'heure");
        add("counter.config.timeOverlayX", "Heure Overlay X");
        add("counter.config.timeOverlayY", "Heure Overlay Y");
        add("counter.config.timeOverlaySize", "Taille de l'overlay de l'heure");
        add("counter.config.timeOverlayAlign", "Alignement de l'overlay de l'heure");
        add("counter.config.timeOverlayTextColor", "Couleur du texte de l'overlay de l'heure");

        // --- Config translations: Coordinates Overlay ---
        add("counter.config.showCoordsOverlay", "Afficher l'overlay des coordonnées");
        add("counter.config.showCoordsOverlayAlways", "Toujours afficher les coordonnées");
        add("counter.config.coordsOverlayX", "Coordonnées Overlay X");
        add("counter.config.coordsOverlayY", "Coordonnées Overlay Y");
        add("counter.config.coordsOverlaySize", "Taille de l'overlay des coordonnées");
        add("counter.config.coordsOverlayAlign", "Alignement de l'overlay des coordonnées");
        add("counter.config.coordsOverlayTextColor", "Couleur du texte des coordonnées");

        // --- Config translations: Ping & Emojis ---
        add("counter.config.showPingAsText", "Afficher le ping en texte");
        add("counter.config.pingColorGood", "Couleur de bon ping");
        add("counter.config.pingColorMedium", "Couleur de ping moyen");
        add("counter.config.pingColorBad", "Couleur de mauvais ping");
        add("counter.config.showEmojis", "Afficher les emojis");

        // --- Config translations: General & Misc ---
        add("counter.config.enableDayCounter", "Activer le compteur de jours");
        add("counter.config.showOverlay", "Afficher l'overlay");
        add("counter.config.enableDayMessage", "Activer le message du jour");
        add("counter.config.showDayInChat", "Afficher le jour dans le chat");
        add("counter.config.enableDeathCounter", "Activer le compteur de morts");
        add("counter.config.maxPlayersShown", "Nombre max de joueurs affichés");
        add("counter.config.deathOverlayMode", "Mode de la liste des morts");
        add("counter.config.showDeathInChat", "Afficher la mort dans le chat");
        add("counter.config.showDeathInChatMode", "Déclencheur du chat de mort");
        add("counter.config.deathChatMode", "Mode du chat de mort");
        add("counter.config.showDeathListOnDeathGlobal", "Diffuser la liste des morts");
        add("counter.config.deathListChatTextColor", "Couleur du chat de la liste des morts");
        add("counter.config.deathSelfChatTextColor", "Couleur du chat du compteur personnel");

        add("counter.config.enableSurvivalCounter", "Activer le compteur de survie");
        add("counter.config.survivalUseRealTime", "Utiliser le temps réel");
        add("counter.config.survivalTimeFormat", "Format du temps de survie");
        add("counter.config.showBestSurvivalTime", "Afficher le meilleur temps de survie");
        add("counter.config.showBestSurvivalInDeathCounter", "Afficher le meilleur dans le compteur de morts");
        add("counter.config.survivalHistorySize", "Taille de l'historique de survie");
        add("counter.config.showSurvivalInChat", "Afficher la survie dans le chat");
        add("counter.config.showSurvivalInChatMode", "Déclencheur du chat de survie");
        add("counter.config.showSurvivalInChatGlobal", "Diffuser le temps de survie");
        add("counter.config.showBestSurvivalInChat", "Afficher le meilleur dans le chat");

        add("counter.config.enableTimeCounter", "Activer le compteur de temps");
        add("counter.config.showCombinedDayTime", "Afficher jour/heure combinés");
        add("counter.config.timeFormat24h", "Format 24h");

        add("counter.config.enableCoordsCounter", "Activer le compteur de coordonnées");

        // --- Config translations: Commands ---
        add("counter.config.enableDayCommand", "Activer la commande de jour");
        add("counter.config.enableDeathCommand", "Activer la commande de morts");
        add("counter.config.enableSurvivalCommand", "Activer la commande de survie");
        add("counter.config.enableTimeCommand", "Activer la commande de temps");
        add("counter.config.enableCoordsCommand", "Activer la commande de coordonnées");

        // --- Config translations: Titles ---
        add("counter.config.title.dayOverlay", "Paramètres de l'overlay du jour");
        add("counter.config.title.deathList", "Paramètres de la liste des morts");
        add("counter.config.title.deathSelf", "Paramètres du compteur personnel");
        add("counter.config.title.survivalOverlay", "Paramètres de l'overlay de survie");
        add("counter.config.title.timeOverlay", "Paramètres de l'overlay de l'heure");
        add("counter.config.title.coordsOverlay", "Paramètres de l'overlay des coordonnées");
        add("counter.config.title.ping", "Paramètres du ping");
        add("counter.config.title.emote", "Paramètres des émotes");

        add("counter.config.title.dayCounter", "Paramètres du compteur de jours");
        add("counter.config.title.deathCounter", "Paramètres du compteur de morts");
        add("counter.config.title.deathCounterChat", "Paramètres du chat de morts");
        add("counter.config.title.survivalCounter", "Paramètres du compteur de survie");
        add("counter.config.title.survivalCounterChat", "Paramètres du chat de survie");
        add("counter.config.title.timeCounter", "Paramètres du compteur de temps");
        add("counter.config.title.coordsCounter", "Paramètres de l'overlay des coordonnées");
        add("counter.config.title.commands", "Paramètres des commandes");
    }
}
