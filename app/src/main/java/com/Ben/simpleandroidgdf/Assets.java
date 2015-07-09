package com.Ben.simpleandroidgdf;

import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.Ben.framework.animation.Frame;

public class Assets {
	private static MediaPlayer mediaPlayer;
	private static SoundPool soundPool;
	public static Bitmap testShip, greenDot, blueDot, UFO, background, blueLaser, redLaser, greenRing, blueRing, shield, beam, shieldLaser, multiLaser, moneyLaser,
	attackGreen, attackBlue, attackRed, attackOrange, moneyBlue, moneyOrange, moneyRed, defenseBlue, defenseOrange, defenseRed, ring, redDot, redSelect, star,
	purpleOrb;
	public static int laserID, hitID, explosionID, movementID, shieldID, beamID, selectID, healID, failID;
    public static Frame[] explosionFrames;
	public static Typeface tf;


	public static void load() {
		testShip = loadBitmap("attackOrange.png", true);
		greenDot = loadBitmap("fx13.png", true);
		UFO = loadBitmap("Bio1_14_green.png", true);
		background = loadBitmap("black.png", true);
		blueDot = loadBitmap("fx12.png", true);
		purpleOrb = loadBitmap("purpleOrb.png", true);
		redDot = loadBitmap("redDot.png", true);
		redSelect = loadBitmap("redSelect.png", true);
		blueLaser = loadBitmap("lzrfx086.png", true);
		redLaser = loadBitmap("lzrfx101.png", true);
		greenRing = loadBitmap("fx15.png", true);
		blueRing = loadBitmap("fx14.png", true);
		shield = loadBitmap("shieldSoft4.png", true);
		beam = loadBitmap("beam.png", true);
		shieldLaser = loadBitmap("shieldLaser.png", true);
		multiLaser = loadBitmap("multiLaser.png", true);
		moneyLaser = loadBitmap("moneyLaser.png", true);
		attackGreen = loadBitmap("attackShipGreen.png", true);
		attackBlue = loadBitmap("attackShipBlue.png", true);
		attackRed = loadBitmap("attackShipRed.png", true);
		attackOrange = loadBitmap("attackOrange.png", true);
		moneyBlue = loadBitmap("moneyBlue.png", true);
		moneyOrange = loadBitmap("moneyOrange.png", true);
		moneyRed = loadBitmap("moneyRed.png", true);
		defenseBlue = loadBitmap("defenseBlue.png", true);
		defenseOrange = loadBitmap("defenseOrange.png", true);
		defenseRed = loadBitmap("defenseRed.png", true);
		ring = loadBitmap("fx04.png", true);
		star = loadBitmap("star.png", true);

		laserID = loadSound("laser1.wav");
		hitID = loadSound("Hit.wav");
		explosionID = loadSound("Explosion.wav");
		movementID = loadSound("Woosh.wav");
		shieldID = loadSound("shield.wav");
		beamID = loadSound("beam.wav");
		selectID = loadSound("Select.wav");
		healID = loadSound("heal.wav");
		failID = loadSound("fail.wav");

        explosionFrames = new Frame[9];
        for (int i = 0; i < 9; ++i) {
            explosionFrames[i] = new Frame(loadBitmap("explosion0" + i + ".png", false), 20.0);
        }

		tf = Typeface.createFromAsset(GameMainActivity.assets, "kenvector_future.ttf");
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

	public static void playSound(int soundID, float volume) {
		soundPool.play(soundID, volume, volume, 1, 0, 1);
	}

	public static void playMusic(String filename, boolean looping) {
		if(mediaPlayer == null){
			mediaPlayer = new MediaPlayer();
		}
		try {
			AssetFileDescriptor afd = GameMainActivity.assets.openFd(filename);
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setVolume(0.2f, 0.2f);
			mediaPlayer.prepare();
            mediaPlayer.setLooping(looping);
			mediaPlayer.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public static void unpauseMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
}