/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.exception.AlreadyVotedException;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import com.severalcircles.flames.frontend.conversations.SparkResultsEmbed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class SparkConversation {
    public static final Map<String, SparkConversation> sparkConversations = new HashMap<>();
    private final List<String> alreadyVoted = new LinkedList<>();
    private final Map<Message, Integer> messageList;
    private final String question;
    final Timer t = new Timer();
    final Timer t2 = new Timer();
    int votes = 0;
    final MessageChannelUnion channel;
    public MessageChannelUnion getChannel() {
        return channel;
    }
    public SparkConversation(MessageChannelUnion channel, String question, int minutes) {
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
//        channel.asTextChannel().getManager().setSlowmode(minutes * 60).complete();
    }
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
