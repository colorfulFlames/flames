/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.amiguito;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.exception.ConsentException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class AmiguitoDataManager {
    static Timer timer = new Timer();
    public static void prepare() {
        TimerTask refreshTask = new TimerTask() {
            @Override
            public void run() {
                Logger.getGlobal().info("Refreshing Amiguitos");
                refreshAmiguitos();
            }
        };
        timer.schedule(refreshTask, 0L, 60000L * (long) Amiguito.RUN_TIME);
    }
    public static final Map<String, Amiguito> loadedAmiguitos = new HashMap<>();
    /**
     * Saves an Amiguito to user directory
     * @param amiguito the Amiguito to save
     */
    public static void save(Amiguito amiguito) throws IOException {
        final File userDir = new File(FlamesDataManager.USER_DIRECTORY + "/" + amiguito.getCaretaker().getDiscordId());
        final File amiguitoFile = new File(userDir + "/amiguito.fl");
        Properties properties = amiguito.createProperties();
        properties.store(new FileWriter(amiguitoFile),"Amiguito data for " + amiguito.getName());
    }
    public static void saveAll() {
        Logger.getGlobal().info("Saving all Amiguitos");
        loadedAmiguitos.forEach((s, amiguito) -> {
            if (amiguito == null) {
                Logger.getGlobal().warning("Amiguito belonging to user " + s + " is null");
                return;
            }
            try {
                Logger.getGlobal().info("Attempting to save " + amiguito.getName() + " belonging to user " + s);
                save(amiguito);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    /**
     * Loads an Amiguito from caw
     * @param discordId the Discord ID of the user
     * @return the Amiguito
     */
    public static Amiguito load(String discordId) throws IOException, ConsentException {
        final File userDir = new File(FlamesDataManager.USER_DIRECTORY + "/" + discordId);
        final File amiguitoFile = new File(userDir + "/amiguito.fl");
        Properties properties = new Properties();
        properties.load(new FileReader(amiguitoFile));
        Amiguito result = Amiguito.fromProperties(properties);
        loadedAmiguitos.put(discordId, result);
        return result;
    }
    public static void loadAll() throws IOException {
        Logger.getGlobal().info("Loading all Amiguitos from Flames Directory");
        final File userDir = new File(FlamesDataManager.USER_DIRECTORY.getPath());
        Arrays.stream(Objects.requireNonNull(userDir.listFiles())).map(File::getName).forEach(s -> {
            try {
                Logger.getGlobal().info("Attempting to load Amiguito for user " + s);
                load(s);
            } catch (IOException | ConsentException e) {
                Logger.getGlobal().warning("Failed to load Amiguito for user " + s);
            }
        });
    }
    public static void refreshAmiguitos() {
        if (!loadedAmiguitos.isEmpty()) {
            saveAll();
            loadedAmiguitos.forEach((s, amiguito) -> {loadedAmiguitos.remove(s);});
        } else return;
        try {
            loadAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadedAmiguitos.forEach((s, amiguito) -> {
            amiguito.refreshThoughts();
            amiguito.runMoodTime();
        });
        if (loadedAmiguitos.isEmpty()) {
            Logger.getGlobal().warning("No Amiguitos loaded because Flames couldn't find any.");
        }
    }
}
