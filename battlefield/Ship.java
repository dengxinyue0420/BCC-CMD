package battlefield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Ship {
	public double x;
	public double y;
	public double dx;
	public double dy;
	public double direction;
	public long time;

	public String id;

	boolean self;

	public Ship(double x, double y, double dx, double dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.time = System.currentTimeMillis();
		
		// TODO Radian and direction
	}
	
	public void draw(Graphics g) {
		if (self) {
			g.setColor(Color.GREEN);

		} else {
			g.setColor(Color.RED);
		}

		g.drawOval((int) x, (int) y, 20, 20);
	}
}
