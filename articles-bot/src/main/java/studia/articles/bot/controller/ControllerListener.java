package studia.articles.bot.controller;

import java.io.IOException;

public interface ControllerListener {
	void onFileDownloadingStart(String title, int total,int currentNumber);
	void onFileException(Exception e);
	void onOtherException(Exception e);
	void onConnectionException(IOException e);
}
