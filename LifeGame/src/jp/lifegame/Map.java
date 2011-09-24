package jp.lifegame;

import java.awt.Color;
import java.awt.Graphics;

public class Map {
	private int width;
	private int height;


	private MapChip[] maps;

	public Map() {
		this(240, 240);
	}

	public Map(int width, int height) {
		this.width = width;
		this.height = height;

		maps = new MapChip[width*height];
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				maps[i + j * width] = new MapChip(i, j);	
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void draw(Graphics g) {
		for (MapChip m : maps) {
			m.draw(g);
		}
	}

	public int redEne(int x, int y, int ene) {
		MapChip map = maps[x + y * width];
		return map.redEne(ene);
	}


	private class MapChip {
		private int energy;
		private int x,y;
		
		public MapChip(int x, int y) {
			energy = 10;
			this.x = x;
			this.y = y;
		}
		
		public int redEne(int x) {
			if (x <= energy) {
				energy -= x;
				return x;
			} else {
				int t = energy;
				energy = 0;
				return t;
			}
		}
		
		public void draw(Graphics g) {
			if (energy > 8) {
				g.setColor(new Color(255,255,128));
			} else if (energy > 3) {
				g.setColor(new Color(255, 255, 200));
			} else if (energy > 0) {
				g.setColor(new Color(255, 255, 220));
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawRect(x, y, 1, 1);
		}
	}

}
