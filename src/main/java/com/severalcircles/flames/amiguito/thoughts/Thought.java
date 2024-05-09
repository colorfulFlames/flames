/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.amiguito.thoughts;

import com.severalcircles.flames.amiguito.Amiguito;

public enum Thought {
    HUNGRY(new HungryEffect()), THIRSTY(new ThirstyEffect()), DEVIANCE_AGREE(new DevianceAgreeEffect()), DEVIANCE_DISAGREE(new DevianceDisagreeEffect());
    ThoughtEffect effect;
    Thought(ThoughtEffect effect) {
        this.effect = effect;
    }
    public ThoughtEffect getEffect() {
        return effect;
    }
}
class HungryEffect implements ThoughtEffect {
    @Override
    public void apply(Amiguito amiguito) {
        amiguito.addMood(-2d);
        amiguito.addEnergy(-10d);
    }

    @Override
    public void clear(Amiguito amiguito) {
        amiguito.addMood(2d);
        amiguito.addEnergy(10d);
    }
}
class ThirstyEffect implements ThoughtEffect {
    @Override
    public void apply(Amiguito amiguito) {
        amiguito.addMood(-1d);
        amiguito.addEnergy(-3d);
    }

    @Override
    public void clear(Amiguito amiguito) {
        amiguito.addMood(1d);
        amiguito.addEnergy(3d);
    }
}
class DevianceAgreeEffect implements ThoughtEffect {
    @Override
    public void apply(Amiguito amiguito) {
        amiguito.addMood(1d);
        amiguito.addEnergy(1d);
    }

    @Override
    public void clear(Amiguito amiguito) {
        // No hace nada porque Amugito se mentendrá en el estado de humor
    }
}
class DevianceDisagreeEffect implements ThoughtEffect {
    @Override
    public void apply(Amiguito amiguito) {
        amiguito.addMood(-2d);
        amiguito.addEnergy(3d);
    }

    @Override
    public void clear(Amiguito amiguito) {
        // No hace nada porque Amugito se mentendrá en el estado de humor
    }
}
