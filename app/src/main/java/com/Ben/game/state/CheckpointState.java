package com.Ben.game.state;

import android.graphics.Color;

import com.Ben.framework.util.InputHandler;
import com.Ben.framework.util.LevelLoader;
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

import java.util.Iterator;

/**
 * Created by Benjamin on 7/7/2015.
 */
public class CheckpointState extends State {
    private PlayerShip selectedShip;
    private static final int SHADOW_COLOR = 0xAA000FFF;

    @Override
    public void init(){
        Player.resetActivated();
        for(PlayerShip p : Player.getParty()){
            p.resetStatus();
        }
        selectedShip = Player.attackBuy;
        Grid.grid[4][3].setShip(Player.attackBuy);
        Grid.grid[5][3].setShip(Player.defenseBuy);
        Grid.grid[6][3].setShip(Player.moneyBuy);
        Assets.setSolidColor(Assets.attackShadow, SHADOW_COLOR);
        Assets.setSolidColor(Assets.defenseShadow, SHADOW_COLOR);
        Assets.setSolidColor(Assets.moneyShadow, SHADOW_COLOR);
    }

    @Override
    public void update(float delta) {
        Renderer.updateBackground(delta);
        for(PlayerShip p : Player.toAddList){
            Player.getParty().add(p);
        }
        Player.toAddList.clear();
    }

    @Override
    public void render(Painter g) {
        Renderer.renderBackground(g);
        Renderer.renderShips(g, CHECK, selectedShip);
        Renderer.renderBuyInfo(g, selectedShip);
        Grid.grid[4][3].getShip().render(g, CHECK, selectedShip);
        Grid.grid[5][3].getShip().render(g, CHECK, selectedShip);
        Grid.grid[6][3].getShip().render(g, CHECK, selectedShip);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                if(Grid.grid[i][j].getShip() == null){
                    g.drawImage(Assets.greenDot, Grid.grid[i][j].x_coordinate+20, Grid.grid[i][j].y_coordinate+40, 10, 10);
                }
            }
        }
        Renderer.renderShips(g, MOVE, selectedShip);
        Renderer.renderEnemies(g, MOVE);
        g.setColor(Color.WHITE);
        g.setFont(Assets.tf, 15);
        g.drawString("ships:  " + Player.getParty().size() + " / 4", 100, 15);
        g.drawString("level " + Player.currentLevel + "/" + LevelLoader.getNumberOfLevels(), 550, 15);
    }

    @Override
    public boolean onTouch(int e, int scaledX, int scaledY) {
        if(e == InputHandler.TOUCHEVENT){
            Tile pressed = Grid.touchInGrid(scaledX, scaledY);
            if(pressed == null) return true;
            if(pressed.getShip() != null){
                Assets.playSound(Assets.selectID, 0.3f);
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
        else if(e == InputHandler.SWIPE_RIGHT){
            if(!Player.getParty().isEmpty()){
                setCurrentState(new CleanupState(new LoadLevelState()));
            }
        }
        return true;
    }

    private void buyOrUpgrade(PlayerShip s){
        int cost;
        if(selectedShip.getPositionX() > 3){       // buy
            if(Player.getParty().size() == 4){
                Assets.playSound(Assets.failID, 1.0f);
                return;
            }
            cost = selectedShip.getCosts()[0];
            if(Player.getVolts() < cost){          // not enough money
                Assets.playSound(Assets.failID, 1.0f);
                return;
            }
            Player.setVolts(Player.getVolts() - cost);
            Assets.playSound(Assets.levelUpID, 1.0f);
            if(s.getPositionX() == 4) Player.addShip(new AttackShip());
            else if(s.getPositionX() == 5) Player.addShip(new DefenseShip());
            else Player.addShip(new MoneyShip());  // PositionX == 6
        }
        else{                                      // upgrade
            if(s.upgradeLevel == 5){
                Assets.playSound(Assets.failID, 1.0f);
                return;
            }
            cost = selectedShip.getCosts()[selectedShip.upgradeLevel+1];
            if(Player.getVolts() < cost){
                Assets.playSound(Assets.failID, 1.0f);
                return;
            }
            Player.setVolts(Player.getVolts() - cost);
            Assets.playSound(Assets.levelUpID, 1.0f);
            s.levelUp();
        }
    }
}
