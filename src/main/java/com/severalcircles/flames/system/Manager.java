/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Manager {
    int priority() default 0;
}
