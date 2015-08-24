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
            g.drawString("TIP:   in the action phase,",350,200);
            g.drawString("swipe left to use shield",350,240);
            g.drawString("swipe right for team attack",350,280);
            g.drawString("swipe down to use ram attack",350,320);
        }
        if(Player.currentLevel == 2){
            g.setFont(Assets.tf, 18);
            g.setColor(Color.WHITE);
            g.drawString("TIP:   ships closer to the enemy", 350, 200);
            g.drawString("are more likely to be targeted.",350,240);
            g.drawString("try to put shielded ships on",350,280);
            g.drawString("the front lines.",350,320);
        }
        if(Player.currentLevel == 3){
            g.setFont(Assets.tf, 18);
            g.setColor(Color.WHITE);
            g.drawString("TIP:   use a ship's special attack", 350, 200);
            g.drawString("by attacking enemies directly", 350, 240);
            g.drawString("across from it", 350, 280);
        }
        if(Player.currentLevel == 4){
            g.setFont(Assets.tf, 18);
            g.setColor(Color.WHITE);
            g.drawString("TIP:   the last enemy left", 350, 200);
            g.drawString("in a level will use more", 350, 240);
            g.drawString("powerful attacks", 350, 280);
        }
    }

    @Override
    public boolean onTouch(int e, int scaledX, int scaledY) {
        ++Player.currentLevel;
        Player.increaseScore(500);
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
