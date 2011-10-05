package jp.lifegame;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InfoPanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	GameMain gameMain;

	private JTextArea infoArea;

	public InfoPanel(GameMain gameMain) {
		// size
		setPreferredSize(new Dimension(480,48));

		this.gameMain = gameMain;

		infoArea = new JTextArea();
		infoArea.setText(String.valueOf(gameMain.getScore()));

		add(infoArea);
	}

	public void update() {
		infoArea.setText(String.valueOf(gameMain.getScore()));
	}
}
