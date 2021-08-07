package com.severalcircles.flames.errors;

import discord4j.core.object.entity.Message;

import java.util.logging.Logger;

public abstract class FlamesException extends Exception {
    public Logger logger = Logger.getGlobal();
    public Message message;
    public FlamesException(Message message) {
        this.message = message;
    }

    public FlamesException() {
    }

    public FlamesException(String message) {
        super(message);
    }

    public FlamesException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlamesException(Throwable cause) {
        super(cause);
    }

    public FlamesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
