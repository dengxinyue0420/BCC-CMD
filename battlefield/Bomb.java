package battlefield;

import java.awt.Color;
import java.awt.Graphics;

public class Bomb {
	public double x;
	public double y;
	long time;
	
	public Bomb(double x, double y) {
		this.x = x;
		this.y = y;
		this.time = System.currentTimeMillis();
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.PINK);
		g.drawRect((int)x,(int)y,10,10);
	}

}
