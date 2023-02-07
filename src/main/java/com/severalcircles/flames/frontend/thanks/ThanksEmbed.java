/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.thanks;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.frontend.message.fivehundred.GenericErrorMessage;
import com.severalcircles.flames.frontend.today.Today;
import com.severalcircles.flames.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ThanksEmbed implements FlamesEmbed {
    private final User thanked;
    private final User sender;
    private final FlamesUser flamesUserThanked;
    private final ResourceBundle resources;
    private String msg;
    static final List<String> success = new LinkedList<>();
    public ThanksEmbed(User thanked, User sender, FlamesUser flamesUserThanked, String msg) {
        this.thanked = thanked;
        this.sender = sender;
        this.flamesUserThanked = flamesUserThanked;
        this.msg = msg;
        resources = ResourceBundle.getBundle("features/ThanksEmbed", flamesUserThanked.getConfig().getLocale());
        if (this.msg.isEmpty()) this.msg = resources.getString("description");

    }

    public MessageEmbed get() {
        if (thanked.getId().equals(sender.getId())) return new EmbedBuilder().setTitle(resources.getString("noThankSelf")).setColor(Color.red).build();
        if (!Today.thanksgivingThanks.containsKey(sender.getId())) Today.thanksgivingThanks.put(sender.getId(), new LinkedList<>());
        if ((!Today.isThanksgiving && Today.thanks.contains(sender.getId())) | (Today.isThanksgiving && Today.thanksgivingThanks.get(sender.getId()).contains(thanked.getId()))) {
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
        List<String> thasnked;
        if (Today.isThanksgiving) {
            try {
                thasnked = Today.thanksgivingThanks.get(sender.getId());
            } catch (NullPointerException e) {
                Today.thanksgivingThanks.put(sender.getId(), new LinkedList<>());
                thasnked = Today.thanksgivingThanks.get(sender.getId());
            }
            thasnked.add(thanked.getId());
            Today.thanksgivingThanks.put(sender.getId(), thasnked);
        }
        success.add(sender.getId());
        return new EmbedBuilder()
                .setAuthor(String.format(resources.getString("author"), sender.getName()), null, sender.getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), thanked.getName()))
                .setDescription(msg)
                .addField(resources.getString("bonus"), StringUtil.formatScore(2500), true)
                .setImage(resources.getString("image"))
                .setColor(Color.yellow)
                .setFooter(String.format(resources.getString("footer"), thanked.getName()), thanked.getAvatarUrl())
                .build();
    }
}
