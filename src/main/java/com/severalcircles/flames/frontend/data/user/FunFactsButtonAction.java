/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserFunFacts;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import com.severalcircles.flames.frontend.message.fourhundred.DataVersionErrorMessage;
import com.severalcircles.flames.util.Ranking;
import com.severalcircles.flames.util.StringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;

public class
FunFactsButtonAction implements FlamesButtonAction {

    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
        UserFunFacts funFacts = user.getFunFacts();
        if (funFacts == null) {
            try {
                user = FlamesDataManager.readUser(event.getUser());
            } catch (ConsentException ignored) {

            } catch (DataVersionException e) {
                event.replyEmbeds(new DataVersionErrorMessage((FlamesError) e).get());
                e.printStackTrace();
            }
            funFacts = user.getFunFacts();
            if (funFacts == null) {
                //oh my god
                funFacts = new UserFunFacts(Instant.now(), user.getEmotion(), Instant.now(), user.getEmotion(), user.getScore(), user.getScore(), Ranking.getRank(user.getScore()), 0);
            }
        }
        String rank = Ranking.getResources(user.getConfig().getLocale()).getString(Ranking.getRank(funFacts.getHighestFlamesScore()).toString());
        MessageEmbed embed = new EmbedBuilder()
                .setColor(new Color(153, 85,187))
                .setAuthor("User Data: Fun Facts", null, event.getUser().getAvatarUrl())
                .setTitle(event.getUser().getName(), "https://flamesapi.severalcircles.com/user/" + event.getUser().getId() + "/funfacts")
                .setDescription("French Toast Score: " + funFacts.getFrenchToastMentioned() + "")
                .addField("Best Day Ever", StringUtils.prettifyDate(funFacts.getHappyDay()),true)
                .addField("Worst Day Ever", (StringUtils.prettifyDate(funFacts.getSadDay())), true)
                .addField("Highest Ever Flames Score", funFacts.getHighestFlamesScore() + " (" + rank + ")", true)
                .addField("Lowest Ever Flames Score", funFacts.getLowestFlamesScore() + "", true)
                .setTimestamp(Instant.now())
                .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl()).build();
        event.editMessageEmbeds(embed).queue();
    }
}
