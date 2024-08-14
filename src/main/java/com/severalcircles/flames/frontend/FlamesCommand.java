/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.ConsentException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public interface FlamesCommand {

    void execute(SlashCommandInteractionEvent event, LegacyFlamesUser sender) throws ConsentException, IOException;
}
