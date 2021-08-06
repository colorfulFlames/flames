package com.severalcircles.flames.events;

import com.severalcircles.flames.command.Command;
import com.severalcircles.flames.data.base.FlamesDatabase;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.features.Analysis;
import com.severalcircles.flames.features.WelcomeBack;
import com.severalcircles.flames.system.Flames;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.sql.SQLException;
import java.util.Map;

public class MessageEvent {
    private final MessageCreateEvent event;
    public MessageEvent(MessageCreateEvent event) {
        this.event = event;
    }
    private int score;
    public void run() throws SQLException {
        Message message = event.getMessage();
        String content = event.getMessage().getContent();
        // First, check if it's a command.
        for (Map.Entry<String, Command> entry: Flames.commands.entrySet()) {
            if (content.startsWith("\\" + entry.getKey())) {
                entry.getValue().execute(event);
                return;
            }
        }
            // If not, process it.
            FlamesDatabase database = new FlamesDatabase();
            FlamesUser user = database.readUser(message.getAuthorAsMember().block().getId());
            UserStats stats = user.getStats();
        try {
                score = Analysis.analyze(message.getContent());
                stats.addExp(Math.abs(score));
                float emotion = (user.getEmotion() / 2) + score;
                score *= stats.getPOW();
                if (score < 0) score /= stats.getRES();
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.addScore(score);
            user.setStats(stats);
            new WelcomeBack().welcomeUserBack(user, message.getAuthor().get());
            database.write(user);
            database.close();
        }
    }
