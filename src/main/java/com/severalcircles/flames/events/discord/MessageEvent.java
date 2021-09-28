package com.severalcircles.flames.events.discord;

import com.google.cloud.language.v1.Sentiment;
import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserFunFacts;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.features.Analysis;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.features.safety.Consent;
import com.severalcircles.flames.features.WelcomeBack;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class MessageEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public List<String> gospel = new LinkedList<>();
    @Override
    public void register(JDA api) {
        api.addEventListener(new MessageEvent());
    }
    FlamesUser user;
    UserStats stats;
    int score;
    private void run(MessageReceivedEvent event) throws Exception {
        Message message = event.getMessage();
        try {
            user = FlamesData.readUser(message.getAuthor().getId(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //noinspection OptionalGetWithoutIsPresent
        if (message.getAuthor().isBot()) return;
//        if (message.getAuthor().getId().asString().equals("775440488605024287")) new EventOfSepiroth(event).run();
//        if (message.getAuthorAsMember().isBot()) return;
        if (user.getConsent() > 1) return;
        if (user.getConsent() < 1) {
            Consent.getConsent(message.getAuthor());
//            user.setConsent(1);
//            FlamesData.write(user);
            return;
        }
        if (user.getLastSeen() == -1 && !gospel.contains(user.getDiscordId())) {
            message.getAuthor().openPrivateChannel().complete().sendMessage("https://cdn.discordapp.com/attachments/849657307263926312/889247441663180860/welcomebackwelcomeback.mp4").queue();
            gospel.add(user.getDiscordId());
        }
        String content = event.getMessage().getContentRaw();
        stats = user.getStats();
        float emotion = user.getEmotion();
        UserStats stats = user.getStats();
            GlobalData.read();
            Sentiment sentiment = Analysis.analyze(message.getContentRaw());
            score = Math.round((sentiment.getScore() * 10) * (sentiment.getMagnitude() * 10));
            GlobalData.globalScore += score;
            stats.addExp(Math.abs(score));
            emotion += sentiment.getMagnitude() * sentiment.getScore();
            if (score > 0) {score *= stats.getPOW();}
            else if (score < 0) score /= stats.getRES();
        user.setEmotion(emotion);
        user.addScore(score);
        user.setStats(stats);
//        user.setLocale(message.getAuthor().getUserData().locale() + "");
        if (user.getEmotion() <= -5 && !user.lowEmotionWarned) {
            //noinspection OptionalGetWithoutIsPresent
//            EmotionMessages.sendLowNote(event.getMember().get(), user);
//            user.lowEmotionWarned = true;
        }
//        new WelcomeBack().welcomeUserBack(user, message.getAuthor());
        UserFunFacts funFacts = user.getFunFacts();
        if (user.getEmotion() > funFacts.getHighestEmotion()) {
            funFacts.setHighestEmotion(user.getEmotion());
            funFacts.setHappyDay(Instant.now());
        }
        if (user.getEmotion() < funFacts.getLowestEmotion()) {
            funFacts.setLowestEmotion(user.getEmotion());
            funFacts.setSadDay(Instant.now());
        }
        if (user.getScore() > funFacts.getHighestFlamesScore()) funFacts.setHighestFlamesScore(user.getScore());
        if (user.getScore() < funFacts.getLowestFlamesScore()) funFacts.setLowestFlamesScore(user.getScore());
        if (Analysis.analyzeEntities(content)) funFacts.setFrenchToastMentioned(funFacts.getFrenchToastMentioned() + 1);
        user.setFunFacts(funFacts);
        GlobalData.write();
        FlamesData.write(user);
    }
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        try {
            this.run(event);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
