package com.Ben.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.PlayerShip;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 6/5/2015.
 */
public class VictoryState extends State {
    @Override
    public void init(){}

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
        //Renderer.renderBackground(g);
        g.setFont(Assets.tf, 50);
        g.setColor(Color.YELLOW);
        g.drawString("Victory",400,100);
        if(Player.currentLevel == 1){
            g.setFont(Assets.tf, 18);
            g.setColor(Color.WHITE);
            g.drawString("TIP:   in the attack phase,",350,200);
            g.drawString("swipe left to use shield",350,240);
            g.drawString("swipe right for column attack",350,280);
        }
        if(Player.currentLevel == 2){
            g.setFont(Assets.tf, 18);
            g.setColor(Color.WHITE);
            g.drawString("TIP:   ships closer to the enemy",350,200);
            g.drawString("are more likely to be targeted",350,240);
        }
    }

    @Override
    public boolean onTouch(int e, int scaledX, int scaledY) {
        ++Player.currentLevel;
        // checkpoint every 2 levels
        if(Player.currentLevel % 2 != 0) {
            setCurrentState(new CleanupState(new CheckpointState()));
        }
        else{
            setCurrentState(new CleanupState(new LoadLevelState()));
        }
        return true;
    }
}
