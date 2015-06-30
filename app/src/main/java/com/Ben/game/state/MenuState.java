package com.Ben.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.widget.TextView;
import android.content.res.Resources;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.game.classes.Enemies;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.Player;
import com.Ben.simpleandroidgdf.Assets;
import com.Ben.simpleandroidgdf.GameMainActivity;

public class MenuState extends State {
	@Override
	public void init() {}

	@Override
	public void update(float delta) {
		Assets.playMusic("cyber-advance.mp3", true);
		Renderer.updateBackground(delta);
	}

	@Override
	public void render(Painter g) {
		Renderer.renderBackground(g);
		g.setFont(Assets.tf, 50);
		g.setColor(Color.YELLOW);
		g.drawString("BOLT SQUADRON",140,200);
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