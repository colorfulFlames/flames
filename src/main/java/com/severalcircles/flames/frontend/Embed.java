/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Embed {
    String name();
}
