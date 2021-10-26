/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.info.error.message.fivehundred;

import com.severalcircles.flames.features.info.error.FlamesError;
import com.severalcircles.flames.features.info.error.message.FlamesErrorMessage;

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
