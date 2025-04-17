package de.bigbull.counter.fabric.config;

import de.bigbull.counter.common.config.ConfigHelper;
import de.bigbull.counter.common.config.IServerConfig;

/**
 * Verwaltet das Backup und die Anwendung der Serverkonfiguration auf dem Client
 */
public class ClientConfigBacking {
    private static IServerConfig originalServerConfig;
    private static IServerConfig currentServerConfig;

    /**
     * Prüft, ob bereits ein Backup der Client-Konfiguration existiert
     */
    public static boolean hasBackup() {
        return originalServerConfig != null;
    }

    /**
     * Erstellt ein Backup der Client-Konfiguration
     */
    public static void backup(IServerConfig config) {
        if (originalServerConfig == null) {
            originalServerConfig = config;
        }
    }

    /**
     * Wendet die empfangene Serverkonfiguration auf den Client an
     */
    public static void applyServerConfig(IServerConfig serverConfig) {
        currentServerConfig = serverConfig;

        // Ersetze die Server-Konfiguration im ConfigHelper
        ConfigHelper.registerServerConfig(() -> currentServerConfig);
    }

    /**
     * Stellt die ursprüngliche Client-Konfiguration wieder her
     */
    public static void restoreOriginalConfig() {
        if (originalServerConfig != null) {
            ConfigHelper.registerServerConfig(() -> originalServerConfig);
            currentServerConfig = null;
        }
    }
}