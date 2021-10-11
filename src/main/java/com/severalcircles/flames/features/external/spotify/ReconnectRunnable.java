package com.severalcircles.flames.features.external.spotify;

import com.severalcircles.flames.system.Flames;

import java.io.IOException;

public class ReconnectRunnable implements Runnable {

    @Override
    public void run() {
        try {
            Flames.spotifyConnection = new SpotifyConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
