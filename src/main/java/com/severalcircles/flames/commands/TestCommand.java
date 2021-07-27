package com.severalcircles.flames.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import java.util.ResourceBundle;

public class TestCommand extends Command{
    static ResourceBundle resources = ResourceBundle.getBundle("commands/TestCommand");
    public TestCommand() {
        super(resources.getString("name"), resources.getString("readableName"),CommandType.INFO);
    }

    @Override
    public void run(Message message) {
        MessageChannel channel = message.getChannel().block();
        channel.createMessage(String.format(resources.getString("response"), message.getAuthorAsMember().block().getNickname()));
    }
}
