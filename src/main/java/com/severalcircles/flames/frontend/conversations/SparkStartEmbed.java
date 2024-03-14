/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.conversations;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.external.ImageSearch;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class SparkStartEmbed implements FlamesEmbed {
    ResourceBundle local;
    FlamesUser fluser;
    User user;
    String question;
    int minutes;
    public SparkStartEmbed(FlamesUser fluser, User user, String question, int minutes) {
        local = ResourceBundle.getBundle("features/data/SparkStartEmbed", fluser.getConfig().getLocale());
        this.fluser = fluser;
        this.user = user;
        this.question = question;
        this.minutes = minutes;
        if (minutes > 5) minutes = 5;
    }

    @Override
    public MessageEmbed get() {
        String eS = "<t:" + (Instant.now().plus(minutes, ChronoUnit.MINUTES).toEpochMilli() / 1000) + ":R>";
        eS = eS.replace(" ", "");
        System.out.println(eS);
        MessageEmbed embed = null;
        try {
            embed = new EmbedBuilder()
                    .setColor(Color.CYAN)
                    .setAuthor(String.format(local.getString("author"), user.getGlobalName()), null, user.getAvatarUrl())
                    .setDescription(String.format(local.getString("description"), question))
                    .setImage(ImageSearch.searchImage(question))
                    .addField(local.getString("toVote"), "", true)
                    .addField(local.getString("voteFast"), eS, true)
                    .setFooter(Flames.api.getSelfUser().getName(), Flames.api.getSelfUser().getAvatarUrl())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return embed;
    }
}
