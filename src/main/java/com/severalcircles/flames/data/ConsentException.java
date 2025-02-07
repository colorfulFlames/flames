/*
 * Copyright (c) 2021-2024 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.data.user.Consent;
import com.severalcircles.flames.exception.FlamesException;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConsentException extends FlamesException {
    public final int consentLevel;

    public ConsentException(int consentLevel, User user) throws InsufficientPermissionException {
        super("Operation not allowed.");
        this.consentLevel = consentLevel;
        if (consentLevel == 0) Consent.getConsent(user); // Ask for consent if the user hasn't answered yet.
        if (consentLevel == 1) throw new IllegalStateException("Consent cannot fail if the user has given consent");
    }

    @Override
    public String getCode() {
        return "403-00" + consentLevel;
    }

    @Override
    public ResourceBundle getRsc(Locale locale) {
        return ResourceBundle.getBundle("exceptions/ConsentException", Locale.ENGLISH);
    }

//    public ConsentException()

}
