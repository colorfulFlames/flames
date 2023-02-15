/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.exception.FlamesRuntimeException;

import java.util.Locale;
import java.util.ResourceBundle;

public class ExpiredConversationException extends FlamesRuntimeException {
    public ExpiredConversationException() {
        super("");
    }

    @Override
    public String getCode() {
        return "406-010";
    }

    @Override
    public ResourceBundle getRsc(Locale locale) {
        return null;
    }
}
