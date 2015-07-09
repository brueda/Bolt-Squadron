package com.Ben.game.state;

import com.Ben.framework.util.InputHandler;
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
    private Tile selectedTile;
    private Ship selectedShip;

    public AttackState(){}

    public void init(){
        Player.resetActivated();
        //selectedTile = Player.getParty().get(0).getTile();
        selectedShip = Player.getParty().get(0);
    }

    public void update(float delta){
        for(Ship s : Player.getParty()) s.update();
        for(Ship s : Enemies.getEnemies()) s.update();
        Renderer.updateBackground(delta);
    }

    public void render(Painter g){
        Renderer.renderBackground(g);
        Renderer.renderShips(g, ATTACK, selectedShip);
        Renderer.renderEnemies(g, State.ATTACK);
    }

    public boolean onTouch(int e, int scaledX, int scaledY){
        if(e == InputHandler.TOUCHEVENT){
            Tile pressed = Grid.touchInGrid(scaledX, scaledY);
            if(pressed != null){
                Ship ship = pressed.getShip();
                if(ship == null) return true;
                if(ship.getPositionX() > 3){      // enemy ship was selected
                    EnemyShip target = (EnemyShip) pressed.getShip();
                    //Ship attacker = selectedTile.getShip();
                    Ship attacker = selectedShip;
                    if(attacker == null) return true;
                    if(!attacker.isActivated() && !attacker.isDead()) {
                        attacker.fire(target);
                    }
                    resolve(attacker);
                }
                //else selectedTile = pressed;      // player ship was selected
                else selectedShip = pressed.getShip();
            }
        }
        else if(e == InputHandler.SWIPE_RIGHT){
            PlayerShip ship = (PlayerShip) selectedShip;
            if(ship == null || ship.isDead() || ship.isActivated()) return true;
            if(Player.getVolts() >= 75) {
                Player.setVolts(Player.getVolts() - 75);
                Assets.playSound(Assets.laserID, 1.0f);
                ship.columnAttack();
                resolve(ship);
            }
            else Assets.playSound(Assets.failID, 1.0f);
        }
        else if(e == InputHandler.SWIPE_LEFT){
            PlayerShip ship = (PlayerShip) selectedShip;
            if(ship == null || ship.isDead() || ship.isActivated()) return true;
            ship.shield();
            resolve(ship);
        }
        else if(e == InputHandler.SWIPE_UP){
            PlayerShip ship = (PlayerShip) selectedShip;
            if(ship == null || ship.isDead() || ship.isActivated()) return true;
            if(Player.getVolts() >= 150) {
                Player.setVolts(Player.getVolts() - 150);
                ship.repair();
                resolve(ship);
            }
            else Assets.playSound(Assets.failID, 1.0f);
        }
        return true;
    }

    private void resolve(Ship attacker){
        attacker.setActivated(true);
        if(Enemies.areDefeated()){
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
}
