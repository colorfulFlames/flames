/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.awt.*;
import java.io.IOException;

public class ConsentButtonAction implements FlamesButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
//        if (event.isAcknowledged()) return;
        if (event.getComponentId().equals("consent")) {
            System.out.println(3);
            MessageEmbed thanks = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setAuthor("Thank You", event.getUser().getAvatarUrl())
                .setTitle("Welcome to Flames, " + event.getUser().getName())
                .setDescription("We're so happy you finally made it.")
                .setFooter("Flames").build();
//        event.getMessage().delete();
        event.editMessageEmbeds(thanks).complete();
//            Consent.welcomeToFlames(event.getUser());
            GlobalData.participants++;
            GlobalData.write();
            user.setConsent(1);
        } else {
            event.editMessage("Alright. Let me know if you change your mind.").queue();
//        FlamesUser user;
        user.setConsent(2);
        }
        FlamesDataManager.save(user);
    }

}