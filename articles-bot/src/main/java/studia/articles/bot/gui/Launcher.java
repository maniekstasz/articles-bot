
package studia.articles.bot.gui;

import java.awt.EventQueue;

public class Launcher {
public static void main(String[] args) {


	 EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new GuiController();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
}
}
