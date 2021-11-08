/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.external.spotify;

//import old.com.severalcircles.flames.system.Flames;

import com.severalcircles.flames.Flames;

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
