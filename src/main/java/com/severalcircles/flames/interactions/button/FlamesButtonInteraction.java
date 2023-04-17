/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.button;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.interactions.FlamesInteraction;
import com.severalcircles.flames.system.exception.ExceptionID;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
@ExceptionID("820")
public abstract class FlamesButtonInteraction extends FlamesInteraction {
    public abstract void execute(ButtonInteraction interaction, FlamesUser user);
}
