package com.Ben.game.classes;

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
        descriptions[1] = "+5 HP.+2 ATK.+2 DEF";
        descriptions[2] = "+";
        int cost = 200;
        for(int i = 0; i < 6; i++){
            costs[i] = cost;
            cost += 100;
        }
    }

    @Override
    public void fire(Ship target){
        if(target.getPositionY() == positionY){
            target.hit(attack);   // double shot if enemy is aligned
        }
        if(upgradeLevel >= 4) target.hit(attack);
        super.fire(target);
        if(upgradeLevel >= 2){
            target.setDefense(target.getDefense() - 1);
        }
    }
}
