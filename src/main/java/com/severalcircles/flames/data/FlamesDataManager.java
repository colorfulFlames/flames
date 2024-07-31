/*
 * Copyright (c) 2021-2021 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.server.FlamesServer;
import com.severalcircles.flames.data.user.*;
import com.severalcircles.flames.exception.ConsentException;
import com.severalcircles.flames.exception.FlamesMetaException;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for managing Flames data, such as user and guild data.
 */
public class FlamesDataManager {
    /**
     * Data directory
     */
    public static final File FLAMES_DIRECTORY = new File(System.getProperty("user.dir") + "/Flames");
    public static final File USER_DIRECTORY = new File(FLAMES_DIRECTORY.getAbsolutePath() + "/user");
    public static final File SERVER_DIRECTORY = new File(FLAMES_DIRECTORY.getAbsolutePath() + "/server");
    public static final File WILDFIRE_FILE = new File(FLAMES_DIRECTORY.getAbsolutePath() + "/wildfire.fl");
    //    static List<File> openFiles = new LinkedList<>();

    /**
     * Checks if data directories exists and creates them if not.
     */
    public static void prepare() {
        Logger.getGlobal().log(Level.INFO, "Preparing Data...");
        //noinspection ResultOfMethodCallIgnored
        FLAMES_DIRECTORY.mkdir();
        //noinspection ResultOfMethodCallIgnored
        USER_DIRECTORY.mkdir();
        //noinspection ResultOfMethodCallIgnored
        SERVER_DIRECTORY.mkdir();
        try {
            //noinspection ResultOfMethodCallIgnored
            WILDFIRE_FILE.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Flames.incrementErrorCount();
        }
    }

    /**
     * Checks if a given user exists. If they do, simply returns false. If not, it'll create user data for that user, then return true.
     */
    public static boolean newUser(User user) throws IOException {
//        String name = Flames.api.getUserById(discordId).getName();
        String name = user.getName();
        String discordId = user.getId();
        File udir = new File(USER_DIRECTORY.getAbsolutePath() + "/" + discordId);
        File userl = new File(udir.getAbsolutePath() + "/user.fl");
        File config = new File(udir.getAbsolutePath() + "/config.fl");
        File entities = new File(udir.getAbsolutePath() + "/entities.fl");
        // If any of the user data files don't exist, we're just going to assume that the data either doesn't exist or is corrupted and start from scratch because it shouldn't ever happen normally.
        if (udir.mkdir() | userl.createNewFile()) {
            FlamesUser flamesUser = new FlamesUser(user.getId());
            Logger.getGlobal().log(Level.INFO, "User Data for " + discordId + " does not exist. Creating it now.");
            FileOutputStream os1 = new FileOutputStream(userl);
            FileOutputStream os4 = new FileOutputStream(config);
            FileOutputStream os5 = new FileOutputStream(entities);
            flamesUser.setDiscordId(discordId);
            flamesUser.createData().store(os1, "User Data for " + name);
            flamesUser.getConfig().createData().store(os4, "Configuration for " + name);
            flamesUser.getEntities().createData().store(os5, "Entities for " + name);
//            Consent.getConsent(user);
            return true;
        } else if (config.createNewFile()) {
            FileOutputStream os4 = new FileOutputStream(config);
            FlamesUser flamesUser = new FlamesUser(user.getId());
            flamesUser.getConfig().createData().store(os4, "Configuration for " + name);
            return false;
        }
        else {
            return false;
        }
    }

