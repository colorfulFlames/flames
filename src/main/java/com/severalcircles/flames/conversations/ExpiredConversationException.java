/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.FlamesError;

public class ExpiredConversationException extends Exception implements FlamesError {
    @Override
    public String getCode() {
        return "406-010";
    }
}
