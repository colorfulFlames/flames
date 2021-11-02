/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.user;

public enum UserSetting {
    LOCALE("1");
    private String code;
    UserSetting(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code = code;
    }
}
