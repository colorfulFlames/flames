package com.severalcircles.flames.command;

public @interface FlamesCommand {
    String commandName();
    String description();
    boolean guildRequired();

}
