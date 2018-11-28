package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ChatClient {
	protected static final int PORT = 8080;

	protected Socket connection;
	protected DataInputStream input;
	protected DataOutputStream output;
	private Runnable connectionListener;

	public final void start() {
		new Thread(() -> {
			try {
				System.out.println("Establishing connection.");
				connect();
				System.out.println("Connection established!");
				input = new DataInputStream(connection.getInputStream());
				output = new DataOutputStream(connection.getOutputStream());
				System.out.println("Input and output streams constructed.");
				if (connectionListener != null) connectionListener.run();
			} catch (IOException e) {
				System.err.println("Encountered an error while starting application.");
			}
		}).start();
	}

	protected void connect() throws IOException {
		connection = new Socket(InetAddress.getLocalHost(), PORT);
	}

	public final void stop() {
		try {
			System.out.println("Closing connection.");
			disconnect();
			System.out.println("Connection closed.");
		} catch (IOException e) {
			System.err.println("Encountered an error while shutting down.");
			e.printStackTrace();
		}
	}

	public void setConnectionListener(Runnable r) {
		connectionListener = r;
	}

	protected void disconnect() throws IOException {
		connection.close();
	}
}