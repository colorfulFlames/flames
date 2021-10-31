/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.info;

import com.severalcircles.flames.data.user.FlamesUser;
//import discord4j.core.event.domain.message.MessageCreateEvent;
import com.severalcircles.flames.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class TestCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        event.reply("You're so based.").complete();
    }
}
