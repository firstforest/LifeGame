package jp.lifegame;

import java.awt.*;
import javax.swing.*;

public class LifeGame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public LifeGame() {
		// title
		setTitle("LifeGame");
		// resizable
		setResizable(false);
		
		// main
		GameMain gameMain = new GameMain();
		Container contentPane = getContentPane();
		contentPane.add(gameMain, BorderLayout.CENTER);
		
		// controlPanel
		ControlPanel ctrlPane = new ControlPanel(gameMain);
		contentPane.add(ctrlPane, BorderLayout.SOUTH);
		
		// infoPanel
		InfoPanel infoPane = new InfoPanel(gameMain);
		contentPane.add(infoPane, BorderLayout.EAST);
		
		gameMain.setInfoPane(infoPane);

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


