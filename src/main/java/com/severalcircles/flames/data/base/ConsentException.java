/*
 * Copyright (c) 2021.
 */

package com.severalcircles.flames.data.base;

public class ConsentException extends Exception {
    public int consentLevel;
    public ConsentException(int consentLevel) {
        this.consentLevel = consentLevel;
    }

}
