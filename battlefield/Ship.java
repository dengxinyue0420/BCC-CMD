package battlefield;

public class Ship extends BFObject {
	public String player;
	public double direction;
	public double velocity;

	public Ship() {
		this.player = null;
		this.direction = 0;
		this.velocity = 0;
	}
}
