package ChatClient.view;

import ChatClient.model.Chat;
import ChatClient.model.Message;
import ChatClient.model.Usuario;
import javafx.scene.layout.FlowPane;

public class UsrPane extends FlowPane{
	private Usuario usr;
	private Chat cht;
	
	
	public UsrPane(Usuario usr) {
		this.usr = usr;
		cht = new Chat();
	}
	
	public Usuario getMembers() {
		return this.usr;
	}
	public Chat getChat() {
		return this.cht;
	}
	
	public void addMsg(Message msg) {
		cht.addMsg(msg);
	}
}
