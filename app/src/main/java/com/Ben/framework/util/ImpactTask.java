package com.Ben.framework.util;

import com.Ben.game.classes.Ship;

/**
 * Created by Benjamin on 6/17/2015.
 */
public class ImpactTask extends Task {
    private Ship target;
    int power;

    public ImpactTask(){
        super();
    }

    public void initialize(Ship t, int p){
        target = t;
        power = p;
    }

    public void update(long delta, Painter g){
        target.hit(power);
        finishTask();
    }
}
