/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class FlamesRuntimeException extends RuntimeException {
    public FlamesRuntimeException(String message) {
        super(message);
    }
    public abstract String getCode();
    public abstract ResourceBundle getRsc(Locale locale);
}
