/*
 * Copyright (c) 2021 Several Circles.
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

public class DataVersionErrorMessage extends FlamesErrorMessage {
    String errorCode;
    ResourceBundle resources = ResourceBundle.getBundle("error/DataVersionErrorMessage", Locale.getDefault());
    public DataVersionErrorMessage(FlamesError e) {
        super(e);
        this.errorCode = e.getCode();
    }

    public DataVersionErrorMessage(Exception e) {
        super(e);
        this.errorCode = "500-999";
    }
    public MessageEmbed get() {
        MessageEmbed embed = new EmbedBuilder()
                .setColor(Color.red)
                .setAuthor(resources.getString("author"))
                .setTitle(resources.getString("title"))
                .setDescription("description")
                .setFooter(String.format(resources.getString("footer"), errorCode))
                .setTimestamp(Instant.now())
                .build();
        return embed;
    }
}
