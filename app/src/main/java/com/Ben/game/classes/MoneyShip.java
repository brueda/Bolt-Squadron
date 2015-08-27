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
        //Index 3 is left empty for now in case of additional levels
        shipImage[PlayerShip.SHADOW] = Assets.moneyShadow;
        shipImage[DESAT + 0] = Assets.monBlueDesat;
        shipImage[DESAT + 1] = Assets.monOrangeDesat;
        shipImage[DESAT + 2] = Assets.monRedDesat;
        descriptions[0] = "*resource generator.+25 volts for attacks against.  enemies in the same row";
        descriptions[1] = "+1 HP.+15 volts for attacks against.  enemies in the same row";
        descriptions[2] = "+1 HP.*+40 volts for kill shots";
        descriptions[3] = "+1 HP.+700 volts when destroyed";
        descriptions[4] = "+1 HP.+1 ATK.+60 volts for all attacks";
        descriptions[5] = "+1 HP.+1 ATK.+2 DEF";
        costs[0] = 150;
        costs[1] = 100;
        costs[2] = 150;
        costs[3] = 200;
        costs[4] = 300;
        costs[5] = 150;
    }

    @Override
    public void fire(Ship target){
        super.fire(target);
        if(upgradeLevel >= 2 && target.isDead()){
            Player.increaseVolts(40);
        }
        if(upgradeLevel >= 4){
            Player.increaseVolts(60);
        }
        if(target.getPositionY() == positionY){
            Player.increaseVolts(25);
            if(upgradeLevel >= 1){
                Player.increaseVolts(15);
            }
        }
    }

    @Override
    public void hit(int power){
        super.hit(power);
        if(dead){
            if(upgradeLevel >= 3) Player.increaseVolts(700);
        }
    }

    @Override
    public void levelUp(){
        super.levelUp();
        if(upgradeLevel == 4) attack += 1;
        if(upgradeLevel == 5){
            attack += 1;
            defense += 2;
        }
        increaseMaxHealth(1);
    }
}
