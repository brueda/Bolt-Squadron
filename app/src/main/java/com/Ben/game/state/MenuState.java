package com.Ben.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.widget.TextView;
import android.content.res.Resources;

import com.Ben.framework.util.Painter;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.Player;
import com.Ben.simpleandroidgdf.Assets;
import com.Ben.simpleandroidgdf.GameMainActivity;

public class MenuState extends State {
	private Typeface tf;
	private TextView tv;
	@Override
	public void init() {
		tf = Typeface.createFromAsset(GameMainActivity.assets, "kenvector_future.ttf");
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
		new Grid();
		new Player();
		new Enemies();
		setCurrentState(new MovementState());
		return true;
	}
}