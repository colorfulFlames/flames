/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.user.FlamesUser;
//import discord4j.core.event.domain.message.MessageCreateEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public interface FlamesCommand {

    void execute(SlashCommandEvent event, FlamesUser sender);
}
