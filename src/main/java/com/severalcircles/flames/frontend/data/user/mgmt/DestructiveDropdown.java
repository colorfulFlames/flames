/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.mgmt;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesDropdown;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.Locale;
import java.util.ResourceBundle;

public class DestructiveDropdown implements FlamesDropdown {
    @Override
    public void execute(GenericSelectMenuInteractionEvent event, FlamesUser sender) {

    }

    @Override
    public StringSelectMenu get(FlamesUser user) {
        ResourceBundle local = Flames.local(Locale.forLanguageTag(user.getLang()));
        StringSelectMenu.create("destructive-dropdown:" + user.getId())
                .addOption(local.getString("resetAll"), "resetAll:" + user.getId(), Emoji.fromUnicode("??"))
                .addOption(local.getString("deleteEntity"), "deleteEntity:" + user.getId(), Emoji.fromUnicode("??"));
        return null;
    }
}
