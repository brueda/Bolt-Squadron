package com.Ben.simpleandroidgdf;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;

import com.Ben.framework.util.LevelLoader;

public class GameMainActivity extends Activity {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 450;
	public static GameView sGame;
	public static AssetManager assets;
	public static SharedPreferences prefs;
	private static final String highScoreKey = "highScoreKey";
	private static int highScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		assets = getAssets();
		sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
		prefs = getPreferences(Activity.MODE_PRIVATE);
		highScore = retrieveHighScore();
        setContentView(sGame);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public static void setHighScore(int score){
		GameMainActivity.highScore = score;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(highScoreKey, score);
        editor.apply();
	}

    private int retrieveHighScore(){
        return prefs.getInt(highScoreKey, 0);
    }

    public static int getHighScore(){
        return highScore;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Assets.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Assets.unpauseMusic();
    }
}
