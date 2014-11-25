package studia.articles.bot.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.StringJoiner;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

public class MultipleChooser extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPopupMenu menu;

	public MultipleChooser(List<String> list) {

		this.setText("SELECT");
		menu = new JPopupMenu();
		for (String s : list) {
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(s);
			item.setUI(new StayOpenCheckBoxMenuItemUI());
			item.setSelected(true);
			menu.add(item);
		}

		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				menu.show(MultipleChooser.this, 0,
						MultipleChooser.this.getHeight());

			}

		});
	}

	public String getValue() {
		StringJoiner joiner = new StringJoiner(",");
		for (Component comp : menu.getComponents()) {
			JMenuItem item = (JMenuItem) comp;
			if (item.isSelected()) {
				joiner.add(item.getText());
			}
		}
		String joined = joiner.toString();
		
		return joined;
	}

}

class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {

	@Override
	protected void doClick(MenuSelectionManager msm) {
		menuItem.doClick(0);
	}

	public static ComponentUI createUI(JComponent c) {
		return new StayOpenCheckBoxMenuItemUI();
	}
}
