package com.Ben.simpleandroidgdf;

import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.Ben.framework.animation.Frame;
import com.Ben.framework.util.Painter;

public class Assets {
	private static MediaPlayer mediaPlayer;
	private static SoundPool soundPool;
	public static Bitmap testShip, greenDot, blueDot, background, blueLaser, redLaser, greenRing, blueRing, shield, beam, shieldLaser, multiLaser, moneyLaser, attackBlue, attackRed, attackOrange, moneyBlue, moneyOrange, moneyRed, defenseBlue, defenseOrange, defenseRed, attackGreen, moneyGreen, defenseGreen, ring, redDot, redSelect, star,
	purpleOrb, fireball, hadoken, bossLaser, attackShadow, defenseShadow, moneyShadow, greenLaser;
	//Inactive versions of sprites
	public static Bitmap attGreenDesat, attBlueDesat, attRedDesat, attOrangeDesat, monBlueDesat, monRedDesat, monOrangeDesat,
	defBlueDesat, defRedDesat, defOrangeDesat, starDesat;
	// enemy sprites
	public static Bitmap enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, bossEnemy;
	public static int laserID, hitID, explosionID, movementID, shieldID, beamID, selectID, healID, failID, levelUpID;
    public static Frame[] explosionFrames;
	public static Typeface tf;

	//The amount to desaturate the inactive ship sprites by
	private static float AMTDESAT = 90.0f;
	private static float AMTBRITE = -20.0f;

