/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.FlamesQuestionManager;
import com.severalcircles.flames.system.manager.UserDataManager;
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
        try {
            builder.setAuthor(String.format(FlamesQuestionManager.getAnswer("welcomeback." + part), user.getDiscordUser().getName()));
        } catch (NullPointerException e) {
            builder.setAuthor(local.getString("defaultMessage." + part));
        }
        builder.setColor(part.getColor());
        builder.setTitle(local.getString("title"));
        builder.setDescription(local.getString("description"));
        builder.addField(local.getString("bonus"), String.valueOf(bonus), true);
        return builder.build();
    }
}
