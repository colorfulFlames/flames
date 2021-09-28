package com.severalcircles.flames.buttonaction.data.deleteuserdata;

import com.severalcircles.flames.buttonaction.ButtonAction;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.io.IOException;

public class NoDontButtonAction implements ButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
        event.editMessage("Alright, cancelled.");
    }
}
