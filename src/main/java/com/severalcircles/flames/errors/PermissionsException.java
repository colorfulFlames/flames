package com.severalcircles.flames.errors;

import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;

public class PermissionsException extends FlamesException{
    public PermissionsException(Message message) {
        super(message);
    }
    @Override
    public void handle() {
        this.message.addReaction(ReactionEmoji.unicode("⛔"));
    }
}
