package jp.lifegame.structure;

import java.awt.Graphics;

import jp.lifegame.GameMain;

public class StructurePool {

	private Structure[] structures;
	private int width, height;

	public StructurePool(int width, int height) {
		this.width = width;
		this.height = height;

		structures = new Structure[width * height];
	}

	public void draw(Graphics g) {
		for (Structure st : structures) {
			if (st != null) st.draw(g);
		}
	}

	public void add(Structure st) {
		int x = st.x;
		int y = st.y;

		if (structures[x + y*width] == null) structures[x + y*width] = st;
	}

	public void destroy(Structure st) {
		structures[st.x + st.y*width] = null;
	}

	public void work() {
		for (Structure st : structures) {
			if (st != null) st.work();
		}
	}
}
