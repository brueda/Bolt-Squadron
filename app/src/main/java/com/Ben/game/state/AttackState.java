package com.Ben.game.state;

import com.Ben.framework.util.InputHandler;
import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.EnemyShip;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.Ship;
import com.Ben.game.classes.Tile;

/**
 * Created by Benjamin on 6/3/2015.
 */
public   class AttackState extends State {

    private Player player;
    private Enemies enemies;
    private Tile selectedTile;
    private int imageOffset;
    private boolean increasing;

    public AttackState(Player p, Enemies e){
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
        Renderer.renderShips(g, player, imageOffset, ATTACK);
        Renderer.renderEnemies(g, enemies, imageOffset);
        Renderer.renderIcons(g, selectedTile, imageOffset, ATTACK);
    }

    public boolean onTouch(int e, int scaledX, int scaledY){
        if(e == InputHandler.TOUCHEVENT){
            Tile pressed = touchInGrid(player.getGrid(), scaledX, scaledY);
            if(pressed != null){
                Ship ship = pressed.getShip();
                if(ship == null) return true;
                if(ship.getPositionX() > 3){      // enemy ship was selected
                    EnemyShip target = (EnemyShip) pressed.getShip();
                    Ship attacker = selectedTile.getShip();
                    if(!attacker.isActivated() && !attacker.isDead()) {attacker.fire(target);}  //I feel like these checks belong somewhere else
                    resolve(attacker);
                }
                else selectedTile = pressed;      // player ship was selected
            }
        }
        return true;
    }

    private void resolve(Ship attacker){
        attacker.setActivated(true);
        if(enemies.areDefeated()){
            setCurrentState(new VictoryState());
            return;
        }
        if(player.allShipsActivated()){
            enemies.attack(player);
            if(player.isDefeated()){
                setCurrentState(new GameOverState());
                return;
            }
            setCurrentState(new MovementState(player, enemies));
        }
    }
}
