package com.Ben.game.classes;

import android.util.Log;

import com.Ben.framework.util.EnemyTypes;
import com.Ben.framework.util.RandomNumberGenerator;

import java.util.ArrayList;

/**
 * Created by homedesk on 7/12/2015.
 */
public class Level {
    private String[] enemies;
    private int numEnemies;

    public Level() {
        enemies = new String[12];
        numEnemies = 0;
    }

    public void addEnemy(String enemy) {
        enemies[numEnemies] = enemy;
        ++numEnemies;
    }

    public ArrayList<EnemyShip> getEnemies() {
        //This should theoretically be handed off to the Enemies class.
        ArrayList<EnemyShip> ret = new ArrayList<EnemyShip>();
        //For each enemy we want in the level...
        for (int i = 0; i < numEnemies; ++i) {
            //Get a concrete instance of it...
            EnemyShip newShip = EnemyTypes.get(enemies[i]);
            if (newShip == null) {
                Log.d("Level", "Could not find shipType: " + enemies[i]);
                System.exit(1);
            }
            int xPosition, yPosition;
            //and generate a pseudo-random, unoccupied position for it.
            do {
                xPosition = RandomNumberGenerator.getRandIntBetween(4, 7);
                yPosition = RandomNumberGenerator.getRandInt(4);
            } while (Grid.grid[xPosition][yPosition].isOccupied());
            Grid.grid[xPosition][yPosition].setShip(newShip);
            ret.add(newShip);
        }
        return ret;
    }
}
