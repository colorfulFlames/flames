/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.exception.user;

import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.FlamesException;
import net.dv8tion.jda.api.entities.User;

import java.util.Locale;
import java.util.ResourceBundle;

public class FlamesUserException extends FlamesException {
    final FlamesUser flamesUser;
    final User user;
    @Override
    public String getCode() {
        return "500x";
    }

    @Override
    public ResourceBundle getRsc(Locale locale) {
        return null;
    }

    public FlamesUserException(FlamesUser flamesUser, String message, User user) {
        super(message);
        this.flamesUser = flamesUser;
        this.user = user;
    }
    public FlamesUser getFlamesUser() {
        return flamesUser;
    }

    public User getUser() {
        return user;
    }
}
