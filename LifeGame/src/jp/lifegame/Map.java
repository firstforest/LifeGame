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
			//TODO energyの種類に未対応
			return map.redEne(0,ene);
		}
		return 0;
	}

	public void setEne(int x, int y, int ene) {
		if (0<=x && x<width && 0<=y && y<height) {
			MapChip map = maps[x + y * width];
			//TODO energyの種類に未対応
			map.setEne(0,ene);
		}
	}


	private class MapChip {
		private int[] energy;
		private int x,y;
		private static final int SIZE = 4;
		private static final int ENE_NUM = 2;

		public MapChip(int x, int y) {
			energy = new int[ENE_NUM];
			for (int i=0; i<ENE_NUM; i++) {
				energy[i] = 0;
			}
			this.x = x;
			this.y = y;
		}

		public void genEne() {
			for (int k=0; k<ENE_NUM; k++) {
				int ene=0;
				int tx, ty;

				for (int i=-1; i<2; i++) {
					for (int j=-1; j<2; j++) {
						if (i==0 && j==0 || (i != 0 && j != 0)) continue;
						tx = x + i;
						ty = y + j;
						if (0<=tx && tx<width && 0<=ty && ty<height) {
							MapChip map = maps[tx + ty * width];
							ene += map.getEne(k);
						}
					}
				}

				ene /= 4;
				this.energy[k] = (this.energy[k] < ene) ? ene : this.energy[k];
			}
		}

		public int redEne(int n, int x) {
			if (x <= energy[n]) {
				energy[n] -= x;
				return x;
			} else {
				int t = energy[n];
				energy[n] = 0;
				return t;
			}
		}

		public int getEne(int n) {
			return energy[n];
		}

		public void setEne(int n, int ene) {
			this.energy[n] = ene;
		}

		public void draw(Graphics g) {
			if (energy[0] > 200) {
				g.setColor(new Color(255,255,0));
			} else if (energy[0] > 100) {
				g.setColor(new Color(255, 255, 128));
			} else if (energy[0] > 0) {
				g.setColor(new Color(255, 255, 200));
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawRect(x*SIZE, y*SIZE, SIZE, SIZE);
		}
	}

}
