package com.severalcircles.flames.system.updater;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.system.WhatTheFuckException;

import javax.xml.crypto.Data;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlamesUpdater {
    private Map<DataChange, Boolean> compatibilityMap = new HashMap<>();
    private Properties userData;
    public FlamesUpdater(Properties userData) {
        Logger.getGlobal().log(Level.INFO, "Updating data for " + userData.get("discordId"));
        compatibilityMap.put(DataChange.THREE_ZERO, false);
        compatibilityMap.put(DataChange.THREE_ONE, false);
        compatibilityMap.put(DataChange.THREE_TWO, false);
        compatibilityMap.put(DataChange.THREE_THREE, false);
        this.userData = userData;
        determineCompatibility();
    }
    private void determineCompatibility() {
        try { if (!userData.get("score").equals(null)) {
            compatibilityMap.put(DataChange.THREE_ZERO, true);
            compatibilityMap.put(DataChange.THREE_ONE, true);
        } } catch (NullPointerException e) {}
        try {if (!userData.get("guilds").equals(null)) compatibilityMap.put(DataChange.THREE_TWO, true);}
        catch (NullPointerException e){};
        try{if (!userData.get("funFacts.happyDay").equals(null)) compatibilityMap.put(DataChange.THREE_THREE, true);}
        catch (NullPointerException e){};
    }
    public Properties update() throws WhatTheFuckException {
        if (!compatibilityMap.get(DataChange.THREE_ZERO) == true) throw new WhatTheFuckException();
        if (!compatibilityMap.get(DataChange.THREE_TWO)) {
            Logger.getGlobal().log(Level.FINE, "User Data for " + userData.get("discordId") + " is below version 3.2.0. Updating it now.");
            userData.put("guilds", 0);
        }
        if (!compatibilityMap.get(DataChange.THREE_THREE) == true) {
            Logger.getGlobal().log(Level.FINE, "User Data for " + userData.get("discordId") + " is below version 3.3.0. Updating it now.");
            userData.put("funFacts.sadDay", Instant.now());
           userData.put("funFacts.lowestEmotion", userData.get("emotion"));
           userData.put("funFacts.happyDay", Instant.now());
           userData.put("funFacts.highestEmotion", userData.get("emotion"));
           userData.put("funFacts.highestFlamesScore", userData.get("score"));
           userData.put("funFacts.lowestFlamesScore", userData.get("score"));
           userData.put("funFacts.bestRank", Ranking.getRank(Integer.parseInt(userData.get("score") + "")).toString());
           userData.put("funFacts.frenchToastMentioned", 0);
        }
        return userData;
    }
}
