package jp.lifegame.creature;

import java.awt.Color;

import jp.lifegame.GameMain;

public class SecondCreature extends Creature {

	public SecondCreature(GameMain gameMain, int x, int y) {
		super(gameMain, x, y);
		LIFE_MAX = 40;
		color = Color.GREEN;
	}
	
	@Override
	public void eat() {
		int dx,dy;
		dx = x+direction.getX();
		dy = y+direction.getY();
		
		Creature c = gameMain.getcPool().getCreatureAtMap(dx, dy);
		if (c != null) life += c.death();
		life -= mileage;
		if (life <= 0) death();
	}
	
	@Override
	public void breed() {
		if (life > LIFE_MAX) {
			if (rnd.nextDouble() < 0.001) {
				gameMain.getcPool().add(new ThirdCreature(gameMain, x, y));
			} else {
				gameMain.getcPool().add(new SecondCreature(gameMain, x, y));
			}life /= 2;
		}
	}
}
