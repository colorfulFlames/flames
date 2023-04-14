/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.exception.runtime;

import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.runtime.FlamesRuntimeException;

@ExceptionID("010")
public class NoCommandException extends FlamesRuntimeException {
    public NoCommandException(String message) {
        super(message);
    }
}
