/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames;

/**
 * Thrown by certain error messages when the wrong error is fed to them.
 */
public class WrongErrorException extends Exception implements FlamesError {
    @Override
    public String getCode() {
        return "500-001";
    }
}
