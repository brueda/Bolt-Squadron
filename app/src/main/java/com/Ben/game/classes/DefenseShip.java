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
        descriptions[0] = "*defense specialist.*aligned attacks activate shield";
        descriptions[1] = "battle-hardened team leader. +2 ATK +2 DEF";
        descriptions[2] = "Extra hit for each attack";
        int cost = 200;
        for(int i = 0; i < 6; i++){
            costs[i] = cost;
            cost += 100;
        }
    }

    @Override
    public void fire(Ship target){
        super.fire(target);
        if(target.getPositionY() == positionY){
            shielded = true;
        }
        if(upgradeLevel >= 3){
           // target.setDefense(target.getDefense() - 2);
        }
    }
}
