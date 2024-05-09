/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.amiguito.thoughts;

import com.severalcircles.flames.amiguito.Amiguito;

public interface ThoughtEffect {
    void apply(Amiguito amiguito);
    void clear(Amiguito amiguito);
}
