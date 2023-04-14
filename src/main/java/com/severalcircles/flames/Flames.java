/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames;

import com.severalcircles.flames.system.manager.*;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.java.FlamesConnectException;
import com.severalcircles.flames.system.exception.MessageCodes;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * The main class of Flames. This class is responsible for starting the bot and initializing the managers.
 * @author Several Circles
 * @version 8
 * @since Flames Flames 2
 */
@ExceptionID("501")
public class Flames {
    private static JDA api;
    private static final FLogger flogger = new FLogger();
    private static final Properties versionProp = new Properties();
    private static String version;
    private static final List<FlamesManager> managers = new ArrayList<>();
    private static final List<FlamesManager> managers2 = new ArrayList<>();
    private static int uptime = -1;
    /**
     * The main method of Flames. This method is responsible for starting the bot and initializing the managers.
     * Also, it's a flamingo. Get it? Flames? Flamingo? I'm so funny.
     * @param args The command line arguments. This is not used. It is, however, mandatory. Maybe just don't call this method directly.
     * @since Flames 2
     */
    public static void main(String[] args) {
        flogger.finest("Secret message for the devs: I'm a flamingo.");
//        flogger.severe(MessageCodes.generateCodeError(new FlamesException("Based")));
        try {
            InputStream is = Flames.class.getClassLoader().getResourceAsStream("version.properties");
            versionProp.load(is);
            version = versionProp.getProperty("version");
        } catch (IOException e) {
            getFlogger().severe("Failed to load version properties. This is a fatal error.");
            getFlogger().severe("I don't know what to do now. I'm just going to exit.");
            System.exit(1);
        }
        flogger.finest("Welcome to Flames+!");
        flogger.info("Flames Version " + version);
        if (version.contains("x")) {
            flogger.warning("This is a development build of Flames. It may be unstable.");
            flogger.warning("In other words, I might shit my pants");
        } if (version.contains("X")) {
            flogger.warning("This is a non-standard build of Flames. You should only use it if you know exactly what the purpose of this build is.");
        }
        flogger.resetLastClass();
        flogger.info("Logging in to Discord");
        try {
            api = JDABuilder.createDefault(System.getenv("FlamesToken"), GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.SCHEDULED_EVENTS)
                    .build();
        } catch (IllegalArgumentException e) {
            getFlogger().severe("FlamesToken environment variable not found. Please set the FlamesToken environment variable to your bot's token.");
            System.exit(1);
        } catch (Exception e) {
            Flames.getFlogger().severe(e.getMessage() + " [" + MessageCodes.generateCodeError(new FlamesConnectException(e.getMessage())) + "]");
        }
        flogger.fine("Flames is now online!");
        flogger.resetLastClass();
        flogger.info("Starting primary managers");
        managers.add(new SystemDataManager());
        managers.add(new EventManager());
        managers.add(new FlamesInteractionManager());
        managers.forEach(manager -> {
            try {
                manager.prepare();
            } catch (IOException e) {
                getFlogger().severe("Failed to prepare primary manager " + manager.getClass().getName());
                getFlogger().severe("""
                        A couple of things to try:
                        - Make sure you have permission to write to the directory Flames is in.
                        - If the name of the data manager does not contain "com.severalcircles", you should not run this JAR file. This data manager does not belong to Flames.
                        - Make sure you have enough disk space to run Flames. Flames needs at least 1 MB of free space to run. More is better, of course.""");
                System.exit(1);
            }
            getFlogger().fine("Prepared primary manager " + manager.getClass().getName());

        });
        flogger.info("Starting secondary managers");
        managers2.add(new UserDataManager());
        managers2.add(new FlamesReportManager());
//        managers2.add(TodayManager.newDay());
        managers2.forEach(manager -> {
            try {
                manager.prepare();
            } catch (IOException e) {
                getFlogger().severe("Failed to prepare secondary manager " + manager.getClass().getName());
                getFlogger().severe("""
                        A couple of things to try:
                        - Make sure you have permission to write to the directory Flames is in.
                        - If the name of the data manager does not contain "com.severalcircles", you should not run this JAR file. This data manager does not belong to Flames.
                        - Make sure you have enough disk space to run Flames. Flames needs at least 1 MB of free space to run. More is better, of course.""");
                System.exit(1);
            }
            getFlogger().fine("Prepared secondary manager " + manager.getClass().getName());

        });
        new SystemDataManager().cleanFlamesFiles();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                uptime++;
                flogger.info("Flames has been running for " + uptime + " hours.");
            }
        },0, 1000 * 60 * 60);
    }
    public static JDA getApi() {
        return api;
    }
    public static FLogger getFlogger() {
        return flogger;
    }
}
// ------------------------
// Why is there a microwave
// A poem by Several Circles
// -----------------------
// why do they call it an oven when it's clearly a microwave
// of in the cold food
// of out the hot food
// eat the food
// why is it called a microwave
// when it's clearly an oven
// of in the cold food
// of out the hot food
// eat the food
// why is there a microwave
// when it's clearly not going to be used
// of in the cold food
// of out the hot food
// eat the food
// and how do you know
// that it's not going to be used
// of in the cold food
// of out the hot food
// eat the food