	public static void load() {
		testShip = loadBitmap("attackOrange.png", true);
		greenDot = loadBitmap("fx13.png", true);
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
		attackGreen = loadBitmap("attackGreen.png", true);
		attackBlue = loadBitmap("attackShipBlue.png", true);
		attackRed = loadBitmap("attackShipRed.png", true);
		attackOrange = loadBitmap("attackOrange.png", true);
		attackShadow = makeMutableBitmap("attackShipBlue.png");
		moneyBlue = loadBitmap("moneyBlue.png", true);
		moneyOrange = loadBitmap("moneyOrange.png", true);
		moneyRed = loadBitmap("moneyRed.png", true);
        moneyGreen = loadBitmap("moneyGreen.png", true);
		moneyShadow = makeMutableBitmap("moneyBlue.png");
		defenseBlue = loadBitmap("defenseBlue.png", true);
		defenseOrange = loadBitmap("defenseOrange.png", true);
		defenseRed = loadBitmap("defenseRed.png", true);
        defenseGreen = loadBitmap("defenseGreen.png", true);
		defenseShadow = makeMutableBitmap("defenseBlue.png");
		ring = loadBitmap("fx04.png", true);
		star = loadBitmap("star.png", true);
		fireball = loadBitmap("heavy1.png",true);
		hadoken = loadBitmap("heavy2.png", true);
		bossLaser = loadBitmap("bossLaser.png", true);
		greenLaser = loadBitmap("greenLaser.png", true);


		generateDesaturatedSprites();


		enemy1 = loadBitmap("enemy1.png", true);
        enemy2 = loadBitmap("enemy2.png", true);
        enemy3 = loadBitmap("enemy3.png", true);
        enemy4 = loadBitmap("enemy4.png", true);
		enemy5 = loadBitmap("enemy5.png", true);
        enemy6 = loadBitmap("enemy6.png", true);
		bossEnemy = loadBitmap("bossEnemy.png", true);


		laserID = loadSound("laser1.wav");
		hitID = loadSound("Hit.wav");
		explosionID = loadSound("Explosion.wav");
		movementID = loadSound("Woosh.wav");
		shieldID = loadSound("shield.wav");
		beamID = loadSound("beam.wav");
		selectID = loadSound("Select.wav");
		healID = loadSound("heal.wav");
		failID = loadSound("fail.wav");
		levelUpID = loadSound("levelUp.wav");

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

	public static Bitmap makeMutableBitmap(String filename) {
		Bitmap ret = loadBitmap(filename, true);
		ret = ret.copy(ret.getConfig(), true);
		return ret;
	}

	// Sets all non-transparent pixels of the sprite to the specified color.
	// Make sure the bitmap is mutable before passing it into this function.
	// The program will likely crash otherwise.
	public static void setSolidColor(Bitmap b, int argb) {
		int width = b.getWidth();
		int height = b.getHeight();
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				if (Color.alpha(b.getPixel(i, j)) > 0) {
					b.setPixel(i, j, argb);
				}
			}
		}
	}

	//Desaturates bitmap "b" by the percentage "percent"
	//0 results in the original sprite whereas 100 results in total grayscale.
	public static void desaturateSprite(Bitmap b, float percent) {
		if (percent > 100.0f) { percent = 100.0f; }
		if (percent < 0.0f) { percent = 0.0f; }
		percent = percent / 100.0f;
		for (int i = 0; i < b.getWidth(); ++i) {
			for (int j = 0; j < b.getHeight(); ++j) {
				int c = b.getPixel(i, j);
				int average = (Color.red(c) + Color.green(c) + Color.blue(c)) / 3;
				int red = (int) (Color.red(c) + ((average - Color.red(c)) * percent));
				int green = (int) (Color.green(c) + ((average - Color.green(c)) * percent));
				int blue = (int) (Color.blue(c) + ((average - Color.blue(c)) * percent));
				b.setPixel(i, j, Color.alpha(c) << 24 | red << 16 | green << 8 | blue);
			}
		}
	}

	//Changes brightness in ratio to the percent given. -100.0f would not guarantee pure black.
	//+100.0f would not guarantee pure white.
	public static void changeSpriteBrightness(Bitmap b, float percent) {
		if (percent < -100.0f) { percent = -100.0f; }
		percent = percent / 100.0f;
		for (int i = 0; i < b.getWidth(); ++i) {
			for (int j = 0; j < b.getHeight(); ++j) {
				int c = b.getPixel(i, j);
				int average = (Color.red(c) + Color.green(c) + Color.blue(c)) / 3;
				//Mins and maxes are to keep the value bounded between 0 and 255.
				int red = (int) Math.min(255, Math.max(0, (Color.red(c) + (average * percent))));
				int green = (int) Math.min(255, Math.max(0, (Color.green(c) + (average * percent))));
				int blue = (int) Math.min(255, Math.max(0, (Color.blue(c) + (average * percent))));
				b.setPixel(i, j, Color.alpha(c) << 24 | red << 16 | green << 8 | blue);
			}
		}
	}

	//So ugly... but for the sake of efficiency...
	private static void generateDesaturatedSprites() {
		//WHY ARE THE FILES INCONSISTENTLY NAMED?!?!
		//WHY DOES ONLY THE ATTACK SHIP HAVE A GREEN SPRITE?!
		Bitmap[] sprites = new Bitmap[11];

		sprites[0] = attGreenDesat = makeMutableBitmap("attackShipGreen.png");
		sprites[1] = attBlueDesat = makeMutableBitmap("attackShipBlue.png");
		sprites[2] = attRedDesat = makeMutableBitmap("attackShipRed.png");
		sprites[3] = attOrangeDesat = makeMutableBitmap("attackOrange.png");
		sprites[4] = monBlueDesat = makeMutableBitmap("moneyBlue.png");
		sprites[5] = monRedDesat = makeMutableBitmap("moneyRed.png");
		sprites[6] = monOrangeDesat = makeMutableBitmap("moneyOrange.png");
		sprites[7] = defBlueDesat = makeMutableBitmap("defenseBlue.png");
		sprites[8] = defRedDesat = makeMutableBitmap("defenseRed.png");
		sprites[9] = defOrangeDesat = makeMutableBitmap("defenseOrange.png");
		sprites[10] = starDesat = makeMutableBitmap("star.png");
		for (int i = 0; i < sprites.length; ++i) {
			desaturateSprite(sprites[i], AMTDESAT);
			changeSpriteBrightness(sprites[i], AMTBRITE);
		}
	}
}