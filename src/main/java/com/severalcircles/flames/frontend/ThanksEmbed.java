/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.Util;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Locale;
import java.util.ResourceBundle;
@Embed(name="Thanks")
public class ThanksEmbed extends FlamesEmbed{
    FlamesUser thanker;
    FlamesUser thankee;
    String msg;
    public static final ResourceBundle values = ResourceBundle.getBundle("values/Thanks");
    private ThanksEmbed(Locale locale) {
        super(locale);
    }
    public static double getBonusAmount() {
        return Double.parseDouble(values.getString("bonus"));
    }
    public ThanksEmbed(FlamesUser thanker, FlamesUser thankee, String msg) {
        super(thankee.getLocale());
        this.thanker = thanker;
        this.thankee = thankee;
        this.msg = msg;
    }
    @Override
    public MessageEmbed get() {
        if (msg == null) msg = local.getString("description");
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(String.format(local.getString("author"), thanker.getDiscordUser().getName()), null, Flames.getApi().getSelfUser().getAvatarUrl())
                .setColor(0x6C3B61)
                .setTitle(String.format(local.getString("title"), thankee.getDiscordUser().getName()))
                .setThumbnail(thanker.getDiscordUser().getAvatarUrl())
                .setImage(local.getString("image"))
                .addField(local.getString("bonus"), Util.formatScore(Double.parseDouble(values.getString("bonus"))), true)
                .setDescription(msg)
                .build();
        return embed;
    }
}
