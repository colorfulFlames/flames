/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.exception.user;

import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.exception.FlamesException;
import net.dv8tion.jda.api.entities.User;

import java.util.Locale;
import java.util.ResourceBundle;

public class FlamesUserException extends FlamesException {
    final LegacyFlamesUser legacyFlamesUser;
    final User user;
    @Override
    public String getCode() {
        return "500x";
    }

    @Override
    public ResourceBundle getRsc(Locale locale) {
        return null;
    }

    public FlamesUserException(LegacyFlamesUser legacyFlamesUser, String message, User user) {
        super(message);
        this.legacyFlamesUser = legacyFlamesUser;
        this.user = user;
    }
    public LegacyFlamesUser getFlamesUser() {
        return legacyFlamesUser;
    }

    public User getUser() {
        return user;
    }
}
