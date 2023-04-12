/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.FlamesManager;
import com.severalcircles.flames.system.exception.FlamesConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class SystemDataManager extends FlamesManager {
    private static final File config = new File("flames.properties");
//    private static final File secureSave = new File(System.getProperty("user.dir") + "/Flames/secure/securesave.sflp");
    private static File flamesDirectory;
    private static final Properties flamesConfig = new Properties();
    @Override
    public void prepare() throws IOException {
        Flames.getFlogger().info("Looking for Flames configuration...");
        if (config.createNewFile()) {
            Properties defaultConfig = new Properties();
            defaultConfig.load(SystemDataManager.class.getClassLoader().getResourceAsStream("defaults/flames.properties"));
            defaultConfig.store(new FileOutputStream(config), "Flames configuration file");
            try {
                throw new FlamesConfigurationException("Looks like this is your first time running Flames+. Please fill out the configuration file (flames.properties) and restart Flames+.");
            } catch (FlamesConfigurationException e) {
                Flames.getFlogger().log(Level.SEVERE, "I'll give you a chance to complete the configuration file. When you're ready, run Flames again.");
                System.exit(1);
            }
        }
        flamesConfig.load(new FileInputStream(config));
        Flames.getFlogger().fine("Flames configuration loaded.");
        Flames.getFlogger().config("Flames configuration: " + flamesConfig);
        Flames.getFlogger().fine("Preparing System Data...");
        flamesDirectory = new File(System.getProperty("user.dir") + "/" + flamesConfig.getProperty("flamesdir"));
        if (!flamesDirectory.exists()) {
            flamesDirectory.mkdir();
            Flames.getFlogger().config("Created Flames directory at " + flamesDirectory.getAbsolutePath());
        }
//        if (!secureSave.exists()) {
//            secureSave.createNewFile();
//            Flames.getFlogger().config("Created secure save data at " + secureSave.getAbsolutePath());
//        }

        Flames.getFlogger().fine("System Data prepared.");
    }
    public void cleanFlamesFiles() {
        File[] files = flamesDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".flamesfile")) {
                    Flames.getFlogger().finest("Cleaned Flamesfile " + file.getName());
                    file.delete();
                }
            }
        }
    }
    public static Properties getFlamesConfig() {
        return flamesConfig;
    }

    public static File getFlamesDirectory() {
        return flamesDirectory;
    }
}
