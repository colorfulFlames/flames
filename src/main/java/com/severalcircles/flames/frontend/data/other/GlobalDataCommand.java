/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.other;

import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class GlobalDataCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
//        Message message = event.getMessage();
        event.replyEmbeds(new GlobalDataEmbed(event.getUser(), sender).get()).complete();
    }
}
