package jp.lifegame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import jp.lifegame.creature.Creature;
import jp.lifegame.creature.CreaturePool;
import jp.lifegame.creature.FirstCreature;
import jp.lifegame.structure.Generator;
import jp.lifegame.structure.StructurePool;

public class GameMain extends JPanel implements Runnable, MouseListener, MouseMotionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// panel size
	private static final int WIDTH = 480;
	private static final int HEIGHT = 480;
	// for mouse
	private MouseStat mouseStat;

	private CreaturePool cPool;
	private StructurePool sPool;
	private Map map;
	private InfoPanel infoPane;
	// リアルタイムレンダリング用
    private Image dbImage = null;
    private Graphics dbg;

    private Thread thread;

	private int score;

	public GameMain() {
		// set size
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		map = new Map(WIDTH/2, HEIGHT/2);
		cPool = new CreaturePool(WIDTH/2, HEIGHT/2);
		sPool = new StructurePool(WIDTH/2, HEIGHT/2);
		mouseStat = new MouseStat();
		score = 100;
		sPool.add(new Generator(this, 100, 100));

		// MouseListener
		addMouseListener(this);
		addMouseMotionListener(this);

		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while (true) {
			gameUpdate();
			gameRender();
			paintScreen();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public  int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public Map getMap() {
		return map;
	}

	public CreaturePool getcPool() {
		return cPool;
	}

	public StructurePool getsPool() {
		return sPool;
	}

	public void setInfoPane(InfoPanel infoPane) {
		this.infoPane = infoPane;
	}

	public int getScore() {
		return score;
	}

	private synchronized void gameUpdate() {
		cPool.move();
		cPool.eat();
		cPool.breed();
		sPool.work();
		map.genEne();
		if (infoPane != null) infoPane.update();
	}

	private synchronized void gameRender() {
		// dual buffer
		if (dbImage == null) {
			dbImage = createImage(WIDTH, HEIGHT);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else {
				dbg = dbImage.getGraphics();
			}
		}

		// clear
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, WIDTH, HEIGHT);

		map.draw(dbg);
		cPool.draw(dbg);
		sPool.draw(dbg);

		if (mouseStat.dragged) {
			dbg.setColor(Color.RED);
			dbg.drawLine(mouseStat.px, mouseStat.py, mouseStat.x, mouseStat.py);
			dbg.drawLine(mouseStat.x, mouseStat.py, mouseStat.x, mouseStat.y);
			dbg.drawLine(mouseStat.x, mouseStat.y, mouseStat.px, mouseStat.y);
			dbg.drawLine(mouseStat.px, mouseStat.y, mouseStat.px, mouseStat.py);
		}
	}

	/**
	 * 繝舌ャ繝輔ぃ縺ｮ蜀�ｮｹ繧堤判髱｢縺ｫ謠冗判
	 */
	private synchronized void paintScreen() {
		Graphics g;
		try {
			g = getGraphics();
			if ((g != null) & (dbImage != null)) {
				g.drawImage(dbImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync();
			if (g != null) {
				g.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			if (score >= 10) {
				int x,y;
				x = e.getX()/2;
				y = e.getY()/2;

				cPool.add(new FirstCreature(this, 10, x, y));
				score -= 10;
			}
			break;
		case MouseEvent.BUTTON3:
			Creature c;
			while ((c = cPool.getCreatureAtMap(e.getX(), e.getY())) != null) {
				cPool.death(c);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseStat.px = e.getX();
		mouseStat.py = e.getY();
	}

	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		if (mouseStat.px < e.getX()) {
			mouseStat.x = e.getX();
		} else {
			mouseStat.x = mouseStat.px;
			mouseStat.px = e.getX();
		}

		if (mouseStat.py < e.getY()) {
			mouseStat.y = e.getY();
		} else {
			mouseStat.y = mouseStat.py;
			mouseStat.py = e.getY();
		}

		mouseStat.px = (mouseStat.px<0) ? 0 : mouseStat.px;
		mouseStat.x = (mouseStat.x>WIDTH) ? WIDTH : mouseStat.x;
		mouseStat.py = (mouseStat.py<0) ? 0 : mouseStat.py;
		mouseStat.y = (mouseStat.y>HEIGHT) ? HEIGHT : mouseStat.y;

		for (int i = mouseStat.px; i < mouseStat.x; i++) {
			for (int j = mouseStat.py; j < mouseStat.y; j++) {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					if (score >= 10 && i%2==0 && j%2==0) {
						cPool.add(new FirstCreature(this, 10, i/2, j/2));
						score -= 10;
					}
					break;
				case MouseEvent.BUTTON3:
					Creature c;
					while ((c = cPool.getCreatureAtMap(i/2, j/2)) != null) {
						score += c.getValue();
						cPool.death(c);
					}
					break;
				default:
					break;
				}
			}
		}
		mouseStat.dragged = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 閾ｪ蜍慕函謌舌＆繧後◆繝｡繧ｽ繝�ラ繝ｻ繧ｹ繧ｿ繝�

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 閾ｪ蜍慕函謌舌＆繧後◆繝｡繧ｽ繝�ラ繝ｻ繧ｹ繧ｿ繝�

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseStat.x = e.getX();
		mouseStat.y = e.getY();
		mouseStat.dragged = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 閾ｪ蜍慕函謌舌＆繧後◆繝｡繧ｽ繝�ラ繝ｻ繧ｹ繧ｿ繝�

	}

	class MouseStat {
		int px,py;
		int x,y;
		boolean dragged;

		public MouseStat() {
			dragged = false;
		}
	}
}

