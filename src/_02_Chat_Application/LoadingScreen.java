package _02_Chat_Application;

import javax.swing.*;

public class LoadingScreen extends JFrame implements AutoCloseable {
	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;

	public LoadingScreen(String title) {
		super(title);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);

		JLabel text = new JLabel("Loading " + title + "...");
		text.setBounds(0, 0, WIDTH, HEIGHT);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setVerticalAlignment(JLabel.CENTER);
		add(text);

		setUndecorated(true);
		setVisible(true);
	}

	@Override
	public void close() {
		setVisible(false);
		dispose();
	}
}
