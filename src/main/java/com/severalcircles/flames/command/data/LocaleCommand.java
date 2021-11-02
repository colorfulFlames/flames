/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.command.data;

import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.base.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserSetting;
import com.severalcircles.flames.features.info.error.exception.BadArgumentsException;
import com.severalcircles.flames.features.info.error.message.fourhundred.BadArgumentsErrorMessage;
import com.severalcircles.flames.features.info.error.message.twohundred.UpdatedSettingsEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.io.IOException;
import java.util.Locale;

public class LocaleCommand implements FlamesCommand {

    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        String setTo = event.getOption("new_locale").getAsString();
        System.out.println(setTo);
        Locale newLocale;
        if (setTo.equals("en")) {
            newLocale = Locale.forLanguageTag("en");
        } else if (setTo.equals("es")) {
            newLocale = Locale.forLanguageTag("es");
        } else {
            event.replyEmbeds(new BadArgumentsErrorMessage(new BadArgumentsException("Locale must be one of `en,es`"), event.getUser()).get()).complete();
            return;
        }
        sender.getConfig().setLocale(newLocale);
        try {
            FlamesDataManager.save(sender);
        } catch (IOException e) {
            e.printStackTrace();
        }
        event.replyEmbeds(new UpdatedSettingsEmbed(UserSetting.LOCALE, event.getUser()).get()).complete();

    }
}
