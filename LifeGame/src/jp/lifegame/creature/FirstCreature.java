package jp.lifegame.creature;

import jp.lifegame.GameMain;

public class FirstCreature extends Creature {

	public FirstCreature(GameMain gameMain, int x, int y) {
		super(gameMain, x, y);
	}
	
	@Override
	public void breed() {
		if (life > LIFE_MAX) {
			if (rnd.nextDouble() < 0.03) {
				gameMain.getcPool().add(new SecondCreature(gameMain, x, y));
			} else {
				gameMain.getcPool().add(new FirstCreature(gameMain, x, y));
			}
			life /= 2;
		}
	}
}
