package jp.lifegame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class CreaturePool {

	private class CreatureBacket {
		public ArrayList<Creature> cs;
		private static final int C_MAX = 4;

		public CreatureBacket() {
			cs = new ArrayList<Creature>();
		}

		public void add(Creature c) {
			if (cs.size() < C_MAX) cs.add(c);
		}

		public void remove(Creature c) {
			cs.remove(c);
		}

		public void move() {
			for (Creature c : cs) {
				c.move();
			}
		}

		public void draw(Graphics g) {
			for (Creature c : cs) {
				c.draw(g);
			}
		}

		public void death(Creature c) {
			cs.remove(c);
		}

		public void eat() {
			for (Creature c : cs) {
				c.eat();
			}
		}
		public void breed() {
			int csSize = cs.size();
			for (int i=0; i<csSize; i++) {
				cs.get(i).breed();
			}
		}
		public Creature getCreature() {
			if (cs.size() > 0) return cs.get(0);
			return null;
		}

		public boolean exist() {
			return (cs.size() > 0);
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
		return creatures[x+y*width].getCreature();
	}

	private void update() { // 移動後の更新
		// init
		CreatureBacket[] tmp = creatures.clone();
		for(int i=0; i < height * width; i++) {
			creatures[i] = new CreatureBacket();
		}
		for (CreatureBacket cb : tmp) {
			for (Creature c : cb.cs) {
				creatures[c.getX() + c.getY()*width].add(c);
			}
		}
	}

	public boolean exsitCreature(int x, int y) {
		if (x<0 || x>=width || y<0 || y>=height) return true;
		return creatures[x + y*width].exist();
	}
}
