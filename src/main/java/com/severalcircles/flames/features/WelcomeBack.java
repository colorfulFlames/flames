package com.severalcircles.flames.features;

import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
//import discord4j.core.object.entity.User;
//import discord4j.rest.util.Color;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class WelcomeBack {
    static ResourceBundle resources;
    public void welcomeUserBack(FlamesUser flamesUser, User discordUser) throws IOException {
//        resources = ResourceBundle.getBundle("features/WelcomeBack", Locale.ENGLISH);
//        Date now = Date.from(Instant.now());
//        int dailyBonus = 100 + (flamesUser.getStreak() * 100) * flamesUser.getStats().getRISE();
//        // noinspection deprecation
//        System.out.println("Last Seen (" + flamesUser.getLastSeen() + ") < Today (" + now.getDay() + ") (" + (flamesUser.getLastSeen() < now.getDay()) + ")");
//        System.out.println("Last Seen == 6 (" + (flamesUser.getLastSeen() == 6) + ") && Today !=6 0 (" + (now.getDay() != 6) + ")");
//        if (flamesUser.getLastSeen() < now.getDay() | (flamesUser.getLastSeen() == 6 && now.getDay() != 6)) {
//            flamesUser.addScore(dailyBonus);
//            GlobalData.globalScore += dailyBonus;
//            GlobalData.write();
//
//                        MessageEmbed embed = new EmbedBuilder()
//                                .setTitle(resources.getString("title"))
//                                .setAuthor(String.format(resources.getString("author"), discordUser.getName()), null, discordUser.getAvatarUrl())
//                                .setDescription(resources.getString("messages"))
//                                .setFooter(resources.getString("footer"), discordUser.getAvatarUrl())
//                                .setImage(resources.getString("image"))
//                                .addField(resources.getString("dailyBonus"), "" + dailyBonus, true)
//                                .addField(resources.getString("flamesScore"), "" + flamesUser.getScore(), true)
//                                .setTimestamp(Instant.now()).build();
//            discordUser.openPrivateChannel().complete().sendMessage(embed);
//                flamesUser.setLastSeen(now.getDay());
//                FlamesData.write(flamesUser);
//        } else return;
    }
}
