package _02_Chat_Application;

import static javax.swing.JOptionPane.*;

public class ChatApp {
	public static void main(String[] args) {
		int response = showConfirmDialog(null, "Would you like to host a connection?", "Chat App", YES_NO_OPTION);
		if (response == YES_OPTION) new ChatGUI("Chat Server", new ChatServer()).start();
		else new ChatGUI("Chat Client", new ChatClient()).start();
	}
}