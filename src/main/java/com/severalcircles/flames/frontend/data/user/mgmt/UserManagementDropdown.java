/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.mgmt;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesDropdown;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.util.Locale;
import java.util.ResourceBundle;

public class UserManagementDropdown implements FlamesDropdown {
    @Override
    public void execute(GenericSelectMenuInteractionEvent event, FlamesUser sender) {
        switch (event.getValues().get(0).toString().split(":")[0]) {

            case "reset-fav-quote":
                sender.setFavoriteQuote(null);
                break;
            case "change-locale":
//                seder.setLang(event.getValues().get(0));
////                sennder.save();
                event.reply("Your locale has been changed.").queue();
                break;
            case "manage-data":
                event.reply("Data management is not yet implemented.").queue();
                break;
        }
        event.replyComponents(ActionRow.of(get(sender))).queue();
    }

    @Override
    public StringSelectMenu get(FlamesUser user) {
        ResourceBundle local = Flames.local(Locale.forLanguageTag(user.getLang()));
        return StringSelectMenu.create("usr-mgmt:" + user.getId())
                .addOption(local.getString("resetQuote"), "reset-fav-quote:" + user.getId(), Emoji.fromUnicode("\uD83D\uDD04"))
                .addOption(local.getString("changeLocale"), "change-locale:" + user.getId(), Emoji.fromUnicode("\uD83D\uDFE3"))
                .addOption(local.getString("dataMgmt"), "manage-data:" + user.getId(), Emoji.fromUnicode("\uD83D\uDD0D"))
                .build();
    }
}
