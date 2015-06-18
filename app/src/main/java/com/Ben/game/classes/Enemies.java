package com.Ben.game.classes;

import java.util.ArrayList;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Enemies {
    private ArrayList<EnemyShip> enemies;
    private Player player;
    //private Tile[][] grid;


    public Enemies(Player p){
        player = p;
        Tile[][] grid = player.getGrid();
        int numEnemies = 4;  // TODO: make this variable
        enemies = new ArrayList<EnemyShip>();
        EnemyShip enemy;

        /* this is just placeholder code for testing */
        for(int i = 0; i < numEnemies; i++){
            enemy = new testEnemy();
            enemies.add(enemy);
            grid[5+i%2][i].setShip(enemy);
        }
    }

    public ArrayList<EnemyShip> getEnemies(){
        return enemies;
    }

    public void attack(Player p){
        for(EnemyShip attacker : enemies) {
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
        for(EnemyShip e : enemies){
            if(!e.isDead()) defeated = false;
        }
        return defeated;
    }
}
