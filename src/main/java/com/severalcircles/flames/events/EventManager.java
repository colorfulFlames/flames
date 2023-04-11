/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.FlamesManager;

public class EventManager extends FlamesManager {
    @Override
    public void prepare() {
        Flames.getFlogger().fine("Preparing event listeners");
        Flames.getApi().addEventListener(new StatusChange());
        Flames.getApi().addEventListener(new InteractionEvents());
        Flames.getApi().addEventListener(new MessageEvent());
//        Flames.getFlogger().finest("Added StatusChange listener");
    }
}
