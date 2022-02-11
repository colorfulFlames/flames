/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import com.severalcircles.flames.frontend.data.user.embed.FunFactsEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.io.IOException;

public class
FunFactsButtonAction implements FlamesButtonAction {

    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
        event.editMessageEmbeds(new FunFactsEmbed(event.getUser(), user).get()).complete();
    }
}
