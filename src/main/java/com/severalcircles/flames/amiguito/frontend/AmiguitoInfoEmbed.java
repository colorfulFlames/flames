/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.amiguito.frontend;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.amiguito.Amiguito;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ResourceBundle;

public class AmiguitoInfoEmbed implements FlamesEmbed {
    private static final Color color = new Color(Color.blue.getRGB());
    Amiguito amiguito;
    FlamesUser fluser;
    User user;
    ResourceBundle local;
    ResourceBundle thoughts;
    public AmiguitoInfoEmbed(Amiguito amiguito, FlamesUser fluser, User user) {
        this.amiguito = amiguito;
        this.fluser = fluser;
        this.user = user;
        local = Flames.local(fluser.getConfig().getLocale());
        thoughts = ResourceBundle.getBundle("/com/severalcircles/flames/amiguito/frontend/thoughts", fluser.getConfig().getLocale());
    }
    @Override
    public MessageEmbed get() {
        StringBuilder thoughtsFormatted = new StringBuilder();
        amiguito.getThoughts().forEach(thought -> {
            thoughtsFormatted
                    .append("* ")
                    .append(local.getString(thought.name()))
                    .append("\n");
        });
        long days = (System.currentTimeMillis() - amiguito.getCreated().toEpochMilli()) / 86400000;
        return new EmbedBuilder()
                .setAuthor(String.format(local.getString("author"), user.getGlobalName()), null, user.getAvatarUrl())
                .setTitle(String.format(local.getString("title"), amiguito.getName()))
                .addField(local.getString("thoughts"), thoughtsFormatted.toString(), false)
                .addField(local.getString("age"), String.format(local.getString("days"), days), true)
                .setColor(color)
                .setFooter(Flames.api.getSelfUser().getGlobalName(), Flames.api.getSelfUser().getAvatarUrl())
                .build();
    }
}