    /**
     * Saves data for a FlamesUser to a file in the user directory
     * @throws IOException If Flames can't write files for whatever reason.
     */
    public static void save(FlamesUser flamesUser) throws IOException {
        String discordId = flamesUser.getDiscordId();
        String name;
        try {name = Objects.requireNonNull(Flames.api.getUserById(discordId)).getName(); } catch (NullPointerException | IllegalArgumentException e) {name = "An Unknown Flames User";}
//        OutputStream outputStream;
        File udir = new File(USER_DIRECTORY.getAbsolutePath() + "/" + discordId);
        File user = new File(udir.getAbsolutePath() + "/user.fl");
        File config = new File(udir.getAbsolutePath() + "/config.fl");
        File entities = new File(udir.getAbsolutePath() + "/entities.fl");
        // If any of the user data files don't exist, we're just going to assume that the data either doesn't exist or is corrupted and start from scratch because it shouldn't ever happen normally.
        if (udir.mkdir() | user.createNewFile() | config.createNewFile() | entities.createNewFile()) {
            Logger.getGlobal().log(Level.INFO, "User Data for " + discordId + " does not exist. Creating it now.");
        }
        flamesUser.setDiscordId(discordId);
        FileOutputStream os1 = new FileOutputStream(user);
        FileOutputStream os4 = new FileOutputStream(config);
        FileOutputStream os6 = new FileOutputStream(entities);
        flamesUser.createData().store(os1, "User Data for " + name);
        flamesUser.getConfig().createData().store(os4, "Configuration for " + name);
        flamesUser.getEntities().getEntities().forEach((key, value) -> {
            System.out.println(key + " | " + value);
        });
        System.out.println(flamesUser.getEntities().createData());
        flamesUser.getEntities().createData().store(os6, "Entities for " + name);
    }

