package com.severalcircles.flames.features;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.time.Instant;

public class NowEnteringGuild {
//    public static void welcomeUser(FlamesUser flamesUser, User user, Guild guild) {
//        String image;
//        if (!guild.getBannerUrl().equals(null)) image = guild.getBannerUrl();
//        else if (!guild.getSplashUrl().equals(null)) image = guild.getSplashUrl();
//        else image = "https://severalcircles.com/flames/assets/flamesbanner.png";
//        flamesUser.setGuilds(flamesUser.getGuilds() + 1);
//        flamesUser.setScore(flamesUser.getScore() + 1000);
//        FlamesDataManager.write(flamesUser);
//        MessageEmbed embed = new EmbedBuilder()
//                .setAuthor("Welcome")
//                .setTitle("Now Entering " + guild.getName())
//                .setDescription("Population: " + guild.getMemberCount())
//                .addField("Join Server Bonus", "1000 Flames Points", true)
//                .setThumbnail(guild.getIconUrl())
//                .setImage(image)
//                .setTimestamp(Instant.now())
//                .setFooter("Flames").build();
//        user.openPrivateChannel().complete().sendMessage(embed).complete();
//    }
}
