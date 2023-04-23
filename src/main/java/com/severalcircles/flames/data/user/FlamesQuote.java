/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.data.user;

/**
 * FlamesQuote class for storing a quote made by a FlamesUser.
 * @param message
 * @param user
 * @author Several Circles
 * @version 8
 * @since Flames 8
 */
public record FlamesQuote(
        String message,
        FlamesUser user
) {
    public static FlamesQuote valueOf(String favoriteQuote, FlamesUser user) {
        return new FlamesQuote(favoriteQuote, user);
    }

    }
