package com.severalcircles.flames.features;

import com.severalcircles.flames.data.user.FlamesUser;
import discord4j.core.object.entity.User;
import discord4j.rest.util.Color;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class WelcomeBack {
    static ResourceBundle resources = ResourceBundle.getBundle("features/WelcomeBack");
    public void welcomeUserBack(FlamesUser flamesUser, User discordUser) throws SQLException {
        Date now = new Date();
        int dailyBonus = 1000 + (flamesUser.getStreak() * 100);
        flamesUser.addScore(dailyBonus);
        // noinspection deprecation
        if (flamesUser.getLastSeen().getDay() < now.getDay() || (flamesUser.getLastSeen().getDay() == 6 && now.getDay() == 0)) {
            discordUser.getPrivateChannel().block().createEmbed(spec ->
                    spec.setColor(Color.CINNABAR)
                    .setTitle(resources.getString("title"))
                    .setAuthor(String.format(resources.getString("author"), discordUser.getUsername()), null, discordUser.getAvatarUrl())
                    .setDescription(resources.getString("messages"))
                    .setFooter(resources.getString("footer"), discordUser.getClient().getSelf().block().getAvatarUrl())
                    .setImage(resources.getString("image"))
                    .addField(resources.getString("dailyBonus"),"" + dailyBonus, true)
                    .addField(resources.getString("flamesScore"), "" + flamesUser.getScore(), true)
                    .setTimestamp(Instant.now())).block();
            flamesUser.saveData();
        } else return;
    }
}
