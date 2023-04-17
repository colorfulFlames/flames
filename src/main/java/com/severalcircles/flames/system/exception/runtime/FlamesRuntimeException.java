/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.exception.runtime;

import com.severalcircles.flames.system.exception.ExceptionID;

@ExceptionID("030")
public class FlamesRuntimeException extends RuntimeException {
    /**
     * You've heard of a FlamesException. Now, get ready for the FlamesRuntimeException. It's like a FlamesException, but it's a runtime exception. It's a FlamesRuntimeException, and it lives in a world of piss and vinegar.
     * It's 1:30 AM. I'm not tired. I just want to work on Flames. I have to be up too fucking early tomorrow.
     * @since Flames 8
     * @param message i don't even know anymore
     */
    public FlamesRuntimeException(String message) {
        super(message);
    }
}
