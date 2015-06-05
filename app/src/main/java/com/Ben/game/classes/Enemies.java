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
        Enemy enemy;

        /* this is just placeholder code for testing */
        for(int i = 0; i < numEnemies; i++){
            enemy = new testEnemy();
            enemies.add(enemy);
            grid[5+i%2][i].setShip(enemy);
        }
    }

    public void attack(Player p){
        for(Enemy attacker : enemies) {
            if (!attacker.isDead()) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                attacker.attack(p);
            }
        }
    }

    public boolean areDefeated(){
        boolean defeated = true;
        for(Enemy e : enemies){
            if(!e.isDead()) defeated = false;
        }
        return defeated;
    }
}
