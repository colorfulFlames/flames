/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.modal;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.interactions.FlamesInteraction;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;

public abstract class FlamesModalInteraction extends FlamesInteraction {
    public abstract void execute(ModalInteraction interaction, FlamesUser user);
}
