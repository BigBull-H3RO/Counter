package de.bigbull.counter.fabric.config;

import de.bigbull.counter.common.Counter;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * Fabric-Konfigurationsimplementierung mit TOML-Format
 */
public class FabricTomlConfig {

    public static final File CONFIG_DIR = new File("config/counter");

    private static final ReadWriteLock configLock = new ReentrantReadWriteLock();

    private static FabricClientConfig clientConfig;
    private static FabricServerConfig serverConfig;

    /**
     * Initialisiert die Konfigurationen
     */
    public static void initialize() {
        // Sicherstellen, dass das Konfigurationsverzeichnis existiert
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }

        // Server-Konfiguration laden
        serverConfig = new FabricServerConfig();
        TempConfig.SERVER = serverConfig;

        // Client-Konfiguration nur auf dem Client laden
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            clientConfig = new FabricClientConfig();
            TempConfig.CLIENT = clientConfig;
        }

        // Dateiüberwachung starten
        startFileWatcher();
    }

    /**
     * Überwacht Änderungen an Konfigurationsdateien
     */
    private static void startFileWatcher() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path configDirPath = CONFIG_DIR.toPath();
            configDirPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            Thread watchThread = new Thread(() -> {
                try {
                    while (true) {
                        WatchKey key = watchService.take();

                        for (WatchEvent<?> event : key.pollEvents()) {
                            Path changedFile = (Path) event.context();
                            String filename = changedFile.toString();

                            if (filename.equals("server_config.toml")) {
                                reloadServerConfig();
                            } else if (filename.equals("client_config.toml") &&
                                    FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                                reloadClientConfig();
                            }
                        }

                        if (!key.reset()) {
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            watchThread.setDaemon(true);
            watchThread.setName("Counter-Config-Watcher");
            watchThread.start();

        } catch (IOException e) {
            Counter.logger.error("Fehler beim Starten der Konfigurationsüberwachung", e);
        }
    }

    /**
     * Lädt die Client-Konfiguration neu
     */
    public static void reloadClientConfig() {
        if (clientConfig == null) return;

        configLock.writeLock().lock();
        try {
            clientConfig.loadConfig();
        } finally {
            configLock.writeLock().unlock();
        }
    }

    /**
     * Lädt die Server-Konfiguration neu
     */
    public static void reloadServerConfig() {
        configLock.writeLock().lock();
        try {
            serverConfig.loadConfig();
        } finally {
            configLock.writeLock().unlock();
        }
    }

    /**
     * Gibt die Client-Konfiguration zurück
     */
    public static FabricClientConfig getClientConfig() {
        if (clientConfig == null) {
            throw new IllegalStateException("Client-Konfiguration ist auf dem Server nicht verfügbar");
        }

        configLock.readLock().lock();
        try {
            return clientConfig;
        } finally {
            configLock.readLock().unlock();
        }
    }

    /**
     * Gibt die Server-Konfiguration zurück
     */
    public static FabricServerConfig getServerConfig() {
        configLock.readLock().lock();
        try {
            return serverConfig;
        } finally {
            configLock.readLock().unlock();
        }
    }

    /**
     * Führt einen zustandsunabhängigen Lesezugriff auf die Konfiguration aus
     */
    public static <T> T withClientConfig(Function<FabricClientConfig, T> accessor) {
        if (clientConfig == null) {
            throw new IllegalStateException("Client-Konfiguration ist auf dem Server nicht verfügbar");
        }

        configLock.readLock().lock();
        try {
            return accessor.apply(clientConfig);
        } finally {
            configLock.readLock().unlock();
        }
    }

    /**
     * Führt einen zustandsunabhängigen Lesezugriff auf die Konfiguration aus
     */
    public static <T> T withServerConfig(Function<FabricServerConfig, T> accessor) {
        configLock.readLock().lock();
        try {
            return accessor.apply(serverConfig);
        } finally {
            configLock.readLock().unlock();
        }
    }
}