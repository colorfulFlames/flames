/*
 * Copyright (c) 2021-2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.google.cloud.language.v1.Sentiment;
import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.guild.FlamesGuild;
import com.severalcircles.flames.data.guild.NewGuildException;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserFunFacts;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.external.Analysis;
import com.severalcircles.flames.frontend.today.Today;
import com.severalcircles.flames.util.BadWordFilter;
import com.severalcircles.flames.util.Rank;
import com.severalcircles.flames.util.Ranking;
import com.severalcircles.flames.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageEvent extends ListenerAdapter implements FlamesDiscordEvent {

    public void register(JDA api) {
        api.addEventListener(new MessageEvent());
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return; // That is a scarecrow
        String content = BadWordFilter.getCensoredText(event.getMessage().getContentDisplay());
        // Try reading the user data. If something goes wrong trying to read the data and the error is uncaught all the way to this point, that's a big uh-oh moment, so it'll increment the fatal error counter. If the user hasn't consented, it'll just ignore and return;
        FlamesUser user;
        FlamesGuild guild;
        try {
            user = FlamesDataManager.readUser(event.getAuthor());
        } catch (IOException e) {
            e.printStackTrace();
            Flames.incrementErrorCount();
            return;
        } catch (ConsentException e) {
            Logger.getGlobal().log(Level.FINE, "Ignoring " + event.getAuthor().getName() + "'s message because they haven't consented yet.");
            return;
        } catch (DataVersionException e) {
            e.printStackTrace();
            return;
        }
        try {
            guild = FlamesDataManager.readGuild(event.getGuild().getId());
        } catch (IOException | NewGuildException e) {
            e.printStackTrace();
            return;
        }
        Sentiment sentiment;
        try {
            sentiment = Analysis.analyze(event.getMessage().getContentRaw());
        } catch (Exception e) {
            e.printStackTrace();
            Flames.incrementErrorCount();
            return;
        }
        int score = Math.round((sentiment.getScore() * 10) * (sentiment.getMagnitude() * 10));
        score *= user.getStats().getPOW();
        if (score <= 0) score += user.getStats().getRES() * 10;
        if (user.getStats().getLUCK() >= Math.random() * 100 - user.getStats().getCAR()) score *= 2;
        user.setEmotion(user.getEmotion() + sentiment.getScore());
        user.setScore(user.getScore() + score);
        guild.setFlamesScore(guild.getFlamesScore() + score);
        guild.setEmotion(guild.getEmotion() + sentiment.getScore());
        //noinspection IntegerDivisionInFloatingPointContext
        Today.emotion += score / GlobalData.participants;
        if (user.getScore() > Today.highScore) {
            Today.highScore = user.getScore();
            Today.highUser = event.getAuthor().getName();
        }
        user.getStats().addExp(Math.round(sentiment.getScore() * 10));
        @SuppressWarnings("IntegerDivisionInFloatingPointContext") int quoteChance = (int) Math.round(Math.random() * Math.round(GlobalData.participants / 2));
        System.out.println(quoteChance);
        if (Today.quoteEmotion < sentiment.getMagnitude() && !Today.quoteLocked) {
            if (sentiment.getMagnitude() < 0.5) return;
            if (Today.quote[2].equals(event.getAuthor().getId())) return;
            if (Ranking.getRank(user.getScore()) == Rank.UNRANKED | Ranking.getRank(user.getScore()) == Rank.APPROACHING_BRONZE) return;
            //noinspection IntegerDivisionInFloatingPointContext
            if (Today.quoteChanges > Math.max(1, Math.round(GlobalData.participants / 10))) Today.quoteLocked = true;
            if (sentiment.getMagnitude() > 1) Today.quoteLocked = true;
            MessageEmbed congrats = new EmbedBuilder()
                    .setAuthor("Flames", null, event.getAuthor().getAvatarUrl())
                    .setTitle(event.getAuthor().getName() + ", congratulations on taking the quote of the day from " + Today.quote[1] + "!")
                    .addField("\"" + content + "\"", "- " + event.getAuthor().getName() + ", " + StringUtil.prettifyDate(Instant.now()), true)
                    .addField("Bonus", StringUtil.formatScore(2500), true)
                    .setFooter("/today to see it for yourself!", Flames.api.getSelfUser().getAvatarUrl())
                    .setColor(Color.CYAN.darker())
                    .build();
            event.getMessage().reply(congrats).complete();
            user.setScore(user.getScore() + 2500);
            Today.quote = new String[]{content, event.getAuthor().getName(), event.getAuthor().getId()};
            Today.quoteChanges++;
        }
        UserFunFacts funFacts = user.getFunFacts();
        if (Analysis.analyzeEntities(event.getMessage().getContentRaw())) funFacts.setFrenchToastMentioned(funFacts.getFrenchToastMentioned() + 1);
        if (funFacts.getHighestFlamesScore() < user.getScore()) funFacts.setHighestFlamesScore(user.getScore());
        if (funFacts.getLowestFlamesScore() > user.getScore()) funFacts.setLowestFlamesScore(user.getScore());
        if (funFacts.getHighestEmotion() < user.getEmotion()) {
            funFacts.setHappyDay(Instant.now());
            funFacts.setHighestEmotion(user.getEmotion());
        }
        if (funFacts.getLowestEmotion() > user.getEmotion()) {
            funFacts.setSadDay(Instant.now());
            funFacts.setLowestEmotion(user.getEmotion());
        }
        user.setFunFacts(funFacts);
        try {
            FlamesDataManager.save(user);
            FlamesDataManager.save(guild);
        } catch (IOException e) {
            e.printStackTrace();
            Flames.incrementErrorCount();
        }
    }
}
