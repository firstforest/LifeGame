package jp.lifegame.creature;

import java.awt.Color;

import jp.lifegame.GameMain;

public class ThirdCreature extends Creature {

	public ThirdCreature(GameMain gameMain, int life, int x, int y) {
		super(gameMain, life, x, y);
		LIFE_MAX = 100;
		value = 80;
		color = Color.RED;
	}

	@Override
	public void eat() {
		for (int i=-1; i<2; i++) {
			for (int j=-1; j<2; j++) {
				if (i==0&&j==0) continue;
				Creature c = gameMain.getcPool().getCreatureAtMap(x+i, y+j);
				if (c != null) life += c.death();
			}
		}
		life -= mileage;
		if (life <= 0) death();
	}

	@Override
	public void breed() {
		if (life > LIFE_MAX) {
			gameMain.getcPool().add(new ThirdCreature(gameMain, life/2, x, y));
			life /= 2;
		}
	}
}
