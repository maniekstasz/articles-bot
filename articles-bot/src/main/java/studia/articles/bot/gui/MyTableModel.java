package studia.articles.bot.gui;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public MyTableModel(String[] columns){
		for (String s : columns) {
			this.addColumn(s);
		}
	}
	
	
	
	public Class<?> getColumnClass(int colIndex) {

		return getValueAt(0, colIndex).getClass();

	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == this.getColumnCount() - 1) {
			return true;
		}
		return false;
	}

}
