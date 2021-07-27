package com.severalcircles.flames.errors;

import discord4j.core.object.entity.Message;

public abstract class FlamesException extends Exception {
    public abstract void handle();
    public Message message;
    public FlamesException(Message message) {
        this.message = message;
    }
}
