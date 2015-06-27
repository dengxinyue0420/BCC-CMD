package battlefield;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BattleField {
	Ship ourShip = new Ship(0, 0, 0, 0);
	Set<Ship> ships;
	Map<String, Mine> mines = new HashMap<String, Mine>();
	Set<Bomb> bombs; 
	static long[][] fog = null;
	
	public static String username;

	public static double mapWidth;
	public static double mapHeight;
	public static double captureRadius;
	public static double visionRadius;
	public static double friction;
	public static double brakeFriction;
	public static double bombPlaceRadius;
	public static double bombEffectRadius;
	public static double bombDelay;
	public static double bombPower;
	public static double scanRadius;
	public static double scanDelay;

	private static final long FADE_SPEED = 3000;

	// commands and response
	public static final String CONFIGURATIONS = "CONFIGURATIONS";
	public static final String CONFIGURATIONS_OUT = "CONFIGURATIONS_OUT";
	public static final String STATUS = "STATUS";
	public static final String STATUS_OUT = "STATUS_OUT";

	public BattleField(String username) {
		this.username = username;
		ships = Collections.newSetFromMap(new ConcurrentHashMap<Ship, Boolean>());
		bombs = Collections.newSetFromMap(new ConcurrentHashMap<Bomb, Boolean>());
	}

	public static void setConfigurations(String config) {
		if (null == config)
			return;

		String[] strs = config.split(" ");

		if (null == strs[0] || !strs[0].equals(CONFIGURATIONS_OUT)) {
			System.out.println("ERROR, " + CONFIGURATIONS_OUT + " != "
					+ strs[0]);
			return;
		}
		for (int i = 1; i < strs.length; i++) {
			switch (strs[i]) {
			case "MAPWIDTH":
				mapWidth = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "MAPHEIGHT":
				mapHeight = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "CAPTURERADIUS":
				captureRadius = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "VISIONRADIUS":
				visionRadius = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "FRICTION":
				friction = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "BRAKEFRICTION":
				brakeFriction = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "BOMBPLACERADIUS":
				bombPlaceRadius = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "BOMBEFFECTRADIUS":
				bombEffectRadius = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "BOMBDELAY":
				bombDelay = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "BOMBPOWER":
				bombPower = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "SCANRADIUS":
				scanRadius = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			case "SCANDELAY":
				scanDelay = Double.parseDouble(strs[i + 1]);
				i++;
				break;
			default:
				System.out.println("unknown config " + strs[i]);
			}
		}

		int w = (int) mapWidth;
		int h = (int) mapHeight;
		long time = System.currentTimeMillis();
		fog = new long[h][w];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				fog[y][x] = time;
			}
		}
	}

	public void updateStatus(String status, int scanX, int scanY) {
		if (null == status)
			return;

		boolean scan = false;

		try {
			String[] strs = status.split(" ");

			if (null == strs[0])
				return;

			double radius = visionRadius;
			if (strs[0].equals("SCAN_OUT")) {
				scan = true;
				radius =scanRadius;
			}

			int count;
			double updateX = scanX;
			double updateY = scanY;

			long curTime = System.currentTimeMillis();
			for (int i = 0; i < strs.length; i++) {
				switch (strs[i]) {
				
				case "STATUS_OUT":
					// x y dx dy
					ourShip.x = Double.parseDouble(strs[++i]);
					ourShip.y = Double.parseDouble(strs[++i]);
					ourShip.dx = Double.parseDouble(strs[++i]);
					ourShip.dy = Double.parseDouble(strs[++i]);
					
					updateX = ourShip.x;
					updateY = ourShip.y;

				case "SCAN_OUT":
					// update fog
					for (int y = (int) (-radius); y < radius; y++) {
						for (int x = (int) (-radius); x < radius; x++) {
							double dist2 = Math.abs(y) * Math.abs(y)
									+ Math.abs(x) * Math.abs(x);
							if (dist2 > radius * radius)
								continue;
							fog[(int) transY(updateY + y)][(int) transX(updateX + x)] = curTime;
						}
					}

					// update ship)
					for (Ship ship : ships) {
						if (withIn(ship.x, ship.y, updateX, updateY,
								radius) || (0 == getFade(ship.time))) {
							ships.remove(ship);
						}
					}
					
					// update bomb
					for (Bomb bomb : bombs) {
						if (withIn(bomb.x, bomb.y, updateX, updateY,
								radius) || (0 == getFade(bomb.time))) {
							bombs.remove(bomb);
						}
					}
					break;

				case "MINES":
					count = Integer.parseInt(strs[++i]);

					for (int j = 0; j < count; j++) {
						String owner = strs[++i];
						double x = Double.parseDouble(strs[++i]);
						double y = Double.parseDouble(strs[++i]);
						String id = Mine.constructId(x, y);
						Mine m = null;
						m = mines.get(id);
						if (null == m) {
							m = new Mine(owner, x, y);
						}
						m.owner = owner;
						mines.put(id, m);
					}

					break;

				case "PLAYERS":
					// only save players within sight
					count = Integer.parseInt(strs[++i]);
					for (int j = 0; j < count; j++) {
						double x = Double.parseDouble(strs[++i]);
						double y = Double.parseDouble(strs[++i]);
						double dx = Double.parseDouble(strs[++i]);
						double dy = Double.parseDouble(strs[++i]);
						Ship ship = new Ship(x, y, dx, dy);
						ships.add(ship);
					}

					break;

				case "BOMBS":
					count = Integer.parseInt(strs[++i]);
					for (int j = 0; j < count; j++) {
						double x = Double.parseDouble(strs[++i]);
						double y = Double.parseDouble(strs[++i]);
						Bomb bomb = new Bomb(x,y);
						bombs.add(bomb);
					}
					break;

				default:
					// System.out.println("unknown config " + strs[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {

		// update fog
		long curTime = System.currentTimeMillis();
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				int b = (int) (255 * getFade(fog[y][x]));
				g.setColor(new Color(b, b, b));
				g.fillRect(x, y, 1, 1);
			}
		}


		ourShip.draw(g);
		for (Ship ship : ships) {
			ship.draw(g);
		}

		for (Mine mine : mines.values()) {
			mine.draw(g);
		}

		for (Bomb bomb : bombs) {
			bomb.draw(g);
		}
	}

	public static double transX(double x) {
		if (x < 0)
			return x + mapWidth;
		if (x >= mapWidth)
			return x - mapWidth;
		return x;
	}

	public static double transY(double y) {
		if (y < 0)
			return y + mapHeight;
		if (y >= mapWidth)
			return y - mapHeight;
		return y;
	}

	public static boolean withIn(double x1, double y1, double x2, double y2,
			double dist) {
		return dist * dist > (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}

	public static double getFade(long time) {
		long diff = System.currentTimeMillis() - time;
		long c = Math.min(FADE_SPEED, Math.max(FADE_SPEED - diff, 0));
		return (double) c / FADE_SPEED;
	}
}
