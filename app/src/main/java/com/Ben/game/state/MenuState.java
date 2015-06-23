package com.Ben.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;
import com.Ben.framework.util.Painter;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Player;
import com.Ben.simpleandroidgdf.Assets;

public class MenuState extends State {
	@Override
	public void init() {
	}

	@Override
	public void update(float delta) {
		Assets.playMusic("cyber-advance.mp3", true);
	}

	@Override
	public void render(Painter g) {
		g.setFont(Typeface.SANS_SERIF, 50);
		g.setColor(Color.YELLOW);
		g.drawString("Touch the Screen to Play",100,200);
	}

	@Override
	public boolean onTouch(int e, int scaledX, int scaledY) {
		Player p = new Player();
		setCurrentState(new MovementState(p, new Enemies(p)));
		return true;
	}
}