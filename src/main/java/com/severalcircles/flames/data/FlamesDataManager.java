/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.data.legacy.server.LegacyFlamesServer;
import com.severalcircles.flames.data.user.FlamesUser;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

/**
 * The FlamesDataManager class is responsible for managing the data of FlamesUser and FlamesServer objects.
 * It is capable of saving, retrieving and preparing necessary directories
 */
public class FlamesDataManager {
    private static final Logger LOGGER = Logger.getLogger(FlamesDataManager.class.getName());

    public static final File FLAMES_DIRECTORY = new File(System.getProperty("user.dir") + "/Flames");
    public static final File USER_DIRECTORY = new File(FLAMES_DIRECTORY.getAbsolutePath() + "/users");
    public static final File SERVER_DIRECTORY = new File(FLAMES_DIRECTORY.getAbsolutePath() + "/servers");

    public static void prepare() {
        LOGGER.info("Preparing Flames Data");
        if (FLAMES_DIRECTORY.mkdir()) LOGGER.info("Created Flames directory");
        if (USER_DIRECTORY.mkdir()) LOGGER.info("Created Users directory");
        if (SERVER_DIRECTORY.mkdir()) LOGGER.info("Created Servers directory");
    }
    public static FlamesUser getUser(String id) throws ConsentException, IOException {
        return getUser(id, false);
    }

    public static FlamesUser getUser(String id, boolean skipConsent) throws ConsentException, IOException {
            File userFile = new File(USER_DIRECTORY.getAbsolutePath() + "/" + id + ".yml");
            Yaml yaml = new Yaml();

            if (!userFile.exists()) {
                FlamesUser newUser = new FlamesUser(id);
                Files.write(userFile.toPath(), yaml.dump(newUser).getBytes());
                return newUser;
            }
            String contents = new String(Files.readAllBytes(userFile.toPath()));
            FlamesUser user = yaml.loadAs(contents, FlamesUser.class);
            if (user.getId().equals("797283404654575657")) Logger.getGlobal().warning("An operation attempted to save user 797283404654575657");
            if (user.getConsent() != 1 && !skipConsent) throw new ConsentException(user.getConsent());
            else return user;
    }

    public static void saveUser(FlamesUser flamesUser) throws IOException {
        File userFile = new File(USER_DIRECTORY.getAbsolutePath() + "/" + flamesUser.getID() + ".yml");
        Yaml yaml = new Yaml();
        Files.write(userFile.toPath(), yaml.dump(flamesUser).getBytes());
    }
    public static FlamesServer getServer(String id) {
        try {
            File serverFile = new File(SERVER_DIRECTORY.getAbsolutePath() + "/" + id + ".yml");
            Yaml yaml = new Yaml();

            if (!serverFile.exists()) {
                FlamesServer newServer = new FlamesServer(id);
                Files.write(serverFile.toPath(), yaml.dump(newServer).getBytes());
                return newServer;
            }

            String contents = new String(Files.readAllBytes(serverFile.toPath()));
            return yaml.loadAs(contents, FlamesServer.class);
        } catch (IOException e) {
            LOGGER.severe("Failed to get server - " + e.getMessage());
            return null;
        }
    }

    public static void saveServer(FlamesServer flamesServer) {
        try {
            File serverFile = new File(SERVER_DIRECTORY.getAbsolutePath() + "/" + flamesServer.getId() + ".yml");
            Yaml yaml = new Yaml();
            Files.write(serverFile.toPath(), yaml.dump(flamesServer).getBytes());
        } catch (IOException e) {
            LOGGER.severe("Failed to save server - " + e.getMessage());
        }
    }
}