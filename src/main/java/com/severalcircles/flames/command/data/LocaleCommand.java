/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.command.data;

import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserSetting;
import com.severalcircles.flames.features.info.error.exception.BadArgumentsException;
import com.severalcircles.flames.features.info.error.message.fourhundred.BadArgumentsErrorMessage;
import com.severalcircles.flames.features.info.error.message.twohundred.UpdatedSettingsEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Locale;

public class LocaleCommand implements FlamesCommand {

    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        String setTo = event.getOption("newLocale").toString();
        Locale newLocale;
        switch (setTo) {
            case "en":
                newLocale = Locale.forLanguageTag("en");
                break;
            case "es":
                newLocale = Locale.forLanguageTag("es");
                break;
            default:
                event.replyEmbeds(new BadArgumentsErrorMessage(new BadArgumentsException("Locale must be one of `en,es`."), event.getUser()).get()).complete();
                return;
        }
        sender.getConfig().setLocale(newLocale);
        event.replyEmbeds(new UpdatedSettingsEmbed(UserSetting.LOCALE, event.getUser()).get()).complete();

    }
}
