package com.Ben.simpleandroidgdf;

import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;

public class Assets {
	private static SoundPool soundPool;
	public static Bitmap welcome;
	public static Bitmap testShip, yellowDot, redDot, crosshair, bolt, UFO, shield, background, blueLaser, redLaser;
	public static int laserID;

	public static void load() {
		welcome = loadBitmap("welcome.png", false);
		testShip = loadBitmap("playerShip1_blue.png", true);
		yellowDot = loadBitmap("dotYellow.png", true);
		bolt = loadBitmap("bolt_gold.png", true);
		UFO = loadBitmap("ufoRed.png", true);
		shield = loadBitmap("shield.png", true);
		background = loadBitmap("black.png", true);
		redDot = loadBitmap("dotRed.png", true);
		crosshair = loadBitmap("crossair_redOutline.png", true);
		blueLaser = loadBitmap("laserBlue01.png", true);
		redLaser = loadBitmap("laserRed01.png", true);

		laserID = loadSound("laser1.wav");
	}

	private static Bitmap loadBitmap(String filename, boolean transparency) {
		InputStream inputStream = null;
		try {
			inputStream = GameMainActivity.assets.open(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Options options = new Options();
		if (transparency) {
			options.inPreferredConfig = Config.ARGB_8888;
		} else {
			options.inPreferredConfig = Config.RGB_565;
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
				options);
		return bitmap;
	}

	private static int loadSound(String filename) {
		int soundID = 0;
		if (soundPool == null) {
			soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
		}
		try {
			soundID = soundPool.load(GameMainActivity.assets.openFd(filename),
					1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return soundID;
	}

	public static void playSound(int soundID) {
		soundPool.play(soundID, 1, 1, 1, 0, 1);
	}
}