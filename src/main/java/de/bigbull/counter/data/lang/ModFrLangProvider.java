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
        // --- Textes de l'Overlay ---
        add("overlay.counter.day_with_emoji", "🌞 Jour : %s");
        add("overlay.counter.day_no_emoji", "Jour : %s");
        add("combined.daytime_with_emoji", "🌞 Jour : %s, %s");
        add("combined.daytime_no_emoji", "Jour : %s, %s");

        add("overlay.counter.deaths_with_emoji", "💀 Morts : %s");
        add("overlay.counter.deaths_no_emoji", "Morts : %s");
        add("overlay.counter.deathlist", "💀 Compteur de Morts :");
        add("overlay.counter.deathlist.entry.full", "%s %s");
        add("overlay.counter.deathlist.entry.singular", "%s : %s mort");
        add("overlay.counter.deathlist.entry.plural", "%s : %s morts");
        add("overlay.counter.deathlist.style.invalid", "ERREUR : Style de liste de morts invalide !");

        add("overlay.counter.survival_with_emoji", "⏳ Survécu : %s");
        add("overlay.counter.survival_no_emoji", "Survécu : %s");
        add("overlay.counter.best_survival_with_emoji", "⏳ Record : %s");
        add("overlay.counter.best_survival_no_emoji", "Record : %s");

        add("overlay.counter.fps", "FPS : %s");

        // --- Raccourcis clavier ---
        add("key.counter.edit_mode", "Mode édition");
        add("key.counter.show_overlays", "Afficher les overlays");
        add("key.category.counter.main", "Counter");

        // --- Écran d'édition des overlays ---
        add("screen.overlay_edit", "Écran d'édition des overlays");
        add("screen.overlay_edit.done", "Terminé");
        add("screen.overlay_edit.cancel", "Annuler");
        add("screen.overlay_edit.toggle_overlay", "Basculer l'overlay");

        // --- Messages du Chat ---
        add("chat.daycounter.new_day", "🌞 C'est maintenant le jour %s !");
        add("chat.deathcounter.player_death.singular", "💀 %s est mort 1 fois !");
        add("chat.deathcounter.player_death.plural", "💀 %s est mort %s fois !");
        add("chat.survivalcounter.personal", "⏳ Survécu : %s");
        add("chat.survivalcounter.personal.best", "⏳ Survécu : %s (%s)");
        add("chat.survivalcounter.broadcast", "⏳ %s a survécu pendant %s");
        add("chat.survivalcounter.broadcast.best", "⏳ %s a survécu pendant %s (%s)");

        // --- Commandes ---
        add("command.daycounter.set", "🌞 Compteur de jours réglé sur %s !");
        add("command.deathcounter.reset", "💀 Le compteur de morts a été réinitialisé !");
        add("command.deathcounter.set", "💀 Compteur de morts de %s réglé sur %s !");
        add("command.survival.global_best", "⏳ %s détient le meilleur temps : %s");
        add("command.coords.broadcast", "%s a partagé ses coordonnées : X : %s, Y : %s, Z : %s");
        add("command.player_not_found", "❌ Joueur introuvable !");
        add("command.counter.no_data", "Aucune donnée disponible pour le moment.");

        // --- Configurations : Overlay du Jour ---
        add("counter.config.showDayOverlay", "Afficher l'overlay du jour");
        add("counter.config.showOverlayAlways", "Toujours afficher l'overlay");
        add("counter.config.dayOverlayX", "Position X de l'overlay du jour");
        add("counter.config.dayOverlayY", "Position Y de l'overlay du jour");
        add("counter.config.dayOverlaySize", "Taille de l'overlay du jour");
        add("counter.config.dayOverlayAlign", "Alignement de l'overlay du jour");
        add("counter.config.dayOverlayTextColor", "Couleur du texte de l'overlay du jour");

        // --- Configurations : Overlay Liste des Morts ---
        add("counter.config.showDeathListOverlay", "Afficher l'overlay de la liste des morts");
        add("counter.config.showListOverlayAlways", "Toujours afficher la liste des morts");
        add("counter.config.deathListX", "Position X de la liste des morts");
        add("counter.config.deathListY", "Position Y de la liste des morts");
        add("counter.config.deathListSize", "Taille de la liste des morts");
        add("counter.config.deathOverlayStyle", "Style de la liste des morts");
        add("counter.config.deathOverlayMinWidth", "Largeur minimale de la liste des morts");
        add("counter.config.deathListAlign", "Alignement de la liste des morts");
        add("counter.config.deathListTextColor", "Couleur du texte de la liste des morts");
        add("counter.config.firstPlaceColor", "Couleur pour la 1ère place");
        add("counter.config.secondPlaceColor", "Couleur pour la 2ème place");
        add("counter.config.thirdPlaceColor", "Couleur pour la 3ème place");

        // --- Configurations : Compteur de Morts Personnel ---
        add("counter.config.showDeathSelfOverlay", "Afficher son propre overlay de morts");
        add("counter.config.showSelfOverlayAlways", "Toujours afficher son propre overlay");
        add("counter.config.deathSelfX", "Position X de ses propres morts");
        add("counter.config.deathSelfY", "Position Y de ses propres morts");
        add("counter.config.deathSelfSize", "Taille de ses propres morts");
        add("counter.config.deathSelfAlign", "Alignement de ses propres morts");
        add("counter.config.deathSelfTextColor", "Couleur du texte de ses propres morts");

        // --- Configurations : Overlay de Survie ---
        add("counter.config.showSurvivalOverlay", "Afficher l'overlay de survie");
        add("counter.config.showSurvivalOverlayAlways", "Toujours afficher l'overlay de survie");
        add("counter.config.survivalOverlayX", "Position X de l'overlay de survie");
        add("counter.config.survivalOverlayY", "Position Y de l'overlay de survie");
        add("counter.config.survivalOverlaySize", "Taille de l'overlay de survie");
        add("counter.config.survivalOverlayAlign", "Alignement de l'overlay de survie");
        add("counter.config.survivalOverlayTextColor", "Couleur du texte de l'overlay de survie");

        // --- Configurations : Overlay de Temps ---
        add("counter.config.showTimeOverlay", "Afficher l'overlay du temps");
        add("counter.config.showTimeOverlayAlways", "Toujours afficher l'overlay du temps");
        add("counter.config.timeOverlayX", "Position X de l'overlay du temps");
        add("counter.config.timeOverlayY", "Position Y de l'overlay du temps");
        add("counter.config.timeOverlaySize", "Taille de l'overlay du temps");
        add("counter.config.timeOverlayAlign", "Alignement de l'overlay du temps");
        add("counter.config.timeOverlayTextColor", "Couleur du texte de l'overlay du temps");

        // --- Configurations : Overlay des Coordonnées ---
        add("counter.config.showCoordsOverlay", "Afficher l'overlay des coordonnées");
        add("counter.config.showCoordsOverlayAlways", "Toujours afficher les coordonnées");
        add("counter.config.coordsOverlayX", "Position X des coordonnées");
        add("counter.config.coordsOverlayY", "Position Y des coordonnées");
        add("counter.config.coordsOverlaySize", "Taille des coordonnées");
        add("counter.config.coordsOverlayAlign", "Alignement des coordonnées");
        add("counter.config.coordsOverlayTextColor", "Couleur du texte des coordonnées");

        // --- Configurations : Overlay des FPS ---
        add("counter.config.showFpsOverlay", "Afficher l'overlay des FPS");
        add("counter.config.showFpsOverlayAlways", "Toujours afficher les FPS");
        add("counter.config.fpsOverlayX", "Position X des FPS");
        add("counter.config.fpsOverlayY", "Position Y des FPS");
        add("counter.config.fpsOverlaySize", "Taille des FPS");
        add("counter.config.fpsOverlayAlign", "Alignement des FPS");
        add("counter.config.fpsOverlayTextColor", "Couleur du texte des FPS");

        // --- Configurations : Ping & Émojis ---
        add("counter.config.showPingAsText", "Afficher le ping sous forme de texte");
        add("counter.config.pingColorGood", "Couleur pour un bon ping");
        add("counter.config.pingColorMedium", "Couleur pour un ping moyen");
        add("counter.config.pingColorBad", "Couleur pour un mauvais ping");
        add("counter.config.showEmojis", "Afficher les émojis");

        // --- Configurations : Général & Serveur ---
        add("counter.config.enableDayCounter", "Activer le compteur de jours");
        add("counter.config.showOverlay", "Afficher l'overlay");
        add("counter.config.enableDayMessage", "Activer le message du nouveau jour");
        add("counter.config.showDayInChat", "Afficher le jour dans le chat lors de la connexion");
        add("counter.config.enableDeathCounter", "Activer le compteur de morts");
        add("counter.config.maxPlayersShown", "Nombre maximum de joueurs affichés");
        add("counter.config.deathOverlayMode", "Mode d'overlay des morts");
        add("counter.config.showDeathInChat", "Afficher les morts dans le chat");
        add("counter.config.showDeathInChatMode", "Déclencheur des messages de mort dans le chat");
        add("counter.config.deathChatMode", "Mode de chat pour les morts");
        add("counter.config.showDeathListOnDeathGlobal", "Diffuser la liste des morts à tout le monde");
        add("counter.config.deathListChatTextColor", "Couleur du texte de la liste des morts dans le chat");
        add("counter.config.deathSelfChatTextColor", "Couleur du texte de sa propre mort dans le chat");

        add("counter.config.enableSurvivalCounter", "Activer le compteur de survie");
        add("counter.config.survivalUseRealTime", "Utiliser le temps réel");
        add("counter.config.survivalTimeFormat", "Format du temps de survie");
        add("counter.config.showBestSurvivalTime", "Afficher le meilleur temps de survie");
        add("counter.config.showBestSurvivalInDeathCounter", "Ajouter le record au message de mort");
        add("counter.config.survivalHistorySize", "Taille de l'historique de survie");
        add("counter.config.showSurvivalInChat", "Afficher le temps de survie dans le chat");
        add("counter.config.showSurvivalInChatMode", "Déclencheur du chat de survie");
        add("counter.config.showSurvivalInChatGlobal", "Diffuser le temps de survie");
        add("counter.config.showBestSurvivalInChat", "Afficher le meilleur temps dans le chat");

        add("counter.config.enableTimeCounter", "Activer le compteur de temps");
        add("counter.config.showCombinedDayTime", "Afficher le jour et l'heure combinés");
        add("counter.config.timeFormat24h", "Format 24 heures");

        add("counter.config.enableCoordsCounter", "Activer le compteur de coordonnées");

        add("counter.config.enableFpsCounter", "Activer le compteur de FPS");

        // --- Configurations : Commandes ---
        add("counter.config.enableDayCommand", "Activer la commande jour");
        add("counter.config.enableDeathCommand", "Activer la commande mort");
        add("counter.config.enableSurvivalCommand", "Activer la commande survie");
        add("counter.config.enableTimeCommand", "Activer la commande heure");
        add("counter.config.enableCoordsCommand", "Activer la commande coordonnées");

        // --- Configurations : Titres ---
        add("counter.config.title.dayOverlay", "Paramètres de l'overlay du jour");
        add("counter.config.title.deathList", "Paramètres de la liste des morts");
        add("counter.config.title.deathSelf", "Paramètres du compteur de morts personnel");
        add("counter.config.title.survivalOverlay", "Paramètres de l'overlay de survie");
        add("counter.config.title.timeOverlay", "Paramètres de l'overlay du temps");
        add("counter.config.title.coordsOverlay", "Paramètres de l'overlay des coordonnées");
        add("counter.config.title.fpsOverlay", "Paramètres de l'overlay des FPS");
        add("counter.config.title.ping", "Paramètres du ping");
        add("counter.config.title.emote", "Paramètres des émojis");

        add("counter.config.title.dayCounter", "Paramètres du compteur de jours");
        add("counter.config.title.deathCounter", "Paramètres du compteur de morts");
        add("counter.config.title.deathCounterChat", "Paramètres du chat des morts");
        add("counter.config.title.survivalCounter", "Paramètres du compteur de survie");
        add("counter.config.title.survivalCounterChat", "Paramètres du chat de survie");
        add("counter.config.title.timeCounter", "Paramètres du compteur de temps");
        add("counter.config.title.coordsCounter", "Paramètres de l'overlay des coordonnées");
        add("counter.config.title.fpsCounter", "Paramètres du compteur de FPS");
        add("counter.config.title.commands", "Paramètres des commandes");
    }
}
