/*
 * Copyright (c) 2021-2022 Several Circles.
 */

package com.severalcircles.flames.frontend.info.debug;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Locale;
import java.util.ResourceBundle;

public class DebugCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
        ResourceBundle common = ResourceBundle.getBundle("Common", Locale.ENGLISH);
        event.replyEmbeds(new DebugEmbed(event.getUser(), sender).get()).complete();
    }
}
