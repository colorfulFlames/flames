/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.error.message.fourhundred;

import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.error.FlamesError;
import com.severalcircles.flames.error.message.FlamesErrorMessage;
import com.severalcircles.flames.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsentErrorMessage extends FlamesErrorMessage {
    private ResourceBundle resources = ResourceBundle.getBundle("error/Consent", Locale.getDefault());
    String errorCode;
    public ConsentErrorMessage(FlamesError e) {
        super(e);
        errorCode = e.getCode();
    }
    public ConsentErrorMessage(ConsentException e) {
        super((FlamesError) e);
        errorCode = e.getCode();
    }
    public ConsentErrorMessage(Exception e) {
        super(e);
    }
    @Override
    public MessageEmbed get() {
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(String.format(resources.getString("author"), Flames.api.getSelfUser().getName()))
                .setColor(Color.red)
                .setTitle(resources.getString("title"))
                .setDescription(resources.getString("description"))
                .addField(resources.getString("helpfulText"), resources.getString("helpfulSubtext"), true)
                .setFooter(String.format(resources.getString("footer"), errorCode))
                .setTimestamp(Instant.now())
                .build();
        return embed;
    }
}
