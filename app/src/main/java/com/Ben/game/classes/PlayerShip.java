package com.Ben.game.classes;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.RandomNumberGenerator;
import com.Ben.game.state.State;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 6/7/2015.
 */
public abstract class PlayerShip extends Ship {
    private int offset;
    private boolean increasing;
    public PlayerShip(){
        super();
        offset = RandomNumberGenerator.getRandInt(200);
        increasing = true;
    }

    public void superAttack(Enemies e){

    }

    public void update(){
        if(increasing) offset++;
        else offset--;
        if(offset == 200) increasing = false;
        if(offset == 0) increasing = true;
    }

    public void render(Painter g, int state, Tile selected){
        int x = currentTile.x_coordinate;
        int y = currentTile.y_coordinate;
        int sway = (offset - 100)/20;
        if(!dead){
            g.drawImage(Assets.testShip, x, y + sway, 65, 85);
        }
        if(!activated){
            switch(state){
                case(State.MOVE):
                    g.drawImage(Assets.yellowDot, x + 45, y + 10 + sway, 10, 10);
                    if(currentTile == selected)
                        g.drawImage(Assets.bolt, x + 42, y + sway, 18, 27);
                    break;
                case(State.ATTACK):
                    g.drawImage(Assets.redDot, x + 45, y + 10 + sway, 10, 10);
                    if(currentTile == selected)
                        g.drawImage(Assets.crosshair, x + 42, y + sway, 25, 25);
                    break;
            }
        }
    }
}
