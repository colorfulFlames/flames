/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.reports;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RecurringReport {
    String name();
    long interval();
}
