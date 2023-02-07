/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.guild;

import com.severalcircles.flames.data.FlamesData;

import java.time.Instant;
import java.util.Properties;

public class FlamesGuild implements FlamesData {
    private String name;
    private String id;
    private Instant joined;
//    private List<GuildBan> banList = new LinkedList<>();
    private int flamesScore;
    private float emotion;

    public Properties createData() {
        Properties data = new Properties();
        data.put("name", name);
        data.put("discordId", id);
        data.put("joined", joined.toString());
//        data.put("bans", banList.size() + "");
        data.put("score", String.valueOf(flamesScore));
        data.put("emotion", String.valueOf(emotion));
        return data;
    }

    public String getId() {
        return id;
    }

}
