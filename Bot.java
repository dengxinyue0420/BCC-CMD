import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Bot implements Runnable {
	private String username;
	private String password;
	private String server;
	private int port;
	Random rand = new Random();

	public Bot(String username, String password, String server, int port) {
		this.username = username;
		this.password = password;
		this.server = server;
		this.port = port;
		rand.setSeed(System.currentTimeMillis());
	}

	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			PrintWriter out = null;
			BufferedReader in = null;
			Socket s = new Socket(server, port);
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			out.println(username + " " + password);

			while (true) {
				Thread.sleep(1000 * (1 + rand.nextInt(5)));
				double direction = rand.nextDouble() % (Math.PI * 2);
				out.println("ACCELERATE " + direction + " 1");
				in.readLine();
				Thread.sleep(500 * (1 + rand.nextInt(3)));
				out.println("BRAKE");
				in.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
