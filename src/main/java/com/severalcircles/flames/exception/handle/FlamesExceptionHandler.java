/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.exception.handle;

import com.severalcircles.flames.exception.FlamesException;
import com.severalcircles.flames.exception.FlamesHandlerEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class FlamesExceptionHandler extends ThrowableHandler {
    final FlamesException e;
    public FlamesExceptionHandler(FlamesException e) {
        this.e = e;
    }

    public MessageEmbed handleThenGetFrontend() {
        handle();
        return new FlamesHandlerEmbed(e).get();
    }

    private void handle() {

    }
}
