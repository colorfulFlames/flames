/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import com.severalcircles.flames.frontend.data.user.embed.FunFactsEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
//import net.dv8tion.jda.api.events.interaction.ButtonInteractionEvent;

public class
FunFactsButtonAction implements FlamesButtonAction {

    @Override
    public void execute(ButtonInteractionEvent event, FlamesUser user) {
        event.editMessageEmbeds(new FunFactsEmbed(event.getUser(), user).get()).complete();
    }
}
