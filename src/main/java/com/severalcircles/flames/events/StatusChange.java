/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.ExceptionID;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
@ExceptionID("603")
public class StatusChange extends ListenerAdapter {
    @Override
    public void onStatusChange(@NotNull net.dv8tion.jda.api.events.StatusChangeEvent event) {
        super.onStatusChange(event);
        Flames.getFlogger().fine(event.getOldStatus() + " → " + event.getNewStatus());
    }
}