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
    private int destination_x;
    private final int TRAVEL = 25;

    public BeamTask(){super();}

    public void initialize(PlayerShip src, Ship target){
        x_coordinate = src.getTile().x_coordinate;
        y_coordinate = src.getTile().y_coordinate;
        destination_x = (target == null ? 800 : target.getTile().x_coordinate);
        if(target != null && target.isDead()){
            DestroyTask impact = new DestroyTask();
            impact.initialize(target);
            attachChild(impact);
        }
    }

    public void update(long delta, Painter g){
        x_coordinate += TRAVEL;
        if(x_coordinate >= destination_x){
            finishTask();
        }
        g.drawImage(Assets.beam, x_coordinate, y_coordinate);
    }


}
