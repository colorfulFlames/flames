/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager.primary;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.events.InteractionEvents;
import com.severalcircles.flames.events.MessageEvent;
import com.severalcircles.flames.events.StatusChange;
import com.severalcircles.flames.system.manager.FlamesManager;
import com.severalcircles.flames.system.exception.ExceptionID;

@ExceptionID("920")
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
