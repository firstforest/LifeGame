package jp.lifegame;

import java.awt.*;
import javax.swing.*;

public class LifeGame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public LifeGame() {
		setTitle("LifeGame");

		GameMain gameMain = new GameMain();
		Container contentPane = getContentPane();
		contentPane.add(gameMain);

		pack();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LifeGame frame = new LifeGame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}


