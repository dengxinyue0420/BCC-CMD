package battlefield;

import java.util.HashMap;
import java.util.Map;

public class BattleField {
	Ship ourShip = new Ship();
	Map<Long, Ship> ships = new HashMap<Long, Ship>();
	Map<Long, Mine> mines = new HashMap<Long, Mine>();
	Map<Long, Bomb> bombs = new HashMap<Long, Bomb>();

}
