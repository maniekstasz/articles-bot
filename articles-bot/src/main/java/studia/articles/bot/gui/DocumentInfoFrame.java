package studia.articles.bot.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import studia.articles.bot.model.Document;

public class DocumentInfoFrame extends JFrame {

	private static final long serialVersionUID = 1995430866542162143L;
	private Document document;

	public DocumentInfoFrame(Document document) {
		this.document = document;
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
		builder.append("<br/><b>Year: </b>");
		builder.append(document.getYear());
		builder.append("<br/><br/> <b>ABSTRACT:</b><br/>");
		builder.append(document.getAbstr());
		builder.append("</html>");
		return builder.toString();
	}

	private void fill() {

		JLabel label = new JLabel(createInfo());
		Font font = label.getFont();
		Font boldFont = new Font(font.getFontName(), Font.PLAIN,
				font.getSize() + 2);
		label.setFont(boldFont);
		Dimension d = label.getPreferredSize();

		d.width = this.getWidth() - 35;
		// d.height=width*height/d.width/2;
		label.setPreferredSize(d);

		label.setVerticalAlignment(JLabel.TOP);
		label.setVerticalTextPosition(JLabel.TOP);

		label.setBackground(Color.WHITE);
		label.setOpaque(true);

		JScrollPane jsp = new JScrollPane(label);
		this.add(jsp);
	}
}
