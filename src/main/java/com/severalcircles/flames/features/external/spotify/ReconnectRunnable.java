package com.severalcircles.flames.features.external.spotify;

import com.severalcircles.flames.system.Flames;

import java.io.IOException;

/**
 * Runnable that destroys the previous SpotifyConnection and creates a new one
 */
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
