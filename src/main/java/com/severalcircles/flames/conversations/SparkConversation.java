/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.exception.AlreadyVotedException;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import com.severalcircles.flames.frontend.conversations.SparkResultsEmbed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class SparkConversation extends Conversation {
    public static final Map<String, SparkConversation> sparkConversations = new HashMap<>();
    private final List<String> alreadyVoted = new LinkedList<>();
    private final Map<Message, Integer> messageList;
    private final String question;
    Timer t = new Timer();
    Timer t2 = new Timer();
    int votes = 0;
    MessageChannelUnion channel;

    /**
     * Initializes a new SparkConversation object.
     *
     * @param channel The message channel in which the conversation is taking place.
     * @param question The question for the conversation.
     * @param minutes The duration of the conversation in minutes (between 1 and 5).
     */
    public SparkConversation(MessageChannelUnion channel, String question, int minutes) {
        super((GuildMessageChannel) channel);
        this.channel = channel;
        this.question = question;
        if (minutes < 1) {
             minutes = 1;
        }
        if (minutes > 5) {
            minutes = 5;
        }
        messageList = new HashMap<>();
        t2.schedule(new TimerTask() {
            @Override
            public void run() {
                channel.sendMessage("Don't forget to consider " + Conversation.entityList.get(new Random().nextInt(Conversation.entityList.size())) + "!").queue();
            }
        }, (minutes * 60000L) / 2);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, minutes * 60000L);
        channel.asTextChannel().getManager().setSlowmode(minutes * 60).complete();
    }

    /**
     * Finish the spark conversation by selecting the message with the highest votes,
     * sending the results as an embed, and performing necessary cleanup tasks.
     */
    void finish() {
        AtomicReference<String> answer = new AtomicReference<>("");
        AtomicInteger highest = new AtomicInteger();
        messageList.forEach((message, integer) -> {
            if (integer > highest.get()) {
                highest.set(integer);
                answer.set(message.getContentRaw());
            }
        });
        getChannel().sendMessageEmbeds(new SparkResultsEmbed(question, answer.get(), votes).get()).queue();
        sparkConversations.remove(getChannel().getId());
        channel.asTextChannel().getManager().setSlowmode(0).complete();
        Logger.getGlobal().info("Spark conversation ended.");
    }

    /**
     * Process a message in the conversation.
     *
     * @param message           The message to be processed.
     * @param finishedAnalysis  The analysis result of the message.
     * @throws ExpiredConversationException  if the conversation has expired.
     */
    @Override
    public void processMessage(Message message, FinishedAnalysis finishedAnalysis) throws ExpiredConversationException {
        super.processMessage(message, finishedAnalysis);

    }

    /**
     * Adds a vote to a message by a user.
     *
     * @param message The message to vote for.
     * @param user    The user who is voting.
     * @throws AlreadyVotedException if the user has already voted for the message.
     */
    public void addMessageVote(Message message, User user) throws AlreadyVotedException {
        if (alreadyVoted.contains(user.getId())) {
            throw new AlreadyVotedException("User has already voted.");
        }
        if (messageList.containsKey(message)) {
            messageList.put(message, messageList.get(message) + 1);
        } else {
            messageList.put(message, 1);
        }
        votes++;
        alreadyVoted.add(user.getId());
    }
}
