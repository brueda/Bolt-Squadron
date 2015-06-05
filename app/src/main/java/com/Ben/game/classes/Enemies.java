package com.Ben.game.classes;

import java.util.ArrayList;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Enemies {
    private ArrayList<Enemy> enemies;
    private Player player;
    //private Tile[][] grid;


    public Enemies(Player p){
        player = p;
        Tile[][] grid = player.getGrid();
        int numEnemies = 4;  // TODO: make this variable
        enemies = new ArrayList<Enemy>();
        //grid = new Tile[7][4];
        Enemy enemy;

//        for(int i = 4; i < 7; i++){
//            for(int j = 0; j < 4; j++){
//                grid[i][j] = new Tile(i, j);
//            }
//        }

        /* this is just placeholder code for testing */
        for(int i = 0; i < numEnemies; i++){
            enemy = new testEnemy();
            enemies.add(enemy);
            grid[5+i%2][i].setShip(enemy);
        }
    }
}
