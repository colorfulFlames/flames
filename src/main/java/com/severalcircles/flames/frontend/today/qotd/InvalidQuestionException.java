/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.today.qotd;

import com.severalcircles.flames.FlamesError;

public class InvalidQuestionException extends Exception implements FlamesError {
    @Override
    public String getCode() {
        return "406-100";
    }
}
