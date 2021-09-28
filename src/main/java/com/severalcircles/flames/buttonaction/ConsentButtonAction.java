package com.severalcircles.flames.buttonaction;

import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.safety.Consent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.io.IOException;

public class ConsentButtonAction implements ButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
        if (event.isAcknowledged()) return;
        System.out.println(2);
        if (!Consent.awaitingConsent.contains(event.getUser().getId())) {
            event.getMessage().delete().queue();
//            event.reply("Whoops, you've already responded to that! I'll just get that out of the way for you.").queue();
            return;
        }
        if (event.getComponentId().equals("consent")) {
            System.out.println(3);
            event.reply("👋");
            Consent.welcomeToFlames(event.getUser());
            GlobalData.participants++;
            GlobalData.write();
            user.setConsent(1);
        } else {
            event.reply("Oh, okay. I'm sorry to hear that. I wont bug you anymore.").queue();
//        FlamesUser user;
            user.setConsent(2);
        }
        Consent.awaitingConsent.remove(event.getUser().getId());
        FlamesData.write(user);
    }

}