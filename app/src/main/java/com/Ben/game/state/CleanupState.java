package com.Ben.game.state;

import com.Ben.framework.util.Painter;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.EnemyShip;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.Player;
import com.Ben.game.classes.PlayerShip;
import com.Ben.game.classes.Tile;
import com.Ben.simpleandroidgdf.Assets;

import java.util.Iterator;

/**
 * Created by Benjamin on 7/10/2015.
 */
public class CleanupState extends State {
    private State next;

    public CleanupState(State nextState){
        next = nextState;
    }

    @Override
    public void init() {
    }

    @Override
    public void update(float delta) {
        // use iterators for safe removal
        Iterator<PlayerShip> it = Player.getParty().iterator();
        while(it.hasNext()){
            if(it.next().isDead()){
                it.remove();
            }
        }
        Iterator<EnemyShip> it2 = Enemies.getEnemies().iterator();
        while(it2.hasNext()){
            if(it2.next().isDead()){
                it2.remove();
            }
        }
        for(int i = 4; i < 7; i++){
            for(int j = 0; j < 4; j++){
                Grid.grid[i][j].setShip(null);
            }
        }
        setCurrentState(next);
    }

    @Override
    public void render(Painter g) {
    }

    @Override
    public boolean onTouch(int e, int scaledX, int scaledY) {
        return false;
    }
}
