
/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames;

import com.severalcircles.flames.conversations.Conversation;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.events.*;
import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.frontend.conversations.ConversationCommand;
import com.severalcircles.flames.frontend.conversations.SparkCommand;
import com.severalcircles.flames.frontend.data.other.GlobalDataCommand;
import com.severalcircles.flames.frontend.data.user.HiCommand;
import com.severalcircles.flames.frontend.data.user.LocaleCommand;
import com.severalcircles.flames.frontend.data.user.MyDataCommand;
import com.severalcircles.flames.frontend.info.AboutCommand;
import com.severalcircles.flames.frontend.info.HelpCommand;
import com.severalcircles.flames.frontend.info.TestCommand;
import com.severalcircles.flames.frontend.thanks.ThanksCommand;
import com.severalcircles.flames.frontend.today.ResetTodayRunnable;
import com.severalcircles.flames.frontend.today.TodayCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main class for Flames. Sets up everything you could ever hope for.
 */
public class Flames {
    static final Properties properties = new Properties();
    public static String version;
    public static final Map<String, FlamesCommand> commandMap = new HashMap<>();
    public static JDA api;
    public static final List<CommandData> commandDataList = new LinkedList<>();
    static int fatalErrorCounter;
    public static boolean runningDebug = false;
    public static ResourceBundle getCommonRsc(Locale locale) {
        return ResourceBundle.getBundle("Common", locale);
    }
    public static String reportHeader;

