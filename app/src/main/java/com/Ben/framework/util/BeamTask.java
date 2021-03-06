package com.Ben.framework.util;

import android.graphics.Bitmap;

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
    private Bitmap image;
    private final int TRAVEL = 20;

    public BeamTask(){super();}

    public void initialize(PlayerShip src, Ship target){
        x_coordinate = src.getTile().x_coordinate;
        y_coordinate = src.getTile().y_coordinate;
        image = (src.isDead() ? src.getShipImage() : Assets.beam);
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
        if(image == Assets.beam) {   // column attack
            g.drawImage(image, x_coordinate, y_coordinate);
        }
        else{   // kamikaze attack
            g.drawImage(image, x_coordinate, y_coordinate, 65, 85);
        }
    }


}
