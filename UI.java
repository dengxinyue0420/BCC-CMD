import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JPanel;

public class UI extends JPanel {

	private CanvasPanel canvas;
	private BattleField bf;
	PrintWriter out;
	BufferedReader in;

	private static int FPS = 30;
	private static long lastRefresh = System.currentTimeMillis();

	public static String username;

	public static double targetX;
	public static double targetY;

	public UI(final BattleField bf, final PrintWriter out, final BufferedReader in, String username) {
		this.bf = bf;
		this.out = out;
		this.in = in;
		this.username = username;

		canvas = new CanvasPanel();
		PointListener listener = new PointListener();
		canvas.addMouseMotionListener(listener);
		canvas.addMouseListener(listener);

		setLayout(new BorderLayout());
		add(canvas);

		Thread gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					while (true) {
						long curTime = System.currentTimeMillis();
						if ((curTime - lastRefresh) < (1000 / FPS)) {
							Thread.sleep(1000 / FPS - (curTime - lastRefresh));
						}

						out.println("STATUS");
						String status = in.readLine();
						// System.out.println(status);
						bf.updateStatus(status, -1, -1);

						canvas.repaint();
						lastRefresh = System.currentTimeMillis();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		gameThread.start();
	}

	// CanvasPanel is the panel where shapes will be drawn
	private class CanvasPanel extends JPanel {
		private static final long serialVersionUID = 5445617773765304453L;

		// this method draws all shapes specified by a user
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.WHITE);
			bf.draw(g);
		}
	} // end of CanvasPanel class

	// listener class that listens to the mouse
	public class PointListener implements MouseMotionListener, MouseListener {
		// in case that a user presses using a mouse,
		// record the point where it was pressed.
		public void mousePressed(MouseEvent event) {
			try {
				int x = event.getX();
				int y = event.getY();
				int btn = event.getButton();
				System.out.println("Mouse " + btn + " pressed at " + x + ", " + y);
				if (1 == btn) {
					out.println("SCAN " + x + " " + y);
					System.out.println("SCAN " + x + " " + y);
					String scan = in.readLine();
					System.out.println("SCAN:" + scan);
					if (!scan.contains("ERROR")) {
						System.out.println("has ERROR");
						bf.updateStatus(scan, x, y);
					}
				} else if (3 == btn) {
					bf.playSound();
					out.println("BOMB " + x + " " + y);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			canvas.repaint();
		}

		public void mouseReleased(MouseEvent event) {
		}

		public void mouseClicked(MouseEvent event) {
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}

		@Override
		public void mouseDragged(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			System.out.println("Mouse Dragged to " + x + ", " + y);
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
		}

	} // end of PointListener

} // end of Whole Panel Class
