package com.Ben.game.classes;

import com.Ben.framework.util.Painter;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 6/7/2015.
 */
public abstract class PlayerShip extends Ship {
    public PlayerShip(){
        super();
    }

    public void update(){}

    public void render(Painter g){
        if(!dead)
            g.drawImage(Assets.testShip, currentTile.x_coordinate, currentTile.y_coordinate, 65, 85);
    }
}
