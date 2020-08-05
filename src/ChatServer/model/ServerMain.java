package ChatServer.model;

public class ServerMain {
	public void run() {
		final int port = 8818;
		Server server = new Server(port);
		server.start();
		
		
	}
}
