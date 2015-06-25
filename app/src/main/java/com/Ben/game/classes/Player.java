package com.Ben.game.classes;

import java.util.ArrayList;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Player {

    private static ArrayList<PlayerShip> party;
    //private Tile[][] grid;
    private static int volts;

    public Player(){
        volts = 1000;
        party = new ArrayList<PlayerShip>();

        for(int i = 0; i < 4; i++){          // add the test ships
            PlayerShip ship = new testShip();
            party.add(ship);
            Grid.grid[1][i].setShip(ship);
        }
    }

    public static void addShip(PlayerShip ship){
        party.add(ship);
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
