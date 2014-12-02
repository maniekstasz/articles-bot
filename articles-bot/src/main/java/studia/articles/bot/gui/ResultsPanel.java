package studia.articles.bot.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import studia.articles.bot.model.Document;

public class ResultsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6702190242865534412L;
	private GuiController guiController;
	private DefaultTableModel myTableModel;
	private JButton nextButton;
	private JButton prevButton;
	private JButton saveBibtex;
	private JButton selectAll;
	private JButton downloadPdf;
	private JLabel infoLabel;
	private int pageCounter = 1;
	private int numberOfResults = 0;
	private List<Document> currentlyDisplayedDocuments = new ArrayList<Document>();

	public ResultsPanel(GuiController guiController) {
		this.guiController = guiController;
		initMyTableModel(new String[] { "Title", "authors", "year", "save?" });
		this.setLayout(new BorderLayout());
		add(createNavigatePanel(), BorderLayout.NORTH);
		add(createTableScrollPane(), BorderLayout.CENTER);
	}

	private void fillInfoLabel() {
		if (numberOfResults > 0) {
			int firstResult = (pageCounter - 1) * 100 + 1;
			int lastResult = pageCounter * 100;
			if (lastResult > numberOfResults) {
				lastResult = numberOfResults;
			}
			infoLabel.setText(firstResult + "-" + lastResult + " of "
					+ numberOfResults);
		} else {
			infoLabel.setText("no results");
		}
	}

	public void addResults(List<Document> docList) {
		myTableModel.setRowCount(0);
		currentlyDisplayedDocuments = docList;

		for (Document doc : docList) {

			myTableModel.addRow(new Object[] { doc.getTitle(), doc.getAuthor(),
					doc.getYear(), new Boolean(false) });
		}

	}

	public void setNewSearch(int n) {

		myTableModel.setRowCount(0);
		pageCounter = 1;
		nextButton.setEnabled(false);
		prevButton.setEnabled(false);

		numberOfResults = n;
		fillInfoLabel();

		if (n > 0) {
			saveBibtex.setEnabled(true);
			selectAll.setEnabled(true);
			if (guiController.hasNext()) {
				nextButton.setEnabled(true);
			}

		}

	}

	private void initMyTableModel(String[] columnNames) {

		myTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

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

		};

		for (String s : columnNames) {
			myTableModel.addColumn(s);
		}

	}

	private JPanel createNavigatePanel() {
		JPanel navigatePanel = new JPanel();
		navigatePanel.setBackground(Color.gray);

		selectAll = new JButton("select all");
		selectAll.setEnabled(false);
		selectAll.addActionListener(this);

		downloadPdf = new JButton("download pdf files");
		downloadPdf.addActionListener(this);

		saveBibtex = new JButton("save selected");
		saveBibtex.setEnabled(false);
		saveBibtex.addActionListener(this);

		prevButton = new JButton("PREV");
		prevButton.setEnabled(false);
		prevButton.addActionListener(this);

		nextButton = new JButton("NEXT");
		nextButton.setEnabled(false);
		nextButton.addActionListener(this);

		infoLabel = new JLabel();
		navigatePanel.add(selectAll);
		navigatePanel.add(saveBibtex);
		navigatePanel.add(downloadPdf);
		navigatePanel.add(prevButton);
		navigatePanel.add(nextButton);
		navigatePanel.add(infoLabel);

		return navigatePanel;
	}

	private JScrollPane createTableScrollPane() {
		JTable table = new JTable(myTableModel);
		TableColumn column = null;

		column = table.getColumnModel().getColumn(3);
		column.setMaxWidth(40);
		column.setMinWidth(40);
		column = table.getColumnModel().getColumn(2);
		column.setMaxWidth(40);
		column.setMinWidth(40);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					if (currentlyDisplayedDocuments.size() > row) {
						new DocumentInfoFrame(currentlyDisplayedDocuments
								.get(row));
					}
				}
			}
		});

		table.setFillsViewportHeight(true);
		return new JScrollPane(table);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		if (source.equals(saveBibtex)) {
			saveBibtex();
		} else if (source.equals(nextButton)) {
			next();
		} else if (source.equals(prevButton)) {
			prev();
		} else if (source.equals(selectAll)) {
			selectAll();
		} else if (source.equals(downloadPdf)) {
			downloadPdf();
		}

	}

	private void saveBibtex() {

		int i = 0;
		int count = 0;

		Vector vec = myTableModel.getDataVector();
		for (Object row : vec) {

			boolean save = (boolean) (((Vector) row).lastElement());
			if (save) {
				guiController.getController().save(
						currentlyDisplayedDocuments.get(i));
				count++;
			}
			i++;

		}
		if (count > 0) {
			
			try {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(guiController.getFrame());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					guiController.getController().saveToBibetex(
							file.getAbsolutePath());
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(guiController.getFrame(),
					"Not selected item.", "", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void selectAll() {
		int lastColumn = myTableModel.getColumnCount() - 1;
		for (int i = 0; i < myTableModel.getRowCount(); i++) {

			myTableModel.setValueAt(true, i, lastColumn);

		}
	}

	private void downloadPdf() {
		
		
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("select bibtex file");

		int returnVal = fc.showOpenDialog(guiController.getFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File bibtexFile = fc.getSelectedFile();
			fc.setDialogTitle("select dictionary to save pdf files");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			returnVal = fc.showOpenDialog(guiController.getFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File dirPath = fc.getSelectedFile();
				guiController.getController()
						.downloadFiles(bibtexFile.getAbsolutePath(),
								dirPath.getAbsolutePath());
			}

		}
	}

	private void next() {
		guiController.next();
		pageCounter++;
		fillInfoLabel();
		nextButton.setEnabled(guiController.hasNext());
		prevButton.setEnabled(guiController.hasPrev());
	}

	private void prev() {
		guiController.prev();
		pageCounter--;
		fillInfoLabel();
		prevButton.setEnabled(guiController.hasPrev());
		nextButton.setEnabled(guiController.hasNext());
	}

}