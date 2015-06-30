package com.Ben.game.classes;

import java.util.ArrayList;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Enemies {
    private static ArrayList<EnemyShip> enemies;
    //private Tile[][] grid;


    public Enemies(){
        Tile[][] grid = Grid.grid;
        int numEnemies = 4;  // TODO: make this variable
        enemies = new ArrayList<EnemyShip>();
        EnemyShip enemy;

        /* this is just placeholder code for testing */
        for(int i = 0; i < numEnemies; i++){
            enemy = new TestEnemy();
            enemies.add(enemy);
            grid[5+i%2][i].setShip(enemy);
        }
    }

    public static ArrayList<EnemyShip> getEnemies(){
        return enemies;
    }

    public static void attack(){
        for(EnemyShip attacker : enemies) {
            if (!attacker.isDead()) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                attacker.move();
                attacker.attack();
            }
        }
    }

    public static boolean areDefeated(){
        boolean defeated = true;
        for(EnemyShip e : enemies){
            if(!e.isDead()) defeated = false;
        }
        return defeated;
    }
}
