package ChatServer.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
	private final int port;
	private ArrayList<ServerWorker> workerList = new ArrayList<>();
	
	public Server(int port) {
		this.port = port;
	}
	
	public ArrayList<ServerWorker> getWorkerList(){
		return workerList;
	}
	@Override
	public void run(){
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			
			while(true) {
				System.out.println("Aceitando conexao");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Aceita");
				ServerWorker newClient = new ServerWorker(this, clientSocket);
				workerList.add(newClient);
				newClient.start();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
