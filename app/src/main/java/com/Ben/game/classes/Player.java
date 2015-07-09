package com.Ben.game.classes;

import java.util.ArrayList;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Player {

    private static ArrayList<PlayerShip> party;
    //private Tile[][] grid;
    private static int volts;
    public static AttackShip attackBuy;
    public static DefenseShip defenseBuy;
    public static MoneyShip moneyBuy;

    public Player(){
        volts = 1000;
        party = new ArrayList<PlayerShip>();
        attackBuy = new AttackShip();
        defenseBuy = new DefenseShip();
        moneyBuy = new MoneyShip();

        for(int i = 0; i < 3; i++){          // add the test ships
            PlayerShip ship = new AttackShip();
            party.add(ship);
            Grid.grid[1][i].setShip(ship);
        }
    }

    public static void addShip(PlayerShip ship){
        party.add(ship);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                if(Grid.grid[i][j].getShip() == null){
                    Grid.grid[i][j].setShip(ship);
                }
            }
        }
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
