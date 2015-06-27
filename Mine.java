

import java.awt.Color;
import java.awt.Graphics;

public class Mine {
	private static int PRECISION = 10;
	String id;
	double x;
	double y;
	double size; 
	String owner;

	public Mine(String owner, double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.owner = owner;
		this.id = constructId(x, y);
	}

	public static String constructId(double x, double y) {
		return String.format("%." + PRECISION + "f", x) + "_"
				+ String.format("%." + PRECISION + "f", y);
	}

	public void draw(Graphics g) {
		if (null == owner || owner.equals("--")) {
			g.drawImage(BattleField.mine_blue, (int) (x-size/2), (int) (y-size/2), (int) (x + size/2), (int) (y + size/2), 9,10,43,43, null);
		} else if (owner.equals(BattleField.username)) {
			g.drawImage(BattleField.mine_green, (int) (x-size/2), (int) (y-size/2), (int) (x + size/2), (int) (y + size/2), 9,10,43,43, null);
		} else {
			g.drawImage(BattleField.mine_pink, (int) (x-size/2), (int) (y-size/2), (int) (x + size/2), (int) (y + size/2), 9,10,43,43, null);
		}
	}
}
