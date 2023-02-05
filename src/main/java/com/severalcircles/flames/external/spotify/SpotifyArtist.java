/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.external.spotify;

/**
 * Represents an Artist on Spotify
 */
public class SpotifyArtist {
//    String url;
final int followers;
    final String image;
    final String name;
    final int popularity;
    final String uri;
    final String genre;

    public int getFollowers() {
        return followers;
    }

    public SpotifyArtist(String genre, int followers, String id, String image, String name, int popularity, String uri) {
//        this.url = url;
        this.followers = followers;
        this.image = image;
        this.name = name;
        this.popularity = popularity;
        this.uri = uri;
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getUri() {
        return uri;
    }
}
