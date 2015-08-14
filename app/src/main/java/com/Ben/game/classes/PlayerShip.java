package com.Ben.game.classes;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.Ben.framework.util.BeamTask;
import com.Ben.framework.util.Painter;
import com.Ben.framework.util.ProjectileTask;
import com.Ben.framework.util.RandomNumberGenerator;
import com.Ben.framework.util.TaskList;
import com.Ben.game.state.State;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 6/7/2015.
 */
public abstract class PlayerShip extends Ship {
    public int upgradeLevel;
    protected Bitmap specialLaser;
    protected Bitmap[] shipImage;
    protected String[] descriptions;
    protected int[] costs;

    public PlayerShip(){
        super();
        upgradeLevel = 0;
        specialLaser = Assets.multiLaser;
        laserImage = Assets.blueLaser;
        shipImage = new Bitmap[3];
        descriptions = new String[6];
        costs  = new int[6];
    }

    public String[] getDescriptions(){
        return descriptions;
    }

    public int[] getCosts(){
        return costs;
    }

    @Override
    public void fire(Ship target){
        super.fire(target);
        ProjectileTask laser = new ProjectileTask();
        Bitmap image = (target.getPositionY() == positionY ? specialLaser : laserImage);
        laser.initialize(this, target, attack, target.isDead(), image);
        laser.makeRunnable();
        TaskList.addTask(laser);
    }

    @Override
    public void hit(int power){
        super.hit(power);
        if(dead){
            Player.removeShip(this);
        }
    }

    public Bitmap[] getShipImage(){
        return shipImage;
    }

    public void shield(){
        Assets.playSound(Assets.shieldID, 1.0f);
        shielded = true;
    }

    public void repair(){
        health = Math.min(maxHealth, health + (maxHealth / 5));
    }

    public void levelUp(){
        upgradeLevel += 1;
    }

    // all allied ships in the same column attack
    public void columnAttack(){
        for(PlayerShip s : Player.getParty()){
            if(s.getPositionX() == positionX && !s.isDead()){
                Ship target = null;
                for(int i = 4; i < 7; i++){
                    if(Grid.grid[i][s.getPositionY()].getShip() != null){
                        target = Grid.grid[i][s.getPositionY()].getShip();
                        target.hit(s.getAttack()-2);
                        break;
                    }
                }
                BeamTask task = new BeamTask();
                task.initialize(s, target);
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
                    g.drawImage(Assets.blueDot, x + 45, y + 10 + sway, 10, 10);
                    if (this == selected)
                        g.drawImage(Assets.blueRing, x + 40, y + 5 + sway, 20, 20);
                    break;
                case (State.ATTACK):
                    g.drawImage(Assets.redDot, x + 45, y + 10 + sway, 10, 10);
                    if (this == selected)
                        g.drawImage(Assets.redSelect, x + 40, y + 5 + sway, 20, 20);
                    break;
                case (State.CHECK):
                    if(this == selected)
                        g.drawImage(Assets.greenRing, x + 40, y + 5 + sway, 20, 20);
                    break;
            }
        }
        if(renderable) {
            g.setFont(Assets.tf, 15f);
            g.setColor(Color.GREEN);
            g.drawString("" + health, x - 22, y + sway + 20);
            g.drawImage(shipImage[upgradeLevel/2], x, y + sway, 65, 85);
            if(upgradeLevel % 2 == 1){
                g.drawImage(Assets.star, x+9, y+35 + sway, 15, 15);
            }
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
