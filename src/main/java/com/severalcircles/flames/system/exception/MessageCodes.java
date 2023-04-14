/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.exception;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.flames.FlamesException;

public class MessageCodes {
    static final String PREFIX = "FL-";
    public static String generateCodeError(FlamesException e) {
        String code = "";
        code += PREFIX;
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                Flames.getFlogger().severe(e.getClass().getSimpleName());
            }
        try {
            Flames.getFlogger().finest(Class.forName(e.getStackTrace()[0].getClassName()).getName());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        try {
            code += Class.forName(e.getStackTrace()[0].getClassName()).getAnnotation(ExceptionID.class).value();
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        code += "-";
        code += e.getClass().getAnnotation(ExceptionID.class).value();
        return code;
    }
}
