package com.severalcircles.flames.commands;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;

public abstract class Command {
    private static String name;
    private static String readableName;

    public Command(String name, String readableName, CommandType type) {
        this.name = name;
        this.readableName = readableName;
        this.type = type;
//        this.permissionLevel = permissionLevel;
    }
    private static CommandType type;
//    private int permissionLevel;
    public abstract void run(Message message);

    public static String getName() {
        return name;
    }

    public static String getReadableName() {
        return readableName;
    }

    public static CommandType getType() {
        return type;
    }
}
