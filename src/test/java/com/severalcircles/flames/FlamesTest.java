/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FlamesTest {

    @Test
    void main() throws IOException {
        Flames.main(new String[]{});
        assertNotNull(Flames.api);
        assertNotNull(Flames.version);
        Flames.api.shutdownNow();
        assertFalse(new File(Flames.version + ".flamesfile").createNewFile());
    }
}