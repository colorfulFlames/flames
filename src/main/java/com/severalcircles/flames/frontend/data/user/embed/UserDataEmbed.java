/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.embed;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.legacy.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserEntities;
import com.severalcircles.flames.data.user.UserEntity;
import com.severalcircles.flames.external.FlamesAssets;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.Ranking;
import com.severalcircles.flames.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserDataEmbed implements FlamesEmbed {
    private final User user;
    private final FlamesUser flamesUser;
    // I'll fix this later lmao
    private final ResourceBundle resources;
    public UserDataEmbed(User user, FlamesUser flamesUser) {
        this.user = user;
        this.flamesUser = flamesUser;
        resources = Flames.local(Locale.forLanguageTag(flamesUser.getLang()));
    }

    public MessageEmbed get() {
        String tonext;
        if (Ranking.toNext(flamesUser.getScore()) <= 0) tonext = "---";
        else tonext = StringUtil.formatScore(Ranking.toNext(flamesUser.getScore()));
        User.Profile profile = user.retrieveProfile().complete();
        UserEntities entities = flamesUser.getEntities();
        List<UserEntity> topAndBottom = entities.getTopAndBottom();
        Color color = profile.getAccentColor();
        if (color == null) color = Color.MAGENTA;
        return new EmbedBuilder()
                .setAuthor(flamesUser.getFavoriteQuote(), null, user.getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), user.getGlobalName()))
                .setDescription(flamesUser.getTitle())
                .addField(resources.getString("score"), StringUtil.formatScore(flamesUser.getScore()), true)
                .addField(resources.getString("rank"), Ranking.getResources(Locale.forLanguageTag(flamesUser.getLang())).getString(Ranking.getRank(flamesUser.getScore()).toString()), true)
                .addField(resources.getString("toNext"), tonext, true)
//                .addField(resources.getString("emotion"), Emotion.getEmotionString(legacyFlamesUser.getEmotion(), legacyFlamesUser.getConfig().getLocale()), true)
                .addField(resources.getString("globalContribution"), Math.round(((float) flamesUser.getScore() / GlobalData.globalScore) * 100) + "%", true)
                .addField(resources.getString("likes"), "* " + topAndBottom.get(0).getName()
                +"\n* " + topAndBottom.get(1).getName()
                +"\n* " + topAndBottom.get(2).getName(), true)
                .addField(resources.getString("dislikes"), "* " + topAndBottom.get(3).getName()
                +"\n* " + topAndBottom.get(4).getName()
                +"\n* " + topAndBottom.get(5).getName(), true)
                .setColor(color)
                .setThumbnail(FlamesAssets.getRankIcon(Ranking.getRank(flamesUser.getScore())))
                .setFooter(Flames.api.getSelfUser().getGlobalName(), Flames.api.getSelfUser().getAvatarUrl())
//                .setImage("https://severalcircles.com/flames/assets/apps/footer.png")
                .build();
    }
}
