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
        add("overlay.counter.deathlist", "💀 Contador de Muertes:");
        add("overlay.counter.deathlist.entry.full", "%s %s");
        add("overlay.counter.deathlist.entry.singular", "%s: %s muerte");
        add("overlay.counter.deathlist.entry.plural", "%s: %s muertes");
        add("overlay.counter.deathlist.style.invalid", "¡ERROR: Estilo de lista de muertes no válido!");

        add("overlay.counter.survival_with_emoji", "⏳ Sobrevivido: %s");
        add("overlay.counter.survival_no_emoji", "Sobrevivido: %s");
        add("overlay.counter.best_survival_with_emoji", "⏳ Récord: %s");
        add("overlay.counter.best_survival_no_emoji", "Récord: %s");

        add("overlay.counter.fps", "FPS: %s");

        // --- Key Bindings ---
        add("key.counter.edit_mode", "Modo de edición");
        add("key.counter.show_overlays", "Mostrar superposiciones");
        add("key.category.counter.main", "Counter");

        // --- Overlay Edit Screen ---
        add("screen.overlay_edit", "Pantalla de edición de superposiciones");
        add("screen.overlay_edit.done", "Hecho");
        add("screen.overlay_edit.cancel", "Cancelar");
        add("screen.overlay_edit.toggle_overlay", "Alternar superposición");

        // --- Chat translations ---
        add("chat.daycounter.new_day", "🌞 ¡Ahora es el día %s!");
        add("chat.deathcounter.player_death.singular", "💀 ¡%s ha muerto 1 vez!");
        add("chat.deathcounter.player_death.plural", "💀 ¡%s ha muerto %s veces!");
        add("chat.survivalcounter.personal", "⏳ Sobrevivido: %s");
        add("chat.survivalcounter.personal.best", "⏳ Sobrevivido: %s (%s)");
        add("chat.survivalcounter.broadcast", "⏳ %s sobrevivió durante %s");
        add("chat.survivalcounter.broadcast.best", "⏳ %s sobrevivió durante %s (%s)");

        // --- Command translations ---
        add("command.daycounter.set", "🌞 ¡Contador de días establecido en %s!");
        add("command.deathcounter.reset", "💀 ¡El contador de muertes ha sido restablecido!");
        add("command.deathcounter.set", "💀 ¡Contador de muertes de %s establecido en %s!");
        add("command.survival.global_best", "⏳ %s tiene el mejor tiempo: %s");
        add("command.coords.broadcast", "%s ha compartido sus coordenadas: X: %s, Y: %s, Z: %s");
        add("command.player_not_found", "❌ ¡Jugador no encontrado!");
        add("command.counter.no_data", "Aún no hay datos disponibles.");

        // --- Config translations: Day Overlay ---
        add("counter.config.showDayOverlay", "Mostrar superposición de días");
        add("counter.config.showOverlayAlways", "Mostrar siempre superposición");
        add("counter.config.dayOverlayX", "Posición X de la superposición de días");
        add("counter.config.dayOverlayY", "Posición Y de la superposición de días");
        add("counter.config.dayOverlaySize", "Tamaño de la superposición de días");
        add("counter.config.dayOverlayAlign", "Alineación de la superposición de días");
        add("counter.config.dayOverlayTextColor", "Color del texto de la superposición de días");

        // --- Config translations: Death List Overlay ---
        add("counter.config.showDeathListOverlay", "Mostrar superposición de lista de muertes");
        add("counter.config.showListOverlayAlways", "Mostrar siempre lista de muertes");
        add("counter.config.deathListX", "Posición X de la lista de muertes");
        add("counter.config.deathListY", "Posición Y de la lista de muertes");
        add("counter.config.deathListSize", "Tamaño de la lista de muertes");
        add("counter.config.deathOverlayStyle", "Estilo de la lista de muertes");
        add("counter.config.deathOverlayMinWidth", "Ancho mínimo de la lista de muertes");
        add("counter.config.deathListAlign", "Alineación de la lista de muertes");
        add("counter.config.deathListTextColor", "Color del texto de la lista de muertes");
        add("counter.config.firstPlaceColor", "Color del primer puesto");
        add("counter.config.secondPlaceColor", "Color del segundo puesto");
        add("counter.config.thirdPlaceColor", "Color del tercer puesto");

        // --- Config translations: Personal Death Counter ---
        add("counter.config.showDeathSelfOverlay", "Mostrar superposición de muertes propia");
        add("counter.config.showSelfOverlayAlways", "Mostrar siempre muertes propia");
        add("counter.config.deathSelfX", "Posición X de muertes propia");
        add("counter.config.deathSelfY", "Posición Y de muertes propia");
        add("counter.config.deathSelfSize", "Tamaño de muertes propia");
        add("counter.config.deathSelfAlign", "Alineación de muertes propia");
        add("counter.config.deathSelfTextColor", "Color del texto de muertes propia");

        // --- Config translations: Survival Overlay ---
        add("counter.config.showSurvivalOverlay", "Mostrar superposición de supervivencia");
        add("counter.config.showSurvivalOverlayAlways", "Mostrar siempre supervivencia");
        add("counter.config.survivalOverlayX", "Posición X de la superposición de supervivencia");
        add("counter.config.survivalOverlayY", "Posición Y de la superposición de supervivencia");
        add("counter.config.survivalOverlaySize", "Tamaño de la superposición de supervivencia");
        add("counter.config.survivalOverlayAlign", "Alineación de la superposición de supervivencia");
        add("counter.config.survivalOverlayTextColor", "Color del texto de supervivencia");

        // --- Config translations: Time Overlay ---
        add("counter.config.showTimeOverlay", "Mostrar superposición de tiempo");
        add("counter.config.showTimeOverlayAlways", "Mostrar siempre tiempo");
        add("counter.config.timeOverlayX", "Posición X de la superposición de tiempo");
        add("counter.config.timeOverlayY", "Posición Y de la superposición de tiempo");
        add("counter.config.timeOverlaySize", "Tamaño de la superposición de tiempo");
        add("counter.config.timeOverlayAlign", "Alineación de la superposición de tiempo");
        add("counter.config.timeOverlayTextColor", "Color del texto de tiempo");

        // --- Config translations: Coordinates Overlay ---
        add("counter.config.showCoordsOverlay", "Mostrar superposición de coordenadas");
        add("counter.config.showCoordsOverlayAlways", "Mostrar siempre coordenadas");
        add("counter.config.coordsOverlayX", "Posición X de coordenadas");
        add("counter.config.coordsOverlayY", "Posición Y de coordenadas");
        add("counter.config.coordsOverlaySize", "Tamaño de coordenadas");
        add("counter.config.coordsOverlayAlign", "Alineación de coordenadas");
        add("counter.config.coordsOverlayTextColor", "Color del texto de coordenadas");

        // --- Config translations: FPS Overlay ---
        add("counter.config.showFpsOverlay", "Mostrar superposición de FPS");
        add("counter.config.showFpsOverlayAlways", "Mostrar siempre FPS");
        add("counter.config.fpsOverlayX", "Posición X de FPS");
        add("counter.config.fpsOverlayY", "Posición Y de FPS");
        add("counter.config.fpsOverlaySize", "Tamaño de FPS");
        add("counter.config.fpsOverlayAlign", "Alineación de FPS");
        add("counter.config.fpsOverlayTextColor", "Color del texto de FPS");

        // --- Config translations: Ping & Emojis ---
        add("counter.config.showPingAsText", "Mostrar ping como texto");
        add("counter.config.pingColorGood", "Color de ping bueno");
        add("counter.config.pingColorMedium", "Color de ping medio");
        add("counter.config.pingColorBad", "Color de ping malo");
        add("counter.config.showEmojis", "Mostrar emojis");

        // --- Config translations: General & Server ---
        add("counter.config.enableDayCounter", "Activar contador de días");
        add("counter.config.showOverlay", "Mostrar superposición");
        add("counter.config.enableDayMessage", "Activar mensaje de nuevo día");
        add("counter.config.showDayInChat", "Mostrar día en el chat al unirse");
        add("counter.config.enableDeathCounter", "Activar contador de muertes");
        add("counter.config.maxPlayersShown", "Máximo de jugadores mostrados");
        add("counter.config.deathOverlayMode", "Modo de superposición de muertes");
        add("counter.config.showDeathInChat", "Mostrar muertes en el chat");
        add("counter.config.showDeathInChatMode", "Activador de chat de muertes");
        add("counter.config.deathChatMode", "Modo de chat de muertes");
        add("counter.config.showDeathListOnDeathGlobal", "Difundir lista de muertes al morir");
        add("counter.config.deathListChatTextColor", "Color del texto de la lista de muertes en chat");
        add("counter.config.deathSelfChatTextColor", "Color del texto de muerte propia en chat");

        add("counter.config.enableSurvivalCounter", "Activar contador de supervivencia");
        add("counter.config.survivalUseRealTime", "Usar tiempo real");
        add("counter.config.survivalTimeFormat", "Formato de tiempo de supervivencia");
        add("counter.config.showBestSurvivalTime", "Mostrar mejor tiempo de supervivencia");
        add("counter.config.showBestSurvivalInDeathCounter", "Añadir mejor tiempo al mensaje de muerte");
        add("counter.config.survivalHistorySize", "Tamaño del historial de supervivencia");
        add("counter.config.showSurvivalInChat", "Mostrar tiempo de supervivencia en chat");
        add("counter.config.showSurvivalInChatMode", "Activador de chat de supervivencia");
        add("counter.config.showSurvivalInChatGlobal", "Difundir tiempo de supervivencia");
        add("counter.config.showBestSurvivalInChat", "Mostrar mejor tiempo en el chat");

        add("counter.config.enableTimeCounter", "Activar contador de tiempo");
        add("counter.config.showCombinedDayTime", "Mostrar día y hora combinados");
        add("counter.config.timeFormat24h", "Formato de 24 horas");

        add("counter.config.enableCoordsCounter", "Activar contador de coordenadas");

        add("counter.config.enableFpsCounter", "Activar contador de FPS");

        // --- Config translations: Commands ---
        add("counter.config.enableDayCommand", "Activar comando de días");
        add("counter.config.enableDeathCommand", "Activar comando de muertes");
        add("counter.config.enableSurvivalCommand", "Activar comando de supervivencia");
        add("counter.config.enableTimeCommand", "Activar comando de tiempo");
        add("counter.config.enableCoordsCommand", "Activar comando de coordenadas");

        // --- Config translations: Titles ---
        add("counter.config.title.dayOverlay", "Configuración de superposición de días");
        add("counter.config.title.deathList", "Configuración de lista de muertes");
        add("counter.config.title.deathSelf", "Configuración de muerte propia");
        add("counter.config.title.survivalOverlay", "Configuración de superposición de supervivencia");
        add("counter.config.title.timeOverlay", "Configuración de superposición de tiempo");
        add("counter.config.title.coordsOverlay", "Configuración de superposición de coordenadas");
        add("counter.config.title.fpsOverlay", "Configuración de superposición de FPS");
        add("counter.config.title.ping", "Configuración de ping");
        add("counter.config.title.emote", "Configuración de emociones");

        add("counter.config.title.dayCounter", "Configuración del contador de días");
        add("counter.config.title.deathCounter", "Configuración del contador de muertes");
        add("counter.config.title.deathCounterChat", "Configuración del chat de muertes");
        add("counter.config.title.survivalCounter", "Configuración del contador de supervivencia");
        add("counter.config.title.survivalCounterChat", "Configuración del chat de supervivencia");
        add("counter.config.title.timeCounter", "Configuración del contador de tiempo");
        add("counter.config.title.coordsCounter", "Configuración de superposición de coordenadas");
        add("counter.config.title.fpsCounter", "Configuración del contador de FPS");
        add("counter.config.title.commands", "Configuración de comandos");
    }
}
