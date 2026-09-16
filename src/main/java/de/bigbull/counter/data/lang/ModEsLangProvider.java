package de.bigbull.counter.data.lang;

import de.bigbull.counter.Counter;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEsLangProvider extends LanguageProvider {
    public ModEsLangProvider(PackOutput output) {
        super(output, Counter.MODID, "es_es");
    }

    @Override
    protected void addTranslations() {
        // --- Overlay Texts ---
        add("overlay.counter.day_with_emoji", "🌞 Día: %s");
        add("overlay.counter.day_no_emoji", "Día: %s");
        add("combined.daytime_with_emoji", "🌞 Día: %s, %s");
        add("combined.daytime_no_emoji", "Día: %s, %s");

        add("overlay.counter.deaths_with_emoji", "💀 Muertes: %s");
        add("overlay.counter.deaths_no_emoji", "Muertes: %s");
        add("overlay.counter.deathlist", "💀 Contador de muertes:");
        add("overlay.counter.deathlist.entry.full", "%s %s");
        add("overlay.counter.deathlist.entry.singular", "%s: %s muerte");
        add("overlay.counter.deathlist.entry.plural", "%s: %s muertes");
        add("overlay.counter.deathlist.style.invalid", "ERROR: ¡Estilo de lista de muertes inválido!");

        add("overlay.counter.fps", "FPS: %s");

        add("overlay.counter.survival_with_emoji", "⏳ Sobrevivido: %s");
        add("overlay.counter.survival_no_emoji", "Sobrevivido: %s");
        add("overlay.counter.best_survival_with_emoji", "⏳ Mejor: %s");
        add("overlay.counter.best_survival_no_emoji", "Mejor: %s");

        // --- Key Bindings ---
        add("key.counter.edit_mode", "Modo de edición");
        add("key.counter.show_overlays", "Mostrar superposiciones");
        add("key.category.counter.main", "Contador");

        // --- Overlay Edit Screen ---
        add("screen.overlay_edit", "Pantalla de edición de superposición");
        add("screen.overlay_edit.done", "Hecho");
        add("screen.overlay_edit.cancel", "Cancelar");
        add("screen.overlay_edit.toggle_overlay", "Alternar superposición");

        // --- Chat translations ---
        add("chat.daycounter.new_day", "🌞 ¡Ahora es el día %s!");
        add("chat.deathcounter.player_death.singular", "💀 ¡%s ha muerto una vez!");
        add("chat.deathcounter.player_death.plural", "💀 ¡%s ha muerto %s veces!");
        add("chat.survivalcounter.personal", "⏳ Sobrevivido: %s");
        add("chat.survivalcounter.personal.best", "⏳ Sobrevivido: %s (%s)");
        add("chat.survivalcounter.broadcast", "⏳ %s sobrevivió durante %s");
        add("chat.survivalcounter.broadcast.best", "⏳ %s sobrevivió durante %s (%s)");

        // --- Command translations ---
        add("command.daycounter.set", "🌞 ¡Contador de días establecido en %s!");
        add("command.deathcounter.reset", "💀 ¡El contador de muertes ha sido reiniciado!");
        add("command.deathcounter.set", "💀 ¡El número de muertes de %s se ha establecido en %s!");
        add("command.survival.global_best", "⏳ %s tiene el mejor tiempo: %s");
        add("command.coords.broadcast", "%s ha compartido sus coordenadas: X: %s, Y: %s, Z: %s");
        add("command.player_not_found", "❌ ¡Jugador no encontrado!");
        add("command.counter.no_data", "Aún no hay datos disponibles.");

        // --- Config translations: Day Overlay ---
        add("counter.config.showDayOverlay", "Mostrar superposición del día");
        add("counter.config.showOverlayAlways", "Mostrar siempre la superposición");
        add("counter.config.dayOverlayX", "Superposición del día X");
        add("counter.config.dayOverlayY", "Superposición del día Y");
        add("counter.config.dayOverlaySize", "Tamaño de la superposición del día");
        add("counter.config.dayOverlayAlign", "Alineación de la superposición del día");
        add("counter.config.dayOverlayTextColor", "Color del texto de la superposición del día");

        // --- Config translations: Death List Overlay ---
        add("counter.config.showDeathListOverlay", "Mostrar lista de muertes");
        add("counter.config.showListOverlayAlways", "Mostrar siempre la lista de muertes");
        add("counter.config.deathListX", "Lista de muertes X");
        add("counter.config.deathListY", "Lista de muertes Y");
        add("counter.config.deathListSize", "Tamaño de la lista de muertes");
        add("counter.config.deathOverlayStyle", "Estilo de la lista de muertes");
        add("counter.config.deathOverlayMinWidth", "Ancho mínimo de la lista de muertes");
        add("counter.config.deathListAlign", "Alineación de la lista de muertes");
        add("counter.config.deathListTextColor", "Color del texto de la lista de muertes");
        add("counter.config.firstPlaceColor", "Color del primer lugar");
        add("counter.config.secondPlaceColor", "Color del segundo lugar");
        add("counter.config.thirdPlaceColor", "Color del tercer lugar");

        // --- Config translations: Personal Death Counter ---
        add("counter.config.showDeathSelfOverlay", "Mostrar contador de muertes personal");
        add("counter.config.showSelfOverlayAlways", "Mostrar siempre el contador personal");
        add("counter.config.deathSelfX", "Muerte personal X");
        add("counter.config.deathSelfY", "Muerte personal Y");
        add("counter.config.deathSelfSize", "Tamaño del contador personal");
        add("counter.config.deathSelfAlign", "Alineación del contador personal");
        add("counter.config.deathSelfTextColor", "Color del texto del contador personal");

        // --- Config translations: FPS Overlay ---
        add("counter.config.showFpsOverlay", "Mostrar superposición de FPS");
        add("counter.config.showFpsOverlayAlways", "Mostrar siempre FPS");
        add("counter.config.fpsOverlayX", "Superposición de FPS X");
        add("counter.config.fpsOverlayY", "Superposición de FPS Y");
        add("counter.config.fpsOverlaySize", "Tamaño de la superposición de FPS");
        add("counter.config.fpsOverlayAlign", "Alineación de la superposición de FPS");
        add("counter.config.fpsOverlayTextColor", "Color del texto de FPS");

        // --- Config translations: Survival Overlay ---
        add("counter.config.showSurvivalOverlay", "Mostrar superposición de supervivencia");
        add("counter.config.showSurvivalOverlayAlways", "Mostrar siempre la superposición de supervivencia");
        add("counter.config.survivalOverlayX", "Supervivencia X");
        add("counter.config.survivalOverlayY", "Supervivencia Y");
        add("counter.config.survivalOverlaySize", "Tamaño de la superposición de supervivencia");
        add("counter.config.survivalOverlayAlign", "Alineación de la superposición de supervivencia");
        add("counter.config.survivalOverlayTextColor", "Color del texto de la superposición de supervivencia");

        // --- Config translations: Time Overlay ---
        add("counter.config.showTimeOverlay", "Mostrar superposición de tiempo");
        add("counter.config.showTimeOverlayAlways", "Mostrar siempre el tiempo");
        add("counter.config.timeOverlayX", "Tiempo X");
        add("counter.config.timeOverlayY", "Tiempo Y");
        add("counter.config.timeOverlaySize", "Tamaño de la superposición de tiempo");
        add("counter.config.timeOverlayAlign", "Alineación de la superposición de tiempo");
        add("counter.config.timeOverlayTextColor", "Color del texto de la superposición de tiempo");

        // --- Config translations: Coordinates Overlay ---
        add("counter.config.showCoordsOverlay", "Mostrar superposición de coordenadas");
        add("counter.config.showCoordsOverlayAlways", "Mostrar siempre las coordenadas");
        add("counter.config.coordsOverlayX", "Coordenadas X");
        add("counter.config.coordsOverlayY", "Coordenadas Y");
        add("counter.config.coordsOverlaySize", "Tamaño de la superposición de coordenadas");
        add("counter.config.coordsOverlayAlign", "Alineación de la superposición de coordenadas");
        add("counter.config.coordsOverlayTextColor", "Color del texto de las coordenadas");

        // --- Config translations: Ping & Emojis ---
        add("counter.config.showPingAsText", "Mostrar ping como texto");
        add("counter.config.pingColorGood", "Color de buen ping");
        add("counter.config.pingColorMedium", "Color de ping medio");
        add("counter.config.pingColorBad", "Color de mal ping");
        add("counter.config.showEmojis", "Mostrar emojis");

        // --- Config translations: General & Misc ---
        add("counter.config.enableDayCounter", "Habilitar contador de días");
        add("counter.config.showOverlay", "Mostrar superposición");
        add("counter.config.enableDayMessage", "Habilitar mensaje del día");
        add("counter.config.showDayInChat", "Mostrar día en el chat");
        add("counter.config.enableDeathCounter", "Habilitar contador de muertes");
        add("counter.config.maxPlayersShown", "Máximo de jugadores mostrados");
        add("counter.config.deathOverlayMode", "Modo de lista de muertes");
        add("counter.config.showDeathInChat", "Mostrar muerte en el chat");
        add("counter.config.showDeathInChatMode", "Disparador de chat de muerte");
        add("counter.config.deathChatMode", "Modo de chat de muerte");
        add("counter.config.showDeathListOnDeathGlobal", "Transmitir lista de muertes");
        add("counter.config.deathListChatTextColor", "Color del texto del chat de la lista de muertes");
        add("counter.config.deathSelfChatTextColor", "Color del texto del chat personal");

        add("counter.config.enableFpsCounter", "Habilitar contador de FPS");

        add("counter.config.enableSurvivalCounter", "Habilitar contador de supervivencia");
        add("counter.config.survivalUseRealTime", "Usar tiempo real");
        add("counter.config.survivalTimeFormat", "Formato de tiempo de supervivencia");
        add("counter.config.showBestSurvivalTime", "Mostrar mejor tiempo de supervivencia");
        add("counter.config.showBestSurvivalInDeathCounter", "Mostrar mejor en el contador de muertes");
        add("counter.config.survivalHistorySize", "Tamaño del historial de supervivencia");
        add("counter.config.showSurvivalInChat", "Mostrar supervivencia en el chat");
        add("counter.config.showSurvivalInChatMode", "Disparador de chat de supervivencia");
        add("counter.config.showSurvivalInChatGlobal", "Transmitir tiempo de supervivencia");
        add("counter.config.showBestSurvivalInChat", "Mostrar mejor en el chat");

        add("counter.config.enableTimeCounter", "Habilitar contador de tiempo");
        add("counter.config.showCombinedDayTime", "Mostrar día/tiempo combinado");
        add("counter.config.timeFormat24h", "Formato 24h");

        add("counter.config.enableCoordsCounter", "Habilitar contador de coordenadas");

        // --- Config translations: Commands ---
        add("counter.config.enableDayCommand", "Comando del contador de días");
        add("counter.config.enableDeathCommand", "Comando del contador de muertes");
        add("counter.config.enableSurvivalCommand", "Comando del contador de supervivencia");
        add("counter.config.enableTimeCommand", "Comando del contador de tiempo");
        add("counter.config.enableCoordsCommand", "Comando del contador de coordenadas");

        // --- Config translations: Titles ---
        add("counter.config.title.dayOverlay", "Configuración de superposición del día");
        add("counter.config.title.deathList", "Configuración de lista de muertes");
        add("counter.config.title.deathSelf", "Configuración de contador personal");
        add("counter.config.title.fpsOverlay", "Configuración de superposición de FPS");
        add("counter.config.title.survivalOverlay", "Configuración de superposición de supervivencia");
        add("counter.config.title.timeOverlay", "Configuración de superposición de tiempo");
        add("counter.config.title.coordsOverlay", "Configuración de superposición de coordenadas");
        add("counter.config.title.ping", "Configuración de ping");
        add("counter.config.title.emote", "Configuración de emojis");

        add("counter.config.title.dayCounter", "Configuración de contador de días");
        add("counter.config.title.deathCounter", "Configuración de contador de muertes");
        add("counter.config.title.deathCounterChat", "Configuración de chat de muertes");
        add("counter.config.title.fpsCounter", "Configuración de contador de FPS");
        add("counter.config.title.survivalCounter", "Configuración de contador de supervivencia");
        add("counter.config.title.survivalCounterChat", "Configuración de chat de supervivencia");
        add("counter.config.title.timeCounter", "Configuración de contador de tiempo");
        add("counter.config.title.coordsCounter", "Configuración de superposición de coordenadas");
        add("counter.config.title.commands", "Configuración de comandos");
    }
}
