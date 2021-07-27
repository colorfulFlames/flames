package com.severalcircles.flames.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    public static Map<String, Command> commandMap = new HashMap<String, Command>();
    public static void registerCommands() {
        commandMap.put(TestCommand.getName(), new TestCommand());
    }
}
