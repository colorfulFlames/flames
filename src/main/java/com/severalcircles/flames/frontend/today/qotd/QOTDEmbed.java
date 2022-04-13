/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.today.qotd;

import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class QOTDEmbed implements FlamesEmbed {
    private User user;
    public QOTDEmbed(User user) {
        this.user = user;
    }
    @Override
    public MessageEmbed get() {
        ResourceBundle resources = ResourceBundle.getBundle("QOTDEmbed", Locale.ENGLISH);
        String[] answer = QOTD.answers.poll();
        return new EmbedBuilder()
                .setAuthor(String.format(resources.getString("author"), QOTD.qotd[1]))
                .setTitle(String.format(resources.getString("title"),QOTD.qotd[0]))
                .addField(String.format(resources.getString("answer"), answer[1]), answer[0], false)
                .addField(resources.getString("wdyt"), " ", false)
                .setFooter(resources.getString("footer"), user.getAvatarUrl())
                .setTimestamp(Instant.now())
                .setColor(Color.decode("#F53733"))
                .build();
    }
}
