/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public class FlamesMetaException extends FlamesRuntimeException{
    public FlamesMetaException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public ResourceBundle getRsc(Locale locale) {
        return ResourceBundle.getBundle("exceptions/FlamesMetaException", locale);
    }
}
