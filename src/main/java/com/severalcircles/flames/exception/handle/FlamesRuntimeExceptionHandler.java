/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.exception.handle;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.exception.FlamesHandlerEmbed;
import com.severalcircles.flames.exception.FlamesRuntimeException;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class FlamesRuntimeExceptionHandler extends ThrowableHandler {
    FlamesRuntimeException e;
    String cause;
    public FlamesRuntimeExceptionHandler(FlamesRuntimeException e, Class cls) {
        this.e = e;
        if (Flames.runningDebug) this.cause = cls.getName() + "(" + e.getStackTrace()[0].getLineNumber() + ")";
        else this.cause = cls.getSimpleName().replace("Embed", "").replace("Command", "").replace("Context", "").replace("Event", "").replace("ButtonAction", "");
    }

    public MessageEmbed handleThenGetFrontend() {
        handle();
        return new FlamesHandlerEmbed(e, cause).get();
    }
    private void handle() {

    }
}
