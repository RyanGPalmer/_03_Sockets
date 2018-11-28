package _02_Chat_Application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ChatServer extends ChatClient {
	private ServerSocket server;

	@Override
	protected void connect() throws IOException {
		server = new ServerSocket(PORT, 0, InetAddress.getLocalHost());
		connection = server.accept();
	}

	@Override
	protected void disconnect() throws IOException {
		super.disconnect();
		server.close();
	}
}