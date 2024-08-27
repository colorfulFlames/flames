/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.data.user.FlamesUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

public class FlamesDataManagerTest {

    @Test
    public void getUser_WhenUserDoesNotExists_ShouldCreateAndReturnNewUser() throws IOException, ConsentException {
        FlamesDataManager.prepare();
        String id = new Random().nextInt(10000) + "";
        File expectedUserFile = new File(FlamesDataManager.USER_DIRECTORY.getAbsolutePath() + "/" + id + ".yml");

        if (expectedUserFile.exists()) {
            Assertions.fail("Test precondition failed. A user with the id already exists.");
        } else {
            FlamesUser user = FlamesDataManager.getUser(id, true);
            Assertions.assertTrue(expectedUserFile.exists());
            Assertions.assertNotNull(user);
            Assertions.assertEquals(id, user.getId());
        }
        if (FlamesDataManager.FLAMES_DIRECTORY.delete()) Logger.getGlobal().info("Deleted Flames directory");

    }

    @Test
    public void getUser_WhenUserExists_ShouldReturnExistingUser() throws IOException, ConsentException {
        FlamesDataManager.prepare();
        String id = "existingUserId";

        FlamesUser expectedUser = createAndSaveUserWithId(id);

        FlamesUser actualUser = FlamesDataManager.getUser(id);
        Assertions.assertEquals(expectedUser.getId(), actualUser.getId());
        if (FlamesDataManager.FLAMES_DIRECTORY.delete()) Logger.getGlobal().info("Deleted Flames directory");
    }

    private FlamesUser createAndSaveUserWithId(String id) {
        FlamesDataManager.prepare();
        FlamesUser user = new FlamesUser(id);
        user.setConsent(1);
        try {
            FlamesDataManager.saveUser(user);
        } catch (IOException e) {
            Assertions.fail("Error creating Flames User for test.", e);
        }
        if (FlamesDataManager.FLAMES_DIRECTORY.delete()) Logger.getGlobal().info("Deleted Flames directory");
        return user;
    }
}