/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import com.severalcircles.flames.frontend.data.user.embed.StatsEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class StatsButtonAction implements FlamesButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) {
        event.editMessageEmbeds(new StatsEmbed(event.getUser(), user).get()).queue();

    }
}
