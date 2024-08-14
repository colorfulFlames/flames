/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.embed;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.legacy.global.GlobalData;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.user.UserEntities;
import com.severalcircles.flames.data.user.UserEntity;
import com.severalcircles.flames.data.legacy.user.UserFunFacts;
import com.severalcircles.flames.external.FlamesAssets;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.Ranking;
import com.severalcircles.flames.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.util.List;
import java.util.ResourceBundle;

public class UserDataEmbed implements FlamesEmbed {
    private final User user;
    private final LegacyFlamesUser legacyFlamesUser;
    // I'll fix this later lmao
    private final ResourceBundle resources;
    public UserDataEmbed(User user, LegacyFlamesUser legacyFlamesUser) {
        this.user = user;
        this.legacyFlamesUser = legacyFlamesUser;
        resources = Flames.local(legacyFlamesUser.getConfig().getLocale());
    }


    public MessageEmbed get() {
        String tonext;
        if (Ranking.toNext(legacyFlamesUser.getScore()) <= 0) tonext = "?";
        else tonext = StringUtil.formatScore(Ranking.toNext(legacyFlamesUser.getScore()));
        User.Profile profile = user.retrieveProfile().complete();
        UserFunFacts funFacts = legacyFlamesUser.getFunFacts();
        UserEntities entities = legacyFlamesUser.getEntities();
        List<UserEntity> topAndBottom = entities.getTopAndBottom();
        return new EmbedBuilder()
                .setAuthor(funFacts.getFavoriteQuote(), null, user.getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), user.getGlobalName()))
                .addField(resources.getString("score"), StringUtil.formatScore(legacyFlamesUser.getScore()), true)
                .addField(resources.getString("rank"), Ranking.getResources(legacyFlamesUser.getConfig().getLocale()).getString(Ranking.getRank(legacyFlamesUser.getScore()).toString()), true)
                .addField(resources.getString("toNext"), tonext, true)
//                .addField(resources.getString("emotion"), Emotion.getEmotionString(legacyFlamesUser.getEmotion(), legacyFlamesUser.getConfig().getLocale()), true)
                .addField(resources.getString("globalContribution"), Math.round(((float) legacyFlamesUser.getScore() / GlobalData.globalScore) * 100) + "%", true)
                .addField(resources.getString("likes"), "* " + topAndBottom.get(0).getName()
                +"\n* " + topAndBottom.get(1).getName()
                +"\n* " + topAndBottom.get(2).getName(), true)
                .addField(resources.getString("dislikes"), "* " + topAndBottom.get(3).getName()
                +"\n* " + topAndBottom.get(4).getName()
                +"\n* " + topAndBottom.get(5).getName(), true)
                .setColor(profile.getAccentColor())
                .setThumbnail(FlamesAssets.getRankIcon(Ranking.getRank(legacyFlamesUser.getScore())))
                .setFooter(Flames.api.getSelfUser().getGlobalName(), Flames.api.getSelfUser().getAvatarUrl())
                .build();
    }
}
