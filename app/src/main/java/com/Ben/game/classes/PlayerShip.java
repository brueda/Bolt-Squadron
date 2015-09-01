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

    protected static final int SHADOW = 4;
    protected static final int DESAT = 5;
    
    public int upgradeLevel;
    protected int totalHealing;
    protected Bitmap specialLaser;
    protected Bitmap[] shipImage;
    protected String[] descriptions;
    protected int[] costs;
    protected boolean green;
    protected int totalCost;

    public PlayerShip(){
        super();
        green = false;
        upgradeLevel = 0;
        totalHealing = 0;
        specialLaser = Assets.multiLaser;
        laserImage = Assets.blueLaser;
        shipImage = new Bitmap[9];
        descriptions = new String[6];
        costs  = new int[7];
    }

    public String[] getDescriptions(){
        return descriptions;
    }

    public int[] getCosts(){
        return costs;
    }

    public void goGreen(){
        upgradeLevel = 6;
        green = true;
        Player.increaseVolts((totalCost * 3) / 4);
    }

    @Override
    public void fire(Ship target){
        super.fire(target);
        ProjectileTask laser = new ProjectileTask();
        Bitmap image = (target.getPositionY() == positionY ? specialLaser : laserImage);
        image = (isGreen() ? Assets.greenLaser : image);
        laser.initialize(this, target, target.isDead(), image);
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

    @Override
    public Bitmap getShipImage(){
        return shipImage[upgradeLevel/2];
    }

    public Bitmap[] getImageArray(){
        return shipImage;
    }

    public boolean isGreen(){
        return green;
    }

    public int getTotalCost(){
        return totalCost;
    }

    public void shield(){
        Assets.playSound(Assets.shieldID, 1.0f);
        shieldRenderable = true;
        shielded = true;
    }

    public void repair(){
        // no more than 30 healing per round allowed
        int healingPotential = Math.min(5, maxHealth - health);
        int healingDone = Math.min(30-totalHealing, healingPotential);
        totalHealing += healingDone;
        health += healingDone;
    }

    public void levelUp(){
        upgradeLevel += 1;
        totalCost += costs[upgradeLevel];
    }

    // all allied ships in the same column attack
    public void columnAttack(){
        for(PlayerShip s : Player.getParty()){
            if(s.getPositionX() == positionX && !s.isDead()){
                Ship target = null;
                for(int i = 4; i < 7; i++){
                    if(Grid.grid[i][s.getPositionY()].getShip() != null){
                        target = Grid.grid[i][s.getPositionY()].getShip();
                        target.hit(s.getAttack());
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

    public void kamikaze(){
        shielded = false;
        hit(50);
        destroy();
        Ship target = null;
        for(int i = 4; i < 7; i++){
            if(Grid.grid[i][getPositionY()].getShip() != null){
                target = Grid.grid[i][getPositionY()].getShip();
                target.hit(60);
                break;
            }
        }
        BeamTask task = new BeamTask();
        task.initialize(this, target);
        task.makeRunnable();
        TaskList.addTask(task);
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
                    //g.drawImage(Assets.blueDot, x + 45, y + 10 + sway, 10, 10);
                    if(this == selected)
                        g.drawImage(shipImage[SHADOW], x - 3, y + sway - 3, 71, 91);
                    break;
                case (State.ATTACK):
                    //g.drawImage(Assets.redDot, x + 45, y + 10 + sway, 10, 10);
                    if(this == selected)
                        g.drawImage(shipImage[SHADOW], x - 3, y + sway - 3, 71, 91);
                    break;
                case (State.CHECK):
                    if(this == selected)
                        g.drawImage(shipImage[SHADOW], x - 3, y + sway - 3, 71, 91);
                    break;
            }
        }
        if(renderable) {
            if (this.selected && !this.isActivated()) {
                g.drawImage(shipImage[SHADOW], x - 3, y + sway - 3, 71, 91);
            }
            g.setFont(Assets.tf, 15f);
            g.setColor(Color.GREEN);
            g.drawString("" + health, x - 22, y + sway + 20);
            int shipSpriteIndex = !this.isActivated() ? upgradeLevel/2 : (upgradeLevel/2) + DESAT;
            g.drawImage(shipImage[shipSpriteIndex], x, y + sway, 65, 85);

            if(upgradeLevel % 2 == 1) {
                Bitmap starSprite = !this.isActivated() ? Assets.star : Assets.starDesat;
                g.drawImage(starSprite, x+9, y+35 + sway, 15, 15);
            }
            if(shieldRenderable){
                g.drawImage(Assets.shield, x - 10, y + sway, 95 + sway, 95 + sway);
            }
        }
    }

    public void destroy(){
        //Player.getParty().remove(this);   ** concurrency issues with this **
        renderable = false;
    }

    public void resetStatus(){
        setShield(false);
        setHealth(getMaxHealth());
        totalHealing = 0;
    }


}
