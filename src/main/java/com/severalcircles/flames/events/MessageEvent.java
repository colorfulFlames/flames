/*
 * Copyright (c) 2021-2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.google.cloud.language.v1.Sentiment;
import com.severalcircles.flames.data.base.ConsentException;
import com.severalcircles.flames.data.base.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserFunFacts;
import com.severalcircles.flames.features.Analysis;
import com.severalcircles.flames.features.BadWordFilter;
import com.severalcircles.flames.features.FlamesPrettyDate;
import com.severalcircles.flames.features.today.Today;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
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
        try {
            user = FlamesDataManager.readUser(event.getAuthor());
        } catch (IOException e) {
            e.printStackTrace();
            Flames.incrementErrorCount();
            return;
        } catch (ConsentException e) {
            Logger.getGlobal().log(Level.FINE, "Ignoring " + event.getAuthor().getName() + "'s message because they haven't consented yet.");
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
        if (score >= 0) score *= user.getStats().getPOW();
        else score /= user.getStats().getRES();
        user.setEmotion(user.getEmotion() + sentiment.getScore());
        user.getStats().addExp(Math.max(0, score));
        user.setScore(user.getScore() + score);

        Today.emotion += score;
        if (user.getScore() > Today.highScore) {
            Today.highScore = user.getScore();
            Today.highUser = event.getAuthor().getName();
        }
        @SuppressWarnings("IntegerDivisionInFloatingPointContext") int quoteChance = (int) Math.round(Math.random() * Math.round(GlobalData.participants / 2));
        System.out.println(quoteChance);
        //noinspection IntegerDivisionInFloatingPointContext
        if (Objects.equals(Today.quote[0], "We're still waiting for somebody to say something epic.") && sentiment.getMagnitude() >= 0.9 && quoteChance == Math.min(1, Math.round(GlobalData.participants / 2))) {
            Today.quote = new String[]{content, event.getAuthor().getName()};
            MessageEmbed congrats = new EmbedBuilder()
                    .setAuthor("Flames", null, event.getAuthor().getAvatarUrl())
                    .setTitle(event.getAuthor().getName() + ", congratulations on being selected for the quote of the day!")
                    .addField("\"" + content + "\"", "- " + event.getAuthor().getName() + ", " + FlamesPrettyDate.prettifyDate(Instant.now()), true)
                    .setFooter("/today to see it for yourself!", Flames.api.getSelfUser().getAvatarUrl())
                    .setColor(Color.CYAN.darker())
                    .build();
            event.getMessage().reply(congrats).complete();
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
        } catch (IOException e) {
            e.printStackTrace();
            Flames.incrementErrorCount();
        }
    }
}
