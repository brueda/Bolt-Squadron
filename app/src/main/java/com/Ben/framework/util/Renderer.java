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

    public static void renderBackground(Painter g){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){
                g.drawImage(Assets.background, i * 256, j * 256, 256, 256);
            }
        }
    }

    public static void renderShips(Painter g, int state, Tile selected){
        for(Ship s : Player.getParty()){
            s.render(g, state, selected);
        }
    }

    public static void renderEnemies(Painter g, int state){
        for(Ship s : Enemies.getEnemies()){
            s.render(g, state, null);
        }
    }
}
