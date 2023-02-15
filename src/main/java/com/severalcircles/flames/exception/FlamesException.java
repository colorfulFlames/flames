/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.exception;

import com.severalcircles.flames.exception.handle.FlamesExceptionHandler;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class FlamesException extends Exception {
    public FlamesException(String message) {
        super(message);
    }
    public abstract String getCode();
    public abstract ResourceBundle getRsc(Locale locale);
    public FlamesExceptionHandler getHandler() {
        return new FlamesExceptionHandler(this);
    }
}
