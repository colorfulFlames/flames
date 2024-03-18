/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.conversations;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.external.ImageSearch;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.io.IOException;
import java.util.ResourceBundle;

public class SparkEndEmbed implements FlamesEmbed {
    String question;
    String answer;
    int votes;
public SparkEndEmbed(String question, String answer, int votes) {
        this.question = question;
        this.answer = answer;
        this.votes = votes;
    }
    ResourceBundle local = ResourceBundle.getBundle("features/data/SparkResultsEmbed");
    @Override
    public MessageEmbed get() {
        MessageEmbed embed = null;
        try {
            embed = new EmbedBuilder()
                    .setTitle(question)
                    .setDescription("# " + answer)
                    .setImage(ImageSearch.searchImage(answer))
                    .addField(local.getString("title"), String.format(local.getString("votes"), votes), false)
                    .setColor(Color.CYAN)
                    .setFooter(Flames.api.getSelfUser().getName(), Flames.api.getSelfUser().getAvatarUrl())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return embed;
    }
}
