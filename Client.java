import util.MySocket;

public class Client {
	public static void main(String[] args) {
		Window window = new Window("Welcome");
		MySocket sock = window.connect();
		new Thread() {
			public void run() {
				String line;
				while ((line = sock.read()) != null) {
					window.write(line);
				}
			}
		}.start();
	}
}
