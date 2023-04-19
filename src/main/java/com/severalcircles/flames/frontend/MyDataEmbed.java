/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.StringUtil;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Locale;
@Embed(name = "MyData")
public class MyDataEmbed extends FlamesDataEmbed {

    public MyDataEmbed(FlamesUser user) {
        super(user);
    }
    @Override
    public MessageEmbed get() {
        EmbedBuilder builder = new EmbedBuilder();
        if (user.getDiscordUser().getId().equals("202098742013198336") | user.getDiscordUser().getId().equals("504025964414763039")) builder.setFooter(String.format(local.getString("footer.associate"), user.getDiscordUser().getName()), user.getDiscordUser().getAvatarUrl());
        else builder.setFooter(String.format(local.getString("footer"), user.getDiscordUser().getName()), user.getDiscordUser().getAvatarUrl());
        builder.setTitle(user.getDiscordUser().getName())
                .setAuthor(local.getString("author"), null, Flames.getApi().getSelfUser().getAvatarUrl())
                .setDescription(local.getString("description"))
                .setColor(Color.WHITE)
                .addField(local.getString("score"), StringUtil.formatScore(user.getScore()), true)
//                .addField(local.getString("emotion"), String.valueOf(user.getEmotion()), true)
                .addBlankField(false)
                .addField("\"" + user.getFavoriteQuote().message() + "\"", String.format(local.getString("favoriteQuote"), user.getDiscordUser().getName()), false)
                .addField(local.getString("messages"), String.valueOf(user.getMessages()), true)
                .addField(local.getString("conversations"), String.valueOf(user.getConversations()), true)
                .addField(local.getString("rank"), user.getRank().toString(), true);
        return builder.build();
    }
}
