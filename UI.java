import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JPanel;

public class UI extends JPanel {
	private static final long serialVersionUID = 6509217204123368745L;
	private static final int UPDATE_THREAD_INTERVAL = 20;
	private static int FPS = 10;
	private static long lastRefresh = System.currentTimeMillis();
	private CanvasPanel canvas;

	private static String username = "a";//"CMD";
	private static String password = "a";//"i1ev2mtpfw";
	private static String server = "127.0.0.1";
	private static int port = 17429;


	public UI() {
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
					// connect to server
					PrintWriter out = null;
					BufferedReader in = null;
					Socket s = new Socket(server, port);
					out = new PrintWriter(s.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(
							s.getInputStream()));

					out.println(username + " " + password);

					while (true) {
						long curTime = System.currentTimeMillis();
						if ((curTime - lastRefresh) < (1000 / FPS)) {
							Thread.sleep(1000 / FPS - (curTime - lastRefresh));
						}

						// TODO make query
						out.println("STATUS");
						String response = in.readLine();
						System.out.println(response);

						// TODO update battle field

						canvas.repaint();

						// TODO send commands

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
		}
	} // end of CanvasPanel class

	// listener class that listens to the mouse
	public class PointListener implements MouseMotionListener, MouseListener {
		// in case that a user presses using a mouse,
		// record the point where it was pressed.
		public void mousePressed(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			System.out.println("Mouse pressed at " + x + ", " + y);
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
