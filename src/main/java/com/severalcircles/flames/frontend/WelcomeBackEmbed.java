/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Locale;

@Embed(name = "WelcomeBack")
public class WelcomeBackEmbed extends FlamesEmbed {

    public WelcomeBackEmbed(Locale locale) {
        super(locale);
    }

    @Override
    public MessageEmbed get() {
        return null;
    }
}
