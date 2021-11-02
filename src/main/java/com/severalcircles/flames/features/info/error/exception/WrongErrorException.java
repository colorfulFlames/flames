/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.info.error.exception;

import com.severalcircles.flames.features.info.error.FlamesError;

public class WrongErrorException extends Exception implements FlamesError {
    @Override
    public String getCode() {
        return "500-001";
    }
}
