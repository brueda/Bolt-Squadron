package com.Ben.simpleandroidgdf;

import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class Assets {
	private static MediaPlayer mediaPlayer;
	private static SoundPool soundPool;
	public static Bitmap welcome;
	public static Bitmap testShip, greenDot, blueDot, crosshair, bolt, UFO, background, blueLaser, redLaser, greenLaser, greenRing, blueRing, shield;
	public static int laserID;

	public static void load() {
		welcome = loadBitmap("welcome.png", false);
		testShip = loadBitmap("playerShip1_orange.png", true);
		greenDot = loadBitmap("fx13.png", true);
		bolt = loadBitmap("bolt_gold.png", true);
		UFO = loadBitmap("Bio1_14_green.png", true);
		background = loadBitmap("black.png", true);
		blueDot = loadBitmap("fx12.png", true);
		crosshair = loadBitmap("crossair_redOutline.png", true);
		blueLaser = loadBitmap("lzrfx086.png", true);
		redLaser = loadBitmap("lzrfx101.png", true);
		greenLaser = loadBitmap("illuminaFX03.png", true);
		greenRing = loadBitmap("fx15.png", true);
		blueRing = loadBitmap("fx14.png", true);
		shield = loadBitmap("shieldSoft4.png", true);

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

	public static void playMusic(String filename, boolean looping) {
		if(mediaPlayer == null){
			mediaPlayer = new MediaPlayer();
		}
		try {
			AssetFileDescriptor afd = GameMainActivity.assets.openFd(filename);
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setVolume((float)0.1,(float)0.1);
			mediaPlayer.prepare();
			mediaPlayer.setLooping(looping);
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}