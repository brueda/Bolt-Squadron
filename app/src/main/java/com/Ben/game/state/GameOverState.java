package com.Ben.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.Player;
import com.Ben.simpleandroidgdf.Assets;
import com.Ben.simpleandroidgdf.GameMainActivity;

/**
 * Created by Benjamin on 6/5/2015.
 */
public class GameOverState extends State {

    public String message;
    @Override
    public void init(){
        message = "Game Over";
        if(Player.getScore() > GameMainActivity.getHighScore()){
            GameMainActivity.setHighScore(Player.getScore());
            message = "High Score";
        }
        if(Player.currentLevel == 21){
            message = "You Win!";
        }
    }

    @Override
    public void update(float delta) {
        Renderer.updateBackground(delta);
    }

    @Override
    public void render(Painter g) {
        Renderer.renderBackground(g);
        g.setFont(Assets.tf, 50);
        g.setColor(Color.YELLOW);
        g.drawString(message,225,150);
        g.setFont(Assets.tf, 30);
        g.setColor(Color.WHITE);
        g.drawString("Score:   " + Player.getScore(),275,300);
    }

    @Override
    public boolean onTouch(int e, int scaledX, int scaledY) {
        setCurrentState(new MenuState());
        return true;
    }
}
