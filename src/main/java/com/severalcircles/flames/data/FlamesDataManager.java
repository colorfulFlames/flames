/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.FlamesManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * This manager is responsible for managing the global data of Flames. This includes the average score of Flames, the global score of Flames, and the global score of Flames.
 * @author Several Circles
 * @version 8
 * @since Flames 8
 */
public class FlamesDataManager extends FlamesManager {
    public static final File dataFile = new File(SystemDataManager.getFlamesDirectory().getAbsolutePath() + "/global.flp");
private static double averageScore = 0;
private static double globalScore = 0;
    public static double getAverageScore() {
        return averageScore;
    }
    public static void setAverageScore(double averageScore) {
        FlamesDataManager.averageScore = averageScore;
    }
    public static void addScore(double score) {
        averageScore = (averageScore + score) / 2;
        save();
    }
    public static double getGlobalScore() {
        return globalScore;
    }
    public static void addGloabalScore(double gloabalScore) {
        FlamesDataManager.globalScore += gloabalScore;
        save();

    }
    @Override
    public void prepare() throws IOException {
        if (dataFile.createNewFile()) {
            Properties properties = new Properties();
            properties.setProperty("averageScore", "0");
            properties.setProperty("globalScore", "0");
            FileWriter writer = new FileWriter(dataFile);
            properties.store(writer, "Flames Global Data File");
            Flames.getFlogger().info("Created new global data file because an existing one was not found.");
            writer.close();
        }
        else {
            Flames.getFlogger().fine("Found existing global data file. Loading it.");
            Properties properties = new Properties();
            properties.load(dataFile.toURI().toURL().openStream());
            averageScore = Double.parseDouble(properties.getProperty("averageScore"));
            globalScore = Double.parseDouble(properties.getProperty("globalScore"));
        }
    }
    static void save() {
        try {
            Properties properties = new Properties();
            properties.setProperty("averageScore", String.valueOf(averageScore));
            properties.setProperty("globalScore", String.valueOf(globalScore));
            FileWriter writer = new FileWriter(dataFile);
            properties.store(writer, "Flames Global Data File");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
