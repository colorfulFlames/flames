/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.frontend.ConsentEmbed;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.ConversationManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.manager.UserDataManager;
import com.severalcircles.flames.system.exception.flames.ConsentException;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Locale;

@ExceptionID("602")
public class MessageEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        if (event.getAuthor().isBot()) return;
        FlamesUser flamesUser = null;
        try {
            Flames.getFlogger().fine("Trying to load user " + event.getAuthor().getAsTag());
            flamesUser = new UserDataManager().loadUser(event.getAuthor(), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ConsentException e) {
            e.printStackTrace();
            if (e.getConsent() == 2) return;
            new ConsentEmbed(Locale.ROOT).sendTo(event.getAuthor());
//            event.getAuthor().openPrivateChannel().complete().sendMessage("Welcome to Flames. Participation in " + event.getGuild().getName() + " constitutes consent to the Flames Privacy Policy. You can read more and opt out at any time by running the /consent command.").queue();
        }
        Flames.getFlogger().finest("User " + event.getAuthor().getAsTag() + " loaded successfully.");
        ConversationManager.processMessage(event.getChannel(), event.getMessage(), flamesUser);
    }
}
