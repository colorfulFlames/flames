/*
 * Copyright (c) 2021.
 */

package com.severalcircles.flames.data.base;

import com.severalcircles.flames.features.safety.Consent;
import net.dv8tion.jda.api.entities.User;

public class ConsentException extends Exception {
    public int consentLevel;
    public User user;
    public ConsentException(int consentLevel, User user) {
        this.consentLevel = consentLevel;
        if (consentLevel == 0) {
            Consent.getConsent(user);
        }
    }
//    public ConsentException()

}
