package com.Ben.game.state;

import android.view.MotionEvent;
import com.Ben.framework.util.Painter;
import com.Ben.game.classes.Tile;
import com.Ben.simpleandroidgdf.GameMainActivity;

public abstract class State {
	public void setCurrentState(State newState) {
		GameMainActivity.sGame.setCurrentState(newState);
	}

	public abstract void init();

	public abstract void update(float delta);

	public abstract void render(Painter g);

	public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);

	protected Tile touchInGrid(Tile[][] grid, int x, int y){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 4; j++){
				if(grid[i][j].wasPressed(x,y)) return grid[i][j];
			}
		}

		for(int i = 4; i < 7; i++){
			for(int j = 0; j < 4; j++){
				if(grid[i][j].wasPressed(x,y)) return grid[i][j];
			}
		}

		return null;
	}

}