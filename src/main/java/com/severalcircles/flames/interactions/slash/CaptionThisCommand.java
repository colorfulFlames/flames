/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.conversations.Conversation;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.ConversationManager;
import com.severalcircles.flames.conversations.external.Tenor;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
@ExceptionID("810")
@FlamesCommand(name = "captionthis", description = "Captions this conversation")
public class CaptionThisCommand extends FlamesSlashCommand {
    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        Conversation conversation = ConversationManager.conversations.get(interaction.getChannel().getId());
        if (conversation == null) {
            interaction.replyEmbeds(new EmbedBuilder().setTitle("No conversation").setDescription("There is no conversation in this channel.").build()).complete();
            return;
        }
//        AtomicReference<Integer> i = new AtomicReference<>(0);
        AtomicReference<String> tp = new AtomicReference<>("");
        tp.set(conversation.getTopics().keySet().stream().toList().get(new Random().nextInt(conversation.getTopics().keySet().size())).name().substring(0, Math.min(conversation.getTopics().keySet().size(), 256)));
        List<String> urls = Tenor.extractUrls(Objects.requireNonNull(Tenor.getSearchResults(tp.get().replace(" ", "%20"), 10)).toString());
        String url = urls.get(new Random().nextInt(urls.size()));
        int attempts = 1;
        while ((url.contains(".mp4") | url.contains("webm")) && attempts < 10) {
            Flames.getFlogger().fine("URL (" + url + ") is a video, retrying... (" + attempts + "/10)");
            url = urls.get(new Random().nextInt(urls.size()));
            attempts++;
        }
        if (attempts > 100) {
            url = null;
        }
        Flames.getFlogger().fine("URL: " + url);
        if (url == null) {
            url = "https://images-ext-2.discordapp.net/external/TQJl0O1GVifR1dTKHELdP2uGC_wg_whK09i4BXeoZkw/%3Fauto%3Dwebp%26s%3D72f0c7468ef69127c1e945b160e0b9807d066de5/https/preview.redd.it/nappqljautm51.jpg?width=1094&height=1108";
        }
        MessageEmbed embed = new EmbedBuilder()
                .setTitle(conversation.getFavoriteQuote().message().substring(0, Math.min(conversation.getFavoriteQuote().message().length(), 256)))
                .setImage(url)
                .build();
        interaction.replyEmbeds(embed).complete();
    }

}
