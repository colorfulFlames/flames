/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend;

import net.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;

public interface FlamesMessageContext {
    public void execute(MessageContextInteraction event);
}
