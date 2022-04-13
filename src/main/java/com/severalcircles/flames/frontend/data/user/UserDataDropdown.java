/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesDropdown;
import com.severalcircles.flames.frontend.data.user.embed.FunFactsEmbed;
import com.severalcircles.flames.frontend.data.user.embed.UserDataEmbed;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import java.util.Locale;
import java.util.ResourceBundle;

public class UserDataDropdown implements FlamesDropdown {
    ResourceBundle resources;


    @Override
    public void execute(SelectMenuInteractionEvent event, String[] data, FlamesUser user) {
        switch (data[2]) {
            case "mydata":
                event.editMessageEmbeds(new UserDataEmbed(event.getUser(), user).get()).complete();
            case "funfacts":
                event.editMessageEmbeds(new FunFactsEmbed(event.getUser(), user).get()).complete();
        }
    }

    @Override
    public SelectMenu getDropdown(FlamesUser user) {
        Locale locale = user.getConfig().getLocale();
        resources = ResourceBundle.getBundle("features/data/UserDataDropdown", locale);
        return SelectMenu.create("dataMenu")
                .addOption(resources.getString("mydata.label"), "DataDropdown:" + user.getDiscordId() + ":mydata", resources.getString("mydata.description"))
                .addOption(resources.getString("funfacts.label"), "DataDropdown:" + user.getDiscordId() + ":funfacts", resources.getString("funfacts.description"))
                .build();
    }
}
