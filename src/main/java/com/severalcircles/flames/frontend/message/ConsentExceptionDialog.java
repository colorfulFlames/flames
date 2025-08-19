/*
 * Copyright (c) 2025 Several Circles.
 */

package com.severalcircles.flames.frontend.message;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsentExceptionDialog implements FlamesEmbed {
    Locale locale;
    ResourceBundle local;
    public ConsentExceptionDialog(Locale locale) {
        this.locale = locale != null ? locale : Locale.getDefault();
        local = Flames.local(locale);
    }

    @Override
    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(local.getString("author"), Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(local.getString("title"))
                .setDescription(local.getString("description"))
                .setColor(Color.RED)
                .setImage("https://cdn.discordapp.com/attachments/543162982536970240/1407483441695690852/caption-24.png?ex=68a6449a&is=68a4f31a&hm=996956e5cc6f879731f946629442460653dfc8356a1bdf479c643c9a1780a10a&")
                .build();
    }
}
