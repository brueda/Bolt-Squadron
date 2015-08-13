package com.Ben.game.classes;

import java.util.ArrayList;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Player {

    private static ArrayList<PlayerShip> party;
    private static int partySize;
    //private Tile[][] grid;
    private static int volts;
    public static AttackShip attackBuy;
    public static DefenseShip defenseBuy;
    public static MoneyShip moneyBuy;
    public static int currentLevel = 1;
    public static int totalLevels = 1;

    public Player(){
        volts = 500;
        party = new ArrayList<PlayerShip>();
        attackBuy = new AttackShip();
        defenseBuy = new DefenseShip();
        moneyBuy = new MoneyShip();
        partySize = 0;

        for(int i = 0; i < 1; i++){          // add the test ships
            PlayerShip ship = new MoneyShip();
            addShip(ship);
        }
    }

    public static void addShip(PlayerShip ship){
        party.add(ship);
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
