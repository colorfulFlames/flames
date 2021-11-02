/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.message.fivehundred;

import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.frontend.message.FlamesErrorMessage;

/**
 * Returns the default error message set in the superclass
 */
public class GenericErrorMessage extends FlamesErrorMessage {
    public GenericErrorMessage(FlamesError e) {
        super(e);
    }

    public GenericErrorMessage(Exception e) {
        super(e);
    }
}
