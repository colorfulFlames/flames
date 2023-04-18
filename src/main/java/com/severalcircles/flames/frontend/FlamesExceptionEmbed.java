/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.MessageCodes;
import com.severalcircles.flames.system.exception.flames.FlamesException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Locale;

@ExceptionID("703")
@Embed(name="FlamesException")
public class FlamesExceptionEmbed extends FlamesEmbed{
    FlamesException e;
    String code;
    public FlamesExceptionEmbed(Locale locale, FlamesException e) {
        super(locale);
        this.e = e;
        code = MessageCodes.generateCodeError(e);
    }
    public FlamesExceptionEmbed(Locale locale, Exception e) {
        super(locale);
        this.e = new FlamesException(e.getMessage());
        code = e.getClass().getSimpleName();
    }

    @Override
    public MessageEmbed get() {
        String title = local.getString("title." + e.getClass().getSimpleName());
        String description;
        try {
            description = local.getString("description." + e.getClass().getSimpleName());
        } catch (NullPointerException ignored) {
            description = e.getMessage();
        }
        String helpfultitle = local.getString("helpful.title." + e.getClass().getSimpleName());
        try {
            helpfultitle = local.getString("helpful.title." + e.getClass().getSimpleName());
        } catch(NullPointerException ignored) {
            helpfultitle = local.getString("helpful.title.default");
        }String helpfuldesc = local.getString("helpful.description." + e.getClass().getSimpleName());
        try {
            helpfultitle = local.getString("helpful.description." + e.getClass().getSimpleName());
        } catch(NullPointerException ignored) {
            helpfultitle = local.getString("helpful.description.default");
        }

        return new EmbedBuilder()
                .setAuthor(String.format(local.getString("author"), code), null, Flames.getApi().getSelfUser().getAvatarUrl())
                .setTitle(title)
                .setDescription(description)
                .addField(helpfultitle, helpfuldesc, false)
                .setTimestamp(Instant.now())
                .setColor(Color.RED)
                .build();
    }
}
