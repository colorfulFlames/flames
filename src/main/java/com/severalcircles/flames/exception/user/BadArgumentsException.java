/*
 * Copyright (c) 2021-2023 Several Circles.
 */

package com.severalcircles.flames.exception.user;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.user.FlamesUserException;
import net.dv8tion.jda.api.entities.User;

import java.util.Locale;
import java.util.ResourceBundle;

public class BadArgumentsException extends FlamesUserException {
    public BadArgumentsException(FlamesUser flamesUser, String message, User user) {
        super(flamesUser, message, user);
    }

    @Override
    public String getCode() {
        return "400-001";
    }

    @Override
    public ResourceBundle getRsc(Locale locale) {
        return ResourceBundle.getBundle("exceptions/BadArgumentsException", Locale.ENGLISH);
    }
}
