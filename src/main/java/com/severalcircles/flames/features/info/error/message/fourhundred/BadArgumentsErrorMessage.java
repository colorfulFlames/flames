/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.info.error.message.fourhundred;

import com.severalcircles.flames.data.base.ConsentException;
import com.severalcircles.flames.data.base.FlamesDataManager;
import com.severalcircles.flames.features.info.FlamesEmbed;
import com.severalcircles.flames.features.info.error.FlamesError;
import com.severalcircles.flames.features.info.error.exception.BadArgumentsException;
import com.severalcircles.flames.features.info.error.message.FlamesErrorMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class BadArgumentsErrorMessage extends FlamesErrorMessage implements FlamesEmbed {
    ResourceBundle resources;
    private User user;
    private BadArgumentsException exception;
    public BadArgumentsErrorMessage(FlamesError e) {
        super(e);
        throw new IllegalArgumentException("Argument must be a BadArgumentsException.");
    }
    public BadArgumentsErrorMessage(BadArgumentsException e, User user) {
       super((FlamesError) e);
        try {
            resources = ResourceBundle.getBundle("error/BadArgumentsErrorMessage", FlamesDataManager.readUser(user).getConfig().getLocale());
        } catch (IOException | ConsentException ex) {
            resources = ResourceBundle.getBundle("error/BadArgumentsErrorMessage", Locale.getDefault());
        }
        this.user = user;
        this.exception = e;
    }
    public BadArgumentsErrorMessage(Exception e) {
        super(e);
        throw new IllegalArgumentException("Argument must be a BadArgumentsException.");
    }
    @Override
    public MessageEmbed get() {
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(resources.getString("author"))
                .setTitle(String.format(resources.getString("title"), user.getName()))
                .setDescription(exception.getMessage())
                .setColor(Color.red)
                .setFooter(String.format(resources.getString("footer"), exception.getCode()))
                .build();
        return embed;
    }
}