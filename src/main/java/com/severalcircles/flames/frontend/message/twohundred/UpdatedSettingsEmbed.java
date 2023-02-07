/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.message.twohundred;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.UserSetting;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.frontend.message.fivehundred.GenericErrorMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.ResourceBundle;

public class UpdatedSettingsEmbed implements FlamesEmbed {
    private final UserSetting updatedSetting;
    private final User user;
    GenericErrorMessage em;
    ResourceBundle resources = ResourceBundle.getBundle("message/UpdatedSettingsEmbed");
    public UpdatedSettingsEmbed(UserSetting updatedSetting, User user) {
        this.updatedSetting = updatedSetting;
        this.user = user;
        try {
            this.resources = ResourceBundle.getBundle("message/UpdatedSettingsEmbed", FlamesDataManager.readUser(user).getConfig().getLocale());
        } catch (IOException | DataVersionException e) {
            // Weird DataVersionException but go off
            em = new GenericErrorMessage(e);
        } catch (ConsentException e) {
            e.printStackTrace();
            em = new GenericErrorMessage((FlamesError) e);
        }
    }
    public MessageEmbed get() {
        if (em != null) return em.get();
        return new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), user.getName()))
                .setFooter(String.format(resources.getString("footer"), "200-00" + updatedSetting.getCode()), user.getAvatarUrl())
                .setColor(Color.GREEN)
                .setTimestamp(Instant.now())
                .build();
    }
}
