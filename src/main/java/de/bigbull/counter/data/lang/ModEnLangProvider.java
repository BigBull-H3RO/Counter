package de.bigbull.counter.data.lang;

import de.bigbull.counter.Counter;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnLangProvider extends LanguageProvider {
    public ModEnLangProvider(PackOutput output) {
        super(output, Counter.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("overlay.counter.day_with_emoji", "🌞 Day: %s");
        add("overlay.counter.day_no_emoji", "Day: %s");
        add("command.daycounter.set", "🌞 Day Counter set to %s!");
        add("chat.daycounter.new_day", "🌞 It's now day %s!");
        add("combined.daytime_with_emoji", "🌞 Day: %s, %s");
        add("combined.daytime_no_emoji", "Day: %s, %s");

        add("overlay.counter.deaths_with_emoji", "💀 Deaths: %s");
        add("overlay.counter.deaths_no_emoji", "Deaths: %s");
        add("chat.deathcounter.player_death.singular", "💀 %s has died once!");
        add("chat.deathcounter.player_death.plural", "💀 %s has died %s times!");
        add("overlay.counter.deathlist", "💀 Death Counter:");
        add("overlay.counter.deathlist.entry.full", "%s %s");
        add("overlay.counter.deathlist.entry.singular", "%s: %s death");
        add("overlay.counter.deathlist.entry.plural", "%s: %s deaths");
        add("overlay.counter.deathlist.style.invalid", "ERROR: Invalid death list style!");
        add("command.deathcounter.reset", "💀 Death Counter has been reset!");
        add("command.deathcounter.set", "💀 Set the death count of %s to %s!");

        add("overlay.counter.survival_with_emoji", "⏳ Survived: %s");
        add("overlay.counter.survival_no_emoji", "Survived: %s");
        add("overlay.counter.best_survival_with_emoji", "⏳ Best: %s");
        add("overlay.counter.best_survival_no_emoji", "Best: %s");
        add("command.survival.global_best", "⏳ %s holds the best time: %s");

        add("chat.survivalcounter.personal", "⏳ Survived: %s");
        add("chat.survivalcounter.personal.best", "⏳ Survived: %s (%s)");
        add("chat.survivalcounter.broadcast", "⏳ %s survived for %s");
        add("chat.survivalcounter.broadcast.best", "⏳ %s survived for %s (%s)");

        add("command.coords.broadcast", "%s has shared their coordinates: X:%s, Y:%s, Z:%s");
        add("command.coords.player_not_found", "❌ Player not found!");

        add("key.counter.edit_mode", "Edit Mode");
        add("key.counter.show_overlays", "Show Overlays");
        add("key.categories.counter", "Counter");

        add("screen.overlay_edit", "Overlay Edit Screen");
        add("screen.overlay_edit.done", "Done");
        add("screen.overlay_edit.cancel", "Cancel");
        add("screen.overlay_edit.toggle_overlay", "Toggle Overlay");

        // Config translations
        add("counter.config.showDayOverlay", "Show Day Overlay");
        add("counter.config.showOverlayAlways", "Always Show Overlay");
        add("counter.config.dayOverlayX", "Day Overlay X");
        add("counter.config.dayOverlayY", "Day Overlay Y");
        add("counter.config.dayOverlaySize", "Day Overlay Size");
        add("counter.config.dayOverlayAlign", "Day Overlay Alignment");
        add("counter.config.dayOverlayTextColor", "Day Overlay Text Color");

        add("counter.config.showDeathListOverlay", "Show Death List Overlay");
        add("counter.config.showListOverlayAlways", "Always Show Death List");
        add("counter.config.deathListX", "Death List X");
        add("counter.config.deathListY", "Death List Y");
        add("counter.config.deathListSize", "Death List Size");
        add("counter.config.deathOverlayStyle", "Death List Style");
        add("counter.config.deathOverlayMinWidth", "Death List Min Width");
        add("counter.config.deathListAlign", "Death List Alignment");
        add("counter.config.deathListTextColor", "Death List Text Color");
        add("counter.config.firstPlaceColor", "First Place Color");
        add("counter.config.secondPlaceColor", "Second Place Color");
        add("counter.config.thirdPlaceColor", "Third Place Color");

        add("counter.config.showDeathSelfOverlay", "Show Personal Death Overlay");
        add("counter.config.showSelfOverlayAlways", "Always Show Personal Death");
        add("counter.config.deathSelfX", "Personal Death X");
        add("counter.config.deathSelfY", "Personal Death Y");
        add("counter.config.deathSelfSize", "Personal Death Size");
        add("counter.config.deathSelfAlign", "Personal Death Alignment");
        add("counter.config.deathSelfTextColor", "Personal Death Text Color");

        add("counter.config.showSurvivalOverlay", "Show Survival Overlay");
        add("counter.config.showSurvivalOverlayAlways", "Always Show Survival");
        add("counter.config.survivalOverlayX", "Survival Overlay X");
        add("counter.config.survivalOverlayY", "Survival Overlay Y");
        add("counter.config.survivalOverlaySize", "Survival Overlay Size");
        add("counter.config.survivalOverlayAlign", "Survival Overlay Alignment");
        add("counter.config.survivalOverlayTextColor", "Survival Overlay Text Color");

        add("counter.config.showTimeOverlay", "Show Time Overlay");
        add("counter.config.showTimeOverlayAlways", "Always Show Time");
        add("counter.config.timeOverlayX", "Time Overlay X");
        add("counter.config.timeOverlayY", "Time Overlay Y");
        add("counter.config.timeOverlaySize", "Time Overlay Size");
        add("counter.config.timeOverlayAlign", "Time Overlay Alignment");
        add("counter.config.timeOverlayTextColor", "Time Overlay Text Color");

        add("counter.config.showCoordsOverlay", "Show Coordinates Overlay");
        add("counter.config.showCoordsOverlayAlways", "Always Show Coordinates");
        add("counter.config.coordsOverlayX", "Coords Overlay X");
        add("counter.config.coordsOverlayY", "Coords Overlay Y");
        add("counter.config.coordsOverlaySize", "Coords Overlay Size");
        add("counter.config.coordsOverlayAlign", "Coords Overlay Alignment");
        add("counter.config.coordsOverlayTextColor", "Coords Overlay Text Color");

        add("counter.config.showPingAsText", "Show Ping As Text");
        add("counter.config.pingColorGood", "Good Ping Color");
        add("counter.config.pingColorMedium", "Medium Ping Color");
        add("counter.config.pingColorBad", "Bad Ping Color");

        add("counter.config.showEmojis", "Show Emojis");

        add("counter.config.enableDayCounter", "Enable Day Counter");
        add("counter.config.showOverlay", "Show Overlay");
        add("counter.config.enableDayMessage", "Enable Day Message");
        add("counter.config.showDayInChat", "Show Day In Chat");
        add("counter.config.enableDeathCounter", "Enable Death Counter");
        add("counter.config.maxPlayersShown", "Max Players Shown");
        add("counter.config.deathOverlayMode", "Death Overlay Mode");
        add("counter.config.showDeathInChat", "Show Death In Chat");
        add("counter.config.showDeathInChatMode", "Death Chat Trigger");
        add("counter.config.deathChatMode", "Death Chat Mode");
        add("counter.config.showDeathListOnDeathGlobal", "Broadcast Death List");
        add("counter.config.deathListChatTextColor", "Death List Chat Color");
        add("counter.config.deathSelfChatTextColor", "Personal Death Chat Color");

        add("counter.config.enableSurvivalCounter", "Enable Survival Counter");
        add("counter.config.survivalUseRealTime", "Use Real Time");
        add("counter.config.survivalTimeFormat", "Survival Time Format");
        add("counter.config.showBestSurvivalTime", "Show Best Survival Time");
        add("counter.config.showBestSurvivalInDeathCounter", "Show Best In Death Counter");
        add("counter.config.survivalHistorySize", "Survival History Size");
        add("counter.config.showSurvivalInChat", "Show Survival In Chat");
        add("counter.config.showSurvivalInChatMode", "Survival Chat Trigger");
        add("counter.config.showSurvivalInChatGlobal", "Broadcast Survival Time");
        add("counter.config.showBestSurvivalInChat", "Show Best In Chat");

        add("counter.config.enableTimeCounter", "Enable Time Counter");
        add("counter.config.showCombinedDayTime", "Show Combined Day/Time");
        add("counter.config.timeFormat24h", "24h Format");

        add("counter.config.enableCoordsCounter", "Enable Coords Counter");

        add("counter.config.title.dayOverlay", "Day Counter Overlay Settings");
        add("counter.config.title.deathList", "Death Counter List Settings");
        add("counter.config.title.deathSelf", "Death Counter Self Settings");
        add("counter.config.title.survivalOverlay", "Survival Counter Overlay Settings");
        add("counter.config.title.timeOverlay", "Time Overlay Settings");
        add("counter.config.title.coordsOverlay", "Coordinates Overlay Settings");
        add("counter.config.title.ping", "Ping Settings");
        add("counter.config.title.emote", "Emote Settings");

        add("counter.config.title.dayCounter", "Day Counter Settings ");
        add("counter.config.title.deathCounter", "Death Counter Settings");
        add("counter.config.title.deathCounterChat", "Death Counter Chat Settings");
        add("counter.config.title.survivalCounter", "Survival Counter Settings");
        add("counter.config.title.survivalCounterChat", "Survival Counter Chat Settings");
        add("counter.config.title.timeCounter", "Time Counter Settings");
        add("counter.config.title.coordsCounter", "Coordinates Overlay Settings");
    }
}