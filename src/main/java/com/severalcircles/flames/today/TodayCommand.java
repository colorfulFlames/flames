/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.today;

import com.severalcircles.flames.FlamesCommand;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class TodayCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        event.replyEmbeds(new TodayEmbed(event.getUser(), sender).get()).complete();
    }
}
