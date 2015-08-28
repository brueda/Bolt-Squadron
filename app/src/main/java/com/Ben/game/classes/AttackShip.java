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
        //Index 3 is left empty for now in case of additional levels.
        shipImage[PlayerShip.SHADOW] = Assets.attackShadow;
        shipImage[DESAT + 0] = Assets.attBlueDesat;
        shipImage[DESAT + 1] = Assets.attOrangeDesat;
        shipImage[DESAT + 2] = Assets.attRedDesat;
        descriptions[0] = "*attack specialist.*attacks against enemies in.  the same row hit twice";
        descriptions[1] = "+2 HP.+1 ATK.+1 DEF";
        descriptions[2] = "+2 HP.*kill shots cause.  nearby allies to attack";
        descriptions[3] = "+2 HP.+2 ATK.+1 DEF";
        descriptions[4] = "+2 HP.*+1 hit per attack";
        descriptions[5] = "+2 HP.+2 ATK.+2 DEF";
        costs[0] = 200;
        costs[1] = 400;
        costs[2] = 600;
        costs[3] = 800;
        costs[4] = 1000;
        costs[5] = 1500;
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
                if(Math.abs(positionY - s.getPositionY()) <= 1 &&  Math.abs(positionX - s.getPositionX()) <= 1 && !s.isDead()){
                    Ship t = null;
                    for(int i = 4; i < 7; i++){
                        if(Grid.grid[i][s.getPositionY()].getShip() != null){
                            t = Grid.grid[i][s.getPositionY()].getShip();
                            t.hit(s.getAttack());
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
                break;
            case 3:
                increaseMaxHealth(2);
                attack += 2;
                defense += 1;
                break;
            case 4:
                increaseMaxHealth(2);
                laserImage = Assets.multiLaser;
                specialLaser = Assets.hadoken;
                break;
            case 5:
                increaseMaxHealth(2);
                attack += 2;
                defense += 2;
                break;
        }
    }
}


