/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.external.spotify;

//import com.google.gson.Gson;
import com.severalcircles.flames.external.ParameterStringBuilder;
//import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Handles connecting to Spotify and retrieving data from it
 */
public class SpotifyConnection {
    String token;
    public static final String clientId = System.getenv("SPOTIFY_CLIENT");
    public static final String secret = System.getenv("SPOTIFY_SECRET");
    public static URL authUrl;
    public static URL searchUrl;
    static {
        try {
            authUrl = new URL("https://accounts.spotify.com/api/token");
//            searchUrl = new URL("https://api.spotify.com/v1/search");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public SpotifyConnection() throws IOException {
        HttpURLConnection con = (HttpURLConnection) authUrl.openConnection();
        con.setRequestMethod("POST");
        String auth = clientId + ":" + secret;
        System.out.println(Base64.getEncoder().encodeToString(auth.getBytes()));
        con.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
        con.setRequestProperty("Content-Length", "29");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        int status = con.getResponseCode();
        con.disconnect();
        this.token = new JSONObject(content.toString()).getString("access_token");
    }
    public SpotifyArtist getArtist(String query) throws IOException, JSONException {
        searchUrl = new URL("https://api.spotify.com/v1/search?q=" + query.replace(" ", "%20") + "&type=artist");
        HttpURLConnection con = (HttpURLConnection) searchUrl.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);
        con.setRequestProperty("Content-Length", String.valueOf(13 + query.length()));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        int status = con.getResponseCode();
        con.disconnect();
        System.out.println(content);
        JSONArray response = new JSONObject(content.toString()).getJSONObject("artists").getJSONArray("items");
        JSONObject artist = response.getJSONObject(0);
        System.out.println(artist);
        String genre;
        try {
            genre = artist.getJSONArray("genres").getString(0);
        } catch (JSONException e) {
            genre = "Unknown";
        }
    return new SpotifyArtist(genre, artist.getJSONObject("followers").getInt("total"), artist.getString("id"), artist.getJSONArray("images").getJSONObject(0).getString("url"), artist.getString("name"), artist.getInt("popularity"), artist.getJSONObject("external_urls").getString("spotify"));
    }
}
