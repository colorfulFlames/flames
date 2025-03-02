/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.data.legacy.server.LegacyFlamesServer;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.user.FlamesUser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class DataUpgradeUtil {
    private final List<LegacyFlamesUser> legacyFlamesUsers = new LinkedList<>();
    private final List<LegacyFlamesServer> legacyFlamesServers = new LinkedList<>();
    private final List<FlamesUser> flamesUsers = new LinkedList<>();
    private final List<FlamesServer> flamesServers = new LinkedList<>();
    public void upgradeData() {
        LegacyFlamesDataManager.prepare();
        FlamesDataManager.prepare();
        File userDirectory = LegacyFlamesDataManager.USER_DIRECTORY;
        for (File file : userDirectory.listFiles()) {
                Logger.getGlobal().info("Upgrading user " + file.getName());
                try {
                    legacyFlamesUsers.add(LegacyFlamesDataManager.readUser(file.getName().replace(".fl", ""), true));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ConsentException e) {
                    throw new RuntimeException(e);
                }
        }
        File serverDirectory = LegacyFlamesDataManager.SERVER_DIRECTORY;
        for (File file : serverDirectory.listFiles()) {
            if (file.getName().endsWith(".fl")) {
                Logger.getGlobal().info("Upgrading server " + file.getName());
                if (file.getName().contains("-hootenanny")) {
                    continue;
                }
                legacyFlamesServers.add(LegacyFlamesDataManager.getServer(file.getName().replace(".fl", "")));
            }
        }
        legacyFlamesUsers.forEach(legacyFlamesUser -> flamesUsers.add(new FlamesUser(legacyFlamesUser)));
        legacyFlamesServers.forEach(legacyFlamesServer -> flamesServers.add(new FlamesServer(legacyFlamesServer)));

        flamesUsers.forEach(flamesUser -> {
            Logger.getGlobal().info("Saving user " + flamesUser.getID());
            try {
                FlamesDataManager.saveUser(flamesUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Logger.getGlobal().info("Saving servers");
        flamesServers.forEach(FlamesDataManager::saveServer);
    }
    public static void main(String[] args) {
        new DataUpgradeUtil().upgradeData();
    }
}
