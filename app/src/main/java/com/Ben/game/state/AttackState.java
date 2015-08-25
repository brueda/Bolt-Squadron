package com.Ben.game.state;

import android.graphics.Color;

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
 * Created by Benjamin on 6/3/2015.
 */
public class AttackState extends State {
    private Ship selectedShip;
    private static final int SHADOW_COLOR = 0xAAFF0000;
    private int textPosition = 0;

    public AttackState(){}

    public void init() {
        Player.resetActivated();
        Assets.setSolidColor(Assets.attackShadow, SHADOW_COLOR);
        Assets.setSolidColor(Assets.defenseShadow, SHADOW_COLOR);
        Assets.setSolidColor(Assets.moneyShadow, SHADOW_COLOR);
        Assets.playSound(Assets.movementID, 0.2f);
    }

    public void update(float delta){
        for(Ship s : Player.getParty()) s.update();
        for(Ship s : Enemies.getEnemies()) s.update();
        Renderer.updateBackground(delta);
        if (textPosition < 130) { textPosition += 12; }
    }

    public void render(Painter g){
        Renderer.renderBackground(g);
        Renderer.renderShips(g, ATTACK, selectedShip);
        Renderer.renderEnemies(g, ATTACK);
        g.setColor(Color.RED);
        g.setFont(Assets.tf, 20);
        g.drawString("action phase", textPosition, 15);
        g.drawString("level " + Player.currentLevel + " / " + LevelLoader.getNumberOfLevels(), 550, 15);
    }

    public boolean onTouch(int e, int scaledX, int scaledY){
        if(e == InputHandler.TOUCHEVENT){
            Tile pressed = Grid.touchInGrid(scaledX, scaledY);
            if(pressed != null){
                Ship ship = pressed.getShip();
                if(ship == null) return true;
                if(ship.getPositionX() > 3){      // enemy ship was selected
                    EnemyShip target = (EnemyShip) pressed.getShip();
                    Ship attacker = selectedShip;
                    if(attacker == null) return true;
                    if(!attacker.isActivated() && !attacker.isDead()) {
                        attacker.fire(target);
                    }
                    resolve(attacker);
                }
                else {
                    if (selectedShip != null) { selectedShip.setSelected(false); }
                    selectedShip = pressed.getShip();
                    pressed.getShip().setSelected(true);
                }
            }
        }
        // column attack
        else if(e == InputHandler.SWIPE_RIGHT){
            PlayerShip ship = (PlayerShip) selectedShip;
            if(ship == null || ship.isDead() || ship.isActivated()) return true;
            Assets.playSound(Assets.laserID, 1.0f);
            ship.columnAttack();
            resolve(ship);
        }
        // shield
        else if(e == InputHandler.SWIPE_LEFT){
            PlayerShip ship = (PlayerShip) selectedShip;
            if(ship == null || ship.isDead() || ship.isActivated()) return true;
            ship.shield();
            resolve(ship);
        }
        // kamikaze
        else if(e == InputHandler.SWIPE_DOWN){
            PlayerShip ship = (PlayerShip) selectedShip;
            if(ship == null || ship.isDead() || ship.isActivated()) return true;
            ship.kamikaze();
            resolve(ship);
        }
        return true;
    }

    private void resolve(Ship attacker){
        attacker.setActivated(true);
        if(Player.isDefeated()){
            setCurrentState(new GameOverState());
            return;
        }
        if(Enemies.areDefeated()){
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            setCurrentState(new VictoryState());
            return;
        }
        if(Player.allShipsActivated()){
            Enemies.attack();
            if(Player.isDefeated()){
                setCurrentState(new GameOverState());
                return;
            }
            setCurrentState(new MovementState());
        }
    }

    @Override
    public void cleanup() {
        if (selectedShip != null) { selectedShip.setSelected(false); }
    }
}
