package studia.articles.bot.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import studia.articles.bot.model.Document;

public class ResultsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6702190242865534412L;
	private GuiController guiController;
	private DefaultTableModel myTableModel;

	@SuppressWarnings("serial")
	private void init() {
		myTableModel = new DefaultTableModel() {
			public Class<?> getColumnClass(int colIndex) {

				return getValueAt(0, colIndex).getClass();

			}

		};

		myTableModel.addColumn("title");
		myTableModel.addColumn("authors");
		myTableModel.addColumn("year");
		myTableModel.addColumn("save");

		final JTable table = new JTable(myTableModel);
		TableColumn column = null;

		column = table.getColumnModel().getColumn(3);
		column.setMaxWidth(40);
		column.setMinWidth(40);
		column = table.getColumnModel().getColumn(2);
		column.setMaxWidth(40);
		column.setMinWidth(40);

		table.setFillsViewportHeight(true);
		this.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void addResults(List<Document> docList) {

		myTableModel.setRowCount(0);
		for (Document doc : docList) {

			myTableModel.addRow(new Object[] { doc.getTitle(), doc.getAuthor(),
					doc.getYear(), new Boolean(false) });
		}

	}

	public ResultsPanel(GuiController guiController) {
		this.guiController = guiController;
		init();
	}

}
