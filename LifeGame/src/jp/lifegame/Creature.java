package jp.lifegame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Creature {
	// 座標
	private int x;
	private int y;
	// life
	private int life;
	private static final int LIFE_MAX = 20;
	private static final int SIZE = 1;
	private Direction direction = new Direction(1, 1);
	GameMain gameMain;
	Map map;
	private int timer=0;
	// randamizer
	private Random rnd = new Random();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


	public Creature(GameMain gameMain, int x, int y) {
		this.x = x;
		this.y = y;

		this.gameMain = gameMain;
		this.map = gameMain.getMap();
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

		if (x>gameMain.getWidth()-SIZE) {
			x = gameMain.getWidth()-SIZE;
			direction.x *= -1;
		}

		if (y<0) {
			y = 0;
			direction.y *= -1;
		}

		if (y>gameMain.getHeight()-SIZE) {
			y = gameMain.getHeight()-SIZE;
			direction.y *= -1;
		}

		timer++;
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, SIZE, SIZE);
	}

	public void death() {
		gameMain.getcPool().death(this);
	}

	public void eat() {
		life++;
	}

	public void breed() {
		if (life > LIFE_MAX) {
			gameMain.getcPool().add(new Creature(gameMain, x, y));
			life /= 2;
		}
	}


	private class Direction {
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
