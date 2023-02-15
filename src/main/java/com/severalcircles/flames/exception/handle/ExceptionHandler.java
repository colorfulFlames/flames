/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.exception.handle;

import com.severalcircles.flames.exception.FlamesHandlerEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ExceptionHandler extends ThrowableHandler {
    final Exception e;
    public ExceptionHandler(Exception e) {
this.e = e;
    }

    public MessageEmbed handleThenGetFrontend() {
        return new FlamesHandlerEmbed(e).get();
    }

    public void handle() {
        e.printStackTrace();
    }
}
