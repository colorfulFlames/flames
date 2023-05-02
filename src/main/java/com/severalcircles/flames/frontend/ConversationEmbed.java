/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.Util;
import com.severalcircles.flames.conversations.Conversation;
import com.severalcircles.flames.conversations.EmotionLevel;
import com.severalcircles.flames.conversations.external.Tenor;
import com.severalcircles.flames.system.manager.secondary.ConversationManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Embed(name = "Conversation")
public class ConversationEmbed extends FlamesEmbed {
    MessageChannelUnion channel;
    public ConversationEmbed(Locale locale, MessageChannelUnion channel) {
        super(locale);
        this.channel = channel;
    }

    @Override
    public MessageEmbed get() {
        Conversation conversation = ConversationManager.conversations.get(channel.getId());
        SortedMap<Integer, String> sortedTopics = new ConcurrentSkipListMap<>();
        sortedTopics.putAll(Util.invertMapUsingStreams(conversation.getTopics()));
        String topic = (String) sortedTopics.entrySet().toArray()[0];
        List<String> urls = Tenor.extractUrls(Tenor.getSearchResults(topic, 1).toString());
        String url = urls.get(0);
        return new EmbedBuilder()
                .setAuthor(local.getString("author"))
                .setTitle(String.format(local.getString("title"), topic))
                .setDescription(String.format(local.getString("description"), channel.getName()))
                .addField(local.getString("emotion"), EmotionLevel.getLevel(conversation.getEmotion()).getName(locale), true)
                .setThumbnail(url)
                .setColor(Color.LIGHT_GRAY)
                .build();
    }
}
