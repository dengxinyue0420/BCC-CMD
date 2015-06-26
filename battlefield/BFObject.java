package battlefield;

public class BFObject {
	long id;
	double x;
	double y;

	public BFObject() {
		id = Util.getNextId();
		x = 0;
		y = 0;
	}

	public BFObject(double x, double y) {
		id = Util.getNextId();
		this.x = x;
		this.y = y;
	}
}
