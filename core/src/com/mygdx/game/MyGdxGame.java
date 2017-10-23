package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.menu.MainMenu;

public class MyGdxGame extends Game {

	public SpriteBatch batch;

	public static final int V_WIDTH = 1600;
	public static final int V_HEIGHT = 900;
	public static final float PPM = 500;

	public static final short APPLE_BIT = 1;
	public static final short TWILIGHT_BIT = 2;
	public static final short SPELL_BIT = 4;
	public static final short ENEMY_BIT = 8;
	public static final short FINAL_BIT = 16;
	public static final short NOTHING_BIT = 32;
	public static final short LOCK_BIT = 64;
	public static final short LOCK_HIT_BIT = 128;
	public static final short GROUND_BIT = 256;
	public static final short ENEMY_HIT_BIT = 512;
	public static final short TWILIGHT_HIT_BIT = 1024;
	public static final short APPLEJACK_BIT = 2048;
	public static final short PLATFORM_BIT = 4096;
	public static final short CHECKPOINT_BIT = 8192;

	private static boolean volumeOn = true;
	private static boolean volumeNow = true; //Текущий статус включения звука в игре
	private static float VOLUME = 1.0f;

	private boolean dialog_1lvl_start = true;   //Флаги для однократного показа диалогов
	private boolean dialog_2lvl_start = true;   //Флаги для однократного показа диалогов

	private static int numCheckPoint = 0;

	public Music music;


	@Override
	public void create() {
		batch = new SpriteBatch();

		music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Menu/Woodland Fantasy.ogg"));
		music.setLooping(true);

		setScreen(new MainMenu(this));

	}

	public static boolean isVolumeOn() {
		return volumeOn;
	}

	public static void setVolumeOn(boolean x) {
		MyGdxGame.volumeOn = x;
	}

	public static boolean isVolumeNow() {
		return volumeNow;
	}

	public static void setVolumeNow(boolean x) {
		MyGdxGame.volumeNow = x;
	}

	public static float getVOLUME() {
		return VOLUME;
	}

	public static void setVOLUME(float VOLUME) {
		MyGdxGame.VOLUME = VOLUME;
	}

	public static void setNumCheckPoint (int num) {numCheckPoint = num;}
	public static int getNumCheckPoint () {return numCheckPoint;}

	public boolean isDialog_1lvl_start() {
		return dialog_1lvl_start;
	}
	public boolean isDialog_2lvl_start() {
		return dialog_2lvl_start;
	}

	public void setDialog_1lvl_start(boolean dialog_1lvl_start) {
		this.dialog_1lvl_start = dialog_1lvl_start;
	}
	public void setDialog_2lvl_start(boolean dialog_2lvl_start) {
		this.dialog_2lvl_start = dialog_2lvl_start;
	}
}





// Интересные мысли по игре:

// 1. Сценка уровня с AJ - Твай стоит на платформах, Эйджей в стороне. Камера трясется, Эйджей говорит Run
// при этом и звучит тот момент из песни "Run" после чего Эйджей проваливается, а игра стартует

// 2. Арбалет Бондарчука - состоит из элементов, которые получены при прохождении разных уровней. Перед финальной битвой
// Пинки собирает и дает название "дарованное свыше Пинки чувством" - Арбалет Бондарчука. Суть - бесполезная хрень,
// отсылка к роликам BadComedian и фильмам Бондарчука