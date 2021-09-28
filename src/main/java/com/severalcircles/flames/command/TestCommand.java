package com.severalcircles.flames.command;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class TestCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        event.reply("You're so based.").queue();
    }
}
