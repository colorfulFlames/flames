/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.FlamesError;

public class DataVersionException extends Exception implements FlamesError {
    private int code;
    public DataVersionException(boolean tooOld) {
        if (tooOld) code = 2;
        else code = 1;
    }
    @Override
    public String getCode() {
        return "406-00" + code;
    }
}
