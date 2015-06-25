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

/**
 * Created by Benjamin on 6/3/2015.
 */
public class AttackState extends State {

    private Player player;
    private Enemies enemies;
    private Tile selectedTile;

    public AttackState(){}

    public void init(){
        Player.resetActivated();
        selectedTile = Player.getParty().get(0).getTile();
    }

    public void update(float delta){
        for(Ship s : Player.getParty()) s.update();
        for(Ship s : Enemies.getEnemies()) s.update();
    }

    public void render(Painter g){
        Renderer.renderBackground(g);
        Renderer.renderShips(g, ATTACK, selectedTile);
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
                    Ship attacker = selectedTile.getShip();
                    if(!attacker.isActivated() && !attacker.isDead()) {
                        attacker.fire(target);
                    }
                    resolve(attacker);
                }
                else selectedTile = pressed;      // player ship was selected
            }
        }
        else if(e == InputHandler.SWIPE_RIGHT){
            PlayerShip ship = (PlayerShip) selectedTile.getShip();
            if(ship.isDead() || ship.isActivated()) return true;
            ship.teamAttack(enemies);
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
            Enemies.attack(player);
            if(Player.isDefeated()){
                setCurrentState(new GameOverState());
                return;
            }
            setCurrentState(new MovementState());
        }
    }
}
