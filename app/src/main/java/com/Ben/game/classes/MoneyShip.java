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
        descriptions[1] = "+3 HP.+25 volts for aligned attacks";
        descriptions[2] = "+3 HP.+100 volts for kill shots";
        descriptions[3] = "+3 HP.+25 volts for aligned attacks";
        descriptions[4] = "+3 HP.+650 volts when destroyed";
        descriptions[5] = "+3 HP.+900 volts when destroyed";
        int cost = 150;
        for(int i = 0; i < 6; i++){
            costs[i] = cost;
            cost += i * 50;
        }
    }

    @Override
    public void fire(Ship target){
        super.fire(target);
        if(target.getPositionY() == positionY){
            Player.setVolts(Player.getVolts() + 50);
            if(upgradeLevel >= 1){
                Player.setVolts(Player.getVolts() + 25);
                if(upgradeLevel >= 3){
                    Player.setVolts(Player.getVolts() + 25);
                }
            }
        }
    }

    @Override
    public void hit(int power){
        super.hit(power);
        if(dead){
            if(upgradeLevel == 4) Player.setVolts(Player.getVolts() + 650);
            if(upgradeLevel == 5) Player.setVolts(Player.getVolts() + 900);
        }
    }

    @Override
    public void levelUp(){
        super.levelUp();
        increaseMaxHealth(3);
    }
}
