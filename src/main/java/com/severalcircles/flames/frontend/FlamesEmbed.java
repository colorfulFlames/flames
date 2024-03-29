/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.system.exception.ExceptionID;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Abstract class for embeds that are used in the bot. Embeds are used to display information in a more visually appealing way than text.
 * big chungus, big chungus, big chungus, big big chungus
 * @author Several Circles
 * @version 8
 * @since Flames 8
 */
@ExceptionID("700")
public abstract class FlamesEmbed {
    ResourceBundle local;
    Locale locale;
    public FlamesEmbed(Locale locale) {
        local = ResourceBundle.getBundle("strings/" + this.getClass().getAnnotation(Embed.class).name(), locale);
        this.locale = locale;
    }
    public abstract MessageEmbed get();
}
