package com.severalcircles.flames.command;

import com.severalcircles.flames.data.user.FlamesUser;
//import discord4j.core.event.domain.message.MessageCreateEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Locale;
import java.util.ResourceBundle;

public class TestCommand implements FlamesCommand{
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        event.reply("You're so based.");
    }
}
