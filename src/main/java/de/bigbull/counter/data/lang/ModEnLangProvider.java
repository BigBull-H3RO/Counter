package de.bigbull.counter.data.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModEnLangProvider extends FabricLanguageProvider {
    public ModEnLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        // --- Overlay Texts ---
        translationBuilder.add("overlay.counter.day_with_emoji", "🌞 Day: %s");
        translationBuilder.add("overlay.counter.day_no_emoji", "Day: %s");
        translationBuilder.add("combined.daytime_with_emoji", "🌞 Day: %s, %s");
        translationBuilder.add("combined.daytime_no_emoji", "Day: %s, %s");

        translationBuilder.add("overlay.counter.deaths_with_emoji", "💀 Deaths: %s");
        translationBuilder.add("overlay.counter.deaths_no_emoji", "Deaths: %s");
        translationBuilder.add("overlay.counter.deathlist", "💀 Death Counter:");
        translationBuilder.add("overlay.counter.deathlist.entry.full", "%s %s");
        translationBuilder.add("overlay.counter.deathlist.entry.singular", "%s: %s death");
        translationBuilder.add("overlay.counter.deathlist.entry.plural", "%s: %s deaths");
        translationBuilder.add("overlay.counter.deathlist.style.invalid", "ERROR: Invalid death list style!");

        translationBuilder.add("overlay.counter.survival_with_emoji", "⏳ Survived: %s");
        translationBuilder.add("overlay.counter.survival_no_emoji", "Survived: %s");
        translationBuilder.add("overlay.counter.best_survival_with_emoji", "⏳ Best: %s");
        translationBuilder.add("overlay.counter.best_survival_no_emoji", "Best: %s");

        // --- Key Bindings ---
        translationBuilder.add("key.counter.edit_mode", "Edit Mode");
        translationBuilder.add("key.counter.show_overlays", "Show Overlays");
        translationBuilder.add("key.categories.counter", "Counter");

        // --- Overlay Edit Screen ---
        translationBuilder.add("screen.overlay_edit", "Overlay Edit Screen");
        translationBuilder.add("screen.overlay_edit.done", "Done");
        translationBuilder.add("screen.overlay_edit.cancel", "Cancel");
        translationBuilder.add("screen.overlay_edit.toggle_overlay", "Toggle Overlay");

        // --- Chat translations ---
        translationBuilder.add("chat.daycounter.new_day", "🌞 It's now day %s!");
        translationBuilder.add("chat.deathcounter.player_death.singular", "💀 %s has died once!");
        translationBuilder.add("chat.deathcounter.player_death.plural", "💀 %s has died %s times!");
        translationBuilder.add("chat.survivalcounter.personal", "⏳ Survived: %s");
        translationBuilder.add("chat.survivalcounter.personal.best", "⏳ Survived: %s (%s)");
        translationBuilder.add("chat.survivalcounter.broadcast", "⏳ %s survived for %s");
        translationBuilder.add("chat.survivalcounter.broadcast.best", "⏳ %s survived for %s (%s)");

        // --- Command translations ---
        translationBuilder.add("command.daycounter.set", "🌞 Day Counter set to %s!");
        translationBuilder.add("command.deathcounter.reset", "💀 Death Counter has been reset!");
        translationBuilder.add("command.deathcounter.set", "💀 Set the death count of %s to %s!");
        translationBuilder.add("command.survival.global_best", "⏳ %s holds the best time: %s");
        translationBuilder.add("command.coords.broadcast", "%s has shared their coordinates: X: %s, Y: %s, Z: %s");
        translationBuilder.add("command.player_not_found", "❌ Player not found!");
        translationBuilder.add("command.counter.no_data", "No data available yet.");

        // --- Config translations: Day Overlay ---
        translationBuilder.add("counter.config.showDayOverlay", "Show Day Overlay");
        translationBuilder.add("counter.config.showOverlayAlways", "Always Show Overlay");
        translationBuilder.add("counter.config.dayOverlayX", "Day Overlay X");
        translationBuilder.add("counter.config.dayOverlayY", "Day Overlay Y");
        translationBuilder.add("counter.config.dayOverlaySize", "Day Overlay Size");
        translationBuilder.add("counter.config.dayOverlayAlign", "Day Overlay Alignment");
        translationBuilder.add("counter.config.dayOverlayTextColor", "Day Overlay Text Color");

        // --- Config translations: Death List Overlay ---
        translationBuilder.add("counter.config.showDeathListOverlay", "Show Death List Overlay");
        translationBuilder.add("counter.config.showListOverlayAlways", "Always Show Death List");
        translationBuilder.add("counter.config.deathListX", "Death List X");
        translationBuilder.add("counter.config.deathListY", "Death List Y");
        translationBuilder.add("counter.config.deathListSize", "Death List Size");
        translationBuilder.add("counter.config.deathOverlayStyle", "Death List Style");
        translationBuilder.add("counter.config.deathOverlayMinWidth", "Death List Min Width");
        translationBuilder.add("counter.config.deathListAlign", "Death List Alignment");
        translationBuilder.add("counter.config.deathListTextColor", "Death List Text Color");
        translationBuilder.add("counter.config.firstPlaceColor", "First Place Color");
        translationBuilder.add("counter.config.secondPlaceColor", "Second Place Color");
        translationBuilder.add("counter.config.thirdPlaceColor", "Third Place Color");

        // --- Config translations: Personal Death Counter ---
        translationBuilder.add("counter.config.showDeathSelfOverlay", "Show Personal Death Overlay");
        translationBuilder.add("counter.config.showSelfOverlayAlways", "Always Show Personal Death");
        translationBuilder.add("counter.config.deathSelfX", "Personal Death X");
        translationBuilder.add("counter.config.deathSelfY", "Personal Death Y");
        translationBuilder.add("counter.config.deathSelfSize", "Personal Death Size");
        translationBuilder.add("counter.config.deathSelfAlign", "Personal Death Alignment");
        translationBuilder.add("counter.config.deathSelfTextColor", "Personal Death Text Color");

        // --- Config translations: Survival Overlay ---
        translationBuilder.add("counter.config.showSurvivalOverlay", "Show Survival Overlay");
        translationBuilder.add("counter.config.showSurvivalOverlayAlways", "Always Show Survival");
        translationBuilder.add("counter.config.survivalOverlayX", "Survival Overlay X");
        translationBuilder.add("counter.config.survivalOverlayY", "Survival Overlay Y");
        translationBuilder.add("counter.config.survivalOverlaySize", "Survival Overlay Size");
        translationBuilder.add("counter.config.survivalOverlayAlign", "Survival Overlay Alignment");
        translationBuilder.add("counter.config.survivalOverlayTextColor", "Survival Overlay Text Color");

        // --- Config translations: Time Overlay ---
        translationBuilder.add("counter.config.showTimeOverlay", "Show Time Overlay");
        translationBuilder.add("counter.config.showTimeOverlayAlways", "Always Show Time");
        translationBuilder.add("counter.config.timeOverlayX", "Time Overlay X");
        translationBuilder.add("counter.config.timeOverlayY", "Time Overlay Y");
        translationBuilder.add("counter.config.timeOverlaySize", "Time Overlay Size");
        translationBuilder.add("counter.config.timeOverlayAlign", "Time Overlay Alignment");
        translationBuilder.add("counter.config.timeOverlayTextColor", "Time Overlay Text Color");

        // --- Config translations: Coordinates Overlay ---
        translationBuilder.add("counter.config.showCoordsOverlay", "Show Coordinates Overlay");
        translationBuilder.add("counter.config.showCoordsOverlayAlways", "Always Show Coordinates");
        translationBuilder.add("counter.config.coordsOverlayX", "Coords Overlay X");
        translationBuilder.add("counter.config.coordsOverlayY", "Coords Overlay Y");
        translationBuilder.add("counter.config.coordsOverlaySize", "Coords Overlay Size");
        translationBuilder.add("counter.config.coordsOverlayAlign", "Coords Overlay Alignment");
        translationBuilder.add("counter.config.coordsOverlayTextColor", "Coords Overlay Text Color");

        // --- Config translations: Ping & Emojis ---
        translationBuilder.add("counter.config.showPingAsText", "Show Ping As Text");
        translationBuilder.add("counter.config.pingColorGood", "Good Ping Color");
        translationBuilder.add("counter.config.pingColorMedium", "Medium Ping Color");
        translationBuilder.add("counter.config.pingColorBad", "Bad Ping Color");
        translationBuilder.add("counter.config.showEmojis", "Show Emojis");

        // --- Config translations: General & Misc ---
        translationBuilder.add("counter.config.enableDayCounter", "Enable Day Counter");
        translationBuilder.add("counter.config.showOverlay", "Show Overlay");
        translationBuilder.add("counter.config.enableDayMessage", "Enable Day Message");
        translationBuilder.add("counter.config.showDayInChat", "Show Day In Chat");
        translationBuilder.add("counter.config.enableDeathCounter", "Enable Death Counter");
        translationBuilder.add("counter.config.maxPlayersShown", "Max Players Shown");
        translationBuilder.add("counter.config.deathOverlayMode", "Death Overlay Mode");
        translationBuilder.add("counter.config.showDeathInChat", "Show Death In Chat");
        translationBuilder.add("counter.config.showDeathInChatMode", "Death Chat Trigger");
        translationBuilder.add("counter.config.deathChatMode", "Death Chat Mode");
        translationBuilder.add("counter.config.showDeathListOnDeathGlobal", "Broadcast Death List");
        translationBuilder.add("counter.config.deathListChatTextColor", "Death List Chat Color");
        translationBuilder.add("counter.config.deathSelfChatTextColor", "Personal Death Chat Color");

        translationBuilder.add("counter.config.enableSurvivalCounter", "Enable Survival Counter");
        translationBuilder.add("counter.config.survivalUseRealTime", "Use Real Time");
        translationBuilder.add("counter.config.survivalTimeFormat", "Survival Time Format");
        translationBuilder.add("counter.config.showBestSurvivalTime", "Show Best Survival Time");
        translationBuilder.add("counter.config.showBestSurvivalInDeathCounter", "Show Best In Death Counter");
        translationBuilder.add("counter.config.survivalHistorySize", "Survival History Size");
        translationBuilder.add("counter.config.showSurvivalInChat", "Show Survival In Chat");
        translationBuilder.add("counter.config.showSurvivalInChatMode", "Survival Chat Trigger");
        translationBuilder.add("counter.config.showSurvivalInChatGlobal", "Broadcast Survival Time");
        translationBuilder.add("counter.config.showBestSurvivalInChat", "Show Best In Chat");

        translationBuilder.add("counter.config.enableTimeCounter", "Enable Time Counter");
        translationBuilder.add("counter.config.showCombinedDayTime", "Show Combined Day/Time");
        translationBuilder.add("counter.config.timeFormat24h", "24h Format");

        translationBuilder.add("counter.config.enableCoordsCounter", "Enable Coords Counter");

        // --- Config translations: Commands ---
        translationBuilder.add("counter.config.enableDayCommand", "Enable Day Command");
        translationBuilder.add("counter.config.enableDeathCommand", "Enable Death Command");
        translationBuilder.add("counter.config.enableSurvivalCommand", "Enable Survival Command");
        translationBuilder.add("counter.config.enableTimeCommand", "Enable Time Command");
        translationBuilder.add("counter.config.enableCoordsCommand", "Enable Coords Command");

        // --- Config translations: Titles ---
        translationBuilder.add("counter.config.title.dayOverlay", "Day Counter Overlay Settings");
        translationBuilder.add("counter.config.title.deathList", "Death Counter List Settings");
        translationBuilder.add("counter.config.title.deathSelf", "Death Counter Self Settings");
        translationBuilder.add("counter.config.title.survivalOverlay", "Survival Counter Overlay Settings");
        translationBuilder.add("counter.config.title.timeOverlay", "Time Overlay Settings");
        translationBuilder.add("counter.config.title.coordsOverlay", "Coordinates Overlay Settings");
        translationBuilder.add("counter.config.title.ping", "Ping Settings");
        translationBuilder.add("counter.config.title.emote", "Emote Settings");

        translationBuilder.add("counter.config.title.dayCounter", "Day Counter Settings");
        translationBuilder.add("counter.config.title.deathCounter", "Death Counter Settings");
        translationBuilder.add("counter.config.title.deathCounterChat", "Death Counter Chat Settings");
        translationBuilder.add("counter.config.title.survivalCounter", "Survival Counter Settings");
        translationBuilder.add("counter.config.title.survivalCounterChat", "Survival Counter Chat Settings");
        translationBuilder.add("counter.config.title.timeCounter", "Time Counter Settings");
        translationBuilder.add("counter.config.title.coordsCounter", "Coordinates Overlay Settings");
        translationBuilder.add("counter.config.title.commands", "Command Settings");
    }
}