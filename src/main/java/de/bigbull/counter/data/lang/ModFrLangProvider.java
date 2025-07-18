package de.bigbull.counter.data.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModFrLangProvider extends FabricLanguageProvider {
    public ModFrLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "fr_fr", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        // --- Overlay Texts ---
        translationBuilder.add("overlay.counter.day_with_emoji", "🌞 Jour : %s");
        translationBuilder.add("overlay.counter.day_no_emoji", "Jour : %s");
        translationBuilder.add("combined.daytime_with_emoji", "🌞 Jour : %s, %s");
        translationBuilder.add("combined.daytime_no_emoji", "Jour : %s, %s");

        translationBuilder.add("overlay.counter.deaths_with_emoji", "💀 Morts : %s");
        translationBuilder.add("overlay.counter.deaths_no_emoji", "Morts : %s");
        translationBuilder.add("overlay.counter.deathlist", "💀 Compteur de morts :");
        translationBuilder.add("overlay.counter.deathlist.entry.full", "%s %s");
        translationBuilder.add("overlay.counter.deathlist.entry.singular", "%s : %s mort");
        translationBuilder.add("overlay.counter.deathlist.entry.plural", "%s : %s morts");
        translationBuilder.add("overlay.counter.deathlist.style.invalid", "ERREUR : Style de liste de morts invalide !");

        translationBuilder.add("overlay.counter.survival_with_emoji", "⏳ Survécu : %s");
        translationBuilder.add("overlay.counter.survival_no_emoji", "Survécu : %s");
        translationBuilder.add("overlay.counter.best_survival_with_emoji", "⏳ Meilleur : %s");
        translationBuilder.add("overlay.counter.best_survival_no_emoji", "Meilleur : %s");

        // --- Key Bindings ---
        translationBuilder.add("key.counter.edit_mode", "Mode édition");
        translationBuilder.add("key.counter.show_overlays", "Afficher les overlays");
        translationBuilder.add("key.categories.counter", "Compteur");

        // --- Overlay Edit Screen ---
        translationBuilder.add("screen.overlay_edit", "Écran d'édition d'overlay");
        translationBuilder.add("screen.overlay_edit.done", "Terminé");
        translationBuilder.add("screen.overlay_edit.cancel", "Annuler");
        translationBuilder.add("screen.overlay_edit.toggle_overlay", "Basculer l'overlay");

        // --- Chat translations ---
        translationBuilder.add("chat.daycounter.new_day", "🌞 C'est maintenant le jour %s !");
        translationBuilder.add("chat.deathcounter.player_death.singular", "💀 %s est mort une fois !");
        translationBuilder.add("chat.deathcounter.player_death.plural", "💀 %s est mort %s fois !");
        translationBuilder.add("chat.survivalcounter.personal", "⏳ Survécu : %s");
        translationBuilder.add("chat.survivalcounter.personal.best", "⏳ Survécu : %s (%s)");
        translationBuilder.add("chat.survivalcounter.broadcast", "⏳ %s a survécu pendant %s");
        translationBuilder.add("chat.survivalcounter.broadcast.best", "⏳ %s a survécu pendant %s (%s)");

        // --- Command translations ---
        translationBuilder.add("command.daycounter.set", "🌞 Compteur de jours réglé à %s !");
        translationBuilder.add("command.deathcounter.reset", "💀 Compteur de morts réinitialisé !");
        translationBuilder.add("command.deathcounter.set", "💀 Nombre de morts de %s réglé à %s !");
        translationBuilder.add("command.survival.global_best", "⏳ %s détient le meilleur temps : %s");
        translationBuilder.add("command.coords.broadcast", "%s a partagé ses coordonnées : X: %s, Y: %s, Z: %s");
        translationBuilder.add("command.player_not_found", "❌ Joueur non trouvé !");
        translationBuilder.add("command.counter.no_data", "Aucune donnée disponible pour le moment.");

        // --- Config translations: Day Overlay ---
        translationBuilder.add("counter.config.showDayOverlay", "Afficher l'overlay du jour");
        translationBuilder.add("counter.config.showOverlayAlways", "Toujours afficher l'overlay");
        translationBuilder.add("counter.config.dayOverlayX", "Jour Overlay X");
        translationBuilder.add("counter.config.dayOverlayY", "Jour Overlay Y");
        translationBuilder.add("counter.config.dayOverlaySize", "Taille de l'overlay du jour");
        translationBuilder.add("counter.config.dayOverlayAlign", "Alignement de l'overlay du jour");
        translationBuilder.add("counter.config.dayOverlayTextColor", "Couleur du texte de l'overlay du jour");

        // --- Config translations: Death List Overlay ---
        translationBuilder.add("counter.config.showDeathListOverlay", "Afficher la liste des morts");
        translationBuilder.add("counter.config.showListOverlayAlways", "Toujours afficher la liste des morts");
        translationBuilder.add("counter.config.deathListX", "Liste des morts X");
        translationBuilder.add("counter.config.deathListY", "Liste des morts Y");
        translationBuilder.add("counter.config.deathListSize", "Taille de la liste des morts");
        translationBuilder.add("counter.config.deathOverlayStyle", "Style de la liste des morts");
        translationBuilder.add("counter.config.deathOverlayMinWidth", "Largeur minimale de la liste des morts");
        translationBuilder.add("counter.config.deathListAlign", "Alignement de la liste des morts");
        translationBuilder.add("counter.config.deathListTextColor", "Couleur du texte de la liste des morts");
        translationBuilder.add("counter.config.firstPlaceColor", "Couleur de la première place");
        translationBuilder.add("counter.config.secondPlaceColor", "Couleur de la deuxième place");
        translationBuilder.add("counter.config.thirdPlaceColor", "Couleur de la troisième place");

        // --- Config translations: Personal Death Counter ---
        translationBuilder.add("counter.config.showDeathSelfOverlay", "Afficher le compteur de morts personnel");
        translationBuilder.add("counter.config.showSelfOverlayAlways", "Toujours afficher le compteur personnel");
        translationBuilder.add("counter.config.deathSelfX", "Mort personnel X");
        translationBuilder.add("counter.config.deathSelfY", "Mort personnel Y");
        translationBuilder.add("counter.config.deathSelfSize", "Taille du compteur personnel");
        translationBuilder.add("counter.config.deathSelfAlign", "Alignement du compteur personnel");
        translationBuilder.add("counter.config.deathSelfTextColor", "Couleur du texte du compteur personnel");

        // --- Config translations: Survival Overlay ---
        translationBuilder.add("counter.config.showSurvivalOverlay", "Afficher l'overlay de survie");
        translationBuilder.add("counter.config.showSurvivalOverlayAlways", "Toujours afficher l'overlay de survie");
        translationBuilder.add("counter.config.survivalOverlayX", "Survie Overlay X");
        translationBuilder.add("counter.config.survivalOverlayY", "Survie Overlay Y");
        translationBuilder.add("counter.config.survivalOverlaySize", "Taille de l'overlay de survie");
        translationBuilder.add("counter.config.survivalOverlayAlign", "Alignement de l'overlay de survie");
        translationBuilder.add("counter.config.survivalOverlayTextColor", "Couleur du texte de l'overlay de survie");

        // --- Config translations: Time Overlay ---
        translationBuilder.add("counter.config.showTimeOverlay", "Afficher l'overlay de l'heure");
        translationBuilder.add("counter.config.showTimeOverlayAlways", "Toujours afficher l'heure");
        translationBuilder.add("counter.config.timeOverlayX", "Heure Overlay X");
        translationBuilder.add("counter.config.timeOverlayY", "Heure Overlay Y");
        translationBuilder.add("counter.config.timeOverlaySize", "Taille de l'overlay de l'heure");
        translationBuilder.add("counter.config.timeOverlayAlign", "Alignement de l'overlay de l'heure");
        translationBuilder.add("counter.config.timeOverlayTextColor", "Couleur du texte de l'overlay de l'heure");

        // --- Config translations: Coordinates Overlay ---
        translationBuilder.add("counter.config.showCoordsOverlay", "Afficher l'overlay des coordonnées");
        translationBuilder.add("counter.config.showCoordsOverlayAlways", "Toujours afficher les coordonnées");
        translationBuilder.add("counter.config.coordsOverlayX", "Coordonnées Overlay X");
        translationBuilder.add("counter.config.coordsOverlayY", "Coordonnées Overlay Y");
        translationBuilder.add("counter.config.coordsOverlaySize", "Taille de l'overlay des coordonnées");
        translationBuilder.add("counter.config.coordsOverlayAlign", "Alignement de l'overlay des coordonnées");
        translationBuilder.add("counter.config.coordsOverlayTextColor", "Couleur du texte des coordonnées");

        // --- Config translations: Ping & Emojis ---
        translationBuilder.add("counter.config.showPingAsText", "Afficher le ping en texte");
        translationBuilder.add("counter.config.pingColorGood", "Couleur de bon ping");
        translationBuilder.add("counter.config.pingColorMedium", "Couleur de ping moyen");
        translationBuilder.add("counter.config.pingColorBad", "Couleur de mauvais ping");
        translationBuilder.add("counter.config.showEmojis", "Afficher les emojis");

        // --- Config translations: General & Misc ---
        translationBuilder.add("counter.config.enableDayCounter", "Activer le compteur de jours");
        translationBuilder.add("counter.config.showOverlay", "Afficher l'overlay");
        translationBuilder.add("counter.config.enableDayMessage", "Activer le message du jour");
        translationBuilder.add("counter.config.showDayInChat", "Afficher le jour dans le chat");
        translationBuilder.add("counter.config.enableDeathCounter", "Activer le compteur de morts");
        translationBuilder.add("counter.config.maxPlayersShown", "Nombre max de joueurs affichés");
        translationBuilder.add("counter.config.deathOverlayMode", "Mode de la liste des morts");
        translationBuilder.add("counter.config.showDeathInChat", "Afficher la mort dans le chat");
        translationBuilder.add("counter.config.showDeathInChatMode", "Déclencheur du chat de mort");
        translationBuilder.add("counter.config.deathChatMode", "Mode du chat de mort");
        translationBuilder.add("counter.config.showDeathListOnDeathGlobal", "Diffuser la liste des morts");
        translationBuilder.add("counter.config.deathListChatTextColor", "Couleur du chat de la liste des morts");
        translationBuilder.add("counter.config.deathSelfChatTextColor", "Couleur du chat du compteur personnel");

        translationBuilder.add("counter.config.enableSurvivalCounter", "Activer le compteur de survie");
        translationBuilder.add("counter.config.survivalUseRealTime", "Utiliser le temps réel");
        translationBuilder.add("counter.config.survivalTimeFormat", "Format du temps de survie");
        translationBuilder.add("counter.config.showBestSurvivalTime", "Afficher le meilleur temps de survie");
        translationBuilder.add("counter.config.showBestSurvivalInDeathCounter", "Afficher le meilleur dans le compteur de morts");
        translationBuilder.add("counter.config.survivalHistorySize", "Taille de l'historique de survie");
        translationBuilder.add("counter.config.showSurvivalInChat", "Afficher la survie dans le chat");
        translationBuilder.add("counter.config.showSurvivalInChatMode", "Déclencheur du chat de survie");
        translationBuilder.add("counter.config.showSurvivalInChatGlobal", "Diffuser le temps de survie");
        translationBuilder.add("counter.config.showBestSurvivalInChat", "Afficher le meilleur dans le chat");

        translationBuilder.add("counter.config.enableTimeCounter", "Activer le compteur de temps");
        translationBuilder.add("counter.config.showCombinedDayTime", "Afficher jour/heure combinés");
        translationBuilder.add("counter.config.timeFormat24h", "Format 24h");

        translationBuilder.add("counter.config.enableCoordsCounter", "Activer le compteur de coordonnées");

        // --- Config translations: Commands ---
        translationBuilder.add("counter.config.enableDayCommand", "Activer la commande de jour");
        translationBuilder.add("counter.config.enableDeathCommand", "Activer la commande de morts");
        translationBuilder.add("counter.config.enableSurvivalCommand", "Activer la commande de survie");
        translationBuilder.add("counter.config.enableTimeCommand", "Activer la commande de temps");
        translationBuilder.add("counter.config.enableCoordsCommand", "Activer la commande de coordonnées");

        // --- Config translations: Titles ---
        translationBuilder.add("counter.config.title.dayOverlay", "Paramètres de l'overlay du jour");
        translationBuilder.add("counter.config.title.deathList", "Paramètres de la liste des morts");
        translationBuilder.add("counter.config.title.deathSelf", "Paramètres du compteur personnel");
        translationBuilder.add("counter.config.title.survivalOverlay", "Paramètres de l'overlay de survie");
        translationBuilder.add("counter.config.title.timeOverlay", "Paramètres de l'overlay de l'heure");
        translationBuilder.add("counter.config.title.coordsOverlay", "Paramètres de l'overlay des coordonnées");
        translationBuilder.add("counter.config.title.ping", "Paramètres du ping");
        translationBuilder.add("counter.config.title.emote", "Paramètres des émotes");

        translationBuilder.add("counter.config.title.dayCounter", "Paramètres du compteur de jours");
        translationBuilder.add("counter.config.title.deathCounter", "Paramètres du compteur de morts");
        translationBuilder.add("counter.config.title.deathCounterChat", "Paramètres du chat de morts");
        translationBuilder.add("counter.config.title.survivalCounter", "Paramètres du compteur de survie");
        translationBuilder.add("counter.config.title.survivalCounterChat", "Paramètres du chat de survie");
        translationBuilder.add("counter.config.title.timeCounter", "Paramètres du compteur de temps");
        translationBuilder.add("counter.config.title.coordsCounter", "Paramètres de l'overlay des coordonnées");
        translationBuilder.add("counter.config.title.commands", "Paramètres des commandes");
    }
}