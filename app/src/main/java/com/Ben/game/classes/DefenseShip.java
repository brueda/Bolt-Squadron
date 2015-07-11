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
        descriptions[1] = "+4 HP.+1 DEF.+1 ATK.";
        descriptions[2] = "+4 HP.+1 DEF.*repair cost is refunded.*repair activates shield";
        descriptions[3] = "+4 HP.+1 DEF.+1 ATK";
        descriptions[4] = "+4 HP.+1 DEF.*shields adjacent allies";
        descriptions[5] = "+4 HP.+1 DEF.+1 ATK";
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
    }

    @Override
    public void repair(){
        super.repair();
        if(upgradeLevel >= 2) {
            shielded = true;
            Player.setVolts(Player.getVolts() + 100);
        }
    }

    @Override
    public void shield(){
        super.shield();
        if(upgradeLevel >= 4){
            for(PlayerShip s : Player.getParty()){
                if(Math.abs(positionY - s.getPositionY()) + Math.abs(positionX - s.getPositionX()) <= 1){
                    s.shielded = true;
                }
            }
        }
    }

    @Override
    public void levelUp(){
        super.levelUp();
        switch(upgradeLevel){
            case 1:
                increaseMaxHealth(4);
                defense += 1;
                attack += 1;
                break;
            case 2:
                increaseMaxHealth(4);
                defense += 1;
                break;
            case 3:
                increaseMaxHealth(4);
                defense += 1;
                attack += 1;
                break;
            case 4:
                increaseMaxHealth(4);
                defense += 1;
                break;
            case 5:
                increaseMaxHealth(4);
                defense += 1;
                attack += 1;
                break;
        }
    }
}
