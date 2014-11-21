package studia.articles.bot.controller;

import java.io.IOException;

public class ConnectionException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1808267138541574970L;
	
	public ConnectionException(Exception e) {
		super(e);
	}
	
	public ConnectionException(){
		
	}

}
