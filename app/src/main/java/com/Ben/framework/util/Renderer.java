package com.Ben.framework.util;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.Ship;
import com.Ben.game.classes.Tile;
import com.Ben.game.state.State;
import com.Ben.simpleandroidgdf.Assets;
import com.Ben.simpleandroidgdf.R;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Renderer {

    //milliseconds to pass before pixel shift. Lower is faster.
    static final float _backgroundSpeed = 15.0f;
    static float _currentTime = 0;
    static int _backgroundPosition = 0;

    public static void updateBackground(float delta) {
        _currentTime += delta * 1000.0f;
        int numberOfShifts = (int)Math.floor(_currentTime / _backgroundSpeed);
        _backgroundPosition = (_backgroundPosition + numberOfShifts) % 255;
        _currentTime %= _backgroundSpeed;
    }

    public static void renderBackground(Painter g){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 2; j++){
                g.drawImage(Assets.background, (i * 256) - _backgroundPosition, (j * 256), 256, 256);
            }
        }
    }

    public static void renderShips(Painter g, int state, Tile selected){
        for(Ship s : Player.getParty()){
            s.render(g, state, selected);
        }
        g.setFont(Assets.tf, 20f);
        if(selected.getShip() != null && !selected.getShip().isDead()){
            g.setColor(Color.RED);
            g.drawString("ATK: " + selected.getShip().getAttack(), 50, 443);
            g.setColor(Color.CYAN);
            g.drawString("DEF: " + selected.getShip().getDefense(), 150, 443);
        }
        g.setColor(Color.YELLOW);
        g.drawString("VOLTS: " + Player.getVolts(), 250, 443);
    }

    public static void renderEnemies(Painter g, int state){
        for(Ship s : Enemies.getEnemies()){
            s.render(g, state, null);
        }
    }
}
