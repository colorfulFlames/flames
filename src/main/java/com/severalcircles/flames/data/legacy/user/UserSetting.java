/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.legacy.user;

public enum UserSetting {
    LOCALE();
    private final String code;
    UserSetting() {
        this.code = "1";
    }
    public String getCode() {
        return code;
    }
}
