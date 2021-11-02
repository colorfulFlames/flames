/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.FlamesError;

public class BadArgumentsException extends Exception implements FlamesError {
//    public String description;

    public BadArgumentsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "400-001";
    }
}
