package jp.lifegame.creature;

import java.awt.Graphics;

public class CreaturePool {

	private class CreatureBacket {
		public Creature[] cs;
		private static final int C_MAX = 4;

		public CreatureBacket() {
			cs = new Creature[C_MAX];
			for (int i=0; i<C_MAX; i++) {
				cs[i] = null;
			}
		}

		public void add(Creature c) {
			for (int i=0; i<cs.length; i++) {
				if (cs[i] != null) continue;
				cs[i] = c;
				break;
			}
		}

		public void move() {
			for (Creature c : cs) {
				if (c != null) c.move();
			}
		}

		public void draw(Graphics g) {
			for (Creature c : cs) {
				if (c != null) c.draw(g);
			}
		}

		public void death(Creature c) {
			for (int i=0; i<cs.length; i++) {
				if (cs[i] == c) cs[i] = null;
			}			
		}

		public void eat() {
			for (int i=0; i<cs.length; i++) {
				if (cs[i] != null) cs[i].eat();
			}
		}
		public void breed() {
			for (int i=0; i<cs.length; i++) {
				if (cs[i] != null) cs[i].breed();
			}
		}
		public Creature getCreature() {
			for (int i=0; i<cs.length; i++) {
				if (cs[i] != null) return cs[i];
			}
			return null;
		}

		public boolean exist() {
			boolean result=false;
			for (int i=0; i<cs.length; i++) {
				if (cs[i] != null) result = true;
			}
			return result;
		}
	}

	private CreatureBacket[] creatures;
	private int height;
	private int width;

	public CreaturePool(int width, int height){
		creatures = new CreatureBacket[width * height];
		for (int i=0; i<width*height; i++) {
			creatures[i] = new CreatureBacket();
		}
		this.width = width;
		this.height = height;
	}

	public void add(Creature c) {
		int x,y;
		x = c.getX();
		y = c.getY();
		creatures[x + y*width].add(c);
	}

	public void move() {
		for (CreatureBacket cb : creatures) {
			cb.move();
		}
		update();
	}

	public void draw(Graphics g) {
		for (CreatureBacket cb : creatures) {
			cb.draw(g);
		}
	}

	public void death(Creature c) {
		int x,y;
		x = c.getX();
		y = c.getY();
		creatures[x + y*width].death(c);
	}

	public void eat() {
		for (CreatureBacket cb : creatures) {
			cb.eat();
		}
	}

	public void breed() {
		for (CreatureBacket cb : creatures) {
			cb.breed();
		}
	}

	public Creature getCreatureAtMap(int x, int y) {
		if (0<=x && x<width && 0<=y && y<height) {
			return creatures[x+y*width].getCreature();
		}
		return null;
	}

	private void update() { // 移動後の更新
		// init
		CreatureBacket[] tmp = creatures.clone();
		for(int i=0; i < height * width; i++) {
			creatures[i] = new CreatureBacket();
		}
		for (CreatureBacket cb : tmp) {
			for (Creature c : cb.cs) {
				if (c != null) creatures[c.getX() + c.getY()*width].add(c);
			}
		}
	}

	public boolean exsitCreature(int x, int y) {
		if (x<0 || x>=width || y<0 || y>=height) return true;
		return creatures[x + y*width].exist();
	}
}
