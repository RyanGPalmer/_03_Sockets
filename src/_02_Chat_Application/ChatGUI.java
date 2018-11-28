package _02_Chat_Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatGUI extends WindowAdapter {
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final String CHAT_AREA_FORMAT = "<html>%s</html>";

	private final ChatClient client;
	private final JFrame frame;
	private final List<ChatMessage> messageList = new ArrayList<>();
	private JLabel chatArea;

	public ChatGUI(String title, ChatClient client) {
		this.client = client;
		frame = new JFrame(title);
	}

	public void start() {
		try (LoadingScreen l = new LoadingScreen(frame.getTitle())) {
			constructGUI();
			client.setConnectionListener(this::onConnection);
			client.start();
		}
	}

	private void constructGUI() {
		frame.addWindowListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);

		JTextField messageField = new JTextField();
		messageField.setBounds(5, HEIGHT - 5 - 50, 400, 30);
		frame.add(messageField);

		JButton sendMessage = new JButton("Send");
		sendMessage.addActionListener(e -> sendMessage(messageField));
		sendMessage.setBounds(WIDTH - 5 - 100, HEIGHT - 5 - 50, 100, 30);
		frame.add(sendMessage);

		chatArea = new JLabel();
		chatArea.setBounds(5, 5, WIDTH - 10, HEIGHT - 70);
		chatArea.setBackground(Color.WHITE);
		chatArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		chatArea.setVerticalAlignment(JLabel.BOTTOM);
		frame.add(chatArea);

		frame.setVisible(true);
	}

	private void onConnection() {
		addMessageToChatArea(false, "<strong><i>CONNECTED</i></strong>");
		startMessageReceiver();
	}

	private void onDisconnection() {
		addMessageToChatArea(false, "<strong><i>DISCONNECTED</i></strong>");
	}

	private void startMessageReceiver() {
		new Thread(this::receiveMessage).start();
	}

	private void updateChatArea() {
		StringBuilder sb = new StringBuilder();
		messageList.forEach(sb::append);
		chatArea.setText(String.format(CHAT_AREA_FORMAT, sb.toString()));
	}

	private void addMessageToChatArea(boolean self, String message) {
		messageList.add(new ChatMessage(self, message));
		updateChatArea();
	}

	private void sendMessage(JTextField messageField) {
		try {
			String message = messageField.getText();
			if (message == null || message.length() < 1) return;
			if (client.output != null) client.output.writeUTF(message);
			addMessageToChatArea(true, message);
			messageField.setText("");
		} catch (IOException e) {
			System.err.println("Encountered an error while attempting to send a message.");
			e.printStackTrace();
		}
	}

	private void receiveMessage() {
		System.out.println("Message receiver started.");
		while (true) {
			try {
				addMessageToChatArea(false, client.input.readUTF());
			} catch (IOException | NullPointerException e) {
				onDisconnection();
				break;
			}
		}
		System.out.println("Message receiver closed.");
	}

	@Override
	public void windowClosing(WindowEvent e) {
		client.stop();
	}
}