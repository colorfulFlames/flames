/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.message.fourhundred;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.message.FlamesErrorMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsentErrorMessage extends FlamesErrorMessage {
    private final ResourceBundle resources = ResourceBundle.getBundle("error/Consent", Locale.getDefault());
    final String errorCode;

    public ConsentErrorMessage(ConsentException e) {
        super((FlamesError) e);
        errorCode = e.getCode();
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
