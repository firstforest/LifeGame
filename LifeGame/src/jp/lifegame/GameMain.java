package jp.lifegame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GameMain extends JPanel implements Runnable, MouseListener, MouseMotionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// panel sizent
	private static final int WIDTH = 240;
	private static final int HEIGHT = 240;

	// for mouse
	private int preSelX, preSelY, selX, selY;
	private boolean mDragged = false;

	private Thread thread;
	private CreaturePool cPool;


    public CreaturePool getcPool() {
		return cPool;
	}

	// ダブルバッファリング用
    private Image dbImage = null;
    private Graphics dbg;

    private Map map;

	public  int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public Map getMap() {
		return map;
	}

	public GameMain() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		map = new Map(WIDTH, HEIGHT);
		cPool = new CreaturePool(WIDTH, HEIGHT);

		// MouseListenerを登録
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

	private synchronized void gameUpdate() {
		cPool.move();
		cPool.eat();
		cPool.breed();
	}

	private synchronized void gameRender() {
		// ダブルバッファリング用オブジェクトの生成
		if (dbImage == null) {
			dbImage = createImage(WIDTH, HEIGHT);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else {
				dbg = dbImage.getGraphics();
			}
		}

		// バッファをクリアする
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, WIDTH, HEIGHT);

		cPool.draw(dbg);
		if (mDragged) {
			dbg.setColor(Color.RED);
			dbg.drawLine(preSelX, preSelY, selX, preSelY);
			dbg.drawLine(selX, preSelY, selX, selY);
			dbg.drawLine(selX, selY, preSelX, selY);
			dbg.drawLine(preSelX, selY, preSelX, preSelY);
		}
	}

	/**
	 * バッファの内容を画面に描画
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
			int x,y;
			x = e.getX();
			y = e.getY();

			cPool.add(new Creature(this, x, y));
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
		preSelX = e.getX();
		preSelY = e.getY();
	}

	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		if (preSelX < e.getX()) {
			selX = e.getX();
		} else {
			selX = preSelX;
			preSelX = e.getX();
		}

		if (preSelY < e.getY()) {
			selY = e.getY();
		} else {
			selY = preSelY;
			preSelY = e.getY();
		}

		preSelX = (preSelX<0) ? 0 : preSelX;
		selX = (selX>WIDTH) ? WIDTH : selX;
		preSelY = (preSelY<0) ? 0 : preSelY;
		selY = (selY>HEIGHT) ? HEIGHT : selY;

		for (int i = preSelX; i < selX; i++) {
			for (int j = preSelY; j < selY; j++) {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					cPool.add(new Creature(this, i, j));
					break;
				case MouseEvent.BUTTON3:
					Creature c;
					while ((c = cPool.getCreatureAtMap(i, j)) != null) {
						cPool.death(c);
					}
					break;
				default:
					break;
				}
			}
		}

		mDragged = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		selX = e.getX();
		selY = e.getY();
		mDragged = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
