/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.info;

import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AboutCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, LegacyFlamesUser sender) {
        event.replyEmbeds(new AboutEmbed(sender).get()).complete();
    }
}
