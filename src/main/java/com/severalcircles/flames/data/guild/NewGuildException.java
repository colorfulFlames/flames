/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.guild;

import com.severalcircles.flames.FlamesError;

public class NewGuildException extends Exception implements FlamesError {
    @Override
    public String getCode() {
        return "503-001";
    }
}
