package battlefield;

import java.util.HashMap;
import java.util.Map;

public class BattleField {
	Ship ourShip = new Ship();
	Map<Long, Ship> ships = new HashMap<Long, Ship>();
	Map<Long, Mine> mines = new HashMap<Long, Mine>();
	Map<Long, Bomb> bombs = new HashMap<Long, Bomb>();

	// STATUS_OUT 250.0 250.0 0.0 0.0 MINES 9 -- 237.69780038539236
	// 358.98849031851864 -- 345.98200138424863 214.6881476133634 --
	// 128.29130871113114 308.4784622090901 -- 233.70554048041464
	// 331.8099882734012 -- 188.1181174651368 274.9145735227218 --
	// 244.21553733426404 105.70353728817227 -- 396.738865344971
	// 264.2801845259041 -- 253.28207962136761 170.50491263269208 --
	// 213.60486103348958 225.20989627920486 PLAYERS 9 250.0 250.0 0.0 0.0 250.0
	// 250.0 0.0 0.0 250.0 250.0 0.0 0.0 250.0 250.0 0.0 0.0 250.0 250.0 0.0 0.0
	// 250.0 250.0 0.0 0.0 250.0 250.0 0.0 0.0 250.0 250.0 0.0 0.0 250.0 250.0
	// 0.0 0.0 BOMBS 0
	public void updateStatus(String status) {

	}
}
