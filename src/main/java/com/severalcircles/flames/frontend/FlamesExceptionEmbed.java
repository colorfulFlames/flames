/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.flames.FlamesException;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Locale;

@ExceptionID("703")
public class FlamesExceptionEmbed extends FlamesEmbed{
    FlamesException e;
    public FlamesExceptionEmbed(Locale locale, FlamesException e) {
        super(locale);
        this.e = e;
    }

    @Override
    public MessageEmbed get() {
        return null;
    }
}
