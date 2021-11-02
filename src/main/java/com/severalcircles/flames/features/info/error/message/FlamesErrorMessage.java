/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.info.error.message;

import com.severalcircles.flames.features.info.FlamesEmbed;
import com.severalcircles.flames.features.info.error.FlamesError;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class FlamesErrorMessage implements FlamesEmbed {
    private String errorCode;
    private ResourceBundle resources = ResourceBundle.getBundle("error/Generic", Locale.getDefault());
    public FlamesErrorMessage(FlamesError e) {
        errorCode = e.getCode();
//        this.msgRef = msgRef;
    }
    public FlamesErrorMessage(Exception e) {
        errorCode = "500-999";
//        this.msgRef = msgRef;
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
