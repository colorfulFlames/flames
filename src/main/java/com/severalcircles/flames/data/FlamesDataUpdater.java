/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserConfig;
import com.severalcircles.flames.data.user.UserFunFacts;
//import sun.util.logging.PlatformLogger;

import java.io.IOException;

public class FlamesDataUpdater {
    public static final double minimumVersion = 2.0;
    private final FlamesUser flamesUser;
    //    private UserStats stats;

    public FlamesDataUpdater(FlamesUser flamesUser) {
        this.flamesUser = flamesUser;
//        this.stats = flamesUser.getStats();
        UserFunFacts funFacts = flamesUser.getFunFacts();
        UserConfig config = flamesUser.getConfig();
    }
    public void run() throws DataVersionException, IOException {
        if (flamesUser.getDataVersion() < minimumVersion) throw new DataVersionException(true);
        if (flamesUser.getDataVersion() > FlamesUser.latestVersion) throw new DataVersionException(false);
        if (flamesUser.getScore() >= 100000) flamesUser.setScore(flamesUser.getScore() / 2);
        flamesUser.setDataVersion(2.1);
//        flamesUser.setStats(stats);
        FlamesDataManager.save(flamesUser);
    }
}
