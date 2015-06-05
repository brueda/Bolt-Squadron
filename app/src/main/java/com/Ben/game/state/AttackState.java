package com.Ben.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Enemy;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.Ship;
import com.Ben.game.classes.Tile;

/**
 * Created by Benjamin on 6/3/2015.
 */
public   class AttackState extends State {

    private Player player;
    private Enemies enemies;
    private Tile tileDown;
    private Tile tileUp;
    private Tile selectedTile;
    private int imageOffset;
    private boolean increasing;

    public AttackState(Player p, Enemies e){
        player = p;
        enemies = e;
    }

    public void init(){
        tileDown = null;
        tileUp = null;
        selectedTile = null;
        imageOffset = 0;
        increasing = true;
        player.resetActivated();
    }

    public void update(float delta){
        if(increasing) imageOffset += 2;
        else imageOffset -= 2;
        if(imageOffset == 60) increasing = false;
        if(imageOffset == -60) increasing = true;
    }

    public void render(Painter g){
        Renderer.renderBackground(g);
        Renderer.renderShips(g, player, imageOffset, 1);
        Renderer.renderEnemies(g, player, imageOffset);
        Renderer.renderIcons(g, selectedTile, imageOffset, 1);
    }

    public boolean onTouch(MotionEvent e, int scaledX, int scaledY){
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            tileDown = touchInGrid(player.getGrid(), scaledX, scaledY);
        }
        else if(e.getAction() == MotionEvent.ACTION_UP){
            tileUp = touchInGrid(player.getGrid(), scaledX, scaledY);
            if(tileDown == tileUp && tileDown != null){
                Ship ship = tileUp.getShip();
                if(ship == null) return true;
                if(ship.getPositionX() < 3){   // a player ship was selected
                    selectedTile = tileUp;
                }
                else{      // enemy ship was selected
                    if(selectedTile == null || selectedTile.getShip() == null || selectedTile.getShip().isActivated()) return true;
                    Enemy target = (Enemy) tileUp.getShip();
                    Ship attacker = selectedTile.getShip();
                    attacker.fire(target);
                    attacker.setActivated(true);
                    if(enemies.areDefeated()){
                        setCurrentState(new VictoryState());
                        return true;
                    }
                    if(player.allShipsActivated()){
                        enemies.attack(player);
                        if(player.isDefeated()){
                            setCurrentState(new GameOverState());
                            return true;
                        }
                        setCurrentState(new MovementState(player, enemies));
                        return true;
                    }

                }
            }
        }

        return true;
    }
}
