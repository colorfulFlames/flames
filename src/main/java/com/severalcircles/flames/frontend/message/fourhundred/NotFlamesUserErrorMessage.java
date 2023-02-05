/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.message.fourhundred;

import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.frontend.message.FlamesErrorMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class NotFlamesUserErrorMessage extends FlamesErrorMessage {
    final String errorCode;
    final ResourceBundle resources = ResourceBundle.getBundle("error/DataVersionErrorMessage", Locale.getDefault());
    public NotFlamesUserErrorMessage(FlamesError e) {
        super(e);
        this.errorCode = e.getCode();
    }

    public MessageEmbed get() {
        return new EmbedBuilder()
                .setColor(Color.red)
                .setAuthor(resources.getString("author"))
                .setTitle(resources.getString("title"))
                .setDescription("description")
                .setFooter(String.format(resources.getString("footer"), errorCode))
                .setTimestamp(Instant.now())
                .build();
    }
}
