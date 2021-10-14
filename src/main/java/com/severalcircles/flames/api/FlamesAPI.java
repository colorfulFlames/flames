/*
 * Copyright (c) 2021.
 */

package com.severalcircles.flames.api;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for the Flames API
 */
@SpringBootApplication
public class FlamesAPI {
    /**
     * Starts the API
     */
    public static void start() {
        Logger.getGlobal().log(Level.INFO, "Starting API");
        SpringApplication.run(FlamesAPI.class);
    }

    /**
     *
     * @param id Discord User ID
     * @return String with link to the user data in the API
     */
    public static String getUserDataLink(String id) {
        return "https://flamesapi.severalcircles.com/user/" + id;
    }

    /**
     *
     * @param user Flames User
     * @return String with link to the user data in the API
     */
    public static String getUserDataLink(FlamesUser user) {
        return "https://flamesapi.severalcircles.com/user/" + user.getDiscordId();
    }

    /**
     *
     * @param user Discord User object
     * @return String with link to the user data in the API
     */
    public static String getUserDataLink(User user) {
        return "https://flamesapi.severalcircles.com/user/" + user.getId();
    }
}
