/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
//import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TodayCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, LegacyFlamesUser sender) {
        event.replyEmbeds(new TodayEmbed(event.getUser(), sender).get()).complete();
    }
}
