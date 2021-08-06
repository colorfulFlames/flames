package com.severalcircles.flames.command;

import discord4j.core.event.domain.message.MessageCreateEvent;

public class TestCommand implements Command{
    @FlamesCommand(commandName = "test", description = "Allows you to test the Flames Command Framework", guildRequired = false)
    @Override
    public void execute(MessageCreateEvent event) {
        event.getMessage().getChannel().block().createMessage("It works!");
    }
}
