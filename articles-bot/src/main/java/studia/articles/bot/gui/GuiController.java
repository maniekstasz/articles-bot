package studia.articles.bot.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import studia.articles.bot.controller.Controller;
import studia.articles.bot.controller.ControllerListenerImp;
import studia.articles.bot.model.Document;
import studia.articles.bot.searcher.SearchQueryBuilder;

public class GuiController {
	JFrame frame;
	SearchPanel searchPanel;
	ResultsPanel resultsPanel;
	Controller controller = new Controller(new ControllerListenerImp());

	public GuiController() {
		init();
		frame.setVisible(true);
	}

	private void init() {
		frame = new JFrame();
		frame.setSize(new Dimension(1120, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("title");
		initMenu();
		searchPanel = new SearchPanel(this);
		resultsPanel = new ResultsPanel(this);
		frame.setLayout(new BorderLayout());
		frame.add(searchPanel, BorderLayout.PAGE_START);
		frame.add(resultsPanel, BorderLayout.CENTER);

	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Settings");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Proxy settings");
		mnNewMenu.add(mntmNewMenuItem);
	}

	public void search(SearchQueryBuilder searchQueryBuilder) {
		int r = controller.search(searchQueryBuilder);
		List<Document> list = new ArrayList<Document>();
		if (r > 0) {
			
			while (controller.hasNext()) {
				list.addAll(controller.next());
			}
			

		}
		resultsPanel.addResults(list);
	}

}
