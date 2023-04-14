/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.frontend.ConsentEmbed;
import com.severalcircles.flames.system.exception.flames.ConsentException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.Locale;
@ExceptionID("913")
public class ConsentManager extends FlamesManager {
    @Override
    public void prepare() {

    }
    public static void getConsent(FlamesUser user) throws ConsentException {
        if (user.getConsent() != 1) {
            throw new ConsentException(user.getConsent());
        }
    }
    public static void handleConsentException(User discordUser, double consent) throws IllegalArgumentException {
        if (discordUser.getId().equals(Flames.getApi().getSelfUser().getId())) {
            throw new IllegalArgumentException("Can't get consent from myself!");
        }
        if (consent == 0) {
            discordUser.openPrivateChannel().complete().sendMessageEmbeds(new ConsentEmbed(Locale.ROOT).get())
                    .addActionRow(
                            Button.success("consent", "I consent to the use of my data."),
                            Button.primary("noQuotes", "I consent to the use of my data, but I do not want to be quoted."),
                            Button.danger("decline", "I do not consent to the use of my data."))
                    .complete();
        } else if (consent == 2) {
            Flames.getFlogger().warning("User " + discordUser.getAsTag() + " has declined consent. Won't be completing this action.");
            new EmbedBuilder()
                    .setTitle("Consent Error")
                    .setColor(Color.RED)
                    .setDescription("A user affected by this action has declined consent. As such, this action cannot be completed.")
                    .build();
            return;
        }
        new EmbedBuilder()
                .setTitle("Consent Error")
                .setColor(Color.RED)
                .setDescription("You have not consented to the use of your data. Please use the `/consent` command to consent to the use of your data.")
                .build();
    }
}
