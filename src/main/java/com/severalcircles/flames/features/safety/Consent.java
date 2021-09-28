package com.severalcircles.flames.features.safety;

//import discord4j.core.object.entity.Message;
//import discord4j.core.object.entity.User;
//import discord4j.rest.util.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import org.apache.commons.collections4.bag.CollectionBag;

import java.awt.*;
import java.time.Instant;
import java.util.*;
import java.util.List;

public class Consent {
    public static final List<String> awaitingConsent = new LinkedList<>();
    @SuppressWarnings("SameReturnValue")
    public static void getConsent(User user) {
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
        user.openPrivateChannel().complete().sendMessage(embed).setActionRow(Button.danger("consentn't", "I don't consent to the privacy policy."), Button.success("consent", "I consent to the privacy policy")).complete();
        awaitingConsent.add(user.getId());

    }
    public static void welcomeToFlames(User user) {
        MessageEmbed embed = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setAuthor("Thank You", user.getAvatarUrl())
                .setTitle("Welcome to Flames, " + user.getName())
                .setDescription("We're so happy you finally made it.")
                .setFooter("Flames").build();
        user.openPrivateChannel().complete().sendMessage(embed);
    }
}
