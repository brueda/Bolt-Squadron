package com.Ben.game.classes;

import com.Ben.framework.util.BeamTask;
import com.Ben.framework.util.TaskList;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 7/4/2015.
 */
public class AttackShip extends PlayerShip {

    public AttackShip(){
        super();
        attack = 11;
        specialLaser = Assets.multiLaser;
        shipImage[0] = Assets.attackBlue;
        shipImage[1] = Assets.attackOrange;
        shipImage[2] = Assets.attackRed;
        descriptions[0] = "*attack specialist.*aligned attacks hit twice";
        descriptions[1] = "+2 HP.+1 ATK.+1 DEF";
        descriptions[2] = "+2 HP.+1 ATK.*kill shots cause adjacent allies to attack";
        descriptions[3] = "+2 HP.+1 ATK.+1 DEF";
        descriptions[4] = "+2 HP.+1 ATK.*+1 hit per shot";
        descriptions[5] = "+2 HP.+1 ATK.+1 DEF";
        costs[0] = 200;
        costs[1] = 400;
        costs[2] = 600;
        costs[3] = 800;
        costs[4] = 1000;
        costs[5] = 1200;
    }

    @Override
    public void fire(Ship target){
        if(target.getPositionY() == positionY){
            target.hit(attack);   // double shot if enemy is aligned
        }
        if(upgradeLevel >= 4) {
            target.hit(attack);
        }
        super.fire(target);
        if(upgradeLevel >= 2 && target.isDead()){
            for(PlayerShip s : Player.getParty()){
                if(Math.abs(positionY - s.getPositionY()) + Math.abs(positionX - s.getPositionX()) <= 1 && !s.isDead() && s != this){
                    Ship t = null;
                    for(int i = 4; i < 7; i++){
                        if(Grid.grid[i][s.getPositionY()].getShip() != null){
                            t = Grid.grid[i][s.getPositionY()].getShip();
                            t.hit(s.getAttack()-2);
                            break;
                        }
                    }
                    BeamTask task = new BeamTask();
                    task.initialize(s, t);
                    task.makeRunnable();
                    TaskList.addTask(task);
                }
            }
        }
    }

    @Override
    public void levelUp(){
        super.levelUp();
        switch(upgradeLevel){
            case 1:
                increaseMaxHealth(2);
                attack += 1;
                defense += 1;
                break;
            case 2:
                increaseMaxHealth(2);
                attack += 1;
                break;
            case 3:
                increaseMaxHealth(2);
                attack += 1;
                defense += 1;
                break;
            case 4:
                increaseMaxHealth(2);
                attack += 1;
            case 5:
                increaseMaxHealth(2);
                attack += 1;
                defense += 1;
                break;
        }
    }
}
