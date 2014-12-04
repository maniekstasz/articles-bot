package studia.articles.bot.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import studia.articles.bot.model.Document;

public class DocumentInfoFrame extends JFrame {

	private static final long serialVersionUID = 1995430866542162143L;
	private Document document;
	private JButton openPDFButton=new JButton("full text");
	private GuiController guiController;
	public DocumentInfoFrame(Document document, GuiController guiController) {
		this.document = document;
		this.guiController = guiController;
		this.setSize(700, 600);
		this.setTitle(document.getTitle());
		fill();
		this.setVisible(true);
	}

	private String createInfo() {

		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<br/><i><b><center>" + getTitle() + "</center></b></i>");
		builder.append("<br/><br/><b>Authors:</b> ");
		builder.append(document.getAuthor());
		builder.append("<br/><br/><b>Journal:</b> ");
		builder.append(document.getPublisher());
		builder.append("<br/><b>Year: </b>");
		builder.append(document.getYear());
		builder.append("<br/><br/> <b>ABSTRACT:</b><br/>");
		builder.append(document.getAbstr());
		builder.append("</html>");
		return builder.toString();
	}

	private void fill() {

		openPDFButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
				    try {
				    	String fileAbsolutePath = guiController.getController().downloadDocument(document);
				    	System.out.println(fileAbsolutePath);
				    	if(fileAbsolutePath == null) return;
				        File myFile = new File(fileAbsolutePath);
				        Desktop.getDesktop().open(myFile);
				    } catch (IOException ex) {
				    	//custom title, error icon
				    	JOptionPane.showMessageDialog(DocumentInfoFrame.this,
				    	    "No application registered for PDFs",
				    	    "",
				    	    JOptionPane.ERROR_MESSAGE);
				    } catch(IllegalArgumentException ex){
				    	JOptionPane.showMessageDialog(DocumentInfoFrame.this,
					    	    "No access to full text",
					    	    "",
					    	    JOptionPane.ERROR_MESSAGE);
				    }
				}
				
			}
		});
		JTextPane infoPane = new JTextPane();
		infoPane.setContentType("text/html");
		infoPane.setText(createInfo());
		
		Font font = infoPane.getFont();
		Font boldFont = new Font(font.getFontName(), Font.PLAIN,
				font.getSize() + 2);
		infoPane.setFont(boldFont);
		Dimension d = infoPane.getPreferredSize();

		d.width = this.getWidth() - 35;
		// d.height=width*height/d.width/2;
		infoPane.setPreferredSize(d);

	

		infoPane.setBackground(Color.WHITE);
		infoPane.setOpaque(true);
		
		JScrollPane jsp = new JScrollPane(infoPane);
		this.setLayout(new BorderLayout());
		this.add(openPDFButton,BorderLayout.SOUTH);
		this.add(jsp,BorderLayout.CENTER);
	}
}
