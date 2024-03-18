/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.embed;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserFunFacts;
import com.severalcircles.flames.external.FlamesAssets;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.Emotion;
import com.severalcircles.flames.util.Ranking;
import com.severalcircles.flames.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ResourceBundle;

public class UserDataEmbed implements FlamesEmbed {
    private final User user;
    private final FlamesUser flamesUser;
    // I'll fix this later lmao
    private final ResourceBundle resources;
    public UserDataEmbed(User user, FlamesUser flamesUser) {
        this.user = user;
        this.flamesUser = flamesUser;
        resources = ResourceBundle.getBundle("features/data/UserDataEmbed", flamesUser.getConfig().getLocale());
    }


    public MessageEmbed get() {
        String tonext;
        if (Ranking.toNext(flamesUser.getScore()) <= 0) tonext = "?";
        else tonext = StringUtil.formatScore(Ranking.toNext(flamesUser.getScore()));
        User.Profile profile = user.retrieveProfile().complete();
        UserFunFacts funFacts = flamesUser.getFunFacts();
        return new EmbedBuilder()
                .setAuthor(funFacts.getFavoriteQuote(), null, user.getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), user.getGlobalName()))
                .addField(resources.getString("score"), StringUtil.formatScore(flamesUser.getScore()), true)
                .addField(resources.getString("rank"), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(Ranking.getRank(flamesUser.getScore()).toString()), true)
                .addField(resources.getString("toNext"), tonext, true)
                .addField(resources.getString("emotion"), Emotion.getEmotionString(flamesUser.getEmotion(), flamesUser.getConfig().getLocale()), true)
                .addField(resources.getString("globalContribution"), Math.round(((float) flamesUser.getScore() / GlobalData.globalScore) * 100) + "%", true)
                .setColor(profile.getAccentColor())
                .setThumbnail(FlamesAssets.getRankIcon(Ranking.getRank(flamesUser.getScore())))
                .build();
    }
}
