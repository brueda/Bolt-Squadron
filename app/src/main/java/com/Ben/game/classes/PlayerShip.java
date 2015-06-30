package com.Ben.game.classes;

import android.graphics.Color;

import com.Ben.framework.util.BeamTask;
import com.Ben.framework.util.Painter;
import com.Ben.framework.util.RandomNumberGenerator;
import com.Ben.framework.util.TaskList;
import com.Ben.game.state.State;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 6/7/2015.
 */
public abstract class PlayerShip extends Ship {
    private int offset;
    private boolean increasing;
    public PlayerShip(){
        super();
        offset = RandomNumberGenerator.getRandInt(200);
        increasing = true;
    }

    public void shield(){
        Assets.playSound(Assets.shieldID, 1.0f);
        shielded = true;
    }

    public void repair(){
        health = Math.min(maxHealth, health + (maxHealth / 2));
    }

    // all allied ships in the same column attack
    public void columnAttack(){
        for(PlayerShip s : Player.getParty()){
            if(s.getPositionX() == positionX && !s.isDead()){
                BeamTask task = new BeamTask();
                task.initialize(s, s.getAttack());
                task.makeRunnable();
                TaskList.addTask(task);
            }
        }
    }

    public void update(){
        if(increasing) offset++;
        else offset--;
        if(offset == 200) increasing = false;
        if(offset == 0) increasing = true;
    }

    public void render(Painter g, int state, Ship selected){
        int x = currentTile.x_coordinate;
        int y = currentTile.y_coordinate;
        int sway = (offset - 100)/20;

        if(!activated && !dead) {
            switch (state) {
                case (State.MOVE):
                    g.drawImage(Assets.greenDot, x + 45, y + 10 + sway, 10, 10);
                    if (this == selected)
                        g.drawImage(Assets.greenRing, x + 40, y + 5 + sway, 20, 20);
                    break;
                case (State.ATTACK):
                    g.drawImage(Assets.blueDot, x + 45, y + 10 + sway, 10, 10);
                    if (this == selected)
                        g.drawImage(Assets.blueRing, x + 40, y + 5 + sway, 20, 20);
                    break;
            }
        }
        if(renderable) {
            g.setFont(Assets.tf, 15f);
            g.setColor(Color.GREEN);
            g.drawString("" + health, x - 15, y + sway + 20);
            g.drawImage(Assets.testShip, x, y + sway, 65, 85);
            if(shielded){
                g.drawImage(Assets.shield, x - 10, y + sway, 95, 95);
            }
        }
    }

    public void destroy(){
        //Player.getParty().remove(this);   ** concurrency issues with this **
        renderable = false;
    }
}