    static {
        try {
            reportHeader = "OP:" + System.getProperty("user.name") + " HN:" + InetAddress.getLocalHost().getHostName() + " OS:" + System.getProperty("os.name") + " JRE:" + System.getProperty("java.vendor") + " FV:%s" + " IN:";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Bootloader function
     * @param args any arguments passed to Flames via the command line
     */
    public static void main(String[] args) throws IOException {
        // --- Initial Preparations ---
        InputStream is = Flames.class.getClassLoader().getResourceAsStream("version.properties");
        Locale.setDefault(Locale.ENGLISH);
        properties.load(is);
        version = properties.getProperty("version");
        if (args.length > 0) runningDebug = args[0].equals("--debug");
        if (runningDebug) Logger.getGlobal().info("Running in debugging mode.");
        FlamesDataManager.prepare();
        reportHeader = String.format(reportHeader, version);
        String logName = "Flames " + version + "@" + InetAddress.getLocalHost().getHostName() + " " + Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace(":", " ").replace("T", " T") + ".log";
        File logDir = new File(FlamesDataManager.flamesDirectory.getAbsolutePath() + "/logs");
        //noinspection ResultOfMethodCallIgnored
        logDir.mkdir();
        File logFile = new File(logDir.getAbsolutePath() + "/" + logName);
        //noinspection ResultOfMethodCallIgnored
        logFile.createNewFile();
        FileHandler handler = new FileHandler(logFile.getAbsolutePath());
        handler.setFormatter(new SimpleFormatter());
        Logger.getGlobal().addHandler(handler);
        Logger.getGlobal().log(Level.INFO, "Flames");
        Logger.getGlobal().info(reportHeader + Instant.now());
        if (version.contains("-beta") | version.contains("-alpha") | version.contains("-SNAPSHOT")) {
            Logger.getGlobal().log(Level.WARNING, "This is a development version of Flames. It may be too based for you to handle.");
        }
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/New_York"));
        ZonedDateTime nextRun = now.withHour(0).withMinute(0).withSecond(0);
        if(now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusDays(1);
        Duration duration = Duration.between(now, nextRun);
        long initalDelay = duration.getSeconds();
        GlobalData.read();
        Logger.getGlobal().fine("Scheduling tasks");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(new ReconnectRunnable(), 1, 1, TimeUnit.HOURS);
//        scheduler.scheduleAtFixedRate(new RankUpdateRunnable(), 0, 1, TimeUnit.HOURS);
//        scheduler.scheduleAtFixedRate(new FlushHistoricalData(), 1, 1, TimeUnit.HOURS);
        scheduler.scheduleAtFixedRate(new ResetTodayRunnable(), initalDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
        new ResetTodayRunnable().run();
        Runtime.getRuntime().addShutdownHook(new ExitFlames());
        // --- Connecting to the API and Logging in to Discord ---
        try {
            api = JDABuilder.createDefault(System.getenv("FlamesToken"))
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).build();
            api.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // --- Events ---
        new CommandEvent().register(api);
        new MessageEvent().register(api);
        new ButtonEvent().register(api);
        new SelectMenuEvent().register(api);
        new MessageContextEvent().register(api);
        api.addEventListener(new MessageContextEvent());
        // --- Commands ---
        Logger.getGlobal().log(Level.INFO, "Registering Commands");

        commandMap.put("based", new TestCommand());
        commandDataList.add(Commands.slash("based", "based"));
        commandMap.put("me", new MyDataCommand());
        commandDataList.add(Commands.slash("me", "Today, we're talking about you"));
        commandMap.put("globaldata", new GlobalDataCommand());
        commandDataList.add(Commands.slash("globaldata", "Displays the current Global Data"));
//        commandMap.put("artist", new ArtistCommand());
//        commandDataList.add(Commands.slash("artist", "Displays information for a Spotify artist").addOption(OptionType.STRING, "artist", "The name of the artist", true));
        commandMap.put("hi", new HiCommand());
        commandDataList.add(Commands.slash("hi", "Collect your Daily Bonus"));
        commandMap.put("help", new HelpCommand());
        commandDataList.add(Commands.slash("help", "Get links to support resources like the support server and the documentation"));
        commandMap.put("today", new TodayCommand());
        commandDataList.add(Commands.slash("today", "Find out what Today is all about"));
        commandMap.put("locale", new LocaleCommand());
        commandDataList.add(Commands.slash("locale", "Switches your locale").addOption(OptionType.STRING, "new_locale", "The locale you want to switch to", true));
        commandMap.put("thanks", new ThanksCommand());
        commandDataList.add(Commands.slash("thanks", "Gives Thanks to a user").addOption(OptionType.USER, "who", "The user you want to thank", true).addOption(OptionType.STRING, "msg", "An optional message to attach"));
        commandMap.put("conversation", new ConversationCommand());
        new UserContextEvent().register(api);
        commandDataList.add(Commands.slash("conversation", "Shows information about the current conversation"));
        commandMap.put("about", new AboutCommand());
        commandDataList.add(Commands.slash("about", "Who cooked here?"));
//        commandMap.put("getalong", new GetAlongCommand());
//        commandDataList.add(Commands.slash("getalong", "See how well you Get Along with another user"));
        commandMap.put("spark", new SparkCommand());
        commandDataList.add(Commands.slash("spark", "Start a Spark conversation").addOption(OptionType.STRING, "question", "The question you want to ask", true).addOption(OptionType.INTEGER, "minutes", "Time limit for the conversation in minutes.", true));
//        Commands.context(Command.Type.MESSAGE, "SparkVote");
        api.updateCommands()
                .addCommands(commandDataList).
            complete();
        Conversation.entityList.add("Flames");
//        new IntentEvent().register();
        Logger.getGlobal().info("Done loading. Enjoy!");
    }

    /**
     * Increases the counter of the number of times a fatal error has occurred. If this number gets too high, Flames will exit.
     */
    public static void incrementErrorCount() {
        fatalErrorCounter++;
        if (fatalErrorCounter > 10) {
            Logger.getGlobal().log(Level.SEVERE, "Flames has detected a recurring fatal problem. To protect Flames' data, it will now exit. There may be stack traces above with more information.");
            File file = new File(FlamesDataManager.flamesDirectory.getAbsolutePath() + "/logs/Flames FatalReport:" + Instant.now().toString() + ".log");
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            } catch (IOException e) {
                Logger.getGlobal().log(Level.SEVERE, "Could this get any worse?");
                e.printStackTrace();
                System.exit(3);
            }
            String log = reportHeader + "\n" +
                    "Flames has detected a recurring fatal issue. Similar issues are known to cause damage to Flames, so Flames has been shut down.\n" +
                    "Please report this issue to the developers at https://github.com/colorfulFlames/flames/issues/new?assignees=SeveralCircles&labels=bug&template=bug_report.md&title=Flames Protect Exception\n" +
                    "Thank you for your cooperation.";
            FileWriter writer;
            try {
                writer = new FileWriter(file);
                writer.write(log);
            } catch (IOException e) {
                Logger.getGlobal().log(Level.SEVERE, "Could this get any worse?");
                e.printStackTrace();
                System.exit(3);
            }
            System.exit(2);
        }
    }
    public static ResourceBundle local() {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        String className = st[2].getClassName();
        className = className.replace(".", "/");    // This is a hack to make the ResourceBundle work
        return ResourceBundle.getBundle(className);
    }
    public static ResourceBundle local(Locale locale) {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        String className = st[2].getClassName();
        className = className.replace(".", "/");    // This is a hack to make the ResourceBundle work
        return ResourceBundle.getBundle(className, locale);
    }
}