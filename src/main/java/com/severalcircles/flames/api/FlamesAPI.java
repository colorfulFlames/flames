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

@SpringBootApplication
public class FlamesAPI {
    public static void start() {
        Logger.getGlobal().log(Level.INFO, "Starting API");
        SpringApplication.run(FlamesAPI.class);
    }
    public static String getUserDataLink(String id) {
        return "https://flamesapi.severalcircles.com/user/" + id;
    }
    public static String getUserDataLink(FlamesUser user) {
        return "https://flamesapi.severalcircles.com/user/" + user.getDiscordId();
    }
    public static String getUserDataLink(User user) {
        return "https://flamesapi.severalcircles.com/user/" + user.getId();
    }
}
