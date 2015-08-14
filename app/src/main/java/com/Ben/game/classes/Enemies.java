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
    }

    public static void setEnemies(ArrayList<EnemyShip> e) { enemies = e; }

    public static ArrayList<EnemyShip> getEnemies(){
        return enemies;
    }

    public static void attack(){
        for(EnemyShip attacker : enemies) {
            if (!attacker.isDead()) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                attacker.move();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
