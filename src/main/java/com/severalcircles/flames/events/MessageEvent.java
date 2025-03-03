/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.google.cloud.language.v1.Entity;
import com.severalcircles.flames.Flames;
import com.severalcircles.flames.conversations.Conversation;
import com.severalcircles.flames.conversations.ConversationsController;
import com.severalcircles.flames.conversations.ExpiredConversationException;
import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.external.analysis.Analysis;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import com.severalcircles.flames.frontend.today.Today;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageEvent extends ListenerAdapter implements FlamesDiscordEvent {
    /**
     * This method is called when a message is received in the Discord server. It processes the message, analyzes its content, and performs various actions based on the analysis.
     *
     * @param event The event containing the received message.
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Logger.getGlobal().log(Level.FINE,event.getAuthor().getId() + " Triggered Message Event");
        if (event.getMessage().getContentRaw().contains("https://") || event.getMessage().getContentRaw().contains("http://")) return; // Don't process URLs
        if (event.getMessage().getContentRaw().matches(("([0-9])\\w+"))) return; // Don't process numbers
        if (event.getMessage().getContentRaw().contains(".gif")) return; // Don't process GIFs
        if (event.getMessage().getContentRaw().matches("[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")) return; // Don't process URLs
        super.onMessageReceived(event);
        User user = event.getAuthor();
        Logger logger = Logger.getGlobal();
        // Check to make sure it's worth processing the message
        // Bots don't get processed by Flames, simply because it's easier on everyone.
        if (user.isBot()) return;

        if (user.getName().toUpperCase(Locale.ROOT).contains("GOLDLEWIS")) event.getMessage().reply("https://media.discordapp.net/attachments/543162982536970240/943936840015159336/SpottedGoldlewis.gif").complete();
        String nick;
        try {
            nick = Objects.requireNonNull(event.getGuild().getMemberById(Flames.api.getSelfUser().getId())).getNickname();
        } catch (NullPointerException e) {nick = "";}
        if (nick != null && nick.toLowerCase().contains("water")) {
            Objects.requireNonNull(event.getGuild().getMemberById(Flames.api.getSelfUser().getId())).modifyNickname(Flames.api.getSelfUser().getGlobalName()).complete();
        }
        FlamesUser flamesUser;
        // Read Flames User
        try {
            Logger.getGlobal().info("Reading user data");
            flamesUser = FlamesDataManager.getUser(user.getId());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't read user data for " + user.getId() + ".");
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            Flames.incrementErrorCount();
            return;
        } catch (ConsentException e) {
            logger.log(Level.INFO, "Not processing " + user.getName() + "'s message because they haven't consented yet.");
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
        for (Entity entity : finishedAnalysis.getEntityList()) {
            flamesUser.getEntities().addEntity(entity.getName(), finishedAnalysis.getEmotion() >= 0);
            try {
                FlamesDataManager.saveUser(flamesUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // Process conversation
        if (ConversationsController.activeConversations.containsKey(event.getChannel().getId())) {
            Logger.getGlobal().log(Level.INFO, "Already in conversation");
            try {
                System.out.println(finishedAnalysis);
                ConversationsController.activeConversations.get(event.getChannel().getId()).processMessage(event.getAuthor(), finishedAnalysis, event.getMessage());
            } catch (ExpiredConversationException e) {
                logger.log(Level.INFO, "Conversation at " + event.getChannel().getId() + " is expired, removing it from the conversations list.");
                ConversationsController.activeConversations.remove(event.getChannel().getId());
            }
        } else {
            Logger.getGlobal().log(Level.INFO, "New Conversation");
            Conversation conversation = new Conversation(event.getChannel().asTextChannel());
            try {
                Thread thread = new Thread(() -> {
                    conversation.processMessage(event.getAuthor(), finishedAnalysis, event.getMessage());
                });
                thread.start();
            } catch (ExpiredConversationException e) {
                // What the fuck
                e.printStackTrace();
            }
            ConversationsController.activeConversations.put(event.getChannel().getId(), conversation);
            ConversationsController.activeConversations.forEach((element, index) -> System.out.println(element));
        }
        // Check quote of the day
        if (Today.quoteMessage(event.getMessage().getContentRaw(), event.getAuthor().getGlobalName(), finishedAnalysis.getEmotion())) {
            flamesUser.addScore(Today.QUOTE_BONUS);
        }

        Today.highScore(user.getGlobalName(), flamesUser.getScore());
//        if (event.getMessage().getContentRaw().toUpperCase(Locale.ROOT).startsWith("FLAMES,")) {
//            DialogSession session = new DialogSession();
//
//            try {
//                String[] response = session.processMessage(event.getMessage().getContentRaw().replaceAll("([fF])+lames, ", ""), event.getChannel().getId()).split("~");
//                System.out.println(response[0] + "~" + response[1]);
////                if (response[1].replace("~","").contains("&")) new IntentEvent().execute(response, event);
//                event.getMessage().reply(response[0]).complete();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

    public void register(JDA api) {
        Logger.getGlobal().log(Level.FINE, "Registering " + MessageEvent.class.getName());
        api.addEventListener(new MessageEvent());
    }
}
