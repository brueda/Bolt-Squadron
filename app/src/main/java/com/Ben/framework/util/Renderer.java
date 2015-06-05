package com.Ben.framework.util;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.Ship;
import com.Ben.game.classes.Tile;
import com.Ben.simpleandroidgdf.Assets;
import com.Ben.simpleandroidgdf.R;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class Renderer {

    public static void renderBackground(Painter g){
        //g.drawImage(Assets.background,0,0,256,256);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){
                g.drawImage(Assets.background, i * 256, j * 256, 256, 256);
            }
        }
    }

    public static void renderShips(Painter g, Player p, int offset, int state){
        Tile[][] grid = p.getGrid();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                Ship ship = grid[i][j].getShip();
                if(ship != null && !ship.isDead()){
                    int x = grid[i][j].x_coordinate;
                    int y = grid[i][j].y_coordinate;
                    g.drawImage(Assets.testShip,x,y+(j%2==0?1:-1)*offset/15,65,85);  // a normal person probably shouldn't be able to understand what this line is doing
                    g.setFont(Typeface.SANS_SERIF, 15);
                    g.setColor(Color.GREEN);
                    g.drawString("" + ship.getHealth(),x-10,y+15+(j%2==0?1:-1)*offset/15);
                    if(!ship.isActivated()){
                        if(state == 0){     // movement state, yellow dot
                            g.drawImage(Assets.yellowDot,x+45,y+10+(j%2==0?1:-1)*offset/15,10,10);
                        }
                        else{               // attack state, red dot
                            g.drawImage(Assets.redDot,x+45,y+10+(j%2==0?1:-1)*offset/15,10,10);
                        }
                    }
                    //g.drawImage(Assets.shield,x,y-20+offset/3,80,125);
                }
            }
        }
    }

    public static void renderEnemies(Painter g, Player p, int offset){
        Tile[][] grid = p.getGrid();
        for(int i = 4; i < 7; i++){
            for(int j = 0; j < 4; j++){
                Ship ship = grid[i][j].getShip();
                if(ship != null && !ship.isDead()){
                    int x = grid[i][j].x_coordinate;
                    int y = grid[i][j].y_coordinate;
                    g.drawImage(Assets.UFO,x,y+(j%2==0?-1:1)*offset/15,80,80);
                    g.setFont(Typeface.SANS_SERIF, 15);
                    g.setColor(Color.GREEN);
                    g.drawString("" + ship.getHealth(), x - 10, y + 15 + (j % 2 == 0 ? -1 : 1) * offset / 15);
                }
            }
        }

    }

    public static void renderIcons(Painter g, Tile selected, int offset, int state){    // state is 0 for movement and 1 for attack
        if(selected != null && selected.getShip() != null && !selected.getShip().isActivated() && !selected.getShip().isDead()) {
            int x = selected.x_coordinate;
            int y = selected.y_coordinate;
            if(state == 0) {   // movement state, bolt
                g.drawImage(Assets.bolt, x + 42, y + (selected.getPositionY() % 2 == 0 ? 1 : -1) * offset / 15, 18, 27);
            }
            else{              // attack state, crosshair
                g.drawImage(Assets.crosshair, x + 40, y + (selected.getPositionY() % 2 == 0 ? 1 : -1) * offset / 15, 25, 25);
            }
        }
    }
}
