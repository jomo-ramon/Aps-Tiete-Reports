package ChatServer.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

import ChatServer.dao.Dao;
import ChatServer.dao.DaoPostgres;
import ChatServer.dao.DataDecod;
import ChatClient.model.Usuario;

public class ServerWorker extends Thread {
	private DataDecod dataCrypt;
	private Dao dao = new DaoPostgres();
	private Socket clientSocket;
	private PrintWriter outPtStream;
	private BufferedReader reader;
	private Server server;
	private Usuario currentUser;
	
	public ServerWorker(Server server, Socket aClientSocket) {
		this.server = server;
		this.clientSocket = aClientSocket;
	}
	
	public Usuario getUsuario() {
		return this.currentUser;
	}
	
	@Override
	public void run(){
		
			try {
				handleClient();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void handleClient() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
		this.outPtStream  = new PrintWriter(clientSocket.getOutputStream(), true);
		reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
		String line;
		while ((line  =  reader.readLine()) != null) {
			
			String[] tokens = line.split(" ");
			String cmd = tokens[0];
			if("sair".equalsIgnoreCase(cmd)) {
				break;
			} else if ("login".equalsIgnoreCase(cmd)) {
				handleLogin(outPtStream, tokens);
				
			}
			if ("cadastro".equalsIgnoreCase(cmd)) {
				handleCadastro(outPtStream, tokens);
			} else if ("msg".equalsIgnoreCase(cmd)) {
				System.out.println("Server got a message: " + line);
				String[] message = line.split(" ", 4);
				handleMessaging(message);
			}
		}
		System.out.println("closing socket \n");		
		clientSocket.close();
	}
	
	private void handleMessaging(String[] tokens) {
		ArrayList<ServerWorker> workerList = server.getWorkerList();
		for(ServerWorker w : workerList) {
			if(w.getUsuario().getRa().equalsIgnoreCase(tokens[1])) {
				w.sendMessage(tokens);
			}
		}
		
	}

	private void sendMessage(String[] msg) {
		String message = msg[1] + " " + msg[2] + " " + msg[3] + "\n";
		System.out.println("Sending message to user: " + message);
		outPtStream.println(message);
	}

	public void send(ArrayList<Usuario> s) throws IOException {
		Iterator<Usuario> t = s.iterator();
		while(t.hasNext()) {
			Usuario usr = t.next();
			if (usr.equals(currentUser)) {
				t.remove();
			}
		}
		
		ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
		out.writeObject(s);
		
	}

	public void handleLogin(PrintWriter pt, String[] tokens) throws IOException {
		String token[] = new String[2];
		token[0] = tokens[1];
		token[1] = tokens[2];
		if (dao.validateLogin(token) == true) {
			pt.println("Login successful");
			currentUser = dao.searchUsuario(token);
		} else {
			 pt.println("Ra ou senha incorretos, tente novamente!");
		  }
		ArrayList<ServerWorker> workerList = server.getWorkerList();
		ArrayList<Usuario> users = new ArrayList<>();
		users = dao.getUsers();
		send(users);
		
	}
	
	public void handleCadastro(PrintWriter pt, String data[]) throws NoSuchAlgorithmException {
		Usuario user = new Usuario(data[1],data[2], data[3], data[4], data[5], data[6]);
		dataCrypt = new DataDecod();
		byte[] salt = dataCrypt.createSalt();
		String hashed = dataCrypt.generateHash(data[7], salt);
		dao.criaUsuario(user,hashed,salt);
		pt.println("Usuario Criado");
	}
}
