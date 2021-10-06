package com.severalcircles.flames.system.updater;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.system.Flames;
import com.severalcircles.flames.system.WhatTheFuckException;

import javax.xml.crypto.Data;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlamesUpdater {
    private Properties data;
    double version;
    String id;
    public FlamesUpdater(Properties data) {
        this.data = data;
        this.version = Double.parseDouble(data.get("version") + "");
        id = data.get("discordId") + "";
    }
    public void check() {
        if (version < FlamesUser.latestVersion) {
            // Out of Date
            Logger.getGlobal().log(Level.INFO, "User Data for " + Flames.api.getUserById(id).getName() + " (" + id + ") is at version " + version + ", but the version of user data required by this version of Flames is " + FlamesUser.latestVersion + ". Attempting to update that user's data.");
        } else if (version > FlamesUser.latestVersion) {
            // Too New
            Logger.getGlobal().log(Level.SEVERE, "User Data for " + Flames.api.getUserById(id).getName() + " (" + id + ") is too new! It's at " + version + " but this version of Flames is only at " + FlamesUser.latestVersion + ". You might experience issues unless you update your version of Flames or delete this user data file and allow Flames to create a new one.");
        }
    }
}
