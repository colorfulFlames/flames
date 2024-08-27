/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlamesServerTest {
        
    /**
     * This class tests the setId method of FlamesServer class.
     * The setId method is used to set the value of "id" of a FlamesServer object.
     */
    
    @Test
    public void testSetId() {
        FlamesServer server = new FlamesServer();
        server.setId("12345");
        assertEquals("12345", server.getId());
    }
}