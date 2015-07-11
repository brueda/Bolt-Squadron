package com.Ben.game.state;

import com.Ben.framework.util.Painter;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.EnemyShip;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.TestEnemy;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 7/10/2015.
 */
public class LoadLevelState extends State {
    @Override
    public void init() {
    }

    @Override
    public void update(float delta) {
        EnemyShip enemy;
        for(int i = 0; i < 4; i++){
            enemy = new TestEnemy();
            Enemies.getEnemies().add(enemy);
            Grid.grid[5+i%2][i].setShip(enemy);
        }
        setCurrentState(new MovementState());
    }

    @Override
    public void render(Painter g) {
    }

    @Override
    public boolean onTouch(int e, int scaledX, int scaledY) {
        return false;
    }
}
