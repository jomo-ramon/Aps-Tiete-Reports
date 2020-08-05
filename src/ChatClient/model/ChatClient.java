package ChatClient.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import ChatClient.controller.Controller;

public class ChatClient {
	private final String serverName;
	private final int port;
	private Socket cSocket;
	private PrintWriter serverOut;
	private BufferedReader reader;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ArrayList<Usuario> user = new ArrayList<>();
	private Controller con;
	private ClientListener listener;
	
	public ChatClient(String server, int port) {
		this.serverName = server;
		this.port = port;
		
	}
	
	public void setCon(Controller ct) {
		this.con = ct;
	}
	
	public ArrayList<Usuario> getOnline(){
		return user;
	}
	
	public void connect() {
		try {
			this.cSocket = new Socket(serverName, port);
			this.serverOut = new PrintWriter(cSocket.getOutputStream(), true);
			this.reader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void cadastro(String dados[]) throws IOException {
		String data = "cadastro "; 
		data += String.join(" ", dados);
		data += "\n";
		System.out.println(data);
		serverOut.println(data);
		String response = reader.readLine();
		System.out.println(response);
	}
	
	public boolean login(String[] token) throws IOException, ClassNotFoundException {
		boolean login;
		String cmd = "login " + token[0] + " " + token[1] + "\n";
		serverOut.println(cmd);
		String response = reader.readLine();
		System.out.println(response);
		
		if("Login successful".equalsIgnoreCase(response)) {
			login = true;
			in = new ObjectInputStream(cSocket.getInputStream());
			user = (ArrayList<Usuario>) in.readObject();
			for(Usuario usr : user) System.out.println(usr.toString());
			listener = new ClientListener(cSocket, this);
			listener.start();
		} else {
			login = false;
		}
		return login;
	}
	
	public void sendMsg(String[] msg) throws IOException {
		String cmd = "msg" + " " + msg[1] + " " + msg[2] + " " + msg[0] + "\n";
		serverOut.println(cmd);
	}

	public void messageReceived(String[] msg) {
		con.messageReceived(msg);
	}
	
	public void updateOnline(ArrayList<Usuario> usrs) {
		con.sendUsers(usrs);
	}
	
}
