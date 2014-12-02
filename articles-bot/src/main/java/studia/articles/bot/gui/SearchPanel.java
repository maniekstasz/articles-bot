package studia.articles.bot.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.ArrayUtils;

import studia.articles.bot.searcher.Parameter;
import studia.articles.bot.searcher.SearchQueryBuilder;

public class SearchPanel extends JPanel {

	private static final long serialVersionUID = -2523191301599186668L;
	private Map<Parameter, JComponent> parFieldMap = new TreeMap<Parameter, JComponent>();
	private JButton searchButton = new JButton("Search");

	private GuiController guiController;

	SearchPanel(GuiController guiController) {
		this.guiController = guiController;

		this.setLayout(new WrapLayout());
		initializeMap();
		initializeSearchButton();
		addComponents();

	}

	private void initializeSearchButton() {
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SearchQueryBuilder builder = new SearchQueryBuilder();
				JComponent comp = null;
				String value = null;
				for (Parameter par : parFieldMap.keySet()) {
					comp = parFieldMap.get(par);
					if (comp instanceof JTextField)
						value = ((JTextField) comp).getText();
					else if (comp instanceof JComboBox<?>) {
						value = (String) ((JComboBox<String>) comp)
								.getSelectedItem();
					} else if (comp instanceof MultipleChooser) {
						value = ((MultipleChooser) comp).getValue();
					}

					if (!value.equals("")) {
						builder.append(par, value);
					}
				}

				guiController.search(builder);
		

			}
		});

	}

	private void addComponents() {
		addFields();
		add(searchButton);
	}

	private void initializeMap() {
		for (Parameter par : Parameter.values()) {
			JComponent comp = null;

			List<String> list = par.list();

			if (list == null) {
				comp = new JTextField();

			} else {
				
				String[] array = list.toArray(new String[list.size()]);
				array = ArrayUtils.addAll(new String[]{""}, array);
				if (!par.isMultipleSelect()) {
					comp = new JComboBox<String>(array);
				} else {
					comp = new MultipleChooser(list);
				}
			}

			parFieldMap.put(par, comp);
		}

	}

	private void addFields() {
		for (Parameter par : parFieldMap.keySet()) {
			JPanel temp = new JPanel();
			temp.setBackground(Color.LIGHT_GRAY);
			temp.add(new JLabel(par.toString().replace("_", " ")));

			JComponent field = parFieldMap.get(par);
			field.setPreferredSize(new Dimension(100, 20));
			temp.add(field);
			add(temp);
		}

	}

}
