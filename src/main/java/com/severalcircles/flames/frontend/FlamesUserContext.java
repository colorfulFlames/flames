/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend;

import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

public interface FlamesUserContext {
    public void execute(UserContextInteractionEvent event);
}
