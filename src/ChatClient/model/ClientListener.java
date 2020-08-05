package ChatClient.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import ChatClient.controller.Controller;

public class ClientListener extends Thread{
	private Socket socket;
	private BufferedReader clientIn;
	private ArrayList<Usuario> users;
	private Message msg;
	private ChatClient c;
	
	public ClientListener(Socket s, ChatClient c) {
	this.socket = s;	
	this.c = c;
	}
	
	@Override public void  run(){
		try {
			clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String text;
		try {
			while((text =  clientIn.readLine()) != null) {
				 String[] msg = text.split(" ", 3);
				 
				 if(!(msg.length <3)) {
					 for(int i= 0; i<msg.length; i++) {
						 System.out.println("(["+i+"]"+msg[i]+")");
					 }
					 String[] msgDef = {msg[2], msg[0], msg[1]};
					 System.out.println("MsgDef: ");
					 for(int i= 0; i<msgDef.length; i++) {
						 System.out.println("(["+i+"]"+msgDef[i]+")");
					 }
					 c.messageReceived(msgDef);
				 }
				 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
