package battlefield;

import java.util.HashMap;
import java.util.Map;

public class BattleField {
	Ship ourShip = new Ship();
	Map<Long, Ship> ships = new HashMap<Long, Ship>();
	Map<String, Mine> mines = new HashMap<String, Mine>();
	Map<Long, Bomb> bombs = new HashMap<Long, Bomb>();
	
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
	
	
	// commands and response
	public static final String CONFIGURATIONS = "CONFIGURATIONS";
	public static final String CONFIGURATIONS_OUT = "CONFIGURATIONS_OUT";
	public static final String STATUS = "STATUS";
	public static final String STATUS_OUT= "STATUS_OUT";


	/*
	CONFIGURATIONS_OUT 
	MAPWIDTH 500 
	MAPHEIGHT 500 
	CAPTURERADIUS 5.0
	VISIONRADIUS 150.0 
	FRICTION 0.99 
	BRAKEFRICTION 0.987 
	BOMBPLACERADIUS 50.0
	BOMBEFFECTRADIUS 15.0 
	BOMBDELAY 100 
	BOMBPOWER 15.0 
	SCANRADIUS 50.0
	SCANDELAY 200
	*/
	public static void setConfigurations(String config) {
		if (null == config)
			return;
		
		String[] strs = config.split(" ");
		
		if (null == strs[0] || !strs[0].equals(CONFIGURATIONS_OUT)) {
			System.out.println("ERROR, " + CONFIGURATIONS_OUT + " != " + strs[0]);
			return;
		}
		for (int i = 1; i < strs.length; i++) {
			switch (strs[i]) {
			case "MAPWIDTH":
			 mapWidth = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "MAPHEIGHT":
			 mapHeight = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "CAPTURERADIUS":
			 captureRadius = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "VISIONRADIUS":
			 visionRadius = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "FRICTION":
			 friction = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "BRAKEFRICTION":
			 brakeFriction = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "BOMBPLACERADIUS":
			 bombPlaceRadius = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "BOMBEFFECTRADIUS":
			 bombEffectRadius = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "BOMBDELAY":
			 bombDelay = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "BOMBPOWER":
			 bombPower = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "SCANRADIUS":
			 scanRadius = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			case "SCANDELAY":
			 scanDelay = Double.parseDouble(strs[i+1]);
			 i++;
			 break;
			 default:
				 System.out.println("unknown config " + strs[i]);
			}
		}
	}

	/*
STATUS_OUT
326.91078698423473
217.60640444867556
0.44738547863468514
0.004474003920286199
MINES
7
a
292.56004472542145
215.41437081643727
--
187.5864342555274
164.0505663012028
a
279.7800961213231
324.06421774739835
--
378.0267439074415
155.08650674714886
--
235.5130174573461
167.27701877092198
--
387.64082877701645
123.56252006096358
--
297.9054200901543
159.3474118621444
PLAYERS
9
250.0
250.0
0.0
0.0
250.0
250.0
0.0
0.0
250.0
250.0
0.0
0.0
250.0
250.0
0.0
0.0
250.0
250.0
0.0
0.0
250.0
250.0
0.0
0.0
250.0
250.0
0.0
0.0
250.0
250.0
0.0
0.0
250.0
250.0
0.0
0.0
BOMBS
0

	*/
	public void updateStatus(String status) {
		if (null == status)
			return;
		
		try {
		String[] strs = status.split(" ");
		
		if (null == strs[0] || !strs[0].equals(STATUS_OUT)) { 
			System.out.println("ERROR, " + STATUS_OUT + " != strs[0]");
			return;
		}

		int count;
		for (int i = 0; i < strs.length; i++) {
			switch (strs[i]) {
			case "STATUS_OUT":
				// x y dx dy
				ourShip.x = Double.parseDouble(strs[i]);
				ourShip.y = Double.parseDouble(strs[i+1]);
				ourShip.dx = Double.parseDouble(strs[i+2]);
				ourShip.dy = Double.parseDouble(strs[i+3]);
				i += 3;
			 break;

			case "MINES":
				count = Integer.parseInt(strs[++i]);

				for (int j = 0; j < count; j++) {
					String owner = strs[++i];
					double x = Double.parseDouble(strs[++i]);
					double y = Double.parseDouble(strs[++i]);
					String id = Mine.constructId(x, y);
					Mine m = null;
					if (!mines.containsKey(id)) {
						m = new Mine(owner, x, y);
					}
					m.owner = owner;
					mines.put(id, m);
				}

			 break;

			case "PLAYERS":
				count = Integer.parseInt(strs[++i]);
				// FIXME: don't consider players for now
				i+= count * 4;
			 break;

			case "BOMBS":
				count = Integer.parseInt(strs[++i]);
				// FIXME: don't consider players for now
				i+= count * 2;
			 break;

			 default:
				 System.out.println("unknown config " + strs[i]);
				
			}
			
		}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
