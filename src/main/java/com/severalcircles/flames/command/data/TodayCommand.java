/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.command.data;

import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.today.TodayEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class TodayCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        event.replyEmbeds(new TodayEmbed(event.getUser(), sender).get()).complete();
    }
}
