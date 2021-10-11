package com.severalcircles.flames.system.updater;

import java.util.Properties;

public class FlamesUpdater {
    final double version;
    final String id;
    public FlamesUpdater(Properties data) {
        this.version = Double.parseDouble(data.get("version") + "");
        id = data.get("discordId") + "";
    }
}
