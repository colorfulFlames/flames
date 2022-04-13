/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface FlamesIntentResponse {
    void execute(MessageReceivedEvent origMsg);
}
