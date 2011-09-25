package jp.lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel implements ActionListener{
	private GameMain gameMain;
	
	private JButton test;
	
	public ControlPanel(GameMain gameMain) {
		this.gameMain = gameMain;
		
		// button
		test = new JButton("test");
		
		test.addActionListener(this);
		
		add(test);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
