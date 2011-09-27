package jp.lifegame.structure;

import java.awt.Color;
import java.awt.Graphics;

import jp.lifegame.GameMain;

public class Structure {
	protected int x,y;
	protected static final int SIZE = 1;
	protected Color color;
	protected GameMain gameMain;

	public Structure(GameMain gameMain, int x, int y) {
		this.x = x;
		this.y = y;
		this.gameMain = gameMain;

		init();
	}

	private void init() {
		color = Color.WHITE;
	}


	public void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(x, y, SIZE, SIZE);
	}

	public void destroy(Structure st) {
		gameMain.getsPool().destroy(st);
	}

	public void work() {

	}

}
