package com.severalcircles.flames.events;

import com.severalcircles.flames.commands.CommandRegistry;
import com.severalcircles.flames.data.base.FlamesDatabase;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.features.Analysis;
import com.severalcircles.flames.features.WelcomeBack;
import discord4j.core.object.entity.Message;

import java.sql.SQLException;

public class MessageEvent {
    private Message message;
    public MessageEvent(Message message) {
        this.message = message;
    }
    private int score;
    public void run() throws SQLException {
        String content = message.getContent();
        // Check if user is running a Flames command
        if (content.startsWith("\\")) {
            content = content.replace("\\", "");
            content = content.toLowerCase();
            System.out.println(content);
            String[] args = content.split(" ");
            for (String arg : args) {
                System.out.println(arg);
            }
            System.out.println(CommandRegistry.commandMap.containsKey((args[0])));
            if (CommandRegistry.commandMap.containsKey(args[0])) {
                CommandRegistry.commandMap.get(args[0]).run(message);
            }
        } else {
            FlamesDatabase database = new FlamesDatabase();
            FlamesUser user = database.readUser(message.getAuthorAsMember().block().getId());
            UserStats stats = user.getStats();
            try {
                score = Analysis.analyze(message.getContent());
                stats.addExp(score);
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
}
