package ChatClient.model;

public class Message {
	private String sender;
	private String content;
	private String sendTo;
	
	public Message(String msg, String sendTo, String sender) {
		this.content = msg;
		this.sendTo = sendTo;
		this.sender = sender;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String getSender() {
		return this.sender;
	}
	
	public String getSendTo() {
		return this.sendTo;
	}
	
	@Override
	public String toString() {
		return sender + ":" + "\n"
				+ content;
	}
}
