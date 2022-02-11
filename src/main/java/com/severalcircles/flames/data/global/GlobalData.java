/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.global;

import com.severalcircles.flames.data.FlamesDataManager;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages Global Data such as average and global scores
 */
@SuppressWarnings("CanBeFinal")
public class GlobalData {
    public static int averageScore;
    public static int globalScore;
    public static int participants;

    /**
     * Writes to data file.
     *
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void write() throws IOException {
        Logger.getGlobal().log(Level.FINE, "Saving global data");
        Properties properties = new Properties();
//        properties.put("averageScore", averageScore + "");
        properties.put("globalScore", globalScore + "");
        properties.put("participants", participants + "");
        File file = new File(FlamesDataManager.flamesDirectory.getAbsolutePath() + "/global.properties");
        file.createNewFile();
        OutputStream outputStream = new FileOutputStream(file);
        properties.store(outputStream, "Flames Global Data File");
    }

    /**
     * Reads values from data files and then assigns them to the appropriate objects.
     *
     */
    @SuppressWarnings("deprecation")
    public static void read() throws IOException {
        File file = new File(FlamesDataManager.flamesDirectory.getAbsolutePath() + "/global.properties");
        @SuppressWarnings("deprecation") InputStream inputStream = file.toURL().openStream();
        Properties properties = new Properties();
        properties.load(inputStream);
//        averageScore = Integer.parseInt(properties.get("averageScore") + "");
        globalScore = Integer.parseInt(properties.get("globalScore") + "");
        participants = Integer.parseInt(properties.get("participants") + "");
        averageScore = globalScore / participants;
        System.out.println("AS:" + averageScore);
    }
}
