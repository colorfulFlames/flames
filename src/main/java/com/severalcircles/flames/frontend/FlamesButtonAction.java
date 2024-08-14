/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.io.IOException;

public interface FlamesButtonAction {
    void execute(ButtonInteractionEvent event, LegacyFlamesUser user) throws IOException;
}
