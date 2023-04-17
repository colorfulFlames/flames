/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager;

import com.severalcircles.flames.system.exception.ExceptionID;

import java.io.IOException;

/**
 * Abstract class for classes responsible for preparing the bot. FlamesManagers, literally, each have a specific function, often data related, that they manage for the bot.
 * @author Several Circles
 * @version 8
 * @since Flames 8
 */
@ExceptionID("900")
public abstract class FlamesManager {
    /**
     * Prepares the manager for use.
     * @throws IOException if an I/O exception occurs. Shockingly.
     * @since Flames 8
     */
    public abstract void prepare() throws IOException;
}
