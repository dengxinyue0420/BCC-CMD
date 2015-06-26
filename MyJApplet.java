import javax.swing.JApplet;

public class MyJApplet extends JApplet {
	private static final long serialVersionUID = -4259461589671076431L;

	public void init() {
		UI wholePanel = new UI();
		getContentPane().add(wholePanel);
		setSize(500, 500);
	}
}
