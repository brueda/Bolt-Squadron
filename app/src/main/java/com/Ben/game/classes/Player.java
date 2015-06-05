package com.Ben.game.classes;

import java.util.ArrayList;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Player {

    private ArrayList<Ship> party;
    private Tile[][] grid;
    private int volts;

    public Player(){
        volts = 1000;
        party = new ArrayList<Ship>();
        grid = new Tile[7][4];

        for(int i = 0; i < 3; i++){       // make the Tiles for player ships
            for(int j = 0; j < 4; j++){
                grid[i][j] = new Tile(i,j);
            }
        }

        for(int i = 4; i < 7; i++){       // make the Tiles for enemy ships
            for(int j = 0; j < 4; j++){
                grid[i][j] = new Tile(i,j);
            }
        }

        for(int i = 0; i < 4; i++){       // add the test ships
            Ship ship = new testShip();
            party.add(ship);
            grid[1][i].setShip(ship);
        }
    }

    public void addShip(Ship ship){
        party.add(ship);
    }

    public int getVolts(){
        return volts;
    }

    public Tile[][] getGrid(){
        return grid;
    }

    public void setVolts(int _volts){
        volts = _volts;
    }

    public ArrayList<Ship> getParty(){
        return party;
    }

    public boolean allShipsActivated(){
        boolean all = true;
        for(Ship s : party){
            if(!s.isActivated()) all = false;
        }
        return all;
    }

    public void resetActivated(){
        for(Ship s : party){
            s.setActivated(false);
        }
    }
}
