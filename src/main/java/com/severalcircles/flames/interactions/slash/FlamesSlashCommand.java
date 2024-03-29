/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.interactions.FlamesInteraction;
import com.severalcircles.flames.system.exception.ExceptionID;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
@ExceptionID("810")
public abstract class FlamesSlashCommand extends FlamesInteraction {
    private SlashCommandInteraction interaction;

    public abstract void execute(SlashCommandInteraction interaction, FlamesUser user);
    @Override
    public SlashCommandInteraction getInteraction() {
        return interaction;
    }
}
