/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.message.fivehundred;

import com.severalcircles.flames.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class SpotifyConnectionFailedErrorMessage extends GenericErrorMessage {
    static final ResourceBundle resources = ResourceBundle.getBundle("error/SpotifyConnectionFailed", Locale.getDefault());
    final String errorCode;

    public SpotifyConnectionFailedErrorMessage(Exception e) {
        super(e);
        this.errorCode = "500-001";
    }

    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(String.format(resources.getString("author"), Flames.api.getSelfUser().getName()))
                .setColor(Color.red)
                .setTitle(resources.getString("title"))
                .setDescription(resources.getString("description"))
                .addField(resources.getString("helpfulText"), resources.getString("helpfulSubtext"), true)
                .setFooter(String.format(resources.getString("footer"), errorCode))
                .setTimestamp(Instant.now())
                .build();
    }
}
