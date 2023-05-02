/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.interactions.button.ConsentButtons;
import com.severalcircles.flames.interactions.button.FlamesButtonInteraction;
import com.severalcircles.flames.interactions.modal.FlamesModalInteraction;
import com.severalcircles.flames.interactions.modal.QuestionModal;
import com.severalcircles.flames.interactions.slash.*;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.runtime.NoCommandException;
import com.severalcircles.flames.system.FlamesManager;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
/**
 * Manages interactions with the bot, like Slash Commands and Buttons.
 * @author Several Circles
 * @version 8
 * @since Flames 8
 */
@ExceptionID("930")
public class FlamesInteractionManager extends FlamesManager {
    private static final List<FlamesSlashCommand> commandInteractionList = new ArrayList<>();
    private static final List<SlashCommandData> commandDataList = new ArrayList<>();
    private static final Map<String, FlamesButtonInteraction> buttons = new HashMap<>();
    private static final Map<String, FlamesModalInteraction> modals = new HashMap<>();

    @Override
    public void prepare() {
//        commandInteractionList.add(new BasedCommand());
//        commandInteractionList.add(new CaptionThisCommand());
//        commandInteractionList.add(new QuestionCommand());
//        commandInteractionList.add(new HiCommand());
//        commandInteractionList.add(new MyDataCommand());
//        commandInteractionList.add(new TodayCommand());
//        commandInteractionList.add(new ThanksCommand());
//        commandInteractionList.add(new AboutCommand());
//        commandInteractionList.add(new GlobalDataCommand());
        Set<Class<?>> commands = Flames.reflections.getTypesAnnotatedWith(FlamesCommand.class);
        Set <Class<FlamesSlashCommand>> safeCommands = new HashSet<>();
        commands.forEach(command -> {
            try {safeCommands.add((Class<FlamesSlashCommand>) command);} catch (ClassCastException e) {Flames.getFlogger().severe("Class " + command.getName() + " is annotated with FlamesCommand but is not extending FlamesSlashCommand.");}
        });
        safeCommands.forEach(command -> {
            try {
                commandInteractionList.add(command.getDeclaredConstructor().newInstance());
                Flames.getFlogger().fine("Command " + command.getName() + " is valid.");
            } catch (Exception e) {
                Flames.getFlogger().severe("Failed to instantiate command " + command.getName());
                e.printStackTrace();
            }
        });
        commandInteractionList.forEach(command -> {
            SlashCommandData data = Commands.slash(command.getClass().getAnnotation(FlamesCommand.class).name(), command.getClass().getAnnotation(FlamesCommand.class).description());
            for (FlamesCommandOption option : command.getClass().getAnnotation(FlamesCommand.class).options()) {
                data.addOption(option.type(), option.name(), option.description(), option.required());
            }
            commandDataList.add(data);
            Flames.getFlogger().fine("Added command " + command.getClass().getAnnotation(FlamesCommand.class).name() + " to command list");
        });
        Flames.getApi().updateCommands().addCommands(commandDataList).queue();
        buttons.put("consent", new ConsentButtons());
        buttons.put("noQuote", new ConsentButtons());
        buttons.put("decline", new ConsentButtons());
        modals.put("question", new QuestionModal());
    }

    public static FlamesSlashCommand getCommandInteraction(String name) {
        for (FlamesSlashCommand command : commandInteractionList) {
            Flames.getFlogger().fine("Checking command " + command.getClass().getAnnotation(FlamesCommand.class).name() + " for name " + name);
            Flames.getFlogger().fine(String.valueOf(command.getClass().getAnnotation(FlamesCommand.class).name().equals(name)));
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

    public static FlamesModalInteraction getModalInteraction(String name) {
        Flames.getFlogger().fine(name);
        AtomicReference<FlamesModalInteraction> modal = new AtomicReference<>();
        name = name.split(":")[0];
        try {
            String finalName = name;
            modals.forEach((key, value) -> {
                Flames.getFlogger().finest("Checking modal " + key + " for name " + finalName + " (" + key.startsWith(finalName) + ")");
                if (key.startsWith(finalName)) {
                    Flames.getFlogger().fine("Found modal with name " + key);
                    modal.set(value);
                }
            });
            return modal.get();
        } catch (NullPointerException e) {
            throw new NoCommandException("No modal with name " + name + " found.");
        }
    }
}
