package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class BugAttack extends ApplicationAdapter {
	public static final float SCR_WIDTH = 1280, SCR_HEIGHT = 720;

	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;
	BitmapFont font;

	Texture imgGreenBug;
	Texture imgRedBug;
	Texture imgGoldBug;
	Texture imgBackGround;
	Sound sndChpok;
	Sound sndExplosion;

	BugEntity[] bugs = new BugEntity[20];
	BugRed[] redBugs = new BugRed[3];
	BugGold[] goldBugs = new BugGold[1];

	int score;
	long timeStartGame, time;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();

		generateFont();

		imgGreenBug = new Texture("greenBug.png");
		imgRedBug = new Texture("redBug.png");
		imgGoldBug = new Texture("goldBug.png");
		imgBackGround = new Texture("back.jpg");
		sndChpok = Gdx.audio.newSound(Gdx.files.internal("sunchpok.mp3"));
		sndExplosion = Gdx.audio.newSound(Gdx.files.internal("expl.mp3"));



		for (int i = 0; i < bugs.length; i++) {
			bugs[i] = new BugEntity();
		}
		for (int i = 0; i < redBugs.length; i++) {
			redBugs[i] = new BugRed();
		}

		Gdx.input.setInputProcessor(new MyInputProcessor());

		timeStartGame = TimeUtils.millis();
	}

	@Override
	public void render () {
		// обработка касаний
		/*if(Gdx.input.isTouched()){
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			for (int i = 0; i < snowflakes.length; i++) {
				if(snowflakes[i].hit(touch.x, touch.y)){
					snowflakes[i].respawn();
				}
			}
		}*/

		// события игры
		for (int i = 0; i < bugs.length; i++) {
			bugs[i].move();
		}
		for (int i = 0; i < redBugs.length; i++) {
			redBugs[i].move();
		}
		time = TimeUtils.millis()-timeStartGame;

		// отрисовка всего
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
		for (int i = 0; i < bugs.length; i++) {
			batch.draw(imgGreenBug, bugs[i].x, bugs[i].y,
					bugs[i].width/2, bugs[i].height/2, bugs[i].width, bugs[i].height,
					1, 1, bugs[i].angle, 0, 0, 1804, 1804, false, false);
		}
		for (int i = 0; i < redBugs.length; i++) {
			batch.draw(imgRedBug, redBugs[i].x, redBugs[i].y,
					redBugs[i].width/2, redBugs[i].height/2, redBugs[i].width, redBugs[i].height,
					1, 1, bugs[i].angle, 0, 0, 1804, 1804, false, false);
		}
		font.draw(batch, ""+score, SCR_WIDTH-70, SCR_HEIGHT-10);
		font.draw(batch, getTimeString(), 0, 50, SCR_WIDTH, Align.center, true);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		imgGreenBug.dispose();
		imgBackGround.dispose();
		sndChpok.dispose();
	}

	void generateFont(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("isabella.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 40;
		parameter.color = Color.BLACK;
		parameter.borderColor = Color.WHITE;
		parameter.borderWidth = 1;
		parameter.shadowColor = Color.BLACK;
		parameter.shadowOffsetX = 2;
		parameter.shadowOffsetY = 2;
		font = generator.generateFont(parameter);
	}

	String getTimeString(){
		long msec = time%1000;
		long sec = time/1000%60;
		long min = time/1000/60%60;
		long hour = time/1000/60/60;
		return ""+min/10+min%10+":"+sec/10+sec%10+":"+msec/100;
	}

	class MyInputProcessor implements InputProcessor {
		@Override
		public boolean keyDown(int keycode) {
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			touch.set(screenX, screenY, 0);
			camera.unproject(touch);
			for (int i = 0; i < bugs.length; i++) {
				if(bugs[i].hit(touch.x, touch.y)){
					bugs[i].respawn();
					sndChpok.play();
					score++;
				}
			}
			for (int i = 0; i < redBugs.length; i++) {
				if(redBugs[i].hit(touch.x, touch.y)){
					sndExplosion.play();
					score = 0;
					for (int j = 0; j < redBugs.length; j++) {
						redBugs[j].respawn();
					}
					for (int a = 0; a < bugs.length; a++) {
						bugs[a].respawn();
					}
				}
			}
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(float amountX, float amountY) {
			return false;
		}
	}
}
