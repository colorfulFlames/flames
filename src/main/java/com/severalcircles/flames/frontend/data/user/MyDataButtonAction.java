/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import com.severalcircles.flames.frontend.data.user.embed.UserDataEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class MyDataButtonAction implements FlamesButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser sender) {
        event.editMessageEmbeds(new UserDataEmbed(event.getUser(), sender).get()).complete();
    }
}
