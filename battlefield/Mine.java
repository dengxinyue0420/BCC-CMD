package battlefield;

import java.awt.Color;
import java.awt.Graphics;

public class Mine {
	private static int PRECISION = 12;
	String id;
	double x;
	double y;
	String owner;
	
	public Mine(String owner, double x, double y ){
		this.x = x;
		this.y = y;
		this.owner = owner;
		this.id = constructId(x, y);
	}
	
	public static String constructId(double x, double y) {
		return String.format("%."+PRECISION + "f", x) + "_" +String.format("%."+PRECISION + "f", y);
	}

	public void draw(Graphics g) {
		if (null != owner && owner.equals(BattleField.username)) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		g.fillOval((int)x, (int)y, 5, 5);
	}
}
