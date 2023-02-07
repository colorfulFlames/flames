/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.info;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.external.spotify.SpotifyArtist;
import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.frontend.message.fivehundred.SpotifyConnectionFailedErrorMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.json.JSONException;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

public class ArtistCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
        event.deferReply().queue();
        SpotifyArtist artist;
        try {
            artist = Flames.spotifyConnection.getArtist((Objects.requireNonNull(event.getOption("artist")).getAsString()));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            event.getHook().sendMessageEmbeds(new SpotifyConnectionFailedErrorMessage(e).get()).queue();
            return;
        } catch (JSONException e) {
            event.getHook().sendMessage("Couldn't find an artist by that name.").queue();
            return;
        }
        String popularity;
        if (artist.getPopularity() < 10) popularity = "Unranked";
        else if (artist.getPopularity() < 20) popularity = "Approaching Bronze";
        else if (artist.getPopularity() < 30) popularity = "Bronze";
        else if (artist.getPopularity() < 40) popularity = "Silver";
        else if (artist.getPopularity() < 50) popularity = "Shining Silver";
        else if (artist.getPopularity() < 60) popularity = "Gold";
        else if (artist.getPopularity() < 70) popularity = "Beyond Gold";
        else if (artist.getPopularity() < 80) popularity = "Platinum";
        else if (artist.getPopularity() < 90) popularity = "Sparkling Platinum";
        else if (artist.getPopularity() >= 90) popularity = "Platinum Summit";
        else popularity = "Unranked";
        MessageEmbed embed = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setAuthor("Spotify", null, "https://developer.spotify.com/assets/branding-guidelines/icon3@2x.png")
                .setTitle(artist.getName())
                .setThumbnail(artist.getImage())
                .setDescription("Artist on Spotify")
                .addField("Genre", artist.getGenre(), true)
                .addField("Followers", String.valueOf(artist.getFollowers()), true)
                .addField("Flames Rank", popularity, true)
                .setTimestamp(Instant.now())
                .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl()).build();
        event.getHook().sendMessageEmbeds(embed).addActionRow(Button.link(artist.getUri(), "Open in Spotify")).queue();
    }
}
