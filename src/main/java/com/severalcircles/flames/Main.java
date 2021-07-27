package com.severalcircles.flames;

import com.severalcircles.flames.commands.CommandRegistry;
import com.severalcircles.flames.events.MessageEvent;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import io.netty.handler.logging.LogLevel;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static DiscordClient client = null;
    public static void main(final String[] args) {
        CommandRegistry.registerCommands();
        final String token = "ODQ5MzIwMjU5MTUyMTE3ODgy.YLZdIQ.keIWGep6_IjSQvbD4NMXQXwKYl4";
        client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            try {
                new MessageEvent(message).run();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                Logger.getGlobal().log(Level.SEVERE, "Error when processing message.");
            }
        });

        gateway.onDisconnect().block();
    }
}
