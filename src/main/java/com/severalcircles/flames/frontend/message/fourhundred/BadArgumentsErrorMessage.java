/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.message.fourhundred;

import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.frontend.BadArgumentsException;
import com.severalcircles.flames.frontend.message.FlamesErrorMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class BadArgumentsErrorMessage extends FlamesErrorMessage implements FlamesEmbed {
    ResourceBundle resources;
    private final User user;
    private final BadArgumentsException exception;

    public BadArgumentsErrorMessage(BadArgumentsException e, User user) {
       super((FlamesError) e);
        try {
            resources = ResourceBundle.getBundle("error/BadArgumentsErrorMessage", FlamesDataManager.readUser(user).getConfig().getLocale());
        } catch (IOException | ConsentException | DataVersionException ex) {
            resources = ResourceBundle.getBundle("error/BadArgumentsErrorMessage", Locale.getDefault());
        }
        this.user = user;
        this.exception = e;
    }

    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(resources.getString("author"))
                .setTitle(String.format(resources.getString("title"), user.getName()))
                .setDescription(exception.getMessage())
                .setColor(Color.red)
                .setFooter(String.format(resources.getString("footer"), exception.getCode()))
                .build();
    }
}
