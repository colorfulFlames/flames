/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface FlamesCommand {

    void execute(SlashCommandInteractionEvent event, FlamesUser sender);
}
