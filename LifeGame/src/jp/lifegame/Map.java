package jp.lifegame;

import java.awt.Color;
import java.awt.Graphics;

public class Map {
	private int width;
	private int height;
	private int timer=0;

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

	public void genEne() {
		if (timer % 1 == 0) {
			for (MapChip m : maps) {
				m.genEne();
			}
			timer = 0;
		}
		timer++;
	}
	public int redEne(int x, int y, int ene) {
		if (0<=x && x<width && 0<=y && y<height) {
			MapChip map = maps[x + y * width];
			return map.redEne(ene);
		}
		return 0;
	}

	public void setEne(int x, int y, int ene) {
		if (0<=x && x<width && 0<=y && y<height) {
			MapChip map = maps[x + y * width];
			map.setEne(ene);
		}
	}


	private class MapChip {
		private int energy;
		private int x,y;

		public MapChip(int x, int y) {
			energy = 0;
			this.x = x;
			this.y = y;
		}

		public void genEne() {
			int ene=0;
			int tx, ty;

			for (int i=-1; i<2; i++) {
				for (int j=-1; j<2; j++) {
					if (i==0 && j==0) continue;
					tx = x + i;
					ty = y + j;
					if (0<=tx && tx<width && 0<=ty && ty<height) {
						MapChip map = maps[tx + ty * width];
						ene += map.getEne();
					}
				}
			}

			ene /= 8;
			this.energy = (energy < ene) ? ene : energy;
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

		public int getEne() {
			return energy;
		}

		public void setEne(int ene) {
			this.energy = ene;
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
