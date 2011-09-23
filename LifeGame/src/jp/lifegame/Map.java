package jp.lifegame;

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
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int eneRed(int x, int y) {
		MapChip map = maps[x + y * width];
		return map.energy;
	}


	private class MapChip {
		private int energy;

		public MapChip() {
			energy = 10;
		}
	}

}
