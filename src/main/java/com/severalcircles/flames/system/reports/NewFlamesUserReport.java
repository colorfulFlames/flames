/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.reports;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.Map;

public class NewFlamesUserReport extends FlamesReport {
    User user;
    public NewFlamesUserReport(User user) {
        this.user = user;
    }
    @Override
    public Map<String, String> getReport() {
        Map<String, String> report = new HashMap<>();
        report.put("discordUser", user.toString());
        return report;
    }
}
