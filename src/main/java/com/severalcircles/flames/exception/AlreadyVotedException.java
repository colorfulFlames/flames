/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.exception;

import com.severalcircles.flames.exception.FlamesException;

import java.util.Locale;
import java.util.ResourceBundle;

public class AlreadyVotedException extends FlamesException {
    public AlreadyVotedException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "401-000";
    }

    @Override
    public ResourceBundle getRsc(Locale locale) {
        return null;
    }
}
