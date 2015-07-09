package com.Ben.game.classes;

import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 7/7/2015.
 */
public class MoneyShip extends PlayerShip {

    public MoneyShip(){
        super();
        specialLaser = Assets.moneyLaser;
        shipImage[0] = Assets.moneyBlue;
        shipImage[1] = Assets.moneyOrange;
        shipImage[2] = Assets.moneyRed;
        descriptions[0] = "*resource generator.*aligned attacks increase volts";
        descriptions[1] = "battle-hardened team leader. +2 ATK +2 DEF";
        descriptions[2] = "Extra hit for each attack";
        int cost = 150;
        for(int i = 0; i < 6; i++){
            costs[i] = cost;
            cost += 100;
        }
    }

    @Override
    public void fire(Ship target){
        if(target.getPositionY() == positionY){
            Player.setVolts(Player.getVolts() + 50);
        }
    }
}
