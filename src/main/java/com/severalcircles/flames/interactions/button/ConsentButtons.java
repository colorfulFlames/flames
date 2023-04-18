/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.button;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.UserDataManager;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

import java.io.IOException;
@ExceptionID("820")
public class ConsentButtons extends FlamesButtonInteraction {
    @Override
    public void execute(ButtonInteraction interaction, FlamesUser user) {
        switch (interaction.getComponentId()) {
            case "consent" -> {
                user.setConsent(1);
                interaction.reply("You're all set. Enjoy Flames!").setEphemeral(true).queue();
                user.setQuoteConsent(true);
            }
            case "noQuote" -> {
                user.setConsent(1);
                interaction.reply("You're all set. Flames won't select you for quote of the day, or pick a favorite quote from you. You can still use Flames' other features.").setEphemeral(true).queue();
                user.setQuoteConsent(false);
            }
            default -> {
                user.setConsent(2);
                interaction.reply("You're all set. Flames won't process any of your messages or collect any data from you.").setEphemeral(true).queue();
            }

        }
        new UserDataManager().saveUser(user);
    }

}
