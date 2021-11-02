/*
 * Copyright (c) 2021-2021 Several Circles.
 */

package com.severalcircles.flames.data.user.consent;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.severalcircles.flames.FlamesError;

public class ConsentException extends Exception implements FlamesError {
    public final int consentLevel;

    public ConsentException(int consentLevel, User user) throws InsufficientPermissionException {
        this.consentLevel = consentLevel;
        if (consentLevel == 0) {
            Consent.getConsent(user);
        }
    }

    @Override
    public String getCode() {
        return "403-00" + consentLevel;
    }

//    public ConsentException()

}
