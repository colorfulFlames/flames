/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserConfig;
import com.severalcircles.flames.data.user.UserFunFacts;
import com.severalcircles.flames.data.user.UserStats;
//import sun.util.logging.PlatformLogger;

import java.io.IOException;

public class FlamesDataUpdater {
    public static final double minimumVersion = 2.0;
    @SuppressWarnings("FieldMayBeFinal")
    private FlamesUser flamesUser;
    @SuppressWarnings("FieldMayBeFinal")
    private UserStats stats;

    public FlamesDataUpdater(FlamesUser flamesUser) {
        this.flamesUser = flamesUser;
        this.stats = flamesUser.getStats();
        UserFunFacts funFacts = flamesUser.getFunFacts();
        UserConfig config = flamesUser.getConfig();
    }
    public boolean run() throws DataVersionException, IOException {
        if (flamesUser.getDataVersion() < minimumVersion) throw new DataVersionException(true);
        if (flamesUser.getDataVersion() > FlamesUser.latestVersion) throw new DataVersionException(false);
            stats.setLevel((int) Math.round(Math.log(stats.getExp()) / Math.log(2)) + 1);
            stats.setCAR((int) Math.round(Math.random() * stats.getLevel()));
            stats.setLUCK((int) Math.round(Math.random() * stats.getLevel()));
            stats.setRISE((int) Math.round(Math.random() * stats.getLevel()));
            stats.setPOW((int) Math.round(Math.random() * stats.getLevel()));
            stats.setRES((int) Math.round(Math.random() * stats.getLevel()));
            if (flamesUser.getScore() >= 100000) flamesUser.setScore(flamesUser.getScore() / 2);
            flamesUser.setDataVersion(2.1);
        flamesUser.setStats(stats);
        FlamesDataManager.save(flamesUser);
        return true;
    }
}
