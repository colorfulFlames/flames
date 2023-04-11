/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.exception;

import com.severalcircles.flames.Flames;

/**
 * Exception thrown when big big chungus, big chungus, big chungus, big big chungus
 * @since Flames 8
 * @author Several Circles
 * @version 8
 */
public class ConsentException extends FlamesException {
    double consent;
    public ConsentException(double cnst) {
        super("Couldn't obtain consent.");
        int consent = (int) cnst;
        this.consent = cnst;
        switch (consent) {
            case 0 -> //noinspection RedundantLabeledSwitchRuleCodeBlock
            {
                Flames.getFlogger().warning("The user hasn't responded to the consent prompt yet.");
            }
            case 2 -> //noinspection RedundantLabeledSwitchRuleCodeBlock
            {
                Flames.getFlogger().warning("The user has declined consent.");
            }
            default -> //noinspection RedundantLabeledSwitchRuleCodeBlock
            {
                throw new IllegalStateException("Unexpected value: " + consent);
            }
        }
    }

    public double getConsent() {
        return consent;
    }
}
