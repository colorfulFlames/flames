/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.ConversationManager;
import com.severalcircles.flames.system.manager.ConsentManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.manager.UserDataManager;
import com.severalcircles.flames.system.exception.flames.ConsentException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
@ExceptionID("602")
public class MessageEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        if (event.getAuthor().isBot()) return;
        FlamesUser flamesUser;
        try {
            Flames.getFlogger().fine("Trying to load user " + event.getAuthor().getAsTag());
            flamesUser = new UserDataManager().loadUser(event.getAuthor());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ConsentException e) {
            ConsentManager.handleConsentException(event.getAuthor(), e.getConsent());
            return;
        }
        Flames.getFlogger().finest("User " + event.getAuthor().getAsTag() + " loaded successfully.");
        ConversationManager.processMessage(event.getChannel(), event.getMessage(), flamesUser);
    }
}
