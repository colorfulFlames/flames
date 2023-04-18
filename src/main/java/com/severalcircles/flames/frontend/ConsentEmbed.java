/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.system.exception.ExceptionID;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.Locale;
/**
 * Embed for the consent prompt.
 * @author Several Circles
 * @version 8
 * @since Flames 8
 */
@ExceptionID("701")
@Embed(name = "Consent")
public class ConsentEmbed extends FlamesEmbed {

    public ConsentEmbed(Locale locale) {
        super(locale);
    }

    @Override
    public MessageEmbed get() {
        return new EmbedBuilder()
                .setTitle(local.getString("title"), "https://flames.severalcircles.com/privacy-policy")
                .setDescription(local.getString("description"))
                .addField(local.getString("helpful1title"), local.getString("helpful1text"), false)
                .addField(local.getString("helpful2title"), local.getString("helpful2text"), true)
                .addField(local.getString("helpful3title"), local.getString("helpful3text"), true)
                .addField(local.getString("helpful4title"), local.getString("helpful4text"), true)
                .addField(local.getString("helpful5title"), local.getString("helpful5text"), true)
                .addField(local.getString("helpful6title"), local.getString("helpful6text"), true)
                .setColor(Color.ORANGE)
                .build();
    }
    public void sendTo(User user) {
        user.openPrivateChannel().complete().sendMessageEmbeds(new ConsentEmbed(Locale.ROOT).get())
                .addActionRow(
                        net.dv8tion.jda.api.interactions.components.buttons.Button.success("consent", "I consent to the use of my data."),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.primary("noQuotes", "I consent to the use of my data, but I do not want to be quoted."),
                        Button.danger("decline", "I do not consent to the use of my data."))
                .complete();
    }
}
