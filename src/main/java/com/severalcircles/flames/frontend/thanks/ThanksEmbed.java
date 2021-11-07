/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.thanks;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.frontend.message.fivehundred.GenericErrorMessage;
import com.severalcircles.flames.frontend.today.Today;
import com.severalcircles.flames.util.StringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ThanksEmbed implements FlamesEmbed {
    private User thanked;
    private User sender;
    private FlamesUser flamesUserThanked;
    private FlamesUser flamesUserSender;
    private ResourceBundle resources;
    private String msg;
    static List<String> success = new LinkedList<String>();
    public ThanksEmbed(User thanked, User sender, FlamesUser flamesUserThanked, FlamesUser flamesUserSender, String msg) {
        this.thanked = thanked;
        this.sender = sender;
        this.flamesUserThanked = flamesUserThanked;
        this.flamesUserSender = flamesUserSender;
        this.msg = msg;
        resources = ResourceBundle.getBundle("features/ThanksEmbed", flamesUserThanked.getConfig().getLocale());
        if (this.msg.isEmpty()) this.msg = resources.getString("description");

    }
    public ThanksEmbed(User thanked, User sender, FlamesUser flamesUserThanked, FlamesUser flamesUserSender) {
        this.thanked = thanked;
        this.sender = sender;
        this.flamesUserThanked = flamesUserThanked;
        this.flamesUserSender = flamesUserSender;
        resources = ResourceBundle.getBundle("features/ThanksEmbed", flamesUserThanked.getConfig().getLocale());
        this.msg = resources.getString("description");
    }

    @Override
    public MessageEmbed get() {
        if (thanked.getId() == sender.getId()) return new EmbedBuilder().setTitle(resources.getString("noThankSelf")).setColor(Color.red).build();
        if (Today.thanks.contains(sender.getId())) {
            return new EmbedBuilder().setTitle(String.format(resources.getString("alreadyThanked"), sender.getName())).setColor(Color.red).build();
        }
        flamesUserThanked.setScore(flamesUserThanked.getScore() + 2500);
        try {
            FlamesDataManager.save(flamesUserThanked);
        } catch (IOException e) {
            e.printStackTrace();
            return new GenericErrorMessage(e).get();
        }
        Today.thanks.add(sender.getId());
        success.add(sender.getId());
        return new EmbedBuilder()
                .setAuthor(String.format(resources.getString("author"), sender.getName()), null, sender.getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), thanked.getName()))
                .setDescription(msg)
                .addField(resources.getString("bonus"), StringUtils.formatScore(2500), true)
                .setImage(resources.getString("image"))
                .setColor(Color.yellow)
                .setFooter(String.format(resources.getString("footer"), sender.getName()), thanked.getAvatarUrl())
                .build();
    }
}