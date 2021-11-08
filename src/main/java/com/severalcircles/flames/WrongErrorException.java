/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames;

public class WrongErrorException extends Exception implements FlamesError {
    @Override
    public String getCode() {
        return "500-001";
    }
}
