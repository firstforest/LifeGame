package jp.lifegame.creature;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import jp.lifegame.GameMain;

public class Creature {
	// position, direction
	protected int x;
	protected int y;
	protected Direction direction;

	// status
	protected int life;
	protected static int LIFE_MAX;
	private static final int SIZE = 4;
	protected int appetite;
	protected int mileage;
	protected int value;
	protected Color color;
	protected AudioClip deathVoice;

	protected GameMain gameMain;
	private int timer=0;
	// Randomizer
	protected Random rnd = new Random();

	public Creature(GameMain gameMain, int life, int x, int y) {
		this.x = x;
		this.y = y;
		this.life = life;
		init();

		this.gameMain = gameMain;
	}

	private void init() {
		// init
		LIFE_MAX = 20;
		appetite = 2;
		mileage = 1;
		value = 8;
		color = Color.GREEN;
		direction = new Direction(1,1);
		deathVoice = Applet.newAudioClip(getClass().getResource("tm2_can002.wav"));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x*SIZE, y*SIZE, SIZE, SIZE);
	}

	private void selectNewDirection() {
		int vx,vy;
		for (int i=0; i<32; i++) {
			vx = rnd.nextInt(3) - 1;
			vy = rnd.nextInt(3) - 1;

			if (gameMain.getcPool().exsitCreature(x+vx, y+vy)) {
				continue;
			} else {
				direction.x = vx;
				direction.y = vy;
				return;
			}
		}
		direction.x = 0;
		direction.y = 0;
	}

	public void move() {
		if (timer % SIZE == 0) {
			selectNewDirection();
			timer = 0;
		}

		x += direction.getX();
		y += direction.getY();

		if (x<0) {
			x = 0;
			direction.x *= -1;
		}

		if (x>gameMain.getWidth()/SIZE-SIZE) {
			x = gameMain.getWidth()/SIZE-SIZE;
			direction.x *= -1;
		}

		if (y<0) {
			y = 0;
			direction.y *= -1;
		}

		if (y>gameMain.getHeight()/SIZE-SIZE) {
			y = gameMain.getHeight()/SIZE-SIZE;
			direction.y *= -1;
		}

		timer++;
	}

	public void eat() {
		int dx = x+direction.x;
		int dy = y+direction.y;
		life += gameMain.getMap().redEne(dx, dy, appetite);
		life -= mileage;
		if (life <= 0) death();
	}

	public void breed() {
		if (life > LIFE_MAX) {
			gameMain.getcPool().add(new Creature(gameMain, life/2, x, y));
			life /= 2;
		}
	}

	public int death() {
		deathVoice.play();
		gameMain.getcPool().death(this);
		return life;
	}

	public int getValue() {
		deathVoice.play();
		return value;
	}

	protected class Direction {
		private int x;
		private int y;

		public Direction(int x, int y) {
			setX(x);
			setY(y);
		}

		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}

	}
}
