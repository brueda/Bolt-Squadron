package com.Ben.framework.util;

import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.EnemyShip;
import com.Ben.game.classes.PlayerShip;
import com.Ben.game.classes.Ship;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 6/29/2015.
 */
public class BeamTask extends Task {
    private int x_coordinate;
    private int y_coordinate;
    private final int TRAVEL = 15;

    public BeamTask(){super();}

    public void initialize(PlayerShip src, int power){
        x_coordinate = src.getTile().x_coordinate;
        y_coordinate = src.getTile().y_coordinate;
        for(EnemyShip e : Enemies.getEnemies()){
            if(e.getPositionY() == src.getPositionY()){
                e.hit(power);
                if(e.isDead()){
                    DestroyTask impact = new DestroyTask();
                    impact.initialize(e, power);
                    attachChild(impact);
                }
            }
        }
    }

    public void update(long delta, Painter g){
        x_coordinate += TRAVEL;
        if(x_coordinate >= 800){
            finishTask();
        }
        g.drawImage(Assets.beam, x_coordinate, y_coordinate);
    }


}
