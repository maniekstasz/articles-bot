package studia.articles.bot.gui;

import java.awt.Color;

public class DownloadInfoBuilder {
	
	private final static String SUCCESS_TEXT=" (success)";
	private final static String ERROR_TEXT=" (error)";
	private final static String IN_PROGRESS=" (in progress)";

	private int count=0;
	private StringBuilder builder;

	public DownloadInfoBuilder() {
		builder = new StringBuilder();
		builder.append("<html>");
	}

	public void append(String s) {
		builder.append(++count+". ");
		builder.append(s);
		builder.append(IN_PROGRESS);
		builder.append("<br/><br/>");

	}

	public String getContent() {
		return builder.toString() + "</html>";
	}

	public void setLastResult(boolean success, String title) {
		// TODO Auto-generated method stub
		int length = builder.length();
		int toDelete=title.length()+13+IN_PROGRESS.length()+ new Integer(count).toString().length();;
		builder.delete(length - toDelete, builder.length());
		Color color = success ? Color.GREEN : Color.RED;
		String com=success?SUCCESS_TEXT:ERROR_TEXT;
		String hex = Integer.toHexString(color.getRGB()).substring(2);
		builder.append("<font color=#" + hex + ">" + count+". "+title +com +"</font>");
		builder.append("<br/><br/>");
	}
}
