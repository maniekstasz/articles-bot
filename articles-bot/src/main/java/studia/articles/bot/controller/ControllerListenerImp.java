package studia.articles.bot.controller;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import studia.articles.bot.gui.DownloadInfoBuilder;

public class ControllerListenerImp implements ControllerListener {

	private JFrame downloadingFrame;
	JTextPane jTextPane;
	DownloadInfoBuilder downloadInfoBuilder;
	int completed = 0;

	@Override
	public void onFileDownloadingStart(String title, int total,
			int currentNumber) {
		if (currentNumber == 0) {

			createFrame();

		}

		downloadingFrame.setTitle("Downloading file " + (currentNumber + 1)
				+ " of " + total);
		downloadInfoBuilder.append(title);
		jTextPane.setText(downloadInfoBuilder.getContent());

	}

	private void createFrame() {

		downloadingFrame = new JFrame();
		downloadingFrame.setSize(700, 300);
		jTextPane = new JTextPane();
		jTextPane.setContentType("text/html");
		JScrollPane jsp = new JScrollPane(jTextPane);
		downloadingFrame.add(jsp);
		downloadingFrame.setVisible(true);
		downloadInfoBuilder = new DownloadInfoBuilder();
		completed = 0;
	}

	@Override
	public void onFileException(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "file error",
				JOptionPane.ERROR_MESSAGE);

	}

	@Override
	public void onOtherException(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "file error",
				JOptionPane.ERROR_MESSAGE);

	}

	@Override
	public void onConnectionException(IOException e) {
		JOptionPane.showMessageDialog(null,
				"A connection failure has occurred.", "Connection error",
				JOptionPane.ERROR_MESSAGE);

	}

	@Override
	public void onNothingFoundException(NothingFoundException e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileDownloadingFinish(boolean success, String title,
			int total, int currentNumber) {
		if (success) {
			completed++;
		}
		downloadInfoBuilder.setLastResult(success, title);
		jTextPane.setText(downloadInfoBuilder.getContent());

		if (currentNumber == (total - 1)) {

			downloadingFrame.setTitle("finished (downloaded: " + completed
					+ " of " + total + ")");
		}
	}

}
