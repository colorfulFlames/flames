/*
 * Copyright (c) 2021-2022 Several Circles.
 */

package com.severalcircles.flames.frontend.info.debug;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.events.ButtonEvent;
import com.severalcircles.flames.external.FlamesAssets;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import com.severalcircles.flames.util.StringUtil;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class DebugCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
        ResourceBundle common = ResourceBundle.getBundle("Common", Locale.ENGLISH);
        event.replyEmbeds(new DebugEmbed().get()).complete();
    }
}
