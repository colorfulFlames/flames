
/**
 * Main class for Flames. Sets up everything you could ever hope for.
 */

package com.severalcircles.flames.system;

import com.bugsnag.Bugsnag;
import com.severalcircles.flames.command.DebugCommand;
import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.command.HelpCommand;
import com.severalcircles.flames.command.TestCommand;
import com.severalcircles.flames.command.connections.ArtistCommand;
import com.severalcircles.flames.command.data.GlobalDataCommand;
import com.severalcircles.flames.command.data.HiCommand;
import com.severalcircles.flames.command.data.MyDataCommand;
import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.data.base.FlushRunnable;
import com.severalcircles.flames.events.discord.ButtonEvent;
import com.severalcircles.flames.events.discord.CommandEvent;
import com.severalcircles.flames.events.discord.MemberAddEvent;
import com.severalcircles.flames.events.discord.MessageEvent;
import com.severalcircles.flames.features.external.ExternalConnectionFailedException;
import com.severalcircles.flames.features.external.spotify.ReconnectRunnable;
import com.severalcircles.flames.features.external.spotify.SpotifyConnection;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Here we go again
Same old stuff again
Relighting the Flame
Maybe with this we'll be through.
*/

public class Flames {
    public static Map<String, FlamesCommand> commandMap = new HashMap<>();
    public static JDA api;
    public static SpotifyConnection spotifyConnection;
    static {
        try {
            spotifyConnection = new SpotifyConnection();
        } catch (IOException | ExternalConnectionFailedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // --- Initial Preparations ---
        Bugsnag bugsnag = new Bugsnag("4db7c7d93598a437149f27b877cc6a93");
        FlamesData.prepare();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new FlushRunnable(), 5, 5, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(new ReconnectRunnable(), 1, 1, TimeUnit.HOURS);
        // --- Connecting to the API and Logging in to Discord ---
        try {
            api = JDABuilder.createDefault(System.getenv("FlamesToken")).build();
            api.awaitReady();
        } catch (LoginException e) {
            Logger.getGlobal().log(Level.SEVERE, "Yeah that's not funny");
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // --- Commands ---
        commandMap.put("based", new TestCommand());
        commandMap.put("mydata", new MyDataCommand());
        commandMap.put("globaldata", new GlobalDataCommand());
        commandMap.put("artist", new ArtistCommand());
        commandMap.put("hi", new HiCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("debug", new DebugCommand());
        api.updateCommands();
        // --- Events ---
        new CommandEvent().register(api);
        new MessageEvent().register(api);
        new ButtonEvent().register(api);
        new MemberAddEvent().register(api);
    }
}