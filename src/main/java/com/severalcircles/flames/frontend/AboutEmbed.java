/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Locale;
@Embed(name = "About")
public class AboutEmbed extends FlamesEmbed {

    public AboutEmbed(Locale locale) {
        super(locale);
    }

    @Override
    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(local.getString("author"), "https://flames.severalcircles.com", Flames.getApi().getSelfUser().getAvatarUrl())
                .setTitle(Flames.getApi().getSelfUser().getName())
                .setDescription(local.getString("description"))
                .setImage(local.getString("image"))
                .addField(local.getString("version") + " " + Flames.getVersion(), local.getString("version.date"), true)
                .setColor(Color.RED)
                .build();
    }
}
