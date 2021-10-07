/*
 * Copyright (c) 2021.
 */

package com.severalcircles.flames.api;

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
}
