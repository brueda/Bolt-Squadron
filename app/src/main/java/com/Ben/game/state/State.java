package com.Ben.game.state;

import android.view.MotionEvent;
import com.Ben.framework.util.Painter;
import com.Ben.game.classes.Tile;
import com.Ben.simpleandroidgdf.GameMainActivity;

public abstract class State {
	public static final int MOVE = 0;
	public static final int ATTACK = 1;
	public static final int CHECK = 2;

	public void setCurrentState(State newState) {
		GameMainActivity.sGame.setCurrentState(newState);
	}

	public abstract void init();

	public abstract void update(float delta);

	public abstract void render(Painter g);

	public abstract boolean onTouch(int event, int scaledX, int scaledY);

}