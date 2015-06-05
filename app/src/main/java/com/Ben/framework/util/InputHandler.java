package com.Ben.framework.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.Ben.game.state.State;
import com.Ben.simpleandroidgdf.GameMainActivity;

public class InputHandler implements OnTouchListener {
	private State currentState;
	private boolean isDown;
	private int downX;
	private int downY;
	public static final int TOUCHEVENT = 0;
	public static final int SWIPE_LEFT = 1;
	public static final int SWIPE_RIGHT = 2;
	public static final int SWIPE_UP = 3;
	public static final int SWIPE_DOWN = 4;
	private static final int BOUND = 100;

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int scaledX = (int) ((event.getX() / v.getWidth()) * GameMainActivity.GAME_WIDTH);
		int scaledY = (int) ((event.getY() / v.getHeight()) * GameMainActivity.GAME_HEIGHT);
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			downX = scaledX;
			downY = scaledY;
			isDown = true;
		}
		if(event.getAction() == MotionEvent.ACTION_UP){
			if(!isDown) return false;
			if((Math.abs(downX - scaledX) < BOUND) && (Math.abs(downY - scaledY) < BOUND)){
				return currentState.onTouch(TOUCHEVENT, scaledX, scaledY);
			}
			else if((scaledX - downX) > BOUND){
				return currentState.onTouch(SWIPE_RIGHT, scaledX, scaledY);
			}
			else if((scaledY - downY) > BOUND){
				return currentState.onTouch(SWIPE_UP, scaledX, scaledY);
			}
			else if((scaledX - downX) < -BOUND){
				return currentState.onTouch(SWIPE_LEFT, scaledX, scaledY);
			}
			else if((scaledY - downY) < -BOUND){
				return currentState.onTouch(SWIPE_DOWN, scaledX, scaledY);
			}
		}
		return false;
	}
}
