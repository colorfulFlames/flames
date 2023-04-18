/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.MessageCodes;
import com.severalcircles.flames.system.exception.flames.FlamesException;

public class InternalExceptionManager {
    public static void handleException(Exception e) {
        Flames.getFlogger().severe("An internal exception occurred. Please report this to the developer.");
    }
    public static void handleException(FlamesException e) {
        Flames.getFlogger().severe(MessageCodes.generateCodeError(e));
    }
}
