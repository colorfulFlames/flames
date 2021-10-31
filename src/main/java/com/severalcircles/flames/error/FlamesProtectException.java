/*
 * Copyright (c) 2021-2021 Several Circles.
 */

package com.severalcircles.flames.error;

/**
 * Exception thrown when Flames is literally shaking and crying
 */
public class FlamesProtectException extends Exception implements FlamesError {
    /**
     *
     * @param reason the reason the exception was thrown
     */
    public FlamesProtectException(String reason) {
        super(reason);
    }

    @Override
    public String getCode() {
        return "500-001";
    }
}
