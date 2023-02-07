/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.conversations.Conversation;
import com.severalcircles.flames.conversations.ConversationsController;
import com.severalcircles.flames.conversations.ExpiredConversationException;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
//import com.severalcircles.flames.data.user.wildfire.Wildfire;
import com.severalcircles.flames.external.analysis.Analysis;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import com.severalcircles.flames.external.dialog.DialogSession;
import com.severalcircles.flames.frontend.today.Today;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageEvent extends ListenerAdapter implements FlamesDiscordEvent {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Logger.getGlobal().log(Level.FINE,event.getAuthor().getId() + " Triggered Message Event");
        super.onMessageReceived(event);
        User user = event.getAuthor();
        Logger logger = Logger.getGlobal();
        // Check to make sure it's worth processing the message
        // Bots don't get processed by Flames, simply because it's easier on everyone.
        if (user.isBot()) return;
        if (user.getName().toUpperCase(Locale.ROOT).contains("GOLDLEWIS")) event.getMessage().reply("https://media.discordapp.net/attachments/543162982536970240/943936840015159336/SpottedGoldlewis.gif").complete();
        Logger.getGlobal().log(Level.FINE, event.getAuthor().getName() + " is not a bot");
        FlamesUser flamesUser;
        // Read Flames User
        try {
            flamesUser = FlamesDataManager.readUser(user);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't read user data for " + user.getId() + ".");
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            Flames.incrementErrorCount();
            return;
        } catch (ConsentException e) {
            logger.log(Level.FINE, "Not processing " + user.getName() + "'s message because they haven't consented yet.");
            e.printStackTrace();
            return;
        } catch (DataVersionException e) {
            e.printStackTrace();
            return;
        }
//        Wildfire.processMessage(event.getMessage());
        // Analyze Message
        FinishedAnalysis finishedAnalysis;
        try {
            finishedAnalysis = Analysis.analyze(event.getMessage().getContentRaw());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Process conversation
        if (ConversationsController.activeConversations.containsKey(event.getChannel().getId())) {
            Logger.getGlobal().log(Level.FINE, "Already in conversation");
            try {
                ConversationsController.activeConversations.get(event.getChannel().getId()).processMessage(event.getMessage(), finishedAnalysis);
            } catch (ExpiredConversationException e) {
                logger.log(Level.FINE, "Conversation at " + event.getChannel().getId() + " is expired, removing it from the conversations list.");
                ConversationsController.activeConversations.remove(event.getChannel().getId());
            }
        } else {
            Logger.getGlobal().log(Level.FINE, "New Conversation");
            Conversation conversation = new Conversation(event.getChannel().asTextChannel());
            try {
                conversation.processMessage(event.getMessage(), finishedAnalysis);
            } catch (ExpiredConversationException e) {
                // What the fuck
                e.printStackTrace();
            }
            ConversationsController.activeConversations.put(event.getChannel().getId(), conversation);
            ConversationsController.activeConversations.forEach((element, index) -> System.out.println(element));
        }
        // Check quote of the day
        if (!Today.quote[2].equals(event.getAuthor().getId())) {
            if (finishedAnalysis.getEmotion() > Today.quoteEmotion) {
                Today.quote[0] = event.getMessage().getContentRaw();
                Today.quote[1] = event.getAuthor().getName();
                Today.quote[2] = event.getAuthor().getId();
            }
            Logger.getGlobal().log(Level.FINE, "Quote of the day is now " + Arrays.toString(Today.quote));
            flamesUser.setScore(flamesUser.getScore() + 864);
        }
        if (event.getMessage().getContentRaw().toUpperCase(Locale.ROOT).startsWith("FLAMES,")) {
            DialogSession session = new DialogSession();

            try {
                String[] response = session.processMessage(event.getMessage().getContentRaw().replaceAll("([fF])+lames, ", ""), event.getChannel().getId()).split("~");
                System.out.println(response[0] + "~" + response[1]);
                if (response[1].replace("~","").contains("&")) new IntentEvent().execute(response, event);
                event.getMessage().reply(response[0]).complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void register(JDA api) {
        Logger.getGlobal().log(Level.FINE, "Registering " + MessageEvent.class.getName());
        api.addEventListener(new MessageEvent());
    }
}
