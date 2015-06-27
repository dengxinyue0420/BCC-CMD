package battlefield;

public class BFObject {
	long id;
	double x;
	double y;
	double dx;
	double dy;

	public BFObject() {
		id = Util.getNextId();
		x = 0;
		y = 0;
		dx = 0;
		dy = 0;
	}

	public BFObject(double x, double y, double dx, double dy) {
		id = Util.getNextId();
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
}
