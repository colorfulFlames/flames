/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.frontend.FlamesIntentResponse;
import com.severalcircles.flames.frontend.data.other.GlobalDataIntent;
import com.severalcircles.flames.frontend.data.user.intents.HiIntent;
import com.severalcircles.flames.frontend.data.user.intents.MyDataIntent;
import com.severalcircles.flames.frontend.today.TodayIntent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

public class IntentEvent {
    static final Map<String, FlamesIntentResponse> responseMap = new HashMap<>();
    public void register() {
        responseMap.put("&myData", new MyDataIntent());
        responseMap.put("&hi", new HiIntent());
        responseMap.put("&today", new TodayIntent());
        responseMap.put("&globalData", new GlobalDataIntent());
    }
    public void execute(String[] response, MessageReceivedEvent messageEvent) {
        responseMap.forEach((key, value) -> {
            if (response[1].contains(key)) value.execute(messageEvent);
        });
    }
}
