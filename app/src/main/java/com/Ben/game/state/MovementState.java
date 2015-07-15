package com.Ben.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import com.Ben.framework.util.InputHandler;
import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.EnemyShip;
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
    private Ship selectedShip;

    public MovementState(){}

    public void init(){
        Player.resetActivated();
        //selectedTile = Player.getParty().get(0).getTile();
        selectedShip = Player.getParty().get(0);
        //THE BEST BUG TO EVER BUG
        /*for(EnemyShip e : Enemies.getEnemies()){
            e.getTile().setShip(e);
        }*/
    }

    public void update(float delta){
        for(Ship s : Player.getParty()) s.update();
        for(Ship s : Enemies.getEnemies()) s.update();
        Renderer.updateBackground(delta);
    }

    public void render(Painter g){
        Renderer.renderBackground(g);
        if(!selectedShip.isActivated()) {
            for (int i = 0; i < 3; i++) {       // make the Tiles for player ships
                for (int j = 0; j < 4; j++) {
                    if (Math.abs(Grid.grid[i][j].getPositionX() - selectedShip.getPositionX()) + Math.abs(Grid.grid[i][j].getPositionY() - selectedShip.getPositionY()) <= 1) {
                        g.drawImage(Assets.greenDot, Grid.grid[i][j].x_coordinate + 20, Grid.grid[i][j].y_coordinate + 40, 10, 10);
                    }
                }
            }
        }
        Renderer.renderShips(g, MOVE, selectedShip);
        Renderer.renderEnemies(g, MOVE);
    }

    public boolean onTouch(int e, int scaledX, int scaledY){

        if(e == InputHandler.TOUCHEVENT){
            Tile pressed = Grid.touchInGrid(scaledX, scaledY);
            if(pressed == null) return true;
            if(pressed.getShip() != null){
                if(pressed.getShip().getPositionX() > 3) return true;  // can't move Enemy ship
                if(pressed.getShip() == selectedShip) doMove(selectedShip.getTile(), pressed);  // hold position
                selectedShip = pressed.getShip();
            }
            else doMove(selectedShip.getTile(), pressed);
        }
        return true;
    }

    private boolean doMove(Tile source, Tile destination){
        if(source == null || source.getShip() == null || source.getShip().isActivated()) return false;
        Ship ship = source.getShip();
        /* check if the move is valid */
        if(Math.abs(destination.getPositionY() - source.getPositionY()) + Math.abs(destination.getPositionX() - source.getPositionX()) <= 1){
            Assets.playSound(Assets.selectID, 0.3f);
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
