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
        descriptions[1] = "+1 HP.+40 volts for aligned attacks";
        descriptions[2] = "+1 HP.+700 volts when destroyed";
        descriptions[3] = "+1 HP.+1000 volts when destroyed";
        descriptions[4] = "+1 HP.+200 volts for aligned attacks";
        descriptions[5] = "+1 HP.";
        costs[0] = 150;
        costs[1] = 150;
        costs[2] = 200;
        costs[3] = 200;
        costs[4] = 500;
        costs[5] = 0;
    }

    @Override
    public void fire(Ship target){
        super.fire(target);
        if(target.getPositionY() == positionY){
            Player.setVolts(Player.getVolts() + 25);
            if(upgradeLevel >= 1){
                Player.setVolts(Player.getVolts() + 15);
            }
            if(upgradeLevel >= 4){
                Player.setVolts(Player.getVolts() + 160);
            }
        }
    }

    @Override
    public void hit(int power){
        super.hit(power);
        if(dead){
            if(upgradeLevel >= 2) Player.setVolts(Player.getVolts() + 700);
            if(upgradeLevel >= 3) Player.setVolts(Player.getVolts() + 300);
        }
    }

    @Override
    public void levelUp(){
        super.levelUp();
        increaseMaxHealth(1);
    }
}
