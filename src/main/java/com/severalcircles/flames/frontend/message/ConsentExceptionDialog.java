/*
 * Copyright (c) 2025 Several Circles.
 */

package com.severalcircles.flames.frontend.message;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsentExceptionDialog implements FlamesEmbed {
    Locale locale;
    ResourceBundle local;
    public ConsentExceptionDialog(Locale locale) {
        this.locale = locale != null ? locale : Locale.getDefault();
        local = Flames.local(locale);
    }

    @Override
    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(local.getString("author"), Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(local.getString("title"))
                .setDescription(local.getString("description"))
                .setColor(Color.RED)
                .build();
    }
}
