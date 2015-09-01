package com.Ben.framework.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.PlayerShip;
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

    public static void renderBuyInfo(Painter g, PlayerShip selected){
        g.setFont(Assets.tf, 25);
        g.drawString("BUY:",355,380);
        if(selected.getPositionX() > 3){     // buy ship
            g.drawString("SWIPE UP TO BUY:  " + selected.getCosts()[0] + " V", 370, 50);
            Bitmap image = selected.getImageArray()[0];
            g.drawImage(image, 500, 100, 98, 127);
            g.setFont(Assets.tf, 15);
            String[] description = selected.getDescriptions()[0].split("\\.");
            int offset = 250;
            for(String s : description){
                g.drawString(s, 400, offset);
                offset += 20;
            }
        }
        else{                                // upgrade ship
            if(selected.upgradeLevel >= 5) return;
            g.setColor(Color.CYAN);
            g.drawString("SWIPE UP TO UPGRADE:  " + selected.getCosts()[selected.upgradeLevel + 1] + " V", 320, 50);
            g.setColor(Color.GREEN);
            g.drawString("SWIPE DOWN TO GO GREEN:", 320, 75);
            g.drawString("+" + selected.getTotalCost() / 2 + " V", 320, 100);
            Bitmap image = selected.getImageArray()[(selected.upgradeLevel + 1) / 2];
            g.drawImage(image, 500, 100, 98, 127);
            if(selected.upgradeLevel % 2 == 0 || selected.upgradeLevel == 0){
                g.drawImage(Assets.star, 500+14, 100+51, 23, 23);
            }
            g.setFont(Assets.tf, 15);
            g.setColor(Color.CYAN);
            String[] description = selected.getDescriptions()[selected.upgradeLevel+1].split("\\.");
            int offset = 250;
            for(String s : description){
                g.drawString(s, 400, offset);
                offset += 20;
            }
        }
        g.setColor(Color.WHITE);
        g.setFont(Assets.tf, 20);
        g.drawString("SWIPE RIGHT TO BATTLE",450,443);
    }

    public static void renderBackground(Painter g){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 2; j++){
                g.drawImage(Assets.background, (i * 256) - _backgroundPosition, (j * 256), 256, 256);
            }
        }
    }

    public static void renderShips(Painter g, int state, Ship selected){
        if(Player.getParty().isEmpty()) return;
        for(Ship s : Player.getParty()){
            s.render(g, state, selected);
        }
        g.setFont(Assets.tf, 20f);
        if(selected != null && !selected.isDead()){
            g.setColor(Color.RED);
            g.drawString("ATK: " + selected.getAttack(), 50, 443);
            g.setColor(Color.CYAN);
            g.drawString("DEF: " + selected.getDefense(), 150, 443);
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
