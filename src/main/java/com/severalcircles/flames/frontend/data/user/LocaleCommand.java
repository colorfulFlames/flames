/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserSetting;
import com.severalcircles.flames.frontend.BadArgumentsException;
import com.severalcircles.flames.frontend.message.fourhundred.BadArgumentsErrorMessage;
import com.severalcircles.flames.frontend.message.twohundred.UpdatedSettingsEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class LocaleCommand implements FlamesCommand {

    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
        String setTo = Objects.requireNonNull(event.getOption("new_locale")).getAsString();
        System.out.println(setTo);
        Locale newLocale;
        switch (setTo) {
            case "en":
                newLocale = Locale.forLanguageTag("en-US");
                break;
            case "es":
                newLocale = Locale.forLanguageTag("es");
                break;
            case "tra":
                newLocale = Locale.forLanguageTag("tra");
                break;
            default:
                event.replyEmbeds(new BadArgumentsErrorMessage(new BadArgumentsException("Locale must be one of `en,tra,es`"), event.getUser()).get()).complete();
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
