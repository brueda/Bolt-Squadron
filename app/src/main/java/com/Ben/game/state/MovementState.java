package com.Ben.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import com.Ben.framework.util.InputHandler;
import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.Ship;
import com.Ben.game.classes.Tile;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class MovementState extends State {
    private Tile selectedTile;

    public MovementState(){}

    public void init(){
        Player.resetActivated();
        selectedTile = Player.getParty().get(0).getTile();
    }

    public void update(float delta){
        for(Ship s : Player.getParty()) s.update();
        for(Ship s : Enemies.getEnemies()) s.update();
        Renderer.updateBackground(delta);
    }

    public void render(Painter g){
        Renderer.renderBackground(g);
        Renderer.renderShips(g, MOVE, selectedTile);
        Renderer.renderEnemies(g, MOVE);
    }

    public boolean onTouch(int e, int scaledX, int scaledY){

        if(e == InputHandler.TOUCHEVENT){
            Tile pressed = Grid.touchInGrid(scaledX, scaledY);
            if(pressed == null) return true;
            if(pressed.getShip() != null){
                if(pressed.getShip().getPositionX() > 3) return true;  // can't move Enemy ship
                if(pressed == selectedTile) doMove(selectedTile, pressed);  // hold position
                selectedTile = pressed;
            }
            else doMove(selectedTile, pressed);
        }
        return true;
    }

    private boolean doMove(Tile source, Tile destination){
        if(source == null || source.getShip() == null || source.getShip().isActivated()) return false;
        Ship ship = source.getShip();
        /* check if the move is valid */
        if(Math.abs(destination.getPositionY() - source.getPositionY()) + Math.abs(destination.getPositionX() - source.getPositionX()) <= 1){
            Assets.playSound(Assets.movementID, 0.3f);
            source.setShip(null);
            destination.setShip(ship);
            ship.setActivated(true);
            if(Player.allShipsActivated()){
                setCurrentState(new AttackState());
            }
            return true;
        }
        else return false;
    }
}
