/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.Util;
import com.severalcircles.flames.conversations.EmotionLevel;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Embed(name = "MyData")
public class MyDataEmbed extends FlamesDataEmbed {
    private static final String[] ASSOCIATES = {"202098742013198336", "504025964414763039", "521088756992704514", "587035546581467136"};
    private static final List<String> ASSOCIATE_LIST = new LinkedList<>(Arrays.asList(ASSOCIATES));

    public MyDataEmbed(FlamesUser user) {
        super(user);
    }
    @Override
    public MessageEmbed get() {
        EmbedBuilder builder = new EmbedBuilder();
        if (ASSOCIATE_LIST.contains(user.getDiscordUser().getId())) builder.setFooter(String.format(local.getString("footer.associate"), user.getDiscordUser().getName()), user.getDiscordUser().getAvatarUrl());
        else builder.setFooter(String.format(local.getString("footer"), user.getDiscordUser().getName()), user.getDiscordUser().getAvatarUrl());
        builder.setTitle(user.getDiscordUser().getName())
                .setAuthor(local.getString("author"), null, Flames.getApi().getSelfUser().getAvatarUrl())
//                .setDescription(local.getString("description"))
                .setColor(0x2F1231)
                .addField(local.getString("score"), Util.formatScore(user.getScore()), true)
                .addField(local.getString("emotion"), EmotionLevel.getLevel(user.getEmotion()).getName(user.getLocale()), true)
                .addBlankField(false)
                .setThumbnail("https://www.severalcircles.com/flames/assets/apps/mydata.png")
                .addField("\"" + user.getFavoriteQuote().message() + "\"", String.format(local.getString("favoriteQuote"), user.getDiscordUser().getName()), false)
                .addField(local.getString("messages"), String.valueOf(user.getMessages()), true)
                .addField(local.getString("conversations"), String.valueOf(user.getConversations()), true)
                .addField(local.getString("rank"), user.getRank().getName(), true);
        return builder.build();
    }
}
