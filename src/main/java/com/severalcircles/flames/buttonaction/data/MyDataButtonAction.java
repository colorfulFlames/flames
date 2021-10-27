package com.severalcircles.flames.buttonaction.data;

import com.severalcircles.flames.buttonaction.ButtonAction;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.info.data.UserDataEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class MyDataButtonAction implements ButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser sender) {
        event.replyEmbeds(new UserDataEmbed(event.getUser(), sender).get());
    }
}
