/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.user.feature;

import com.severalcircles.flames.FlamesButtonAction;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class MyDataButtonAction implements FlamesButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser sender) {
        event.replyEmbeds(new UserDataEmbed(event.getUser(), sender).get());
    }
}
