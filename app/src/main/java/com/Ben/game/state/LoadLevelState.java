package com.Ben.game.state;

import com.Ben.framework.util.LevelLoader;
import com.Ben.framework.util.Painter;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.EnemyShip;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.Player;

/**
 * Created by Benjamin on 7/10/2015.
 */
public class LoadLevelState extends State {
    @Override
    public void init() {
    }

    @Override
    public void update(float delta) {
        Enemies.setEnemies(LevelLoader.getEnemies(Player.currentLevel));
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
