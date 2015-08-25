package com.Ben.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import com.Ben.framework.util.InputHandler;
import com.Ben.framework.util.LevelLoader;
import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.EnemyShip;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.PlayerShip;
import com.Ben.game.classes.Ship;
import com.Ben.game.classes.Tile;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class MovementState extends State {
    private Ship selectedShip;
    private static final int SHADOW_COLOR = 0xAA00FF00;
    private int textPosition = 0;

    public MovementState(){}

    public void init(){
        Player.resetActivated();
        //THE BEST BUG TO EVER BUG
        /*for(EnemyShip e : Enemies.getEnemies()){
            e.getTile().setShip(e);
        }*/
        Assets.setSolidColor(Assets.attackShadow, SHADOW_COLOR);
        Assets.setSolidColor(Assets.defenseShadow, SHADOW_COLOR);
        Assets.setSolidColor(Assets.moneyShadow, SHADOW_COLOR);
        Assets.playSound(Assets.movementID, 0.2f);
    }

    public void update(float delta){
        for(Ship s : Player.getParty()) s.update();
        for(Ship s : Enemies.getEnemies()) s.update();
        Renderer.updateBackground(delta);
        if (textPosition < 80) { textPosition += 12; }
    }

    public void render(Painter g){
        Renderer.renderBackground(g);
        if(selectedShip != null && !selectedShip.isActivated()) {
            //g.drawImage(Assets.attackShadow, selectedShip.getTile().x_coordinate, selectedShip.getTile().y_coordinate, 75, 95);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (Math.abs(Grid.grid[i][j].getPositionX() - selectedShip.getPositionX()) + Math.abs(Grid.grid[i][j].getPositionY() - selectedShip.getPositionY()) <= 1) {
                        g.drawImage(Assets.greenDot, Grid.grid[i][j].x_coordinate + 20, Grid.grid[i][j].y_coordinate + 40, 10, 10);
                    }
                }
            }
        }
        g.setColor(Color.WHITE);
        g.setFont(Assets.tf, 20);
        g.drawString("movement phase", textPosition, 15);
        g.drawString("level " + Player.currentLevel + "/" + LevelLoader.getNumberOfLevels(), 550, 15);
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
                //manage currently selected ship invariants.
                if (selectedShip != null) { selectedShip.setSelected(false); }
                selectedShip = pressed.getShip();
                pressed.getShip().setSelected(true);
            }
            else{
                if(selectedShip != null){
                    doMove(selectedShip.getTile(), pressed);
                }
            }
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
                cleanup();
                setCurrentState(new AttackState());
            }
            return true;
        }
        else return false;
    }

    @Override
    public void cleanup() {
        if (selectedShip != null) { selectedShip.setSelected(false); }
    }
}
