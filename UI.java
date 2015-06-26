import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class UI extends JPanel {
	private static final long serialVersionUID = 6509217204123368745L;
	private static final int UPDATE_THREAD_INTERVAL = 20;
	private CanvasPanel canvas;

	public UI() {
		canvas = new CanvasPanel();
		PointListener listener = new PointListener();
		canvas.addMouseMotionListener(listener);
		canvas.addMouseListener(listener);

		setLayout(new BorderLayout());
		add(canvas);

		Thread updateThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						canvas.repaint();
						Thread.sleep(UPDATE_THREAD_INTERVAL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		updateThread.start();
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
