package studia.articles.bot.gui;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

public class Launcher {
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new StartSetting();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
