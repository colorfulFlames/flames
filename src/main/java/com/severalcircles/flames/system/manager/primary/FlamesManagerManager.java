/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager.primary;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.manager.FlamesManager;
import com.severalcircles.flames.system.manager.secondary.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FlamesManagerManager extends FlamesManager {
    static final List<FlamesManager> managers = new LinkedList<>();
    static final List<FlamesManager> managers2 = new LinkedList<>();
    @Override
    public void prepare() throws IOException {
        new SystemDataManager().prepare();
        managers.add(new EventManager());
//        managers.add(new SystemDataManager());
        managers.add(new FlamesInteractionManager());

        managers2.add(new FlamesReportManager());
        managers2.add(new ConversationManager());
        managers2.add(new FlamesQuestionManager());
        managers2.add(new UserDataManager());
        managers2.add(new FlamesDataManager());

        for (FlamesManager manager : managers) {
            Flames.getFlogger().fine("Preparing [1]" + manager.getClass().getSimpleName());
            manager.prepare();
        }
        for (FlamesManager manager : managers2) {
            Flames.getFlogger().fine("Preparing [2]" + manager.getClass().getSimpleName());
            manager.prepare();
        }
    }
}
