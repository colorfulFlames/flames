package com.severalcircles.flames.features.external;

import com.severalcircles.flames.features.info.error.FlamesError;

public class ExternalConnectionFailedException extends Exception implements FlamesError {
    @Override
    public String getCode() {
        return "501-001";
    }
}
