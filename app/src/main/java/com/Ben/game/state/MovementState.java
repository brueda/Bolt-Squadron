package com.Ben.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.Ship;
import com.Ben.game.classes.Tile;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class MovementState extends State {

    private Player player;
    private Enemies enemies;
    private Tile tileDown;
    private Tile tileUp;
    private Tile selectedTile;
    private int imageOffset;
    private boolean increasing;

    public MovementState(Player p, Enemies e){
        player = p;
        enemies = e;
    }

    public void init(){
        tileDown = null;
        tileUp = null;
        selectedTile = null;
        imageOffset = 0;
        increasing = true;
        player.resetActivated();
    }

    public void update(float delta){
        if(increasing) imageOffset += 2;
        else imageOffset -= 2;
        if(imageOffset == 60) increasing = false;
        if(imageOffset == -60) increasing = true;
    }

    public void render(Painter g){
        //g.setColor(Color.BLACK);
       // g.fillRect(0,0,800,450);
        Renderer.renderBackground(g);
        Renderer.renderShips(g, player, imageOffset, 0);
        Renderer.renderEnemies(g, player, imageOffset);
        Renderer.renderIcons(g, selectedTile, imageOffset, 0);
    }

    public boolean onTouch(MotionEvent e, int scaledX, int scaledY){
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            tileDown = touchInGrid(player.getGrid(), scaledX, scaledY);
        }
        else if(e.getAction() == MotionEvent.ACTION_UP){
            tileUp = touchInGrid(player.getGrid(), scaledX, scaledY);
            if(tileDown == tileUp  && tileDown != null){   // check if a grid tile was pressed
                if(tileUp.getShip() != null){
                    if(tileUp.getShip().getPositionX() > 3) return true;  // can't move an enemy ship
                    if(tileUp == selectedTile) doMove(selectedTile,tileUp);  // tap on a selected ship to hold position
                    selectedTile = tileUp;
                }
                else doMove(selectedTile, tileUp);
            }
        }
        return true;
    }

    private boolean doMove(Tile source, Tile destination){
        if(source == null || source.getShip() == null || source.getShip().isActivated()) return false;
        Ship ship = source.getShip();
        /* check if the move is valid */
        if(Math.abs(destination.getPositionY() - source.getPositionY()) + Math.abs(destination.getPositionX() - source.getPositionX()) <= 1){
            source.setShip(null);
            destination.setShip(ship);
            ship.setActivated(true);
            if(player.allShipsActivated()){
                setCurrentState(new AttackState(player, enemies));
            }
            return true;
        }
        else return false;
    }
}
