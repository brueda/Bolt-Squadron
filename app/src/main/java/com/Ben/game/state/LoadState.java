package com.Ben.game.state;

import android.view.MotionEvent;
import com.Ben.framework.util.Painter;
import com.Ben.simpleandroidgdf.Assets;

public class LoadState extends State {
	@Override
	public void init() {
		Assets.load();
	}

	@Override
	public void update(float delta) {
		setCurrentState(new MenuState());
	}

	@Override
	public void render(Painter g) {
	}

	@Override
	public boolean onTouch(int e, int scaledX, int scaledY) {
		return false;
	}
}