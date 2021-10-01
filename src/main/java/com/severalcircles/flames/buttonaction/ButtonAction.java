package com.severalcircles.flames.buttonaction;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.io.IOException;

public interface ButtonAction {
    void execute(ButtonClickEvent event, FlamesUser user) throws IOException;
}
