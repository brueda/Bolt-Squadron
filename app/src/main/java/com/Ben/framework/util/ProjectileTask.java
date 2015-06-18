package com.Ben.framework.util;

import android.graphics.Bitmap;

import com.Ben.game.classes.Ship;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 6/17/2015.
 */
public class ProjectileTask extends Task {
    private int x_coordinate;
    private int y_coordinate;
    private int destination_x;
    private int destination_y;
    private int vertical;
    private int direction;
    private final int TRAVEL = 20;
    private Bitmap image;

    public ProjectileTask(){
        super();
    }

    public void initialize(Ship src, Ship dest, int power){
        x_coordinate = src.getTile().x_coordinate;
        y_coordinate = src.getTile().y_coordinate;
        destination_x = dest.getTile().x_coordinate;
        destination_y = dest.getTile().y_coordinate;
        vertical = y_coordinate - destination_y;
        if(destination_x > x_coordinate){
            direction = 1;              // laser is going right
            image = Assets.blueLaser;
        }
        else{
            direction = 0;              // laser is going left
            image = Assets.redLaser;
        }

        ImpactTask impact = new ImpactTask();
        impact.initialize(dest,power);
        attachChild(impact);
    }

    public void update(long delta, Painter g){
        /* the below should be replaced with some actual mathematics */
        if(direction == 1){
            x_coordinate += TRAVEL;
            if(x_coordinate >= destination_x){finishTask();}
        }
        else{
            x_coordinate -= TRAVEL;
            if(x_coordinate <= destination_x){finishTask();}
        }
        y_coordinate -= vertical/25;
        g.drawImage(image, x_coordinate, y_coordinate);
    }
}
