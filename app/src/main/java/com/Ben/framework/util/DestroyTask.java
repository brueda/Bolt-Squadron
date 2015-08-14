package com.Ben.framework.util;

import com.Ben.framework.animation.Animation;
import com.Ben.game.classes.Ship;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 6/17/2015.
 */
public class DestroyTask extends Task {
    private Ship target;
    int x, y;
    Animation explosion;

    public DestroyTask(){
        super();
    }

    public void initialize(Ship t){
        target = t;
        explosion = new Animation(Assets.explosionFrames);
        explosion.setLoop(false);
    }

    public void update(long delta, Painter g){
       // Assets.playSound(Assets.hitID, 1f);
        explosion.update(delta);
        explosion.render(g, x, y, 120, 120);
        if (explosion.isDone()) {
            finishTask();
        }
    }

    @Override
    public void makeRunnable() {
        super.makeRunnable();
        x = 50 + target.getPositionX() * 100;
        y = 25 + target.getPositionY() * 100;
        target.destroy();
        Assets.playSound(Assets.explosionID, 0.5f);
    }
}
