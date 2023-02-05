/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.embed;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.ResourceBundle;

public class GetAlongEmbed implements FlamesEmbed {
    final User sender;
    final User victim;
    final ResourceBundle resources;
    String description;
    final int score;

    public GetAlongEmbed(User sender, FlamesUser flamesUser, User victim, int score) {
        this.sender = sender;
        this.victim = victim;
        this.score = score;
        resources = ResourceBundle.getBundle("features/data/user/embed/GetAlongEmbed", flamesUser.getConfig().getLocale());
        try {
            description = resources.getString("description.level" + Math.round(Math.log(score)/Math.log(2) + 16));
        } catch (Exception e) {
            e.printStackTrace();
            description = resources.getString("description.level1");
        }
    }
    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(String.format(resources.getString("title")), victim.getName())
                .setDescription(description)
                .addField(resources.getString("score"), String.valueOf(score), true)
                .setTimestamp(Instant.now())
                .setFooter("", sender.getAvatarUrl())
                .setColor(Color.decode("F53733"))
                .build();
    }
}
