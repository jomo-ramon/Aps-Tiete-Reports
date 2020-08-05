package ChatClient.model;

import java.util.ArrayList;

public class Chat {
	private ArrayList<Message> messages;
	
	public Chat() {
		messages = new ArrayList<>();
	}
	
	public ArrayList<Message> getMessages(){
		return this.messages;
	}
	
	public void addMsg(Message msg) {
		messages.add(msg);
	}
	
	
}
