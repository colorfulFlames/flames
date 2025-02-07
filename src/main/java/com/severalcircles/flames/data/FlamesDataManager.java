/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserEntities;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

/**
 * The FlamesDataManager class is responsible for managing Flames data such as users and servers.
 * It provides methods to prepare the Flames data, retrieve and save user information, and retrieve and save server information.
 *
 * <p>Usage:</p>
 * <pre>
 * FlamesDataManager.prepare();
 * FlamesUser user = FlamesDataManager.getUser("123456789");
 * FlamesDataManager.saveUser(user);
 * FlamesServer server = FlamesDataManager.getServer("987654321");
 * FlamesDataManager.saveServer(server);
 * </pre>
 *
 * @see FlamesUser
 * @see FlamesServer
 */
public class FlamesDataManager {
    private static final Logger LOGGER = Logger.getLogger(FlamesDataManager.class.getName());

    public static final File FLAMES_DIRECTORY = new File(System.getProperty("user.dir") + "/Flames");
    public static final File USER_DIRECTORY = new File(FLAMES_DIRECTORY.getAbsolutePath() + "/users");
    public static final File SERVER_DIRECTORY = new File(FLAMES_DIRECTORY.getAbsolutePath() + "/servers");

    /**
     * Prepares the Flames data by creating necessary directories.
     * It creates the Flames directory, Users directory, and Servers directory.
     * This method is typically called before performing any data operations.
     *
     * Example usage:
     *
     * DataUpgradeUtil.upgradeData();
     *
     * This method does not return any value.
     */
    public static void prepare() {
        LOGGER.info("Preparing Flames Data");
        if (FLAMES_DIRECTORY.mkdir()) LOGGER.info("Created Flames directory");
        if (USER_DIRECTORY.mkdir()) LOGGER.info("Created Users directory");
        if (SERVER_DIRECTORY.mkdir()) LOGGER.info("Created Servers directory");
    }
    /**
     * Retrieves the FlamesUser object associated with the given ID.
     *
     * @param id           The ID of the user.
     * @return The FlamesUser object for the given ID.
     * @throws ConsentException If the user's consent level is not sufficient.
     * @throws IOException      If an I/O error occurs while accessing the user file.
     */
    public static FlamesUser getUser(String id) throws ConsentException, IOException {
        return getUser(id, false);
    }

    /**
     * Retrieves a FlamesUser object based on the given ID.
     *
     * @param id            The ID of the user.
     * @param skipConsent   If true, skips the consent check.
     * @return The FlamesUser object corresponding to the given ID.
     * @throws ConsentException If the user's consent level is not sufficient and skipConsent is false.
     * @throws IOException      If there is an error reading the user file.
     */
    public static FlamesUser getUser(String id, boolean skipConsent) throws ConsentException, IOException {
            File userFile = new File(USER_DIRECTORY.getAbsolutePath() + "/" + id + ".yml");
            Yaml yaml = new Yaml();

            if (!userFile.exists()) {
                FlamesUser newUser = new FlamesUser(id);
                Files.write(userFile.toPath(), yaml.dump(newUser).getBytes());
                if (!skipConsent) throw new ConsentException(0,  Flames.api.retrieveUserById(id).complete());
                else return newUser;
            }
            String contents = new String(Files.readAllBytes(userFile.toPath()));
            FlamesUser user = yaml.loadAs(contents, FlamesUser.class);
            if (user.getConsent() != 1 && !skipConsent) throw new ConsentException(user.getConsent(), Flames.api.retrieveUserById(id).complete());
            else return user;
    }

    /**
     * Saves the FlamesUser object to a YAML file.
     *
     * @param flamesUser the FlamesUser object to be saved
     * @throws IOException if an I/O error occurs while writing the file
     */
    public static void saveUser(FlamesUser flamesUser) throws IOException {
        File userFile = new File(USER_DIRECTORY.getAbsolutePath() + "/" + flamesUser.getID() + ".yml");
        Yaml yaml = new Yaml();
        Files.write(userFile.toPath(), yaml.dump(flamesUser).getBytes());
    }
    /**
     * Retrieves a FlamesServer object based on the given ID.
     *
     * @param id The ID of the server.
     * @return The FlamesServer object for the specified ID, or null if there was an error.
     */
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

    /**
     * Saves a FlamesServer object to a YAML file.
     *
     * @param flamesServer the FlamesServer object to be saved
     */
    public static void saveServer(FlamesServer flamesServer) {
        try {
            File serverFile = new File(SERVER_DIRECTORY.getAbsolutePath() + "/" + flamesServer.getId() + ".yml");
            Yaml yaml = new Yaml();
            Files.write(serverFile.toPath(), yaml.dump(flamesServer).getBytes());
        } catch (IOException e) {
            LOGGER.severe("Failed to save server - " + e.getMessage());
        }
    }
    public static void deleteUser(String id) {
        File userFile = new File(USER_DIRECTORY.getAbsolutePath() + "/" + id + ".yml");
        if (userFile.delete()) LOGGER.info("Deleted user " + id);
    }
    public static void deleteUserEntities(String id) {
        FlamesUser user;
        try {
            user = getUser(id, true);
        } catch (ConsentException ignored) {
            throw new RuntimeException("ConsentException should not be thrown here");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setEntities(new UserEntities());
        try {
            saveUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}