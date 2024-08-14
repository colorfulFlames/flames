/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.data.legacy.global.GlobalData;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
//import net.dv8tion.jda.api.events.interaction.ButtonInteractionEvent;

import java.awt.*;
import java.io.IOException;

public class ConsentButtonAction implements FlamesButtonAction {
    @Override
    public void execute(ButtonInteractionEvent event, LegacyFlamesUser user) throws IOException {
//        if (event.isAcknowledged()) return;
        if (event.getComponentId().equals("consent")) {
            System.out.println(3);
            MessageEmbed thanks = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setAuthor("Thank You", event.getUser().getAvatarUrl())
                .setTitle("Welcome to Flames, " + event.getUser().getName())
                .setDescription("We're so happy you finally made it.")
                    .addField("One last question...", "Sometimes, you might say something that Flames thinks is pretty cool, and that message may be viewable by others in Today. If you don't want that, you can opt out using the button below.", true)
                .setFooter("Flames").build();
//        event.getMessage().delete();
        event.editMessageEmbeds(thanks).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("noQotd", "Do not use my messages as Quote of the Day")).complete();
//            Consent.welcomeToFlames(event.getUser());
            GlobalData.participants++;
            GlobalData.write();
            user.setConsent(1);
            user.getConfig().setQotdAllowed(true);
            user.getConfig().setFavQuoteAllowed(true);
        } else if (event.getComponentId().equals("consentn't")) {
            event.editMessage("Alright. Let me know if you change your mind.").queue();
//        LegacyFlamesUser user;
        user.setConsent(2);
        } else if (event.getComponentId().equals("noQotd")) {
            event.editMessage("Alright, I won't use your messages as Quote of the Day.").complete();
            user.getConfig().setFavQuoteAllowed(false);
            user.getConfig().setQotdAllowed(false);
        }
        LegacyFlamesDataManager.save(user);
    }

}