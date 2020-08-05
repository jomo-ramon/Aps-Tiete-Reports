package ChatClient.controller;

import ChatClient.model.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Controller {
	
	private ChatClient chatClient;
	private ViewController viewCon;

	
	
	
	public Controller(ChatClient c) throws NoSuchAlgorithmException {
		this.chatClient = c;
		c.setCon(this);
	}
	
	public ArrayList<Usuario> getOnline(){
		return chatClient.getOnline();
	}
	public void setViewCon(ViewController con) {
		this.viewCon = con;
	}
	
	
	public boolean handleLogin(String[] token) throws ClassNotFoundException, IOException {
		chatClient.connect();
		return(chatClient.login(token));
	}
	
	
	

	public void handleCadastro(String[] dados) throws NoSuchAlgorithmException, IOException {
		chatClient.connect();
		chatClient.cadastro(dados);
		System.out.println("Usuario Criado com sucesso \n");
	}
	
	public void sendUsers(ArrayList<Usuario> usr) {
		viewCon.onlineUsers(usr);
	}
	
	public void sendMsg(String[] msg) throws IOException {
		chatClient.sendMsg(msg);
	}
	
	public void messageReceived(String[] msg) {
		viewCon.addMessage(msg);
	}
}
