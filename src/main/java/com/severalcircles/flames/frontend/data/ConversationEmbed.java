/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.conversations.Conversation;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.external.ImageSearch;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.Emotion;
import com.severalcircles.flames.util.Ranking;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONException;

import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConversationEmbed implements FlamesEmbed {
    private final User user;
    private final FlamesUser flamesUser;
    private final ResourceBundle resources;
    private final Conversation conversation;
    public ConversationEmbed(User user, FlamesUser flamesUser, Conversation conversation) {
        this.user = user;
        this.flamesUser = flamesUser;
        this.conversation = conversation;
        resources = ResourceBundle.getBundle("features/data/ConversationEmbed", flamesUser.getConfig().getLocale());
    }
    public MessageEmbed get() {
        String trendingEntity = "";
        int times = 0;
        for (Map.Entry<String, Integer> entry: conversation.getEntities().entrySet()) {
            if (!Objects.equals(entry.getKey(), trendingEntity) && entry.getValue() > times) {
                trendingEntity = entry.getKey();
                times = entry.getValue();
            }}
        try {
            return new EmbedBuilder()
                    .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                    .setColor(Color.decode("#01B0D8"))
                    .setTitle(String.format(resources.getString("title"), trendingEntity))
                    .setThumbnail(ImageSearch.searchImage(trendingEntity))
                    .setDescription(String.format(resources.getString("description"), conversation.getChannel().getName()))
                    .addField(resources.getString("emotion"), Emotion.getEmotionString((float) conversation.getEmotion(), flamesUser.getConfig().getLocale()), false)
                    .addBlankField(false)
                    .addField("\"" + conversation.getQuote()[0] + "\"", " - " + conversation.getQuote()[1], false)
                    .addBlankField(false)
                    .setFooter(String.format(Flames.getCommonRsc(flamesUser.getConfig().getLocale()).getString("userFooter"), user.getName(), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(String.valueOf(Ranking.getRank(flamesUser.getScore())))), user.getAvatarUrl())
                    .build();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return new EmbedBuilder()
                    .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                    .setColor(Color.decode("#01B0D8"))
                    .setTitle(String.format(resources.getString("title"), trendingEntity))
                    .setThumbnail("https://preview.redd.it/nappqljautm51.jpg?auto=webp&s=72f0c7468ef69127c1e945b160e0b9807d066de5")
                    .setDescription(String.format(resources.getString("description"), conversation.getChannel().getName()))
                    .addField(resources.getString("emotion"), Emotion.getEmotionString((float) conversation.getEmotion(), flamesUser.getConfig().getLocale()), false)
                    .addBlankField(false)
                    .addField("\"" + conversation.getQuote()[0] + "\"", " - " + conversation.getQuote()[1], false)
                    .addBlankField(false)
                    .setFooter(String.format(Flames.getCommonRsc(flamesUser.getConfig().getLocale()).getString("userFooter"), user.getName(), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(String.valueOf(Ranking.getRank(flamesUser.getScore())))), user.getAvatarUrl())
                    .build();
        }
    }
}
