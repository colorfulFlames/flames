/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.io.IOException;

public interface FlamesButtonAction {
    void execute(ButtonClickEvent event, FlamesUser user) throws IOException;
}
