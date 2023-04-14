/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
public @interface FlamesCommand {
    String name();
    String description();
    FlamesCommandOption[] options() default {};
}
