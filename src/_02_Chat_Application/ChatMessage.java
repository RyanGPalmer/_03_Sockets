package _02_Chat_Application;

public class ChatMessage {
	private static final String MESSAGE_FORMAT = "<strong><span style=\"color:%s\">%s</span></strong>: %s<br/>";

	public final boolean self;
	public final String message;

	public ChatMessage(boolean self, String message) {
		this.self = self;
		this.message = message;
	}

	@Override
	public String toString() {
		String sender = self ? "Me" : "Friend";
		String color = self ? "black" : "red";
		return String.format(MESSAGE_FORMAT, color, sender, message);
	}
}
