/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.info;

import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
//import discord4j.core.event.domain.message.MessageCreateEvent;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TestCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, LegacyFlamesUser sender) {
        event.reply("You're so based.").complete();
    }
}
