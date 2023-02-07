/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.message;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class FlamesErrorMessage implements FlamesEmbed {
    private final String errorCode;
    private final ResourceBundle resources = ResourceBundle.getBundle("error/Generic", Locale.getDefault());
    public FlamesErrorMessage(FlamesError e) {
        errorCode = e.getCode();
//        this.msgRef = msgRef;
    }
    public FlamesErrorMessage(Exception e) {
        errorCode = "500-999";
//        this.msgRef = msgRef;
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
