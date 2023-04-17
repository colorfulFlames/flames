/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.exception.java;

import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.flames.FlamesException;

import java.net.ConnectException;

@ExceptionID("551")
public class FlamesConnectException extends FlamesException {
    /**
     * Creates a new FlamesException with the specified message.
     * What's a FlamesException? I don't know. I'm just a microwave.
     * Maybe, just maybe, it's a microwave that's on fire. Or, as I like to call it, a "flame-crowave."
     * Why do they call it a microwave? It's not like it's a micro-waive. or a mic or a wave. It's a microwave. Or, as I like to call it, a "wave-cro."
     * The more likely scenario is this is an exception that's on fire. Or, as I like to call it, a "flame-ception."
     * Even more likely, it's an exception created by Flames that only applies to Flames. I have to make it look like I got something done today. I'll put down on my timesheet that I "documented" this class.
     *
     * @param message The message to be displayed.
     * @since Flames 8
     */
    public FlamesConnectException(String message) {
        super(message);
    }
}
