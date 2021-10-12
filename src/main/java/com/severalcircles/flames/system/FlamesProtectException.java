/*
 * Copyright (c) 2021.
 */

package com.severalcircles.flames.system;

/**
 * Exception thrown when Flames is literally shaking and crying
 */
public class FlamesProtectException extends Exception{
    /**
     *
     * @param reason the reason the exception
     */
    public FlamesProtectException(String reason) {
        super(reason);
    }
}
