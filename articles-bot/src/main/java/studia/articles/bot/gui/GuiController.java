package studia.articles.bot.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
	final static private String TITLE="title";
	JFrame frame;
	SearchPanel searchPanel;
	ResultsPanel resultsPanel;
	Controller controller = new Controller(new ControllerListenerImp());

	public GuiController() {
		init();
		initMenu();
		frame.setVisible(true);
	}

	private void init() {
		frame = new JFrame();
		frame.setSize(new Dimension(1200, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(TITLE);
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
		int n = controller.search(searchQueryBuilder);
		resultsPanel.setNewSearch(n);
		if (n > 0) {
			next();
		}

	}

	public void next() {
		resultsPanel.addResults(controller.next());
	}

	public void prev() {
		resultsPanel.addResults(controller.prev());
	}

	public boolean hasNext() {
		return controller.hasNext();
	}

	public boolean hasPrev() {
		return controller.hasPrev();
	}

	public Controller getController() {
		// TODO Auto-generated method stub
		return this.controller;
	}

	public JFrame getFrame() {
		return frame;
	}

}
