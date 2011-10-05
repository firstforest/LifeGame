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
	private JTextArea timeArea;

	public InfoPanel(GameMain gameMain) {
		// size
		setPreferredSize(new Dimension(480,48));

		this.gameMain = gameMain;

		infoArea = new JTextArea();
		infoArea.setText(String.valueOf(gameMain.getScore()));
		timeArea = new JTextArea();
		timeArea.setText(timeFormat(gameMain.getTime()));


		add(infoArea);
		add(timeArea);
	}

	public void update() {
		infoArea.setText(String.valueOf(gameMain.getScore()));
		timeArea.setText(timeFormat(gameMain.getTime()));
	}

	private String timeFormat(long time) {
		java.text.DecimalFormat df2 = new java.text.DecimalFormat("00");
//		int msec = (int)(time%1000/10);
		int sec = (int)(time/1000%60);
		int min = (int)(time/60000%60);
//		return df2.format(min) + ":" + df2.format(sec) + ":" + df2.format(msec);
		return df2.format(min) + ":" + df2.format(sec);
	}
}