    /**
     * Reads data for a user from the data directory
     * @param user Discord User to get data for
     * @return A FlamesUser created from the data file
     * @throws ConsentException If the user hasn't consented yet won't return data
     */
    public static FlamesUser readUser(User user) throws IOException, ConsentException, UnsupportedOperationException, FlamesMetaException {
        return readUser(user.getId(), false);
    }
    public static FlamesUser readUser(User user, boolean skipConsent) throws IOException, ConsentException, UnsupportedOperationException, FlamesMetaException {
        return readUser(user.getId(), skipConsent);
    }
    /**
     * Reads data for a user from the data directory
     * @param skipConsent If true, won't throw an exception if the user hasn't consented. For most cases, leave this false or null.
     * @return A FlamesUser created from the data file
     * @throws ConsentException If the user hasn't consented yet won't return data, unless you ask nicely
     */
    public static FlamesUser readUser(String id, boolean skipConsent) throws IOException, ConsentException {
        FlamesUser fluser = new FlamesUser(id);
        File udir = new File(USER_DIRECTORY.getAbsolutePath() + "/" + id);
        if (id == null) throw new IllegalArgumentException("User ID cannot be null. Did you really think you were going to get away with that?");
        if (udir.mkdir() && !skipConsent) {
            throw new ConsentException(0, Flames.api.retrieveUserById(id).complete());
        }
        File userfl = new File(udir.getAbsolutePath() + "/user.fl");
        File config = new File(udir.getAbsolutePath() + "/config.fl");
        File entities = new File(udir.getAbsolutePath() + "/entities.fl");
        if (userfl.createNewFile() | config.createNewFile() | entities.createNewFile()) {
            Logger.getGlobal().log(Level.INFO, "User Data for " + id + " something something doesn't exist. Creating it now.");
        }
        FileInputStream inputStream1 = new FileInputStream(userfl);
        FileInputStream inputStream4 = new FileInputStream(config);
        FileInputStream inputStream6 = new FileInputStream(entities);

        // Prepare Properties() objects
        Properties data = new Properties();
        Properties configdata = new Properties();
        Properties entitiesData = new Properties();
        // Load data from properties files
        data.load(inputStream1);
        if (data.isEmpty()) data = new FlamesUser(id).createData();
        configdata.load(inputStream4);
        if (configdata.isEmpty()) configdata = new UserConfig().createData();
        entitiesData.load(inputStream6);
        if (entitiesData.isEmpty()) entitiesData = new UserEntities().createData();
            // Set data from properties files for FlamesUser
        fluser.setScore(Integer.parseInt(String.valueOf(data.get("score"))));
        fluser.setEmotion(Float.parseFloat(String.valueOf(data.get("emotion"))));
        fluser.setDiscordId(id);
        fluser.setDataVersion(Double.parseDouble(String.valueOf(data.get("version"))));
        fluser.setConsent(Integer.parseInt(String.valueOf(data.get("consent"))));
        fluser.setStreak(Integer.parseInt(String.valueOf(data.get("streak"))));
        fluser.setLastSeen(Instant.parse(String.valueOf(data.get("lastSeen"))));
        // Set data from properties files for UserConfig
        UserConfig config1;
        try {
        config1 = new UserConfig(Locale.forLanguageTag(String.valueOf(configdata.get("locale"))), Boolean.parseBoolean(configdata.get("qotdAllowed").toString()), Boolean.parseBoolean(configdata.get("favQuoteAllowed").toString()));
        } catch (NullPointerException e) {
            Logger.getGlobal().log(Level.WARNING, "User Config Data for " + id + " is [REDACTED]. Creating it now.");
            config1 = new UserConfig();
        }
        fluser.setConfig(config1);
        // Set data from properties files for UserEntities
        UserEntities userEntities = new UserEntities();
        Map<String, UserEntity> e = new HashMap<>();
        List<String> roots = new LinkedList<>();
        Properties finalEntitiesData = entitiesData;
        entitiesData.forEach((key, value) -> {
            if (key.toString().endsWith(".name")) {
                roots.add(key.toString().replace(".name", ""));
            }
            roots.forEach((root) -> {
                e.put(finalEntitiesData.get(root + ".name").toString(), new UserEntity(finalEntitiesData.get(root + ".name").toString(), Integer.parseInt(finalEntitiesData.get(root + ".count").toString()), Integer.parseInt(finalEntitiesData.get(root + ".happyCount").toString()), Integer.parseInt(finalEntitiesData.get(root + ".sadCount").toString())));
            });
        });
        userEntities.setEntities(e);
        fluser.setEntities(userEntities);
        if (fluser.getConsent() != 1 && !skipConsent) throw new ConsentException(fluser.getConsent(), Flames.api.getUserById(id));
        return fluser;
    }
    public static String[] getChannelWords(Channel channel) {
        File channelFile = new File(SERVER_DIRECTORY.getAbsolutePath() + "/" + ((GuildChannel) channel).getGuild().getId() + "/channels/" + channel.getId() + ".flx");
        try {
            if (channelFile.createNewFile()) return new String[0];
            if (channelFile.getParentFile().mkdirs()) return new String[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileInputStream inputStream = new FileInputStream(channelFile);
            Properties data = new Properties();
            data.load(inputStream);
            return data.get("words").toString().split(" ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addChannelWord(String word, Channel channel) {
        File channelFile = new File(SERVER_DIRECTORY.getAbsolutePath() + "/" + ((GuildChannel) channel).getGuild().getId() + "/channels/" + channel.getId() + ".flx");
        String[] channelWords = getChannelWords(channel);
        String[] newChannelWords = new String[channelWords.length + 1];
        newChannelWords[channelWords.length] = word;
        try {
            FileWriter writer = new FileWriter(channelFile);
        for (String channelWord : channelWords) {
                writer.write(channelWord + " ");
            }
        writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static FlamesServer getServer(String id) {
        File serverFile = new File(SERVER_DIRECTORY.getAbsolutePath() + "/" + id + ".fl");
        FlamesServer server;
        try {
            if (serverFile.createNewFile()) {
                server = new FlamesServer(id);
                saveServer(server);
                return server;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileInputStream inputStream = new FileInputStream(serverFile);
            Properties data = new Properties();
            data.load(inputStream);
            int score = Integer.parseInt(data.get("score").toString());
            int hootanannyDay = Integer.parseInt(data.get("hootanannyDay").toString());
            String id2 = data.get("id").toString();
            return new FlamesServer(score, id2, hootanannyDay);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveServer(FlamesServer server) {
        File serverFile = new File(SERVER_DIRECTORY.getAbsolutePath() + "/" + server.getId() + ".fl");
        try {
            if (serverFile.createNewFile()) return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileOutputStream os = new FileOutputStream(serverFile);
            server.createData().store(os, "Flames Server Data");
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
