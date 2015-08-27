package com.Ben.game.classes;

import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 7/4/2015.
 */
public class DefenseShip extends PlayerShip {

    public DefenseShip(){
        super();
        defense = 6;
        specialLaser = Assets.shieldLaser;
        shipImage[0] = Assets.defenseBlue;
        shipImage[1] = Assets.defenseOrange;
        shipImage[2] = Assets.defenseRed;
        //Index 3 is left empty for now in case of additional levels.
        shipImage[PlayerShip.SHADOW] = Assets.defenseShadow;
        shipImage[DESAT + 0] = Assets.defBlueDesat;
        shipImage[DESAT + 1] = Assets.defOrangeDesat;
        shipImage[DESAT + 2] = Assets.defRedDesat;
        descriptions[0] = "*defense specialist.*attacks against enemies in.  the same row activate shield";
        descriptions[1] = "+2 HP.+1 DEF.+1 ATK.";
        descriptions[2] = "+2 HP.*kill shots shield nearby allies";
        descriptions[3] = "+2 HP.+2 DEF.+1 ATK";
        descriptions[4] = "+2 HP.*shield recovers HP";
        descriptions[5] = "+2 HP.+2 DEF.+2 ATK";
        costs[0] = 200;
        costs[1] = 400;
        costs[2] = 600;
        costs[3] = 800;
        costs[4] = 1000;
        costs[5] = 1200;
    }

    @Override
    public void fire(Ship target){
        super.fire(target);
        if(target.getPositionY() == positionY){
            shield();
        }
        if(upgradeLevel >= 2 && target.isDead()){
            for (PlayerShip s : Player.getParty()) {
                if (Math.abs(positionY - s.getPositionY()) <= 1 &&  Math.abs(positionX - s.getPositionX()) <= 1 && !s.isDead()) {
                    s.shield();
                }
            }
        }
    }

    @Override
    public void shield(){
        super.shield();
        if(upgradeLevel >= 4){
            repair();
        }
    }

    @Override
    public void levelUp(){
        super.levelUp();
        switch(upgradeLevel){
            case 1:
                increaseMaxHealth(2);
                defense += 1;
                attack += 1;
                break;
            case 2:
                increaseMaxHealth(2);
                break;
            case 3:
                increaseMaxHealth(2);
                defense += 2;
                attack += 1;
                break;
            case 4:
                increaseMaxHealth(2);
                break;
            case 5:
                increaseMaxHealth(2);
                defense += 2;
                attack += 2;
                break;
        }
    }
}
