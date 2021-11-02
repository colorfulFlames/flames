/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.external;

import com.severalcircles.flames.FlamesError;

public class ExternalConnectionFailedException extends Exception implements FlamesError {
    @Override
    public String getCode() {
        return "501-001";
    }
}
