package studia.articles.bot.searcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import studia.articles.bot.controller.ConnectionException;

public class PDFDownloader extends IeeConnector {

	private final String path;
	private static final int BUFFER_SIZE = 4096;

	private static final Pattern SRC_PATTERN = Pattern
			.compile(".*src=\"(.*)\".*");

	public PDFDownloader(String path, String socksAddress, int socksPort,
			int throughPort) {
		super(socksAddress, socksPort, throughPort);
		this.path = path;
	}

	public PDFDownloader(String path) {
		super();
		this.path = path;
	}

	public boolean fileExists(String fileName) {
		return new File(getAbsolutePath(fileName)).exists();
	}

	public String getAbsolutePath(String fileName) {
		return path + File.separator + fileName + ".pdf";
	}

	public String downloadAndSave(String urlStr, String fileName)
			throws IOException {
		if (fileExists(fileName))
			return getAbsolutePath(fileName);
		FileOutputStream outputStream = null;
		String pdfUrl = extractPdfUrl(urlStr);
		if (pdfUrl == null)
			return null;
		try {
			InputStream inputStream = getInputStream(pdfUrl, true);
			String saveFilePath = getAbsolutePath(fileName);
			outputStream = new FileOutputStream(saveFilePath);
			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			return saveFilePath;
		} finally {
			outputStream.close();
		}
	}

	private String extractPdfUrl(String url) throws ConnectionException {
		InputStream is = getInputStream(url, true);
		if (is == null)
			return null;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("<frame src=\"http")) {

					Matcher matcher = SRC_PATTERN.matcher(line);
					if (matcher.find())
						return matcher.group(1);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new ConnectionException();
	}

}
