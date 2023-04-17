/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.exception.flames;

import com.severalcircles.flames.system.exception.ExceptionID;

@ExceptionID("070")
public class AlreadyCollectedException extends FlamesException {
    /**
     * Constructs a new AlreadyCollectedException with the specified detail message.
     *
     * @param message The message to be displayed.
     * @since Flames 8
     */
    public AlreadyCollectedException(String message) {
        super(message);
    }
}
