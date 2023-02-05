/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.user;

import com.severalcircles.flames.data.FlamesData;

import java.util.Locale;
import java.util.Properties;

public class UserConfig implements FlamesData {
    private Locale locale = Locale.getDefault();

    public UserConfig(Locale locale) {
        this.locale = locale;
    }

    public UserConfig() {
        this.locale = Locale.getDefault();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Properties createData() {
        Properties properties = new Properties();
        properties.put("locale", this.locale.toLanguageTag());
        return properties;
    }
}
