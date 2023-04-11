/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.data.user;

public record FlamesQuote(
        String message,
        FlamesUser user
) {
    public static FlamesQuote valueOf(String favoriteQuote, FlamesUser user) {
        return new FlamesQuote(favoriteQuote, user);
    }

    }
