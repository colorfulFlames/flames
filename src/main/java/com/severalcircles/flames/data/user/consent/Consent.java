/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.user.consent;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
//import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.time.Instant;
import java.util.*;
/**
 * Handles getting privacy policy consent from users
 */
public class Consent {
//    public static final List<String> awaitingConsent = new LinkedList<>();

    /**
     * Sends the consent dialog to a user
     */
    @SuppressWarnings("SameReturnValue")
    public static void getConsent(User user) throws InsufficientPermissionException {
        ResourceBundle resources = ResourceBundle.getBundle("features/Consent", Locale.ENGLISH);
        MessageEmbed embed = new EmbedBuilder()
                .setColor(Color.PINK)
                .setAuthor(String.format(resources.getString("author"), user.getName()), null, null)
                .setTimestamp(Instant.now())
                .setTitle(resources.getString("title"), "https://docs.severalcircles.com/privacy-policy")
//                .set("https://docs.severalcircles.com/privacy-policy")
                .setDescription(resources.getString("description"))
                .addField("By continuing to use Flames, you are agreeing to this privacy policy.", "tl;dr (because we know you didn't read it), Flames sends the messages you send to Google (completely anonymously!) to be analyzed, then only stores the values it gets back. We don't use your data for advertising, nor do we sell it to anyone.", true)
                .setFooter(resources.getString("footer"), null).build();
        user.openPrivateChannel().complete().sendMessageEmbeds(embed).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("consentn't", "I don't consent to the privacy policy."), Button.success("consent", "I consent to the privacy policy")).complete();
//        awaitingConsent.add(user.getId());

    }
}
