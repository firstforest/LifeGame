package jp.lifegame.creature;

import jp.lifegame.GameMain;

public class FirstCreature extends Creature {

	public FirstCreature(GameMain gameMain, int life, int x, int y) {
		super(gameMain, life, x, y);
	}

	@Override
	public void breed() {
		if (life > LIFE_MAX) {
			if (rnd.nextDouble() < 0.01) {
				gameMain.getcPool().add(new SecondCreature(gameMain, life/2, x, y));
			} else {
				gameMain.getcPool().add(new FirstCreature(gameMain, life/2, x, y));
			}
			life /= 2;
		}
	}
}
