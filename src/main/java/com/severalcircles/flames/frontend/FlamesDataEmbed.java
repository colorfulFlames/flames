/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.entities.User;

import java.util.Locale;

public abstract class FlamesDataEmbed extends FlamesEmbed {
    FlamesUser user;
    private FlamesDataEmbed(Locale locale) {
        super(locale);
    }
    public FlamesDataEmbed(FlamesUser user) {
        super(user.getLocale());
        this.user = user;
    }
}
