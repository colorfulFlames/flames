package com.severalcircles.flames.system;

import com.severalcircles.flames.command.Command;
import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.command.TestCommand;
import com.severalcircles.flames.data.base.FlamesDatabase;
import com.severalcircles.flames.events.MessageEvent;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Flames {
    public static DiscordClient client = null;
    public static OperationMode operationMode = OperationMode.NORMAL;
    public static final Map<String, Command> commands = new HashMap<>();
    private static final Logger logger = Logger.getGlobal();
    public static void main(final String[] args) {
        //Register commands (there has to be a better way to do this, right?)
        logger.log(Level.FINE, "Enabling commands...");
        commands.put(TestCommand.class.getAnnotation(FlamesCommand.class).commandName(), new TestCommand());
        // ---------------------------------------------------------------------------------------------------
        final String token = "ODQ5MzIwMjU5MTUyMTE3ODgy.YLZdIQ.keIWGep6_IjSQvbD4NMXQXwKYl4";
        client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();
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

        gateway.onDisconnect().block();
    }
}
