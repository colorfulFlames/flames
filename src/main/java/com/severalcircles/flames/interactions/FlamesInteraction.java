/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.exception.ExceptionID;
import net.dv8tion.jda.api.interactions.Interaction;
@ExceptionID("800")
public abstract class FlamesInteraction {
    private Interaction interaction;
    private FlamesUser user;
    public Interaction getInteraction() {
        return interaction;
    }
//    public abstract void execute(Interaction interaction, FlamesUser user);
    public FlamesUser getUser() {
        return user;
    }
}
