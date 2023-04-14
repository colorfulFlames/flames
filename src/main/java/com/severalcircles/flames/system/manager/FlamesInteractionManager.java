/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.interactions.button.ConsentButtons;
import com.severalcircles.flames.interactions.button.FlamesButtonInteraction;
import com.severalcircles.flames.interactions.slash.*;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.runtime.NoCommandException;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ExceptionID("930")
public class FlamesInteractionManager extends FlamesManager {
    private static final List<FlamesSlashCommand> commandInteractionList = new ArrayList<>();
    private static final List<SlashCommandData> commandDataList = new ArrayList<>();
    private static final Map<String, FlamesButtonInteraction> buttons = new HashMap<>();
    @Override
    public void prepare() {
        commandInteractionList.add(new BasedCommand());
        commandInteractionList.add(new CaptionThisCommand());
        commandInteractionList.forEach(command -> {
            commandDataList.add(Commands.slash(command.getClass().getAnnotation(FlamesCommand.class).name(), command.getClass().getAnnotation(FlamesCommand.class).description()));
            Flames.getFlogger().fine("Added command " + command.getClass().getAnnotation(FlamesCommand.class).name() + " to command list");
        });
        Flames.getApi().updateCommands().addCommands(commandDataList).queue();
        buttons.put("consent", new ConsentButtons());
        buttons.put("noQuote", new ConsentButtons());
        buttons.put("decline", new ConsentButtons());
    }
    public static FlamesSlashCommand getCommandInteraction(String name) {
        for (FlamesSlashCommand command : commandInteractionList) {
            if (command.getClass().getAnnotation(FlamesCommand.class).name().equals(name)) return command;
        }
        throw new NoCommandException("No command with name " + name + " found.");
    }
    public static FlamesButtonInteraction getButtonInteraction(String name) {
        try {
            return buttons.get(name);
        } catch (NullPointerException e) {
            throw new NoCommandException("No button with name " + name + " found.");
        }
    }
}
