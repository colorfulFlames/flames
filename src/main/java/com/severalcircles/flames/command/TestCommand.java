package com.severalcircles.flames.command;

import com.severalcircles.flames.data.user.FlamesUser;
import discord4j.core.event.domain.message.MessageCreateEvent;

import java.util.Locale;
import java.util.ResourceBundle;

public class TestCommand implements Command{
    ResourceBundle resources;
    @Override
    public void execute(MessageCreateEvent event, FlamesUser sender) {
        resources = ResourceBundle.getBundle("TestCommand", Locale.forLanguageTag(sender.getLocale()));
        event.getMessage().getChannel().block().createMessage(resources.getString("response"));
    }
}
