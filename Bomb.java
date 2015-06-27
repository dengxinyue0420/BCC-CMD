import java.awt.Graphics;

public class Bomb {
	private static int PRECISION = 10;
	public double x;
	public double y;
	public double size;
	String id;
	long time;
	int explosion = 0;

	public Bomb(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.time = System.currentTimeMillis();
		this.explosion = 0;
		this.id = constructId(x, y);
	}

	public static String constructId(double x, double y) {
		return String.format("%." + PRECISION + "f", x) + "_" + String.format("%." + PRECISION + "f", y);
	}

	public void draw(Graphics g) {
		if (explosion == 0) {
			g.drawImage(BattleField.bomb, (int) (x - size / 2), (int) (y - size / 2), (int) (x + size / 2),
					(int) (y + size / 2), 0, 0, 50, 50, null);
		} else {
			double scale = (double) 2 * explosion / 4;
			g.drawImage(BattleField.bomb_explosion, (int) (x - scale * size / 2), (int) (y - scale * size / 2),
					(int) (x + scale * size / 2), (int) (y + scale * size / 2), 0, 0, 100, 100, null);
		}
	}

	public void setExplosion() {
		explosion++;
	}
}
