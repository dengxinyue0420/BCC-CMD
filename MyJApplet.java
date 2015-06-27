import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JApplet;

public class MyJApplet extends JApplet {
	private static String username = "a";// "CMD";
	private static String password = "a";// "i1ev2mtpfw";
	private static String server = "127.0.0.1";
	private static int port = 17429;
	BattleField bf;

	public void init() {

		try {
			// connect to server
			PrintWriter out = null;
			BufferedReader in = null;
			Socket s = new Socket(server, port);
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			out.println(username + " " + password);

			out.println("CONFIGURATIONS");
			String config = in.readLine();
			bf = new BattleField(username);
			bf.setConfigurations(config);

			out.println("ACCELERATE 0 1");
			in.readLine();

			setSize((int)bf.mapWidth, (int)bf.mapHeight);

			UI wholePanel = new UI(bf, out, in, username);
			getContentPane().add(wholePanel);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
