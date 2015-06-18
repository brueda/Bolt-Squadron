package com.Ben.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import com.Ben.framework.util.InputHandler;
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
    private Tile selectedTile;
    private int imageOffset;
    private boolean increasing;

    public MovementState(Player p, Enemies e){
        player = p;
        enemies = e;
    }

    public void init(){
        selectedTile = null;
        imageOffset = 0;
        increasing = true;
        player.resetActivated();
        selectedTile = player.getParty().get(0).getTile();
    }

    public void update(float delta){
        for(Ship s : player.getParty()) s.update();
        for(Ship s : enemies.getEnemies()) s.update();
    }

    public void render(Painter g){
        Renderer.renderBackground(g);
        Renderer.renderShips(g, player, imageOffset, MOVE);
        Renderer.renderEnemies(g, enemies, imageOffset);
        Renderer.renderIcons(g, selectedTile, imageOffset, MOVE);
    }

    public boolean onTouch(int e, int scaledX, int scaledY){

        if(e == InputHandler.TOUCHEVENT){
            Tile pressed = touchInGrid(player.getGrid(), scaledX, scaledY);
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
