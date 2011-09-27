package jp.lifegame.structure;

import java.awt.Color;
import java.awt.Graphics;

import jp.lifegame.GameMain;

public class Generator extends Structure{
	private int amount;

	public Generator(GameMain gameMain, int x, int y) {
		super(gameMain, x, y);
		this.color = Color.ORANGE;

		amount = 100;
	}

	public void genEne() {
		gameMain.getMap().setEne(x, y, amount);
	}

	public void work() {
		genEne();
	}
}
