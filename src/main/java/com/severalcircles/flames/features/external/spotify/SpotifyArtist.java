package com.severalcircles.flames.features.external.spotify;

public class SpotifyArtist {
//    String url;
    int followers;
    String id;
    String image;
    String name;
    int popularity;
    String uri;
    String genre;

    public int getFollowers() {
        return followers;
    }

    public SpotifyArtist(String genre, int followers, String id, String image, String name, int popularity, String uri) {
//        this.url = url;
        this.followers = followers;
        this.id = id;
        this.image = image;
        this.name = name;
        this.popularity = popularity;
        this.uri = uri;
        this.genre = genre;
    }

    public SpotifyArtist() {

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
