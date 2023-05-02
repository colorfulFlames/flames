/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.FlamesDataManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Locale;
@Embed(name = "GlobalData")
public class GlobalDataEmbed extends FlamesEmbed{
    public GlobalDataEmbed(Locale locale) {
        super(locale);
    }

    @Override
    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(local.getString("author"), null, Flames.getApi().getSelfUser().getAvatarUrl())
                .setTitle(String.format(local.getString("title"), Flames.getApi().getSelfUser().getName()))
                .addField(local.getString("average"), String.valueOf(FlamesDataManager.getAverageScore()), true)
                .addField(local.getString("global"), String.valueOf(FlamesDataManager.getGlobalScore()), true)
                .setColor(0x2A9D8F)
                .setThumbnail("https://www.severalcircles.com/flames/assets/apps/globaldata.png")
                .build();
    }
}
