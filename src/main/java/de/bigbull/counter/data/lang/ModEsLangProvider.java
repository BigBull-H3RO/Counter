package de.bigbull.counter.data.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModEsLangProvider extends FabricLanguageProvider {
    public ModEsLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "es_es", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        // --- Overlay Texts ---
        translationBuilder.add("overlay.counter.day_with_emoji", "🌞 Día: %s");
        translationBuilder.add("overlay.counter.day_no_emoji", "Día: %s");
        translationBuilder.add("combined.daytime_with_emoji", "🌞 Día: %s, %s");
        translationBuilder.add("combined.daytime_no_emoji", "Día: %s, %s");

        translationBuilder.add("overlay.counter.deaths_with_emoji", "💀 Muertes: %s");
        translationBuilder.add("overlay.counter.deaths_no_emoji", "Muertes: %s");
        translationBuilder.add("overlay.counter.deathlist", "💀 Contador de muertes:");
        translationBuilder.add("overlay.counter.deathlist.entry.full", "%s %s");
        translationBuilder.add("overlay.counter.deathlist.entry.singular", "%s: %s muerte");
        translationBuilder.add("overlay.counter.deathlist.entry.plural", "%s: %s muertes");
        translationBuilder.add("overlay.counter.deathlist.style.invalid", "ERROR: ¡Estilo de lista de muertes inválido!");

        translationBuilder.add("overlay.counter.survival_with_emoji", "⏳ Sobrevivido: %s");
        translationBuilder.add("overlay.counter.survival_no_emoji", "Sobrevivido: %s");
        translationBuilder.add("overlay.counter.best_survival_with_emoji", "⏳ Mejor: %s");
        translationBuilder.add("overlay.counter.best_survival_no_emoji", "Mejor: %s");

        // --- Key Bindings ---
        translationBuilder.add("key.counter.edit_mode", "Modo de edición");
        translationBuilder.add("key.counter.show_overlays", "Mostrar superposiciones");
        translationBuilder.add("key.categories.counter", "Contador");

        // --- Overlay Edit Screen ---
        translationBuilder.add("screen.overlay_edit", "Pantalla de edición de superposición");
        translationBuilder.add("screen.overlay_edit.done", "Hecho");
        translationBuilder.add("screen.overlay_edit.cancel", "Cancelar");
        translationBuilder.add("screen.overlay_edit.toggle_overlay", "Alternar superposición");

        // --- Chat translations ---
        translationBuilder.add("chat.daycounter.new_day", "🌞 ¡Ahora es el día %s!");
        translationBuilder.add("chat.deathcounter.player_death.singular", "💀 ¡%s ha muerto una vez!");
        translationBuilder.add("chat.deathcounter.player_death.plural", "💀 ¡%s ha muerto %s veces!");
        translationBuilder.add("chat.survivalcounter.personal", "⏳ Sobrevivido: %s");
        translationBuilder.add("chat.survivalcounter.personal.best", "⏳ Sobrevivido: %s (%s)");
        translationBuilder.add("chat.survivalcounter.broadcast", "⏳ %s sobrevivió durante %s");
        translationBuilder.add("chat.survivalcounter.broadcast.best", "⏳ %s sobrevivió durante %s (%s)");

        // --- Command translations ---
        translationBuilder.add("command.daycounter.set", "🌞 ¡Contador de días establecido en %s!");
        translationBuilder.add("command.deathcounter.reset", "💀 ¡El contador de muertes ha sido reiniciado!");
        translationBuilder.add("command.deathcounter.set", "💀 ¡El número de muertes de %s se ha establecido en %s!");
        translationBuilder.add("command.survival.global_best", "⏳ %s tiene el mejor tiempo: %s");
        translationBuilder.add("command.coords.broadcast", "%s ha compartido sus coordenadas: X: %s, Y: %s, Z: %s");
        translationBuilder.add("command.player_not_found", "❌ ¡Jugador no encontrado!");
        translationBuilder.add("command.counter.no_data", "Aún no hay datos disponibles.");

        // --- Config translations: Day Overlay ---
        translationBuilder.add("counter.config.showDayOverlay", "Mostrar superposición del día");
        translationBuilder.add("counter.config.showOverlayAlways", "Mostrar siempre la superposición");
        translationBuilder.add("counter.config.dayOverlayX", "Superposición del día X");
        translationBuilder.add("counter.config.dayOverlayY", "Superposición del día Y");
        translationBuilder.add("counter.config.dayOverlaySize", "Tamaño de la superposición del día");
        translationBuilder.add("counter.config.dayOverlayAlign", "Alineación de la superposición del día");
        translationBuilder.add("counter.config.dayOverlayTextColor", "Color del texto de la superposición del día");

        // --- Config translations: Death List Overlay ---
        translationBuilder.add("counter.config.showDeathListOverlay", "Mostrar lista de muertes");
        translationBuilder.add("counter.config.showListOverlayAlways", "Mostrar siempre la lista de muertes");
        translationBuilder.add("counter.config.deathListX", "Lista de muertes X");
        translationBuilder.add("counter.config.deathListY", "Lista de muertes Y");
        translationBuilder.add("counter.config.deathListSize", "Tamaño de la lista de muertes");
        translationBuilder.add("counter.config.deathOverlayStyle", "Estilo de la lista de muertes");
        translationBuilder.add("counter.config.deathOverlayMinWidth", "Ancho mínimo de la lista de muertes");
        translationBuilder.add("counter.config.deathListAlign", "Alineación de la lista de muertes");
        translationBuilder.add("counter.config.deathListTextColor", "Color del texto de la lista de muertes");
        translationBuilder.add("counter.config.firstPlaceColor", "Color del primer lugar");
        translationBuilder.add("counter.config.secondPlaceColor", "Color del segundo lugar");
        translationBuilder.add("counter.config.thirdPlaceColor", "Color del tercer lugar");

        // --- Config translations: Personal Death Counter ---
        translationBuilder.add("counter.config.showDeathSelfOverlay", "Mostrar contador de muertes personal");
        translationBuilder.add("counter.config.showSelfOverlayAlways", "Mostrar siempre el contador personal");
        translationBuilder.add("counter.config.deathSelfX", "Muerte personal X");
        translationBuilder.add("counter.config.deathSelfY", "Muerte personal Y");
        translationBuilder.add("counter.config.deathSelfSize", "Tamaño del contador personal");
        translationBuilder.add("counter.config.deathSelfAlign", "Alineación del contador personal");
        translationBuilder.add("counter.config.deathSelfTextColor", "Color del texto del contador personal");

        // --- Config translations: Survival Overlay ---
        translationBuilder.add("counter.config.showSurvivalOverlay", "Mostrar superposición de supervivencia");
        translationBuilder.add("counter.config.showSurvivalOverlayAlways", "Mostrar siempre la superposición de supervivencia");
        translationBuilder.add("counter.config.survivalOverlayX", "Supervivencia X");
        translationBuilder.add("counter.config.survivalOverlayY", "Supervivencia Y");
        translationBuilder.add("counter.config.survivalOverlaySize", "Tamaño de la superposición de supervivencia");
        translationBuilder.add("counter.config.survivalOverlayAlign", "Alineación de la superposición de supervivencia");
        translationBuilder.add("counter.config.survivalOverlayTextColor", "Color del texto de la superposición de supervivencia");

        // --- Config translations: Time Overlay ---
        translationBuilder.add("counter.config.showTimeOverlay", "Mostrar superposición de tiempo");
        translationBuilder.add("counter.config.showTimeOverlayAlways", "Mostrar siempre el tiempo");
        translationBuilder.add("counter.config.timeOverlayX", "Tiempo X");
        translationBuilder.add("counter.config.timeOverlayY", "Tiempo Y");
        translationBuilder.add("counter.config.timeOverlaySize", "Tamaño de la superposición de tiempo");
        translationBuilder.add("counter.config.timeOverlayAlign", "Alineación de la superposición de tiempo");
        translationBuilder.add("counter.config.timeOverlayTextColor", "Color del texto de la superposición de tiempo");

        // --- Config translations: Coordinates Overlay ---
        translationBuilder.add("counter.config.showCoordsOverlay", "Mostrar superposición de coordenadas");
        translationBuilder.add("counter.config.showCoordsOverlayAlways", "Mostrar siempre las coordenadas");
        translationBuilder.add("counter.config.coordsOverlayX", "Coordenadas X");
        translationBuilder.add("counter.config.coordsOverlayY", "Coordenadas Y");
        translationBuilder.add("counter.config.coordsOverlaySize", "Tamaño de la superposición de coordenadas");
        translationBuilder.add("counter.config.coordsOverlayAlign", "Alineación de la superposición de coordenadas");
        translationBuilder.add("counter.config.coordsOverlayTextColor", "Color del texto de las coordenadas");

        // --- Config translations: Ping & Emojis ---
        translationBuilder.add("counter.config.showPingAsText", "Mostrar ping como texto");
        translationBuilder.add("counter.config.pingColorGood", "Color de buen ping");
        translationBuilder.add("counter.config.pingColorMedium", "Color de ping medio");
        translationBuilder.add("counter.config.pingColorBad", "Color de mal ping");
        translationBuilder.add("counter.config.showEmojis", "Mostrar emojis");

        // --- Config translations: General & Misc ---
        translationBuilder.add("counter.config.enableDayCounter", "Habilitar contador de días");
        translationBuilder.add("counter.config.showOverlay", "Mostrar superposición");
        translationBuilder.add("counter.config.enableDayMessage", "Habilitar mensaje del día");
        translationBuilder.add("counter.config.showDayInChat", "Mostrar día en el chat");
        translationBuilder.add("counter.config.enableDeathCounter", "Habilitar contador de muertes");
        translationBuilder.add("counter.config.maxPlayersShown", "Máximo de jugadores mostrados");
        translationBuilder.add("counter.config.deathOverlayMode", "Modo de lista de muertes");
        translationBuilder.add("counter.config.showDeathInChat", "Mostrar muerte en el chat");
        translationBuilder.add("counter.config.showDeathInChatMode", "Disparador de chat de muerte");
        translationBuilder.add("counter.config.deathChatMode", "Modo de chat de muerte");
        translationBuilder.add("counter.config.showDeathListOnDeathGlobal", "Transmitir lista de muertes");
        translationBuilder.add("counter.config.deathListChatTextColor", "Color del texto del chat de la lista de muertes");
        translationBuilder.add("counter.config.deathSelfChatTextColor", "Color del texto del chat personal");

        translationBuilder.add("counter.config.enableSurvivalCounter", "Habilitar contador de supervivencia");
        translationBuilder.add("counter.config.survivalUseRealTime", "Usar tiempo real");
        translationBuilder.add("counter.config.survivalTimeFormat", "Formato de tiempo de supervivencia");
        translationBuilder.add("counter.config.showBestSurvivalTime", "Mostrar mejor tiempo de supervivencia");
        translationBuilder.add("counter.config.showBestSurvivalInDeathCounter", "Mostrar mejor en el contador de muertes");
        translationBuilder.add("counter.config.survivalHistorySize", "Tamaño del historial de supervivencia");
        translationBuilder.add("counter.config.showSurvivalInChat", "Mostrar supervivencia en el chat");
        translationBuilder.add("counter.config.showSurvivalInChatMode", "Disparador de chat de supervivencia");
        translationBuilder.add("counter.config.showSurvivalInChatGlobal", "Transmitir tiempo de supervivencia");
        translationBuilder.add("counter.config.showBestSurvivalInChat", "Mostrar mejor en el chat");

        translationBuilder.add("counter.config.enableTimeCounter", "Habilitar contador de tiempo");
        translationBuilder.add("counter.config.showCombinedDayTime", "Mostrar día/tiempo combinado");
        translationBuilder.add("counter.config.timeFormat24h", "Formato 24h");

        translationBuilder.add("counter.config.enableCoordsCounter", "Habilitar contador de coordenadas");

        // --- Config translations: Commands ---
        translationBuilder.add("counter.config.enableDayCommand", "Comando del contador de días");
        translationBuilder.add("counter.config.enableDeathCommand", "Comando del contador de muertes");
        translationBuilder.add("counter.config.enableSurvivalCommand", "Comando del contador de supervivencia");
        translationBuilder.add("counter.config.enableTimeCommand", "Comando del contador de tiempo");
        translationBuilder.add("counter.config.enableCoordsCommand", "Comando del contador de coordenadas");

        // --- Config translations: Titles ---
        translationBuilder.add("counter.config.title.dayOverlay", "Configuración de superposición del día");
        translationBuilder.add("counter.config.title.deathList", "Configuración de lista de muertes");
        translationBuilder.add("counter.config.title.deathSelf", "Configuración de contador personal");
        translationBuilder.add("counter.config.title.survivalOverlay", "Configuración de superposición de supervivencia");
        translationBuilder.add("counter.config.title.timeOverlay", "Configuración de superposición de tiempo");
        translationBuilder.add("counter.config.title.coordsOverlay", "Configuración de superposición de coordenadas");
        translationBuilder.add("counter.config.title.ping", "Configuración de ping");
        translationBuilder.add("counter.config.title.emote", "Configuración de emojis");

        translationBuilder.add("counter.config.title.dayCounter", "Configuración de contador de días");
        translationBuilder.add("counter.config.title.deathCounter", "Configuración de contador de muertes");
        translationBuilder.add("counter.config.title.deathCounterChat", "Configuración de chat de muertes");
        translationBuilder.add("counter.config.title.survivalCounter", "Configuración de contador de supervivencia");
        translationBuilder.add("counter.config.title.survivalCounterChat", "Configuración de chat de supervivencia");
        translationBuilder.add("counter.config.title.timeCounter", "Configuración de contador de tiempo");
        translationBuilder.add("counter.config.title.coordsCounter", "Configuración de superposición de coordenadas");
        translationBuilder.add("counter.config.title.commands", "Configuración de comandos");
    }
}