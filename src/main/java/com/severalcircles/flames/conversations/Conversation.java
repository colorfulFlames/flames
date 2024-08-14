/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserEntities;
import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conversation {
    public static final int hootenanny_BONUS = 2;
    public static final List<String> entityList = new LinkedList<>();
    private final Map<String, Integer> entities;
    private final GuildMessageChannel channel;
    private final List<User> userList;
    private Instant expires;
    private double emotion;
    private String[] quote = {"This isn't epic yet", "Flames"};
    private double quoteScore;
    private final Map<String, FlamesUser> conversationCache;

    public Conversation(GuildMessageChannel channel) {
        this.channel = channel;
        this.entities = new HashMap<>();
        this.userList = new LinkedList<>();
        this.expires = Instant.now().plus(5, ChronoUnit.MINUTES);
        this.emotion = 0;
        this.quoteScore = 0;
        this.conversationCache = new HashMap<>();
    }

    protected boolean expired = false;

    public void processMessage(Message message, FinishedAnalysis finishedAnalysis) throws ExpiredConversationException, IOException, ConsentException {
        Logger.getGlobal().log(Level.FINE, "Processing Message");
        finishedAnalysis.getEntityList().forEach((element) -> {
            FlamesUser user = FlamesDataManager.getUser(message.getAuthor().getId());
            assert user != null;
            UserEntities userEntities = user.getEntities();
            FlamesDataManager.saveUser(user);
        });
        userList.forEach((element) -> {
            if (!conversationCache.containsKey(element.getId())) {
                conversationCache.put(element.getId(), FlamesDataManager.getUser(element.getId()));
            }
            FlamesUser user = conversationCache.get(element.getId());
            conversationCache.put(element.getId(), user);
            FlamesDataManager.saveUser(user);
            conversationCache.forEach((key, value) -> {
                FlamesDataManager.saveUser(value);
            });
        });
    }
}
