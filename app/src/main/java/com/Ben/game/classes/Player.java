package com.Ben.game.classes;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Player {

    private static ArrayList<PlayerShip> party;
    private static int volts;
    private static int score;
    public static AttackShip attackBuy;
    public static DefenseShip defenseBuy;
    public static MoneyShip moneyBuy;
    public static int currentLevel;
    public static int partySize;
    public static ArrayList<PlayerShip> toAddList;

    public Player(){
        volts = 800;
        score = 0;
        party = new ArrayList<PlayerShip>();
        toAddList = new ArrayList<PlayerShip>();
        attackBuy = new AttackShip();
        defenseBuy = new DefenseShip();
        moneyBuy = new MoneyShip();
        partySize = 0;
        currentLevel = 1;

        addShip(new MoneyShip());

    }

    public static void addShip(PlayerShip ship){
        toAddList.add(ship);
        partySize++;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                if(Grid.grid[i][j].getShip() == null){
                    Grid.grid[i][j].setShip(ship);
                    return;
                }
            }
        }
    }

    public static void removeShip(PlayerShip ship){
        ship.setDead(true);
        partySize--;
    }

    public static int getVolts(){
        return volts;
    }

    public static int getScore(){
        return score;
    }

    public static void increaseScore(int amount){
        score += amount;
    }

    public static void setVolts(int _volts){
        volts = _volts;
    }

    public static ArrayList<PlayerShip> getParty(){
        return party;
    }

    public static boolean allShipsActivated(){
        boolean all = true;
        for(Ship s : party){
            if(!s.isActivated() && !s.isDead()) all = false;
        }
        return all;
    }

    public static void increaseVolts(int amount){
        volts += amount;
        increaseScore(amount);
    }

    public static void resetActivated(){
        for(Ship s : party){
            s.setActivated(false);
        }
    }

    public static boolean isDefeated(){
        boolean defeated = true;
        for(Ship s : party){
            if(!s.isDead()) defeated = false;
        }
        return defeated;
    }
}
