package de.bigbull.counter.fabric.config;

import de.bigbull.counter.common.Counter;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FabricTomlConfig {
    public static final File CONFIG_DIR = new File("config/counter");

    private static final ReadWriteLock configLock = new ReentrantReadWriteLock();

    private static FabricClientConfig clientConfig;
    private static FabricServerConfig serverConfig;

    public static void initialize() {
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }

        serverConfig = new FabricServerConfig();
        TempConfig.SERVER = serverConfig;

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            clientConfig = new FabricClientConfig();
            TempConfig.CLIENT = clientConfig;
        }

        startFileWatcher();
    }

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
            Counter.logger.error("Error starting configuration watcher", e);
        }
    }

    public static void reloadClientConfig() {
        if (clientConfig == null) return;

        configLock.writeLock().lock();
        try {
            clientConfig.loadConfig();
        } finally {
            configLock.writeLock().unlock();
        }
    }

    public static void reloadServerConfig() {
        configLock.writeLock().lock();
        try {
            serverConfig.loadConfig();
        } finally {
            configLock.writeLock().unlock();
        }
    }
}