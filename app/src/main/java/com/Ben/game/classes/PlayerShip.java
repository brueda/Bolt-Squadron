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

    public void teamAttack(Enemies e){

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

        if(!activated && !dead){
            switch(state){
                case(State.MOVE):
                    g.drawImage(Assets.greenDot, x + 45, y + 10 + sway, 10, 10);
                    if(currentTile == selected)
                        g.drawImage(Assets.greenRing, x + 40, y + 5 + sway, 20, 20);
                    break;
                case(State.ATTACK):
                    g.drawImage(Assets.blueDot, x + 45, y + 10 + sway, 10, 10);
                    if(currentTile == selected)
                        g.drawImage(Assets.blueRing, x + 40, y + 5 + sway, 20, 20);
                    break;
            }
        }
        if(!dead){
            g.drawImage(Assets.testShip, x, y + sway, 65, 85);
            g.drawImage(Assets.shield, x-10, y + sway, 95, 95);
        }
    }
}
