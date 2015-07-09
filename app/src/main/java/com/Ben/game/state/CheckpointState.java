package com.Ben.game.state;

import android.graphics.Color;

import com.Ben.framework.util.InputHandler;
import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.AttackShip;
import com.Ben.game.classes.DefenseShip;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.MoneyShip;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.PlayerShip;
import com.Ben.game.classes.Tile;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 7/7/2015.
 */
public class CheckpointState extends State {
    private PlayerShip selectedShip;

    @Override
    public void init(){
        Player.resetActivated();
        selectedShip = Player.getParty().get(0);
        Grid.grid[4][3].setShip(Player.attackBuy);
        Grid.grid[5][3].setShip(Player.defenseBuy);
        Grid.grid[6][3].setShip(Player.moneyBuy);
    }

    @Override
    public void update(float delta) {
        Renderer.updateBackground(delta);
    }

    @Override
    public void render(Painter g) {
        Renderer.renderBackground(g);
        Renderer.renderShips(g, CHECK, selectedShip);
        Renderer.renderBuyInfo(g, selectedShip);
        Grid.grid[4][3].getShip().render(g, CHECK, selectedShip);
        Grid.grid[5][3].getShip().render(g,CHECK,selectedShip);
        Grid.grid[6][3].getShip().render(g,CHECK,selectedShip);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                if(Grid.grid[i][j].getShip() == null){
                    g.drawImage(Assets.greenDot, Grid.grid[i][j].x_coordinate+20, Grid.grid[i][j].y_coordinate+40, 10, 10);
                }
            }
        }
    }

    @Override
    public boolean onTouch(int e, int scaledX, int scaledY) {
        if(e == InputHandler.TOUCHEVENT){
            Tile pressed = Grid.touchInGrid(scaledX, scaledY);
            if(pressed == null) return true;
            if(pressed.getShip() != null){
                selectedShip = (PlayerShip) pressed.getShip();
            }
            if(pressed.getPositionX() < 3){
                if(selectedShip.getPositionX() < 3){
                    selectedShip.getTile().setShip(null);
                    pressed.setShip(selectedShip);
                }
            }
        }
        else if(e == InputHandler.SWIPE_UP){
            buyOrUpgrade(selectedShip);
        }
        return true;
    }

    private void buyOrUpgrade(PlayerShip s){
        int cost = 100;
        if(Player.getVolts() < cost){
            return;  // not enough money
        }
        Player.setVolts(Player.getVolts() - cost);
        if(s.getPositionX() < 3){
            s.levelUp();
        }
        else{
            if(Player.getParty().size() < 4){
                if(s.getPositionX() == 4) Player.addShip(new AttackShip());
                else if(s.getPositionX() == 5) Player.addShip(new DefenseShip());
                else Player.addShip(new MoneyShip());
            }
        }
    }
}
