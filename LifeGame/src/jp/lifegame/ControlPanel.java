package jp.lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import jp.lifegame.GameMain.Select;

public class ControlPanel extends JPanel implements ActionListener{
	private GameMain gameMain;
	
	private JButton cButton;
	private JButton genButton;
	
	public ControlPanel(GameMain gameMain) {
		this.gameMain = gameMain;
		
		// button
		cButton = new JButton("Creature");
		genButton = new JButton("Generator");
		
		cButton.addActionListener(this);
		genButton.addActionListener(this);
		
		add(cButton);
		add(genButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cButton) {
			gameMain.setMouseStat(Select.CREATURE);
		} else if (e.getSource() == genButton) {
			gameMain.setMouseStat(Select.GENERATOR);
		}
	}
}
