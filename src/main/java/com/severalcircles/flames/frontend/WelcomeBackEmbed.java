/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.secondary.FlamesQuestionManager;
import com.severalcircles.flames.system.manager.secondary.UserDataManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Locale;
@ExceptionID("704")
@Embed(name = "WelcomeBack")
public class WelcomeBackEmbed extends FlamesEmbed {

    public WelcomeBackEmbed(Locale locale) {
        super(locale);
    }

    @Override
    public MessageEmbed get() {
        return get(0, UserDataManager.flames());
    }

    public MessageEmbed get(double bonus, FlamesUser user) {
        EmbedBuilder builder = new EmbedBuilder();
        DayPart part = DayPart.getPart();
//        Instant now = Instant.now();
//        LocalDateTime ldt = LocalDateTime.ofInstant(now, ZoneId.ofOffset("UTC", ZoneOffset.UTC));
        try {
            builder.setAuthor(String.format(FlamesQuestionManager.getAnswer("welcomeback." + part), user.getDiscordUser().getName()));
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            builder.setAuthor(String.format(local.getString("defaultMessage." + part), user.getDiscordUser().getName()));
        }
        builder.setColor(part.getColor());
        builder.setTitle(local.getString("title"));
        builder.setDescription(local.getString("description"));
        builder.addField(local.getString("bonus"), String.valueOf(bonus), true);
        builder.addField(local.getString("yourScore"), String.valueOf(user.getScore()), true);
        builder.setImage("https://severalcircles.com/flames/assets/welcome/" + part + ".png");
        return builder.build();
    }
}
