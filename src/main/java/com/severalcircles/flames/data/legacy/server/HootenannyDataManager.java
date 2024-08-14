/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data.legacy.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class HootenannyDataManager {
    public static ServerHootenannyData getData(String serverId) throws IOException {
        Properties data = new Properties();
        data.load(Files.newInputStream(Paths.get("server/" + serverId + "-hootenannyData.fl")));
        ServerHootenannyData hootenannyData = new ServerHootenannyData();
        for (String key : data.stringPropertyNames()) {
            hootenannyData.addScore(key, Integer.parseInt(data.getProperty(key)));
        }
        return hootenannyData;
    }
    public static void saveData(ServerHootenannyData hootenannyData, String serverId) throws IOException {
        Properties data = hootenannyData.createData();
        data.store(Files.newOutputStream(Paths.get("server/" + serverId + "-hootenannyData.fl")), "Hootenanny Data for " + serverId);
    }
}
