package studia.articles.bot.controller;

import java.io.IOException;

import javax.swing.JOptionPane;

public class ControllerListenerImp implements ControllerListener {

	@Override
	public void onFileDownloadingStart(String title, int total,
			int currentNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFileException(Exception e) {
		JOptionPane.showMessageDialog(null,
			    e.getMessage(),
			    "file error",
			    JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void onOtherException(Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionException(IOException e) {
		JOptionPane.showMessageDialog(null,
			    "A connection failure has occurred.",
			    "Connection error",
			    JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void onNothingFoundException(NothingFoundException e) {
		// TODO Auto-generated method stub
		
	}

}
