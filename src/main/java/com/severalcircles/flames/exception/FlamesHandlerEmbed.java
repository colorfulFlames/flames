/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.exception;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.user.FlamesUserException;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class FlamesHandlerEmbed implements FlamesEmbed {
    private String code;
    private String message;
    private Locale locale = Locale.ENGLISH;
    private final String causedByImage;
    final ResourceBundle rsc;
    final String causedBy;
    final Color color;
    public FlamesHandlerEmbed(FlamesException e) {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        String className = st[3].getClassName();
        code = e.getCode();
        this.causedBy = className.substring(className.lastIndexOf('.') + 1);
        this.message = e.getMessage();
        this.rsc = e.getRsc(Locale.ENGLISH);
        this.causedByImage = Flames.api.getSelfUser().getAvatarUrl();
        color = Color.RED;
    }
    public FlamesHandlerEmbed(FlamesUserException flamesExceptionByUser) {
        this.setUser(flamesExceptionByUser.getFlamesUser());
        this.locale = flamesExceptionByUser.getFlamesUser().getConfig().getLocale();
        this.rsc = flamesExceptionByUser.getRsc(this.locale);
        this.causedBy = flamesExceptionByUser.getUser().getName();
        this.causedByImage = flamesExceptionByUser.getUser().getAvatarUrl();
        this.message = flamesExceptionByUser.getMessage();
        color = Color.yellow;
    }
    public FlamesHandlerEmbed(FlamesRuntimeException e, String cause) {
        code = e.getCode();
        this.causedBy = cause;
        this.message = e.getMessage();
        this.rsc = e.getRsc(Locale.ENGLISH);
        this.causedByImage = Flames.api.getSelfUser().getAvatarUrl();
        color = Color.PINK;
    }

    public FlamesHandlerEmbed(Exception e) {
        this.rsc = ResourceBundle.getBundle("exceptions/Generic");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        String className = st[3].getClassName();
        this.causedBy = className.substring(className.lastIndexOf('.') + 1);
        e.printStackTrace();
        this.causedByImage = "https://media.tenor.com/PkbRg6xWSuAAAAAC/shit-snake.gif";
        this.message = e.getMessage();
        color = Color.RED;
    }

    public MessageEmbed get() {
        if (message == null) message = "Please kill me";
        return new EmbedBuilder()
                .setAuthor(code)
                .setThumbnail(causedByImage)
                .setTitle(rsc.getString("title"))
                .addField(rsc.getString("message"), message, true)
                .addField(rsc.getString("causedBy"), causedBy, true)
                .setColor(color)
                .build();
    }
    public FlamesHandlerEmbed setUser(FlamesUser user) {
        this.locale = user.getConfig().getLocale();
        return this;
    }
}
