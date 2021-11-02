
/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames;

import com.bugsnag.Bugsnag;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.events.ButtonEvent;
import com.severalcircles.flames.events.CommandEvent;
import com.severalcircles.flames.events.MessageEvent;
import com.severalcircles.flames.external.spotify.ReconnectRunnable;
import com.severalcircles.flames.external.spotify.SpotifyConnection;
import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.frontend.data.other.GlobalDataCommand;
import com.severalcircles.flames.frontend.data.other.GuildDataCommand;
import com.severalcircles.flames.frontend.data.user.HiCommand;
import com.severalcircles.flames.frontend.data.user.LocaleCommand;
import com.severalcircles.flames.frontend.data.user.MyDataCommand;
import com.severalcircles.flames.frontend.info.ArtistCommand;
import com.severalcircles.flames.frontend.info.DebugCommand;
import com.severalcircles.flames.frontend.info.HelpCommand;
import com.severalcircles.flames.frontend.info.TestCommand;
import com.severalcircles.flames.frontend.today.ResetTodayRunnable;
import com.severalcircles.flames.frontend.today.TodayCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for Flames. Sets up everything you could ever hope for.
 */
public class Flames {
    static final Properties properties = new Properties();
    public static String version;
    public static final Map<String, FlamesCommand> commandMap = new HashMap<>();
    public static JDA api;
    /**
     * Global Spotify Connection referenced throughout Flames
     */
    public static SpotifyConnection spotifyConnection;
    /**
     * Bugsnag instance used to report bugs
     */
    public static Bugsnag bugsnag;
    private static int fatalErrorCounter;

    public static ResourceBundle getCommonRsc(Locale locale) {
        return ResourceBundle.getBundle("Common", locale);
    }
    static {
        try {
            spotifyConnection = new SpotifyConnection();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, "Failed to connect to Spotify.");
        }
    }

    /**
     * Bootloader function
     * @param args any arguments passed to Flames via the command line
     */
    public static void main(String[] args) throws IOException {
        // --- Initial Preparations ---
        InputStream is = Flames.class.getClassLoader().getResourceAsStream("version.properties");
        properties.load(is);
        version = properties.getProperty("version");
        Logger.getGlobal().log(Level.INFO, "Flames version " + version);
        if (version.contains("SNAPSHOT")) {
            Logger.getGlobal().log(Level.INFO, "You are running a development snapshot version of Flames. That means this version represents a \"snapshot\" of what the next release looks like at the time it was developed.\n" +
                    "There is absolutely ZERO promises with this build. You get what you get, please do not throw a fit.\n" +
                    "Do not use this version for anything other than testing. If you are even thinking about using this in any kind of production setting, think again. Wait until this version is officially released.");
        }
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/New_York"));
        ZonedDateTime nextRun = now.withHour(0).withMinute(0).withSecond(0);
        if(now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusDays(1);
        Duration duration = Duration.between(now, nextRun);
        long initalDelay = duration.getSeconds();
        bugsnag = new Bugsnag("4db7c7d93598a437149f27b877cc6a93");
        FlamesDataManager.prepare();
        GlobalData.read();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new ReconnectRunnable(), 1, 1, TimeUnit.HOURS);
//        scheduler.scheduleAtFixedRate(new FlushHistoricalData(), 1, 1, TimeUnit.HOURS);
        scheduler.scheduleAtFixedRate(new ResetTodayRunnable(), initalDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
        // --- Connecting to the API and Logging in to Discord ---
        try {
            Logger.getGlobal().log(Level.INFO, "Token is " + System.getenv("FlamesToken"));
            api = JDABuilder.createDefault(System.getenv("FlamesToken")).build();
            api.awaitReady();
        } catch (LoginException e) {
            Logger.getGlobal().log(Level.SEVERE, "Yeah that's not funny");
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // --- Commands ---
        List<CommandData> commandDataList = new LinkedList<>();
        commandMap.put("based", new TestCommand());
        commandDataList.add(new CommandData("based", "based"));
        commandMap.put("mydata", new MyDataCommand());
        commandDataList.add(new CommandData("mydata", "Displays your User Data"));
        commandMap.put("globaldata", new GlobalDataCommand());
        commandDataList.add(new CommandData("globaldata", "Displays the current Global Data"));
        commandMap.put("artist", new ArtistCommand());
        commandDataList.add(new CommandData("artist", "Displays information for a Spotify artist").addOption(OptionType.STRING, "artist", "The name of the artist"));
        commandMap.put("hi", new HiCommand());
        commandDataList.add(new CommandData("hi", "Collect your Daily Bonus"));
        commandMap.put("help", new HelpCommand());
        commandDataList.add(new CommandData("help", "Get links to support resources like the support server and the documentation"));
        commandMap.put("debug", new DebugCommand());
        commandDataList.add(new CommandData("debug", "Displays debugging information"));
        commandMap.put("today", new TodayCommand());
        commandDataList.add(new CommandData("today", "Find out what Today is all about"));
        commandMap.put("guilddata", new GuildDataCommand());
        commandDataList.add(new CommandData("guilddata", "Displays information for the current guild"));
        commandMap.put("locale", new LocaleCommand());
        commandDataList.add(new CommandData("locale", "Switches your locale").addOption(OptionType.STRING, "new_locale", "The locale you want to switch to", true));
//        RegisterCommand.register();
        api.updateCommands().addCommands(commandDataList).complete();
        // --- Events ---
        new CommandEvent().register(api);
        new MessageEvent().register(api);
        new ButtonEvent().register(api);
    }

    /**
     * Increases the counter of the number of times a fatal error has occurred. If this number gets too high, Flames will exit.
     */
    public static void incrementErrorCount() {
        fatalErrorCounter++;
        if (fatalErrorCounter > 10) {
            Logger.getGlobal().log(Level.SEVERE, "Flames has detected a recurring fatal problem. To protect Flames' data, it will now exit. There may be stack traces above with more information.");
            bugsnag.notify(new FlamesProtectException("Fatal error counter went over 5"));
            System.exit(2);
        }
    }
}