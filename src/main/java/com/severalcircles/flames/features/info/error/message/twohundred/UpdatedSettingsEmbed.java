/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.info.error.message.twohundred;

import com.severalcircles.flames.data.user.UserSetting;
import com.severalcircles.flames.features.info.FlamesEmbed;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.ResourceBundle;

public class UpdatedSettingsEmbed implements FlamesEmbed {
    private UserSetting updatedSetting;
    private User user;
    ResourceBundle resources = ResourceBundle.getBundle("message/UpdatedSettingsEmbed");
    public UpdatedSettingsEmbed(UserSetting updatedSetting, User user) {
        this.updatedSetting = updatedSetting;
        this.user = user;
    }
    @Override
    public MessageEmbed get() {
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(resources.getString("title"))
                .setFooter(String.format(resources.getString("footer"), "200-00" + updatedSetting.getCode()), user.getAvatarUrl())
                .setColor(Color.GREEN)
                .setTimestamp(Instant.now())
                .build();
        return embed;
    }
}
