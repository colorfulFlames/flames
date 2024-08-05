/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.user;

import java.util.Locale;
import java.util.Properties;

public class UserConfig {
    private Locale locale;

    static {
        Locale.getDefault();
    }

    private boolean qotdAllowed;
    private boolean favQuoteAllowed;

    public UserConfig(Locale locale, boolean qotdAllowed, boolean favQuoteAllowed) {
        this.locale = locale;
        this.qotdAllowed = qotdAllowed;
        this.favQuoteAllowed = favQuoteAllowed;
    }

    public boolean isQotdAllowed() {
        return qotdAllowed;
    }

    public void setQotdAllowed(boolean qotdAllowed) {
        this.qotdAllowed = qotdAllowed;
    }

    public boolean isFavQuoteAllowed() {
        return favQuoteAllowed;
    }

    public void setFavQuoteAllowed(boolean favQuoteAllowed) {
        this.favQuoteAllowed = favQuoteAllowed;
    }

    public UserConfig() {
        this.locale = Locale.getDefault();
        this.qotdAllowed = true;
        this.favQuoteAllowed = true;
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
        properties.put("qotdAllowed", String.valueOf(this.qotdAllowed));
        properties.put("favQuoteAllowed", String.valueOf(this.favQuoteAllowed));
        return properties;
    }
}
