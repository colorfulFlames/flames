package com.severalcircles.flames.data.base;


import com.severalcircles.flames.data.global.GlobalData;
//import com.severalcircles.flames.data.guild.FlamesGuild;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserFunFacts;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.features.rank.Rank;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.system.Flames;
import com.severalcircles.flames.system.WhatTheFuckException;
import com.severalcircles.flames.system.updater.FlamesUpdater;
//import discord4j.common.util.Snowflake;

import java.io.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

// This is an experimental alternative implementation of the FlamesDatabase class that makes use of a properties file instead of a actual database. Makes me want to cry a bit less.
public class FlamesData {
    public static final String flamesDirectory = System.getProperty("user.dir") + "/Flames";
    public static final Map<String, FlamesUser> userCache = new HashMap<>();

    public static void prepare() {
        Logger.getGlobal().log(Level.INFO, "Preparing data...");
        File directory = new File(flamesDirectory);
        if (!directory.exists()) //noinspection ResultOfMethodCallIgnored
            directory.mkdir();
        directory = new File(flamesDirectory + "/user");
        if (!directory.exists()) //noinspection ResultOfMethodCallIgnored
            directory.mkdir();
        directory = new File(flamesDirectory + "/guild");
        if (!directory.exists()) //noinspection ResultOfMethodCallIgnored
            directory.mkdir();
        File file = new File(flamesDirectory + "/global.properties");
        try {
            if (file.createNewFile()) GlobalData.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(FlamesUser user) {
        userCache.put(user.getDiscordId(), user);
    }

    @SuppressWarnings("deprecation")
    public static FlamesUser readUser(String discordId, boolean fixme) throws IOException, WhatTheFuckException {
        Logger.getGlobal().log(Level.FINE, "Reading user data: " + discordId);
        FlamesUser user;
        InputStream inputStream;
        if (userCache.containsKey(discordId)) return userCache.get(discordId);
        Properties data = new Properties();
        File file = new File(flamesDirectory + "/user/" + discordId + ".properties");
        inputStream = file.toURL().openStream();

        data.load(inputStream);
        if (fixme) {
            new FlamesUpdater(data).update();
        }
        user = new FlamesUser();
        user.setDiscordId(discordId);
        try {
            //noinspection deprecation
            user = new FlamesUser(Integer.parseInt(data.get("score") + ""), "" + data.get("firstSeen"), Float.parseFloat(data.get("emotion") + ""), Integer.parseInt("" + data.get("lastSeen")), Integer.parseInt("" + data.get("streak")), "" + data.get("discordId"), "" + data.get("locale"), new UserStats(Integer.parseInt(data.get("exp") + ""), Integer.parseInt(data.get("level") + ""), Integer.parseInt(data.get("POW") + ""), Integer.parseInt(data.get("RES") + ""), Integer.parseInt(data.get("LUCK") + ""), Integer.parseInt(data.get("RISE") + ""), Integer.parseInt(data.get("PTY") + ""), Integer.parseInt(data.get("SEN") + ""), Integer.parseInt(data.get("CAR") + "")), Integer.parseInt(data.get("consent") + ""), Integer.parseInt(data.get("guilds") + ""), new UserFunFacts(Instant.parse(data.get("funFacts.sadDay") + ""), Float.parseFloat(data.get("funFacts.lowestEmotion") + ""), Instant.parse(data.get("funFacts.happyDay") + ""), Float.parseFloat(data.get("funFacts.highestEmotion") + ""), Integer.parseInt(data.get("funFacts.highestFlamesScore") + ""), Integer.parseInt(data.get("funFacts.lowestFlamesScore") + ""), Rank.valueOf(data.get("funFacts.bestRank") + ""), Integer.parseInt(data.get("funFacts.frenchToastMentioned") + "")));
        } catch (NullPointerException | NumberFormatException e) {
            try {
                data = new FlamesUpdater(data).update();
            } catch (WhatTheFuckException whatTheFuckException) {
//                user = new FlamesUser();
//                user.setDiscordId(discordId);
                userCache.put(user.getDiscordId(), user);
                e.printStackTrace();
                flushCaches();
                return user;
            }
        }
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            data.put("score", user.getScore() + "");
            data.put("firstSeen", user.getFirstSeen() + "");
            data.put("emotion", user.getEmotion() + "");
            data.put("lastSeen", user.getLastSeen() + "");
            data.put("streak", user.getStreak() + "");
            data.put("discordId", user.getDiscordId() + "");
            data.put("locale", user.getLocale() + "");
            UserStats stats = user.getStats();
            data.put("level", stats.getLevel() + "");
            data.put("exp", stats.getExp() + "");
            data.put("POW", stats.getPOW() + "");
            data.put("RES", stats.getRES() + "");
            data.put("LUCK", stats.getLUCK() + "");
            data.put("RISE", stats.getRISE() + "");
            data.put("PTY", stats.getPTY() + "");
            data.put("SEN", stats.getSEN() + "");
            data.put("CAR", stats.getCAR() + "");
            data.put("guilds", user.getGuilds());
            UserFunFacts funFacts = user.getFunFacts();
            data.put("funFacts.sadDay", funFacts.getSadDay().toString());
            data.put("funFacts.lowestEmotion", funFacts.getLowestEmotion());
            data.put("funFacts.happyDay", funFacts.getHappyDay().toString());
            data.put("funFacts.highestEmotion", funFacts.getHighestEmotion());
            data.put("funFacts.highestFlamesScore", funFacts.getHighestFlamesScore());
            data.put("funFacts.bestRank", funFacts.getBestRank().toString());
            data.put("funFacts.frenchToastMentioned", funFacts.getFrenchToastMentioned());
            OutputStream outputStream = new FileOutputStream(flamesDirectory + "/user/" + user.getDiscordId() + ".properties");
            data.store(outputStream, Flames.api.getUserById(discordId).getName() + "'s User Data");
        }
        userCache.put(user.getDiscordId(), user);
        flushCaches();
        return user;
    }
    public static void deleteUser(FlamesUser user) throws IOException {
        Logger.getGlobal().log(Level.INFO, "Deleting user data for " + user.getDiscordId());
        File file = new File(flamesDirectory + "/user/" + user.getDiscordId() + ".properties");
//        if (file.createNewFile()) return false;
        file.delete();
    }
    //    public static FlamesUser readUser(String discordId) throws IOException { return readUser(discordId}
    public static void flushCaches() throws IOException {
        Logger.getGlobal().log(Level.INFO, "Saving data");
        for (Map.Entry<String, FlamesUser> entry : userCache.entrySet()) {
                FlamesUser user = entry.getValue();
                Properties data = new Properties();
                data.put("score", user.getScore() + "");
                data.put("firstSeen", user.getFirstSeen() + "");
                data.put("emotion", user.getEmotion() + "");
                data.put("lastSeen", user.getLastSeen() + "");
                data.put("streak", user.getStreak() + "");
                data.put("discordId", user.getDiscordId() + "");
                data.put("locale", user.getLocale() + "");
                UserStats stats = user.getStats();
                data.put("consent", user.getConsent() + "");
                data.put("level", stats.getLevel() + "");
                data.put("exp", stats.getExp() + "");
                data.put("POW", stats.getPOW() + "");
                data.put("RES", stats.getRES() + "");
                data.put("LUCK", stats.getLUCK() + "");
                data.put("RISE", stats.getRISE() + "");
                data.put("PTY", stats.getPTY() + "");
                data.put("SEN", stats.getSEN() + "");
                data.put("CAR", stats.getCAR() + "");
                UserFunFacts funFacts = user.getFunFacts();
                try {
                    data.put("funFacts.sadDay", funFacts.getSadDay().toString());
                    data.put("funFacts.lowestEmotion", funFacts.getLowestEmotion());
                    data.put("funFacts.happyDay", funFacts.getHappyDay().toString());
                    data.put("funFacts.highestEmotion", funFacts.getHighestEmotion());
                    data.put("funFacts.highestFlamesScore", funFacts.getHighestFlamesScore());
                    data.put("funFacts.bestRank", funFacts.getBestRank().toString());
                    data.put("funFacts.frenchToastMentioned", funFacts.getFrenchToastMentioned() + "");
                } catch (NullPointerException e) {
                    data.put("funFacts.sadDay", Instant.now().toString());
                    data.put("funFacts.lowestEmotion", user.getEmotion() + "");
                    data.put("funFacts.happyDay", Instant.now().toString());
                    data.put("funFacts.highestEmotion", user.getEmotion() + "");
                    data.put("funFacts.highestFlamesScore", user.getScore() + "");
                    data.put("funFacts.bestRank", Ranking.getRank(user.getScore()).toString());
                    data.put("funFacts.frenchToastMentioned", 0 + "");
                }
            File file = new File(flamesDirectory + "/user/" + entry.getKey() + ".properties");
            if (file.createNewFile()) Logger.getGlobal().log(Level.INFO, "Adding new user: " + user.getDiscordId());
            OutputStream outputStream = new FileOutputStream(flamesDirectory + "/user/" + entry.getKey() + ".properties");
            data.store(outputStream, null);
            //Flames.api.getUserById(entry.getKey()).getName() + "'s User Data"
        }
    }
}
//    @SuppressWarnings("deprecation")
//    public static FlamesGuild read(String discordId) throws IOException {
//        Logger.getGlobal().log(Level.FINE, "Reading guild: " + discordId);
//        Properties data = new Properties();
//        File file = new File(flamesDirectory + "/guild/" + discordId + ".properties");
//        @SuppressWarnings("deprecation") InputStream inputStream = file.toURL().openStream();
//        data.load(inputStream);
//        return new FlamesGuild(discordId, "" + data.get("name"), Integer.parseInt(data.get("favorites") + ""), "" + data.get("welcomeMessage"));
//    }
//    public static void write(FlamesGuild guild) throws IOException {
//        Logger.getGlobal().log(Level.FINE, "Writing guild: " + guild.getDiscordID());
//        Properties data = new Properties();
//        data.put("discordId", guild.getDiscordID());
//        data.put("favorites", guild.getFavorites() + "");
//        data.put("name", guild.getName());
//        data.put("welcomeMessage", guild.getWelcomeMessage());
//        String fileName = flamesDirectory + "/guild/" +  "" + guild.getDiscordID().replace("{","").replace("}", "").replace("Snowflake", "") + ".properties";
//        File file = new File(fileName);
//        System.out.println(file.getPath());
//        if (file.createNewFile()) Logger.getGlobal().log(Level.FINE, "Adding new guild: " + guild.getDiscordID());
//        OutputStream outputStream = new FileOutputStream(fileName);
//        data.store(outputStream, "");
//    }


