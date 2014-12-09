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

import studia.articles.bot.controller.Controller;
import studia.articles.bot.model.Document;

public class ResultsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6702190242865534412L;
	private GuiController guiController;
	private MyTableModel myTableModel;
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
		myTableModel = new MyTableModel(new String[] { "Title", "authors",
				"year", "save?" });
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
		selectSaved();

	}

	public void setNewSearch(int n) {

		myTableModel.setRowCount(0);
		pageCounter = 1;
		nextButton.setEnabled(false);
		prevButton.setEnabled(false);
		saveBibtex.setEnabled(false);
		selectAll.setEnabled(false);

		numberOfResults = n;
		fillInfoLabel();

		if (n > 0) {
			saveBibtex.setEnabled(true);
			selectAll.setEnabled(true);
			System.out.println(n);
			if (n > 100) {
				nextButton.setEnabled(true);
			}

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
			public void mouseReleased(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				int col = target.getSelectedColumn();
				int lastColumn = myTableModel.getColumnCount() - 1;

				if (row >= currentlyDisplayedDocuments.size()) {
					return;
				}

				if (col == lastColumn) {
					Document clicked = currentlyDisplayedDocuments.get(row);
					if ((boolean) myTableModel.getValueAt(row, col)) {
						guiController.getController().save(clicked);
					} else {
						guiController.getController().delete(clicked);
					}
				}

				if (e.getClickCount() == 2 && col < lastColumn) {
					new DocumentInfoFrame(currentlyDisplayedDocuments.get(row),
							guiController);
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

		try {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save bibtex as");
			int returnVal = fc.showOpenDialog(guiController.getFrame());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				guiController.getController().saveToBibetex(
						file.getAbsolutePath());
				// *********************
				Object[] options = { "Yes, please", "No, thanks" };
				int n = JOptionPane
						.showOptionDialog(guiController.getFrame(),
								"Would you like to download pdf files? ", "",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (n == 0) {
					if (isPermited()) {
						selectDirAndDownload(file.getAbsolutePath());
					}
				}

			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void selectAll() {
		int lastColumn = myTableModel.getColumnCount() - 1;
		Controller controller = guiController.getController();
		for (int i = 0; i < myTableModel.getRowCount(); i++) {

			myTableModel.setValueAt(true, i, lastColumn);
			controller.save(currentlyDisplayedDocuments.get(i));

		}
	}

	private void downloadPdf() {
		if (isPermited()) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Select bibtex file");

			int returnVal = fc.showOpenDialog(guiController.getFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File bibtexFile = fc.getSelectedFile();

				selectDirAndDownload(bibtexFile.getAbsolutePath());

			}
		}
	}

	private void selectDirAndDownload(String bibtexPath) {

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select directory to save pdf files");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(guiController.getFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File dirPath = fc.getSelectedFile();
			guiController.getController().downloadFiles(bibtexPath,
					dirPath.getAbsolutePath());
		}
	}

	private boolean isPermited() {
		int n = JOptionPane.showConfirmDialog(this,
				"Do you have IEEE's permission to automatically download the files ", "",
				JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
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

	private void selectSaved() {
		Controller controller = guiController.getController();
		int lastColumn = myTableModel.getColumnCount() - 1;
		for (int i = 0; i < currentlyDisplayedDocuments.size(); i++) {
			if (controller.isSaved(currentlyDisplayedDocuments.get(i))) {
				myTableModel.setValueAt(true, i, lastColumn);
			}
		}
	}

}