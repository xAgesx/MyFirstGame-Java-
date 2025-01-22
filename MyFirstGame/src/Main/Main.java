package Main;

import javax.swing.JFrame;

public class Main {
	public static JFrame window ;
	public static void main(String[] args) {
		
		
		 window = new JFrame();
		

		//window.setUndecorated(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("FirstGame");
		window.setLocation(400,100);
		window.setResizable(false);
		window.setVisible(true);
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		//CONFIG
		gamePanel.config.loadConfig();
		
		
		
		window.pack();
		
		gamePanel.SetupGame();
		gamePanel.StartGameThread();
		
	}

}
