package com.severalcircles.flames.system;

import com.severalcircles.flames.command.Command;
import com.severalcircles.flames.command.TestCommand;
import com.severalcircles.flames.data.base.FlamesDatabase;
import com.severalcircles.flames.events.GuildAddEvent;
import com.severalcircles.flames.events.MessageEvent;
//import com.severalcircles.flames.features.events.EventStartEventListener;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Flames {
    public static final ResourceBundle commonResources = ResourceBundle.getBundle("Common");
    public static DiscordClient client = null;
    public static OperationMode operationMode = OperationMode.NORMAL;
    public static final Map<String, Command> commands = new HashMap<>();
    private static final Logger logger = Logger.getGlobal();
//    public static GuildChannelChannel announcementsChannel;
    public static GatewayDiscordClient gatewayDiscordClient;
    public static void main(final String[] args) {
        //Register commands (there has to be a better way to do this, right?)
        logger.log(Level.FINE, "Enabling commands...");
        commands.put("test", new TestCommand());
        // ---------------------------------------------------------------------------------------------------

        final String token = "ODQ5MzIwMjU5MTUyMTE3ODgy.YLZdIQ.keIWGep6_IjSQvbD4NMXQXwKYl4";
        client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();
        gatewayDiscordClient = gateway;
        logger.log(Level.FINE, "Attempting to connect to Flames' Database...");
        try {
            FlamesDatabase fd = new FlamesDatabase();
            fd.close();
        } catch (SQLException throwables) {
            logger.log(Level.SEVERE, "Failed to connect to Flames' Database. Please see logging info below.");
            throwables.printStackTrace();
            logger.log(Level.SEVERE, "Flames wasn't able to connect to it's database. Flames will continue in No Database mode. You'll still be able to test out Flames, but no information will be stored.");
            operationMode = OperationMode.NO_DATABASE;
        }
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            try {
                new MessageEvent(event).run();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                logger.log(Level.SEVERE, "Error when processing message.");
            }
        });
        gateway.on(GuildCreateEvent.class).subscribe(event -> {
            final Guild g = event.getGuild();
            try {
                new GuildAddEvent(g).run();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                g.getOwner().block().getPrivateChannel().block().createMessage("Hi! Looks like someone tried to add Flames to your server. Flames' Database is currently experiencing issues, so Flames can't join any servers right now. Flames has left your server for now, but we hope you'll add him again later when the database is working properly!");
                g.leave();
            }
        });
        gateway.onDisconnect().block();
    }
}
