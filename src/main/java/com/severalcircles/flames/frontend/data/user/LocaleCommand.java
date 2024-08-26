/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.legacy.user.UserSetting;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.FlamesHandlerEmbed;
import com.severalcircles.flames.exception.user.BadArgumentsException;
import com.severalcircles.flames.frontend.FlamesCommand;
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
                event.replyEmbeds(new FlamesHandlerEmbed(new BadArgumentsException(sender,"Locale must be en, es, or tra.", event.getUser())).get()).complete();
                return;
        }
        sender.setLang(newLocale.toLanguageTag());
        try {
            FlamesDataManager.saveUser(sender);
        } catch (IOException e) {
            event.replyEmbeds(new FlamesHandlerEmbed(e).get()).complete();
        }
        event.replyEmbeds(new UpdatedSettingsEmbed(UserSetting.LOCALE, event.getUser()).get()).complete();

    }
}
