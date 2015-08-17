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
        descriptions[0] = "*resource generator.*+25 volts for aligned attacks";
        descriptions[1] = "+1 HP.+15 volts for aligned attacks";
        descriptions[2] = "+1 HP.*+40 volts for kill shots";
        descriptions[3] = "+1 HP.+800 volts when destroyed";
        descriptions[4] = "+1 HP.+60 volts for all attacks";
        descriptions[5] = "+1 HP.";
        costs[0] = 150;
        costs[1] = 100;
        costs[2] = 150;
        costs[3] = 200;
        costs[4] = 300;
        costs[5] = 25;
    }

    @Override
    public void fire(Ship target){
        super.fire(target);
        if(upgradeLevel >= 2 && target.isDead()){
            Player.setVolts(Player.getVolts() + 40);
        }
        if(upgradeLevel >= 4){
            Player.setVolts(Player.getVolts() + 60);
        }
        if(target.getPositionY() == positionY){
            Player.setVolts(Player.getVolts() + 25);
            if(upgradeLevel >= 1){
                Player.setVolts(Player.getVolts() + 15);
            }
        }
    }

    @Override
    public void hit(int power){
        super.hit(power);
        if(dead){
            if(upgradeLevel >= 3) Player.setVolts(Player.getVolts() + 800);
        }
    }

    @Override
    public void levelUp(){
        super.levelUp();
        increaseMaxHealth(1);
    }
}
