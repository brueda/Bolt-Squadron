package com.Ben.framework.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

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
    private int distance;
    private int direction;
    private float angle;
    private Bitmap myProjectile;
    private final int TRAVEL = 20;

    public ProjectileTask(){
        super();
    }

    public void initialize(Ship src, Ship dest, int power, boolean killShot, Bitmap image){
        x_coordinate = src.getTile().x_coordinate;
        y_coordinate = src.getTile().y_coordinate;
        destination_x = dest.getTile().x_coordinate;
        destination_y = dest.getTile().y_coordinate;
        vertical = y_coordinate - destination_y;
        distance = Math.abs(x_coordinate - destination_x);

        int deltaY = destination_y - y_coordinate;
        int deltaX = destination_x - x_coordinate;
        direction = (deltaX > 0 ? 1 : 0);
        angle = (float)(Math.atan2(deltaY, deltaX) * 180 / Math.PI);
        Matrix mat = new Matrix();
        mat.setRotate(angle, image.getWidth()/2, image.getHeight()/2);
        myProjectile = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), mat, false);

        if (killShot) {
            DestroyTask impact = new DestroyTask();
            impact.initialize(dest);
            attachChild(impact);
        }
    }

    public void update(long delta, Painter g){
        if(direction == 1){
            x_coordinate += TRAVEL;
            if(x_coordinate >= destination_x){finishTask();}
        }
        else{
            x_coordinate -= TRAVEL;
            if(x_coordinate <= destination_x){finishTask();}
        }
        y_coordinate -= vertical / (distance / TRAVEL);
        g.drawImage(myProjectile, x_coordinate, y_coordinate);
    }
}
